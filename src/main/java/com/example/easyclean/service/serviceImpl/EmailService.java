package com.example.easyclean.service.serviceImpl;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import javax.mail.internet.MimeMessage;


import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.StringUtils;

@Service
public class EmailService {
    @Autowired
    private Environment environment;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private Configuration config;

   // @Value("${mail.from}")
    public String mailFrom;

    private static final Logger L = LogManager.getLogger(EmailService.class);

    public void sendResetMessage(String emailAddress, Map<String, Object> model) throws AddressException, MessagingException {
        //construct subject and body
        L.info(mailFrom+"fimi4life@gmail.com");
        String subject = "Password Reset For Cicod Support";

        MimeMessage message = mailSender.createMimeMessage();
        try {

            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            Template t = config.getTemplate("resetpassword.ftl");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
            helper.setTo(new InternetAddress(StringUtils.trimAllWhitespace(emailAddress)).getAddress());
            helper.setFrom(new InternetAddress(StringUtils.trimAllWhitespace(mailFrom)).getAddress());
            helper.setText(html, true);
            helper.setSubject(subject);
            mailSender.send(message);

        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
        }

    }

    public void sendRegisterMessage(String emailAddress, Map<String, Object> model)  throws AddressException, MessagingException {
        //construct subject and body
        L.info(mailFrom+"mail sending");
        String subject = "User Creation  ";
        System.out.println(subject);
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            Template t = config.getTemplate("register.ftl");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
            helper.setTo(new InternetAddress(StringUtils.trimAllWhitespace(emailAddress)).getAddress());
            helper.setFrom(environment.getProperty(""));
            helper.setText(html, true);
            helper.setSubject(subject);
            mailSender.send(message);

        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
        }

    }



}
