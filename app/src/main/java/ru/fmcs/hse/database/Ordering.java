package ru.fmcs.hse.database;


import com.google.firebase.database.DatabaseReference;

public class Ordering {
    public static final DatabaseOrdering DEFAULT = (ref) -> ref;
    public static final DatabaseFiltering DEFAULT_FILTER = (ref, str)->ref;
    public static class PostOrdering {
        public static final DatabaseOrdering VIEWS_REVERSED = (ref) -> ref.orderByChild("numberOfViews");
        public static final DatabaseOrdering AUTHOR_NAME = (ref) -> ref.orderByChild("author");
        public static final DatabaseOrdering COMMENTS_REVERSED = (ref) -> ref.orderByChild("numberOfComments");
        public static final DatabaseFiltering AUTHOR_NAME_FILTERING = (ref, str) -> ref.orderByChild("author").equalTo(str);
        public static final DatabaseFiltering TAG_FILTER_REVERSED = (ref, tag) -> ref.orderByChild("tags/"+tag);
    }


    public static class CommentsOrdering{
        public static final DatabaseFiltering POST_COMMENTS = DatabaseReference::child;
    }
    public static DatabaseGlobalOrdering map(DatabaseFiltering filter, DatabaseOrdering order, String str){
        return (ref)->order.getQuery(filter.filter(ref, str));
    }
}
