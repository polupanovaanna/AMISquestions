package ru.fmcs.hse.database;

import java.util.ArrayList;
import java.util.List;

public class Post extends ContentObj{

    static final String GROUP_ID = "posts";
    private String postId;

    private int numberOfComments = 0;
    List<String> listOfCommentsId = new ArrayList<>();
    List<String> tags = new ArrayList<>();

    public Post(String postId, User author, String text) {
        super(author, text);
        this.postId = postId;
    }

    public void increaseNumberOfComments() {
        numberOfComments++;
    }

    public void addComment() {
         //TODO for the firebase master
    }



}
