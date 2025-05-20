package model.dto.admins;

public class UpdateAdminsPasswordDto {
    private int id;
    String salt;
    String passwordHash;

    public UpdateAdminsPasswordDto(int id, String salt, String passwordHash) {
        this.id = id;
        this.salt = salt;
        this.passwordHash = passwordHash;
    }

    public int getId() {
        return id;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
