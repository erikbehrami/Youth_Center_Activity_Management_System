package utils;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class EmailSender {

    private static final String FROM_EMAIL;
    private static final String PASSWORD;
    private static final Properties props;
    private static final Session session;

    static {
        props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        try {
            Properties config = new Properties();
            config.load(new FileInputStream("config.properties"));
            FROM_EMAIL = config.getProperty("email");
            PASSWORD = config.getProperty("password");
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties: " + e.getMessage());
        }

        session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, PASSWORD);
            }
        });
    }

    public static void sendEmail(String toEmail, String subject, String body) throws MessagingException {
        MimeMessage message = createMimeMessage(toEmail, subject, body);
        sendMimeMessage(message);
    }

    public static MimeMessage createMimeMessage(String toEmail, String subject, String body) throws MessagingException {
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(FROM_EMAIL));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        message.setSubject(subject);
        message.setText(body);
        return message;
    }

    public static void sendMimeMessage(MimeMessage message) throws MessagingException {
        Transport.send(message);
        System.out.println("Email sent successfully via Gmail!");
    }
}