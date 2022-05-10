package ru.fmcs.hse.database;

public class Comment extends ContentObj {

    public static final String GROUP_ID = "comments";
    public Comment(){}
    public Comment(String authorId, String text) {
        super(authorId, text);
    }
}