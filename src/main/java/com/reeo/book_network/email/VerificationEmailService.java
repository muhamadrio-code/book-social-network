package com.reeo.book_network.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.mail.javamail.MimeMessageHelper.MULTIPART_MODE_MIXED;

@Service
@RequiredArgsConstructor
public class VerificationEmailService {

  private final JavaMailSender mailSender;
  private final TemplateEngine templateEngine;

  @Value("${application.mailing.from}")
  private String from;


  @Async
  public void sendMail(ActivateAccountMailRequest request) throws MessagingException {
    final MimeMessage mimeMessage = mailSender.createMimeMessage();
    final MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(
        mimeMessage,
        MULTIPART_MODE_MIXED,
        UTF_8.name()
    );
    mimeMessageHelper.setFrom(from);
    mimeMessageHelper.setTo(request.getTo());
    mimeMessageHelper.setSubject(request.getSubject());

    final Context context = new Context();
    final Map<String, Object> variables = new HashMap<>();
    variables.put("username", request.getUsername());
    variables.put("confirmation_url", request.getConfirmationUrl());
    variables.put("activation_code", request.getActivationCode());

    context.setVariables(variables);

    final String templateName = "activate_account";
    final String processedString = templateEngine.process(templateName, context);
    mimeMessageHelper.setText(processedString, true);

    mailSender.send(mimeMessage);
  }

}
