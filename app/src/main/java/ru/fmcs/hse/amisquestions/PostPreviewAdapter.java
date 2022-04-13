package ru.fmcs.hse.amisquestions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.prefs.PreferenceChangeEvent;

import ru.fmcs.hse.database.Post;

public class PostPreviewAdapter extends RecyclerView.Adapter<PostPreviewAdapter.PostPreviewHolder> {
    private final DatabaseReference postRef = FirebaseDatabase.getInstance().getReference(Post.GROUP_ID);
    public static int adapterNumber = 0;
    final LinkedHashMap<String, Integer> currentPosts = new LinkedHashMap<>();
    final ArrayList<Post> posts = new ArrayList<>();

    public PostPreviewAdapter() {
        adapterNumber += 1;
        postRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot post : snapshot.getChildren()) {
                    if (post.hasChildren() && post.getKey() != null) {
                        if(currentPosts.containsKey(post.getKey())){
                            posts.set(currentPosts.get(post.getKey()), post.getValue(Post.class));
                        }else {
                            currentPosts.put(post.getKey(), posts.size());
                            posts.add(post.getValue(Post.class));
                        }
                    }
                }
                notifyDataSetChanged();
                System.out.println(currentPosts.size());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @NonNull
    @Override
    public PostPreviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutId = R.layout.fragment_post_preview;

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutId, parent, false);

        PostPreviewHolder holder = new PostPreviewHolder(view);
        // TODO holder id, not

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PostPreviewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return currentPosts.size()+1;
    }

    class PostPreviewHolder extends RecyclerView.ViewHolder {

        TextView PostPreview;
        int id = -1;

        public PostPreviewHolder(@NonNull View itemView) {
            super(itemView);
            PostPreview = itemView.findViewById(R.id.number_of_post);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), Integer.toString(id), Toast.LENGTH_LONG - 1).show();
                }
            });
        }

        void bind(int position) {
            id = position;

            if(currentPosts.size() > position){

                PostPreview.setText(posts.get(position).getText());
            }

        }
    }
}
