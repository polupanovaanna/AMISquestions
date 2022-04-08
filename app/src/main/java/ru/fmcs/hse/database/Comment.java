package ru.fmcs.hse.database;

public class Comment extends ContentObj {

    static final String GROUP_ID = "posts";
    private String postId;

    public Comment(String postId, User author, String text) {
        super(author, text);
        this.postId = postId;
    }

}
