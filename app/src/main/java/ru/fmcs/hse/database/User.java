package ru.fmcs.hse.database;

public class User {
    public String name;
    public String email;
    public String photoUri;
    public Role role = Role.Guest;
    enum Role{
        Student,
        Teacher,
        Moderator,
        Guest
    }
    static String GROUP_ID = "users";
    public User(){}
    public User(String name_, String email_, String photoUri_){
        name = name_;
        email = email_;
        photoUri = photoUri_;
    }
}
