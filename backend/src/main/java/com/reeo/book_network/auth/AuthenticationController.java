package com.reeo.book_network.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@Tag(name = "Auth")
public class AuthenticationController {

  @Autowired
  private AuthenticationService authenticationService;

  @Operation(
      security = {},
      summary = "Public endpoint"
  )
  @PostMapping(
      path = "/register"
  )
  ResponseEntity<?> register(
      @RequestBody @Valid UserRegisterRequest request
  ) throws MessagingException {
    authenticationService.register(request);
    return ResponseEntity.accepted().build();
  }

  @Operation(
      security = {},
      summary = "Public endpoint"
  )
  @PostMapping(path = "/activate")
  ResponseEntity<?> activate(
      @RequestParam String token
  ) {
    authenticationService.activateAccount(token);
    return ResponseEntity.ok().build();
  }

  @Operation(
      security = {},
      summary = "Public endpoint"
  )
  @PostMapping(path = "/sign-in")
  @ResponseBody
  AuthenticationResponse signin(
      @RequestBody AuthenticationRequest request
  ) {

    return authenticationService.authenticate(request);
  }

}
