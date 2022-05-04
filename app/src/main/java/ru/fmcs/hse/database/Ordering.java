package ru.fmcs.hse.database;


public class Ordering {
    public static final DatabaseOrdering DEFAULT = (ref) -> ref;
    public static final DatabaseFiltering DEFAULT_FILTER = (ref, str)->ref;
    public static class PostOrdering {
        public static final DatabaseOrdering VIEWS_REVERSED = (ref) -> ref.orderByChild("numberOfViews");
        public static final DatabaseOrdering AUTHOR_NAME = (ref) -> ref.orderByChild("author");
        public static final DatabaseOrdering COMMENTS_REVERSED = (ref) -> ref.orderByChild("numberOfComments");
        public static final DatabaseFiltering AUTHOR_NAME_FILTERING = (ref, str) -> ref.orderByChild("author").equalTo(str);
    }

    public static class UserOrdering{
        //TODO
    }

    public static class CommentsOrdering{
        //TODO
    }
    public static DatabaseGlobalOrdering map(DatabaseFiltering filter, DatabaseOrdering order, String str){
        return (ref)->order.getQuery(filter.filter(ref, str));
    }
}
