package com.rsa.webapi.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.awt.*;
import java.io.File;
import java.io.IOException;

@Component
public class EmailUtil {

    private final static Logger log = LogManager.getLogger(EmailUtil.class);

    @Resource
    private JavaMailSender mailSender;
    @Resource
    private MailProperties mailProperties;

    public void sendEmail(String email, String title, String count) {
        sendEmail(email, title, count, null);
    }

    public void sendEmail(String email, String title, String count, File... files) {
        MimeMessage msg = mailSender.createMimeMessage();

        try {
            MimeMessageHelper h = new MimeMessageHelper(msg,true);
            h.setFrom(mailProperties.getUsername());
            h.addTo(email);
            //h.addCc(mailProperties.getUsername());
            h.setSubject(title);
            h.setText(count,true);
            if (files != null && files.length > 0) {
                for (File f : files) {
                    //如果是图片，单独上传显示
                    if (isImage(f)) {
                        h.addInline("ei",f);
                    }
                    //图片也单独再上传为附件
                    h.addAttachment(f.getName(), f);
                }
            }
            mailSender.send(msg);
        } catch (MessagingException e) {
            log.error(e);
        }
    }

    //判断文件是否是图片类型
    private boolean isImage(File file) {
        try {
            Image image = ImageIO.read(file);
            return image != null;
        } catch (IOException e) {
            return false;
        }
    }
}
