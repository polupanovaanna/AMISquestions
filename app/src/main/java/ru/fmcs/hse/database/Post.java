package ru.fmcs.hse.database;

import java.util.ArrayList;
import java.util.List;

public class Post extends ContentObj{

    public static String GROUP_ID = "posts";
    private int numberOfComments = 0;
    List<String> listOfCommentsId = new ArrayList<>();
    List<String> tags = new ArrayList<>();
    public Post(){
    }
    public Post(String authorId, String text) {
        super(authorId, text);
    }

    public void increaseNumberOfComments() {
        numberOfComments++;
    }
    public void addComment(String id){
        listOfCommentsId.add(id);
        increaseNumberOfComments();
    }



}
