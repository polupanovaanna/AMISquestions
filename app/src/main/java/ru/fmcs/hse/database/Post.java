package ru.fmcs.hse.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ru.fmcs.hse.database.ContentObj;

public class Post extends ContentObj{

    public static String GROUP_ID = "posts";
    public int numberOfComments = 0;
    public List<String> listOfCommentsId = new ArrayList<>();
    public Map<String, String> tags = new HashMap<>();
    public List<String> photoUrl = new ArrayList<>();
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
