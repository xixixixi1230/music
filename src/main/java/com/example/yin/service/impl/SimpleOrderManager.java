package com.example.yin.service.impl;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import com.example.yin.model.domain.Order;
import com.example.yin.service.OrderManager;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;



import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;


/**
 * 邮箱信息的发送
 */

@Service
public class SimpleOrderManager implements OrderManager {


        public void sendEmail(String to, String subject, String body) {
            // 邮件服务器配置
            String host = "smtp.163.com";
            final String user = "15754725735@163.com"; // 发件人邮箱
            final String password = "TOHWQBAYUKRBOURY"; // 发件人密码

            // 配置属性
            Properties props = new Properties();
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "25"); // 使用587端口
            props.put("mail.smtp.starttls.enable", "true"); // 启用TLS

            // 获取默认session对象
            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(user, password);
                }
            });

            try {
                // 创建邮件对象
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(user));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
                message.setSubject(subject);
                message.setText(body);

                // 发送邮件
                Transport.send(message);

                System.out.println("Mail sent successfully!");

            } catch (MessagingException e) {
                e.printStackTrace();
            }
        };

        public void sendCode(String code, String receiveAddress) {
            // 使用 sendEmail 方法发送验证码邮件
            String subject = "Your Verification Code";
            String body = "Dear user, your verification code is " + code;

            sendEmail(receiveAddress, subject, body);
        }
}