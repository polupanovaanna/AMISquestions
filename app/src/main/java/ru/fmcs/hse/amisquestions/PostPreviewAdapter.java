package ru.fmcs.hse.amisquestions;

import android.content.Context;
import android.content.Intent;
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

import io.noties.markwon.Markwon;
import io.noties.markwon.ext.latex.JLatexMathPlugin;
import io.noties.markwon.inlineparser.MarkwonInlineParserPlugin;
import ru.fmcs.hse.database.Ordering;
import ru.fmcs.hse.database.Post;
import ru.fmcs.hse.database.PrewiewAdapterWrapper;

public class PostPreviewAdapter extends RecyclerView.Adapter<PostPreviewAdapter.PostPreviewHolder> {
    private final DatabaseReference postRef = FirebaseDatabase.getInstance().getReference(Post.GROUP_ID);
    public static int adapterNumber = 0;
    final ArrayList<Post> posts = new ArrayList<>();
    final ArrayList<String> keyHolder = new ArrayList<>();
    PrewiewAdapterWrapper<PostPreviewHolder> db = new PrewiewAdapterWrapper<>(Post.class);

    private void reverse() {
        db.reverse();
    }

    public PostPreviewAdapter() {
        adapterNumber += 1;
        db.init(this, posts, keyHolder);
        db.changeOrdering(Ordering.PostOrdering.VIEWS_REVERSED);
        reverse();
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
                System.out.println(postText.getText());
                intent.putExtra("ru.hse.fcms.post_text", postText.getText().toString());
                intent.putExtra("ru.hse.fcms.post_author", postAuthor.getText());
                intent.putExtra("ru.hse.fcms.post_id", keyHolder.get(id));
                view.getContext().startActivity(intent);//somewhere should be added keyHolder.get(id) -- id of post
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

            final Markwon markwon = Markwon.builder(itemView.getContext())
                    .usePlugin(MarkwonInlineParserPlugin.create())
                    .usePlugin(JLatexMathPlugin.create(postText.getTextSize(), new JLatexMathPlugin.BuilderConfigure() {
                        @Override
                        public void configureBuilder(@NonNull JLatexMathPlugin.Builder builder) {
                            builder.inlinesEnabled(true);
                        }
                    }))
                    .build();

            markwon.setMarkdown(postText, posts.get(position).getText());
            postAuthor.setText(posts.get(position).getAuthor());
        }
    }
}
