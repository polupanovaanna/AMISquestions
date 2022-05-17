package ru.fmcs.hse.database;

public class User {
    public String name;
    public String email;
    public String photo;
    public Role role = Role.Guest;
    enum Role{
        Student,
        Teacher,
        Moderator,
        Guest
    }
    static String GROUP_ID = "users";
}
