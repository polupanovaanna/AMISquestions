package ru.fmcs.hse.database;

import java.util.ArrayList;
import java.util.List;

public class Post extends ContentObj{

    static String GROUP_ID = "posts";
    private int numberOfComments = 0;
    List<String> listOfCommentsId = new ArrayList<>();
    List<String> tags = new ArrayList<>();
    public Post(){
    }
    public Post(String postId, User author, String text) {
        super(author, text);
        this.id = postId;
    }

    public void increaseNumberOfComments() {
        numberOfComments++;
    }
    public void addComment(Comment comment){
        listOfCommentsId.add(comment.id);
        increaseNumberOfComments();
    }



}
