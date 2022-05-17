package ru.fmcs.hse.database;

import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
//import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Controller {
    private static final FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();;
    public Controller() {
    }


    public static String addUser(User user){
        DatabaseReference ref = mDatabase.getReference(User.GROUP_ID);
        String key = ref.push().getKey();
        ref.child(key).setValue(user);
        return key;
    }

    public static void addPost(@NotNull String text, String userId) {
        DatabaseReference ref = mDatabase.getReference(Post.GROUP_ID);
        Post nPost = new Post(userId, text);
        ref.push().setValue(nPost);
    }

    private static void updatePost(String postKey, Post Post){
        HashMap<String, Object> update = new HashMap<>();
        update.put(postKey, Post);
        mDatabase.getReference(Post.GROUP_ID).updateChildren(update);
    }

    public static void addComment(String postKey, Comment comment) {
        DatabaseReference ref = mDatabase.getReference(Comment.GROUP_ID).child(postKey);
        ref.push().setValue(comment);
        mDatabase.getReference(Post.GROUP_ID).child(postKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Post p = snapshot.getValue(Post.class);
                if(p == null){
                    return;
                }
                p.increaseNumberOfComments();
                updatePost(postKey, p);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}
