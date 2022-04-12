package ru.fmcs.hse.database;

import java.util.Date;

public class ContentObj {

    protected String author = null;
    protected String text = null;
    protected int numberOfViews = 0;
    protected Date timeCreated = null;
    static String GROUP_ID;

    //Список объектов типа: файл
    protected ContentObj(){
    }
    protected ContentObj(String authorId, String text) {
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

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public int getNumberOfViews() {
        return numberOfViews;
    }

}
