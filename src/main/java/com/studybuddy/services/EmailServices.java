
package com.studybuddy.services;

import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMessage.RecipientType;

public class EmailServices {

    public static Boolean sendOTP(String to, String userName, int OTP) {

        String FROM = System.getenv("STUDY_BUDDY_EMAIL");
        String PASSWORD = System.getenv("STUDY_BUDDY_EMAIL_PASSWORD");

        // Check environment variables without exposing credentials
        System.out.println("Email configured: "
                + (FROM != null && !FROM.isBlank()));

        System.out.println("Password configured: "
                + (PASSWORD != null && !PASSWORD.isBlank()));

        System.out.println("Password length: "
                + (PASSWORD == null ? 0 : PASSWORD.length()));

        System.out.println("Password contains spaces: "
                + (PASSWORD != null && PASSWORD.contains(" ")));

        // Stop if credentials are missing
        if (FROM == null || FROM.isBlank()
                || PASSWORD == null || PASSWORD.isBlank()) {

            System.out.println("Email credentials are missing.");
            return false;
        }

        Properties emailProperties = new Properties();

        emailProperties.put("mail.smtp.host", "smtp.gmail.com");
        emailProperties.put("mail.smtp.port", "587");
        emailProperties.put("mail.smtp.auth", "true");
        emailProperties.put("mail.smtp.starttls.enable", "true");

        Authenticator emailAuth = new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM, PASSWORD);
            }
        };

        Session emailSession =
                Session.getInstance(emailProperties, emailAuth);

        try {

            Message msg = new MimeMessage(emailSession);

            msg.setFrom(new InternetAddress(FROM));
            msg.setRecipients(
                    RecipientType.TO,
                    InternetAddress.parse(to)
            );

            msg.setSubject("Account Creation Success.");

            msg.setText(
                    "Hello, " + userName + "\n\n"
                    + "Your account has been created successfully.\n"
                    + "Please use this One Time Password (OTP): " + OTP + "\n"
                    + "If you did not request this OTP, kindly ignore this mail.\n\n"
                    + "Thanks & Regards\n"
                    + "Team Sec C"
            );

            System.out.println("Sending OTP email...");

            Transport.send(msg);

            System.out.println("OTP email sent successfully.");

            return true;

        } catch (Exception e) {

            System.out.println("OTP email sending failed.");
            e.printStackTrace();

            return false;
        }
    }
}
