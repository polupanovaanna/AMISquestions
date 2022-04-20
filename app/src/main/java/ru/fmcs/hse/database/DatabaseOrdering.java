package ru.fmcs.hse.database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

@FunctionalInterface
public interface DatabaseOrdering {
    Query getQuery(DatabaseReference ref);
}

