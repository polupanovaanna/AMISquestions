package ru.fmcs.hse.database;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Controller {
    private DatabaseReference mDatabase;

    public void addUser(String name) {
        mDatabase.child(User.GROUP_ID).child(name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User checked = snapshot.getValue(User.class);
                if (checked != null) {
                    throw new IllegalArgumentException("No such user");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw new UnknownError("Something in db went wrong");
            }
        });

        String nId = mDatabase.getKey();
        User nUser = new User(nId, name);
        mDatabase.setValue(nUser);
    }

    public User getUserById(String userId) {
        final List<User> user = new ArrayList<>();
        mDatabase.child(User.GROUP_ID).child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user.set(0, snapshot.getValue(User.class));//Suggest something like frontend.do_something?
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw new IllegalArgumentException("No such user");
            }
        });
        return user.get(0);
    }

    public void createPost(@NotNull String text, User user) {

    }

    public Stream<Post> posts() {
        Stream<Post> res = new ArrayList<Post>().stream();
        mDatabase.child(Post.GROUP_ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> iterable = snapshot.getChildren();
                Stream<Post> dsnap = StreamSupport.stream(iterable.spliterator(), false).map(snap->(Post)snap.getValue());
                Stream.concat(res, dsnap);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return res;
    }
}
