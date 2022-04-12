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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Controller {
    private final DatabaseReference mDatabase;

    public Controller(DatabaseReference mDatabase) {
        this.mDatabase = mDatabase;
    }

    public void addUser(String name) {
        mDatabase.child(User.GROUP_ID).child(name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User checked = snapshot.getValue(User.class);
                if (checked == null) {
                    String nId = mDatabase.getKey();
                    User nUser = new User(name);
                    nUser.setUserId(nId);
                    mDatabase.setValue(nUser);
                } else {
                    //TODO say to view that user exists
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getUserById(String userId/*TODO argument to se view*/) {
        mDatabase.child(User.GROUP_ID).child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue(User.class) != null) {
                    //view.doSomething(snapshot.getValue(User.class))
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw new IllegalArgumentException("No such user");
            }
        });
    }

    public void addPost(@NotNull String text, User user) {
        String id = mDatabase.child(Post.GROUP_ID).getKey();
        Post nPost = new Post(id, user, text);
        mDatabase.child(Post.GROUP_ID).setValue(nPost);
    }

    public void getAllUsers(/*TODO something like*/TextView result) {
        mDatabase.child(User.GROUP_ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    if (snap.getValue(User.class) != null) {
                        result.setText(snap.getValue(User.class).getUserName());
                        //view actions
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    //Example
    public void getOnePost(TextView res) {
        mDatabase.child(Post.GROUP_ID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue(Post.class) != null) {
                    res.setText(snapshot.getValue(Post.class).getText());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void addComment(Post post, Comment comment) {
        String key = mDatabase.child(Post.GROUP_ID).child(post.getId()).child(Comment.GROUP_ID).getKey();
        comment.setId(key);
        mDatabase.child(Comment.GROUP_ID).setValue(comment);
    }

    public void getComments(Post post /*, view*/) {
        mDatabase.child(Post.GROUP_ID).child(Comment.GROUP_ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    //do something with dataSnapshot.getValue(Comment.class)
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    void push() {
        mDatabase.push();
    }
}
