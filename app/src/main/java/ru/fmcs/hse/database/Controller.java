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
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Deprecated
public class Controller {
    private final FirebaseDatabase mDatabase;
    public Controller() {
        mDatabase = FirebaseDatabase.getInstance();
    }

    public void addUser(String name) {
        DatabaseReference ref = mDatabase.getReference(User.GROUP_ID);
        ref.child(name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User checked = snapshot.getValue(User.class);
                if (checked == null) {
                    String nId = ref.getKey();
                    User nUser = new User(name);
                    nUser.setUserId(nId);
                    ref.setValue(nUser);
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
        DatabaseReference ref = mDatabase.getReference(User.GROUP_ID);
        ref.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
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

    public void addPost(@NotNull String text, String userId) {
        DatabaseReference ref = mDatabase.getReference(Post.GROUP_ID);
        Post nPost = new Post(userId, text);
        ref.push().setValue(nPost);
    }

    public void getAllUsers(/*TODO something like*/TextView result) {
        DatabaseReference ref = mDatabase.getReference(User.GROUP_ID);
        ref.addValueEventListener(new ValueEventListener() {
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
        DatabaseReference ref = mDatabase.getReference(Post.GROUP_ID);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getChildrenCount() > 0) {
                    res.setText(snapshot.getChildren().iterator().next().getValue(Post.class).getText());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void addComment(String postKey, Comment comment) {
        DatabaseReference ref = mDatabase.getReference(Comment.GROUP_ID).child(postKey);
        ref.push().setValue(comment);
    }

    public void getComments(Post post /*, view*/) {
//        DatabaseReference ref = mDatabase.getReference(Post.GROUP_ID);
//        ref.child(post.getId()).child(Comment.GROUP_ID).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    //do something with dataSnapshot.getValue(Comment.class)
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }

    public void getPosts(ArrayList<Post> holder) {
        DatabaseReference ref = mDatabase.getReference(Post.GROUP_ID);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getChildrenCount() > 0) {
                    holder.add(snapshot.getChildren().iterator().next().getValue(Post.class));
                    System.out.println(snapshot.getChildren().iterator().next().getValue(Post.class));
                    //{author=serega, ...}
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
