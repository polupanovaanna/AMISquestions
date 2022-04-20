package ru.fmcs.hse.database;


public class Ordering {
    public static final DatabaseOrdering DEFAULT = (ref)->ref;
    public static final DatabaseOrdering VIEWS_REVERSED = (ref)->ref.orderByChild("numberOfViews");
    public static final DatabaseOrdering AUTHOR_NAME = (ref)->ref.orderByChild("author");
}
