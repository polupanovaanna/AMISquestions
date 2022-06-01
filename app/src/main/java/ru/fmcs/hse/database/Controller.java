package ru.fmcs.hse.database;

import static com.google.firebase.database.Transaction.abort;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
//import com.google.firebase.firestore.auth.User;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

import ru.fmcs.hse.amisquestions.TagsList;

public class Controller {
    private static final FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();

    private static final StorageReference ImageStorage = FirebaseStorage.getInstance().getReference();

    public Controller() {
    }

    public static void getAndUpdateUserField(String key, Field f, Object newValue) {
        f.setAccessible(true);
        DatabaseReference ref = mDatabase.getReference(User.GROUP_ID).child(key);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User u = snapshot.getValue(User.class);
                try {
                    f.set(u, newValue);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                ref.setValue(u);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public static String addUser(User user, String key) {
        DatabaseReference ref = mDatabase.getReference(User.GROUP_ID);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.hasChild(key)) {
                    ref.child(key).setValue(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return key;
    }

    //upload photo and gets Url of photo
    public String uploadPhoto(String id, String path, ImageView image) {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
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

    public static void displayProfilePhotoAndRole(@NotNull String userId, Fragment fragment, ImageView view, TextView roleText) {
        getUserAndApply(userId, (u) -> {
            Glide.with(fragment).load(u.photoUri).into(view);
            roleText.setText(u.role.name());
        });

    }

    public static void displayProfilePhoto(@NotNull String userId, Activity activity, ImageView view) {
        getUserAndApply(userId, (u) -> {
            Glide.with(activity).load(u.photoUri).into(view);
        });

    }

    public static void displayProfilePhotoAndRole(@NotNull String userId, Activity activity, ImageView view, TextView roleText) {
        getUserAndApply(userId, (u) -> {
            Glide.with(activity).load(u.photoUri).into(view);
            roleText.setText(u.role.name());
        });
    }


    public static void getUserAndApply(@NotNull String id, Consumer<User> func){
        getSomethingAndApply(id, func, User.class);
    }

    public static void getSomethingAndApply(@NonNull String id, Consumer func, Class<?> somethingClass) {
        try {
            mDatabase.getReference(somethingClass.getField("GROUP_ID").get(null).toString()).child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Object u = snapshot.getValue(somethingClass);
                    if (u != null) {
                        func.accept(u);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } catch (NoSuchFieldException e) {
            System.err.println("Error no group id");
            System.err.println(somethingClass);
        } catch (IllegalAccessException e) {
            System.err.println("Access denied");
        }
    }

    public static String addPost(@NotNull String text, String userId) {
        DatabaseReference ref = mDatabase.getReference(Post.GROUP_ID);
        Post nPost = new Post(userId, text);
        String key = ref.push().getKey();
        ref.child(key).setValue(nPost);
        return key;
    }

    private static void updatePost(String postKey, Post Post) {
        HashMap<String, Object> update = new HashMap<>();
        update.put(postKey, Post);
        mDatabase.getReference(Post.GROUP_ID).updateChildren(update);
    }

    public static void addTag(String postId, String tag) {
        DatabaseReference ref = mDatabase.getReference(Post.GROUP_ID).child(postId);
        ref.runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                Post p = currentData.getValue(Post.class);
                if(p == null){
                    return abort();
                }
                p.tags.add(tag);
                currentData.setValue(p);
                return Transaction.success(currentData);
            }

            @Override
            public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {

            }
        });
    }
    public static void removeTag(String postId, String tag) {
        DatabaseReference ref = mDatabase.getReference(Comment.GROUP_ID).child(postId);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Post p = snapshot.getValue(Post.class);
                if(p!=null){
                    p.tags.remove(tag);
                    ref.setValue(p);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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

    public static void getAllTags(Consumer<String[]> func){
        mDatabase.getReference("tags/").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String[] res = new String[(int)snapshot.getChildrenCount()];
                int id = 0;
                for(DataSnapshot snap: snapshot.getChildren()){
                    res[id] = snap.getKey();
                    id++;
                }
                func.accept(res);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static void addTagToAll(String tag){
        mDatabase.getReference("tags_count/").runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                Long value = currentData.getValue(Long.class);
                if(value == null){
                    return abort();
                }
                currentData.setValue(value+1);
                mDatabase.getReference("tags/"+tag).setValue(value);
                return Transaction.success(currentData);
            }

            @Override
            public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {

            }
        });
    }
}
