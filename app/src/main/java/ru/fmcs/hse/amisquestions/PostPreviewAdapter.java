package ru.fmcs.hse.amisquestions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import ru.fmcs.hse.database.Post;

public class PostPreviewAdapter extends RecyclerView.Adapter<PostPreviewAdapter.PostPreviewHolder> {

    int numberItems;
    public static int adapterNumber = 0;
    ArrayList<Post> currentPosts = new ArrayList<>();
    public PostPreviewAdapter(int cnt) {
        numberItems = cnt;
        adapterNumber += 1;
        MainActivity.controller.getPosts(currentPosts);
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
        return currentPosts.size();
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
            PostPreview.setText(currentPosts.get(position).getText());
        }
    }
}
