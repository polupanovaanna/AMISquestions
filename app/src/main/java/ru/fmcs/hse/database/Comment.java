package ru.fmcs.hse.database;

public class Comment extends ContentObj {

    static final String GROUP_ID = "posts";

    public Comment(String authorId, String text) {
        super(authorId, text);
    }
}
