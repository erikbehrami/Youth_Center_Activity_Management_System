package services;

import model.User;

public class SessionManager {
    private static SessionManager instance;
    private User user;

    private SessionManager() {}

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void login(User user) {
        this.user = user;
    }

    public void setCurrentUser(User user) {
        this.user = user;
    }

    public User currentUser() {
        return this.user;
    }

    public boolean isAdmin() {
        return user instanceof model.Admins;
    }

    public boolean isProfessor() {
        return user instanceof model.Professors;
    }

    public boolean isStudent() {
        return user instanceof model.Students;
    }
}