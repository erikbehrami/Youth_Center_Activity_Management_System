package model.dto.contactMessage;

import java.util.Date;

public class CreateContactMessageDto {
    private String name;
    private String email;
    private String message;
    private Date sentAt;

    public CreateContactMessageDto(String name, String email, String message, Date sentAt) {
        this.name = name;
        this.email = email;
        this.message = message;
        this.sentAt = sentAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getSentAt() { return sentAt; }

    public void setSentAt(Date sentAt) { this.sentAt = sentAt; }

}
