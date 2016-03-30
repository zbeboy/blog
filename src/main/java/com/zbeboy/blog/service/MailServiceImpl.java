package com.zbeboy.blog.service;

import com.zbeboy.blog.books.WorkBook;
import com.zbeboy.blog.domain.entity.UsersEntity;
import org.apache.commons.lang.CharEncoding;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/29.
 */
@Service("mailService")
public class MailServiceImpl implements MailService {

    private final Logger log = LoggerFactory.getLogger(MailService.class);

    @Resource
    private JavaMailSenderImpl javaMailSender;

    @Resource
    private MessageSource messageSource;

    @Resource
    private VelocityEngine velocityEngine;

    @Async
    @Override
    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        log.debug("Send e-mail[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
                isMultipart, isHtml, to, subject, content);

        // Prepare message using a Spring helper
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, CharEncoding.UTF_8);
            message.setTo(to);
            message.setFrom(WorkBook.getMail_form());
            message.setSubject(subject);
            message.setText(content, isHtml);
            javaMailSender.send(mimeMessage);
            log.debug("Sent e-mail to User '{}'", to);
        } catch (Exception e) {
            log.warn("E-mail could not be sent to user '{}', exception is: {}", to, e.getMessage());
        }
    }

    @Async
    @Override
    public void sendActivationEmail(UsersEntity usersEntity, String baseUrl) {
        log.debug("Sending activation e-mail to '{}'", usersEntity.getUsername());
        Locale locale = Locale.forLanguageTag(usersEntity.getLangKey());
        Map<String, Object> emailMap = new HashMap<>();
        emailMap.put("user", usersEntity);
        emailMap.put("baseUrl", baseUrl);
        emailMap.put("title", messageSource.getMessage("email.activation.title", null, locale));
        emailMap.put("greeting", messageSource.getMessage("email.activation.greeting" , null, locale));
        emailMap.put("text1",messageSource.getMessage("email.activation.text1" , null, locale));
        emailMap.put("text2",messageSource.getMessage("email.activation.text2" , null, locale));
        emailMap.put("signature",messageSource.getMessage("email.signature" , null, locale));
        sendEmail(usersEntity.getUsername(), messageSource.getMessage("email.activation.title", null, locale),
                VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "/mails/activationEmail.vm", CharEncoding.UTF_8, emailMap), false, true);
    }

    @Async
    @Override
    public void sendCreationEmail(UsersEntity usersEntity, String baseUrl) {
        log.debug("Sending creation e-mail to '{}'", usersEntity.getUsername());
        Locale locale = Locale.forLanguageTag(usersEntity.getLangKey());
        Map<String, Object> emailMap = new HashMap<>();
        emailMap.put("user", usersEntity);
        emailMap.put("baseUrl", baseUrl);
        emailMap.put("title", messageSource.getMessage("email.creation.title", null, locale));
        emailMap.put("greeting", messageSource.getMessage("email.activation.greeting" , null, locale));
        emailMap.put("text1",messageSource.getMessage("email.creation.text1" , null, locale));
        emailMap.put("text2",messageSource.getMessage("email.activation.text2" , null, locale));
        emailMap.put("signature",messageSource.getMessage("email.signature" , null, locale));
        sendEmail(usersEntity.getUsername(), messageSource.getMessage("email.creation.title", null, locale),
                VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "/mails/creationEmail.vm", CharEncoding.UTF_8, emailMap), false, true);

    }

    @Async
    @Override
    public void sendPasswordResetMail(UsersEntity usersEntity, String baseUrl) {
        log.debug("Sending password reset e-mail to '{}'", usersEntity.getUsername());
        Locale locale = Locale.forLanguageTag(usersEntity.getLangKey());
        Map<String, Object> emailMap = new HashMap<>();
        emailMap.put("user", usersEntity);
        emailMap.put("baseUrl", baseUrl);
        emailMap.put("title", messageSource.getMessage("email.reset.title", null, locale));
        emailMap.put("greeting", messageSource.getMessage("email.reset.greeting" , null, locale));
        emailMap.put("text1",messageSource.getMessage("email.reset.text1" , null, locale));
        emailMap.put("text2",messageSource.getMessage("email.reset.text2" , null, locale));
        emailMap.put("signature",messageSource.getMessage("email.signature" , null, locale));
        sendEmail(usersEntity.getUsername(), messageSource.getMessage("email.reset.title", null, locale),
                VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "/mails/passwordResetEmail.vm", CharEncoding.UTF_8, emailMap), false, true);
    }
}
