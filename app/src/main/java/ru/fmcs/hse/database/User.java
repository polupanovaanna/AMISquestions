package ru.fmcs.hse.database;

public class User {

    enum Role{
        Student,
        Teacher,
        Moderator,
        Guest
    }

    static final String GROUP_ID = "users";
    private final String userId;

    private String userName;
    Role userRole = Role.Guest;

    public User(String userId, String name) {
        this.userId = userId;
        this.userName = name;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String name) {
        userName = name; //возможно это нужно будет убрать, если пользователь не настраивает свой ник
    }

}
