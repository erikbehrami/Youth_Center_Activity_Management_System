package model;

import java.util.Date;

public interface User {
    int getId();
    String getUsername();
    String getName();
    String getSurname();
    String getEmail();
    Date getBirthdate();
    String getPhoneNumber();
    String getAddress();
    String getGender();
}
