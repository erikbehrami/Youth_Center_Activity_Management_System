package services;

import model.dto.contactMessage.CreateContactMessageDto;
import repository.ContactMessagesRepository;
import utils.EmailSender;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.sql.Timestamp;

public class ContactService {

    private final ContactMessagesRepository contactMessagesRepository = new ContactMessagesRepository();
    private static final String RECEIVER_EMAIL = "youthcentersystem@gmail.com";

    public void sendAndStoreMessage(String name, String email, String message) throws Exception {
        storeMessageInDatabase(name, email, message);
        sendEmail(name, email, message);
    }

    private void storeMessageInDatabase(String name, String email, String message) {
        CreateContactMessageDto createContactMessageDto = new CreateContactMessageDto(name, email, message, new Timestamp(System.currentTimeMillis()));
        contactMessagesRepository.create(createContactMessageDto);
    }

    private void sendEmail(String name, String email, String message) throws MessagingException {
        String subject = "New Contact Message from " + name;
        String body = "From: " + name + "\nEmail: " + email + "\nMessage: " + message;

        MimeMessage mimeMessage = EmailSender.createMimeMessage(RECEIVER_EMAIL, subject, body);
        mimeMessage.setReplyTo(new InternetAddress[]{new InternetAddress(email)});
        EmailSender.sendMimeMessage(mimeMessage);
    }
}