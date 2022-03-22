package ru.fmcs.hse.database;

public class User {
    static final String GROUP_ID = "users";
    private String userId;

    public User(String userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    private String name;
    enum Role{
        Student,
        Teacher,
        Moderator,
        Guest
    }
}
