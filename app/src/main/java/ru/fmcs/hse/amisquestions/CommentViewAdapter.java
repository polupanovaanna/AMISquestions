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

import ru.fmcs.hse.database.Comment;
import ru.fmcs.hse.database.Ordering;
import ru.fmcs.hse.database.PrewiewAdapterWrapper;

public class CommentViewAdapter extends RecyclerView.Adapter<CommentViewAdapter.CommentViewHolder> {

    int numberItems;
    public static int adapterNumber = 0;
    PrewiewAdapterWrapper<CommentViewHolder> db = new PrewiewAdapterWrapper<>(Comment.class);
    ArrayList<Comment> comments = new ArrayList<>();
    String postKey;

    public CommentViewAdapter(int cnt, String postKey_) {
        numberItems = cnt;
        postKey = postKey_;
        adapterNumber += 1;
        db.init(this, comments, null);
        db.addFiltering(Ordering.CommentsOrdering.POST_COMMENTS, postKey);
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutId = R.layout.comment_view;

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutId, parent, false);

        CommentViewHolder holder = new CommentViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        holder.bind(position);
    }


    @Override
    public int getItemCount() {
        return comments.size();
    }

    class CommentViewHolder extends RecyclerView.ViewHolder {

        TextView authorName;
        TextView commentText;
        int id = -1;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            commentText = itemView.findViewById(R.id.comment);
            authorName = itemView.findViewById(R.id.comment_author);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), Integer.toString(id), Toast.LENGTH_LONG - 1).show();
                }
            });
        }

        void bind(int position) {
            id = position;
            if (position >= comments.size()) {
                return;
            }

            commentText.setText(comments.get(position).getText());
            authorName.setText(comments.get(position).getAuthor());
        }
    }
}
