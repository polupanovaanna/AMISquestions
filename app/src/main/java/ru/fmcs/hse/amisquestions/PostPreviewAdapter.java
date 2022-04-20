package ru.fmcs.hse.amisquestions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import ru.fmcs.hse.database.Ordering;
import ru.fmcs.hse.database.Post;
import ru.fmcs.hse.database.PrewiewAdapterWrapper;

public class PostPreviewAdapter extends RecyclerView.Adapter<PostPreviewAdapter.PostPreviewHolder> {
    private final DatabaseReference postRef = FirebaseDatabase.getInstance().getReference(Post.GROUP_ID);
    public static int adapterNumber = 0;
    final ArrayList<Post> posts = new ArrayList<>();
    private boolean reversed = false;
    PrewiewAdapterWrapper<PostPreviewHolder> db = new PrewiewAdapterWrapper<>(Post.class);

    private void reverse() {
        reversed = !reversed;
        db.reverse();
    }

    public PostPreviewAdapter() {
        adapterNumber += 1;
        db.init(this, posts);
        db.changeOrdering(Ordering.VIEWS_REVERSED);
        db.reverse();
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
        return posts.size();
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
            if (!reversed) {
                id = position;
                PostPreview.setText(posts.get(position).getText());
            } else {
                PostPreview.setText(posts.get(posts.size() - 1 - position).getText());
            }
        }
    }
}
