package ru.fmcs.hse.database;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Controller {
    private static final FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    ;
    private static final StorageReference ImageStorage = FirebaseStorage.getInstance().getReference();

    public Controller() {
    }


    public static String addUser(User user, String key) {
        DatabaseReference ref = mDatabase.getReference(User.GROUP_ID);
        ref.child(key).setValue(user);
        return key;
    }

    //upload photo and gets Url of photo
    public String uploadPhoto(String id, String path, ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] pic = baos.toByteArray();
        StorageReference nPath = ImageStorage.child(path).child(id + ".jpg");
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

        Task<Uri> uri = nPath.getDownloadUrl();
        return uri.getResult().toString();
    }

    public static void displayProfilePhotoAndRole(String userId, Fragment fragment, ImageView view, TextView roleText) {
        userId = "-N2I-AbRer2HG9LsPMOi";//TODO remove
        mDatabase.getReference(User.GROUP_ID).child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User u = snapshot.getValue(User.class);
                if (u != null) {
                    Glide.with(fragment).load(u.photoUri).into(view);
                    roleText.setText(u.role.name());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public static void addPost(@NotNull String text, String userId) {
        DatabaseReference ref = mDatabase.getReference(Post.GROUP_ID);
        Post nPost = new Post(userId, text);
        ref.push().setValue(nPost);
    }

    private static void updatePost(String postKey, Post Post) {
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
                if (p == null) {
                    return;
                }
                p.increaseNumberOfComments();
                updatePost(postKey, p);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
