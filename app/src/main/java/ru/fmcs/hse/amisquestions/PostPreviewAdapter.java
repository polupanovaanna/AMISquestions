package ru.fmcs.hse.amisquestions;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import ru.fmcs.hse.database.Controller;
import ru.fmcs.hse.database.DatabaseFiltering;
import ru.fmcs.hse.database.DatabaseOrdering;
import ru.fmcs.hse.database.Ordering;
import ru.fmcs.hse.database.Post;
import ru.fmcs.hse.database.PrewiewAdapterWrapper;

public class PostPreviewAdapter extends RecyclerView.Adapter<PostPreviewAdapter.PostPreviewHolder> {
    private final DatabaseReference postRef = FirebaseDatabase.getInstance().getReference(Post.GROUP_ID);
    public static int adapterNumber = 0;
    final ArrayList<Post> posts = new ArrayList<>();
    final ArrayList<String> keyHolder = new ArrayList<>();
    PrewiewAdapterWrapper<PostPreviewHolder> db = new PrewiewAdapterWrapper<>(Post.class);
    private CheckBox sbd; // SortByDate

    private void reverse() {
        db.reverse();
    }

    public PostPreviewAdapter(CheckBox sbd_) {
        sbd = sbd_;
        adapterNumber += 1;
        db.init(this, posts, keyHolder);
        //db.addFiltering(Ordering.PostOrdering.TAG_FILTER_REVERSED, "java");
        //db.changeOrdering(Ordering.PostOrdering.VIEWS_REVERSED);
        //reverse();
    }

    @NonNull
    @Override
    public PostPreviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutId = R.layout.fragment_post_preview;

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutId, parent, false);

        PostPreviewHolder holder = new PostPreviewHolder(view);
        return holder;
    }

    public void sortChanged() {
        filter(Ordering.DEFAULT_FILTER, null);
        db.reverse();
    }
    public void sort(DatabaseOrdering order){
        db.changeOrdering(order);
    }
    public void filter(DatabaseFiltering filter, String param){
        db.addFiltering(filter, param);
    }

    public void sortByTag (String tag) {
        filter(Ordering.PostOrdering.TAG_FILTER_REVERSED, tag);
        if(!db.reversed){
            db.reverse();
        }
    }

    @Override
    public void onBindViewHolder(@NonNull PostPreviewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return posts.size() + 1;
    }

    class PostPreviewHolder extends RecyclerView.ViewHolder {

        TextView postText;
        TextView postAuthor;
        int id = -1;

        public PostPreviewHolder(@NonNull View itemView) {
            super(itemView);
            postText = itemView.findViewById(R.id.post_text);
            postAuthor = itemView.findViewById(R.id.author_name);
            itemView.setOnClickListener(view ->
                    Toast.makeText(view.getContext(), Integer.toString(id), Toast.LENGTH_LONG - 1).show());
            itemView.setOnClickListener(view -> {
                Intent intent = new Intent(itemView.getContext(), PostCommentsActivity.class);
                intent.putExtra("ru.hse.fcms.post_text", postText.getText());
                intent.putExtra("ru.hse.fcms.post_author", posts.get(id).getAuthor());
                intent.putExtra("ru.hse.fcms.post_id", keyHolder.get(id));
                view.getContext().startActivity(intent);
            });
        }

        void bind(int position) {
            if (position >= posts.size() && posts.size() > 0) {
                db.getMore(posts.get(posts.size() - 1).getNumberOfViews());//get parameter you need
                return;
            } else if (position >= posts.size()) {
                return;
            }
            id = position;

            Controller.getUserAndApply(posts.get(position).getAuthor(), (user) -> postAuthor.setText(user.name));

            postText.setText(posts.get(position).getText());
        }
    }
}
