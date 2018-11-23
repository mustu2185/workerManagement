package com.anahad.projectmanagement;

public class Session {
    private static final Session ourInstance = new Session();

    private User user;

    public static Session getInstance() {
        return ourInstance;
    }

    private Session() {
        user = null;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
