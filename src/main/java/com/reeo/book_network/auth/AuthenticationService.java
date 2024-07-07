package com.reeo.book_network.auth;


import com.reeo.book_network.email.ActivateAccountMailRequest;
import com.reeo.book_network.email.VerificationEmailService;
import com.reeo.book_network.exception.TokenExpiredException;
import com.reeo.book_network.exception.TokenNotFoundException;
import com.reeo.book_network.role.Role;
import com.reeo.book_network.role.RoleRepository;
import com.reeo.book_network.security.JwtService;
import com.reeo.book_network.token.Token;
import com.reeo.book_network.token.TokenRepository;
import com.reeo.book_network.user.User;
import com.reeo.book_network.user.UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
public class AuthenticationService {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private RoleRepository roleRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private VerificationEmailService verificationEmailService;
  @Autowired
  private TokenRepository tokenRepository;
  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtService jwtService;

  @Value("${application.mailing.frontend.confirmation-url}")
  private String mConfirmationUrl;

  public void register(UserRegisterRequest request) throws MessagingException {
    final Role role = roleRepository.findByName("USER").orElseThrow(() ->
        new IllegalStateException("No role matches USER")
    );

    final User user = User.builder()
        .email(request.getEmail())
        .firstName(request.getFirstname())
        .lastName(request.getLastname())
        .password(passwordEncoder.encode(request.getPassword()))
        .roles(List.of(role))
        .enabled(false)
        .accountLocked(false)
        .build();

    userRepository.save(user);
    sendVerificationEmail(user);
  }

  @Transactional
  public void activateAccount(String token) throws TokenNotFoundException {
    Token validatedToken = tokenRepository.findByToken(token)
        .orElseThrow(TokenNotFoundException::new);

    boolean isExpired = validatedToken.getExpiredAt().isBefore(LocalDateTime.now());
    if (isExpired) throw new TokenExpiredException();

    User user = userRepository
        .findByEmail(validatedToken.getUser().getEmail())
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    user.setEnable();
    userRepository.save(user);
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    final Authentication auth = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword()
        )
    );

    final HashMap<String, Object> claims = new HashMap<>();
    final User user = (User) auth.getPrincipal();
    claims.put("fullname", user.getFullname());

    String jwtToken = jwtService.generateToken(claims, user);
    return AuthenticationResponse.builder().token(jwtToken).build();
  }

  private void sendVerificationEmail(User user) throws MessagingException {
    final String activationCode = createAndSaveActivationCode(user);
    final String confirmationUrl = mConfirmationUrl + activationCode;
    final ActivateAccountMailRequest mailRequest = ActivateAccountMailRequest.builder()
        .to(user.getEmail())
        .subject("Activation Email")
        .activationCode(activationCode)
        .confirmationUrl(confirmationUrl)
        .build();

    verificationEmailService.sendMail(mailRequest);
  }

  private String createAndSaveActivationCode(User user) {
    String activationCode = createActivationCode();
    LocalDateTime createdTime = LocalDateTime.now();

    Token token = Token.builder()
        .user(user)
        .token(activationCode)
        .createAt(createdTime)
        .expiredAt(createdTime.plusMinutes(15))
        .build();
    tokenRepository.save(token);
    return activationCode;
  }

  private String createActivationCode() {
    final int VERIFICATION_CODE_LENGTH = 6;
    final char[] charArray = "0123456789".toCharArray();
    final int charArrayLength = charArray.length;
    final SecureRandom secureRandom = new SecureRandom();
    final StringBuilder stringBuilder = new StringBuilder();

    for (int i = 0; i < VERIFICATION_CODE_LENGTH; i++) {
      int randomIndex = secureRandom.nextInt(charArrayLength);
      stringBuilder.append(charArray[randomIndex]);
    }

    return stringBuilder.toString();
  }

}
