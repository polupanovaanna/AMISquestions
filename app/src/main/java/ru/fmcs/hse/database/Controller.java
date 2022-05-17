package ru.fmcs.hse.database;

import android.app.Activity;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
//import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Controller {
    private static final FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();;
    private static final StorageReference ImageStorage = FirebaseStorage.getInstance().getReference();
    public Controller() {
    }


    public static String addUser(User user){
        DatabaseReference ref = mDatabase.getReference(User.GROUP_ID);
        String key = ref.push().getKey();
        ref.child(key).setValue(user);
        return key;
    }

    public void addPhoto(String id, byte[] pic){
        StorageReference nPath = ImageStorage.child(id+".jpg");
        UploadTask uploadTask = nPath.putBytes(pic);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
            }
        });
    }

    public static void loadImage(Activity activity, ImageView view, String userId){
        Glide.with(activity)
                .load(ImageStorage.child(userId+".jpg"))
                .into(view);
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
