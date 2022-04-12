package ru.fmcs.hse.database;

public class Comment extends ContentObj {

    static final String GROUP_ID = "posts";

    public Comment(User author, String text) {
        super(author, text);
    }

}
