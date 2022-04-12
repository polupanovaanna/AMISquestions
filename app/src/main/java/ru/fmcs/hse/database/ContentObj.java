package ru.fmcs.hse.database;

import java.util.Date;

public class ContentObj {

    protected User author = null;
    protected String text = null;
    protected int numberOfViews = 0;
    protected Date timeCreated = null;
    static String GROUP_ID;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    protected String id;
    //Список объектов типа: файл
    protected ContentObj(){
    }
    protected ContentObj(User author, String text) {
        this.author = author;
        setText(text);
        timeCreated = new Date();
    }

    public void increaseNumberOfViews() {
        numberOfViews++;
    }

    public void setText(String newText) {
        text = newText;
    }

    public String getText() {
        return text;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getAuthorName() {
        return author.getUserName();
    }

    public int getNumberOfViews() {
        return numberOfViews;
    }

}
