package ru.fmcs.hse.amisquestions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mikepenz.materialdrawer.Drawer;

import ru.fmcs.hse.amisquestions.databinding.ActivityPostCommentsBinding;
import ru.fmcs.hse.database.Comment;
import ru.fmcs.hse.database.Controller;

public class PostCommentsActivity extends AppCompatActivity {

    public final static String postText = "ru.hse.fcms.post_text";
    public final static String authorName = "ru.hse.fcms.post_author";

    private ActivityPostCommentsBinding mPostCommentsBinding;
    protected RecyclerView mRecyclerView;
    private CommentViewAdapter adapter;
    Toolbar mToolbar;
    private Drawer mDrawer;
    Button addCommentButton;
    EditText commentText;

    private PostItemView post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_comments);
        mPostCommentsBinding = ActivityPostCommentsBinding.inflate(getLayoutInflater());
        post = findViewById(R.id.post_item);
        post.setPostText(getIntent().getStringExtra("ru.hse.fcms.post_text"));
        post.setAuthor(getIntent().getStringExtra("ru.hse.fcms.post_author"));
        mPostCommentsBinding = ActivityPostCommentsBinding.inflate(getLayoutInflater());
    }

    @Override
    public void onStart() {
        super.onStart();
        mRecyclerView = findViewById(R.id.RecyclerViewComments);
        mToolbar = findViewById(R.id.toolbar_pc);
        addCommentButton = findViewById(R.id.add_comment_button);
        commentText = findViewById(R.id.comment_input);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);
        adapter = new CommentViewAdapter(100);
        mRecyclerView.setAdapter(adapter);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comment = commentText.getText().toString();
                Controller c = new Controller();
                c.addComment("-N07NRctppLGNqAYdQL2", new Comment("hater", comment));//TODO get key
                commentText.getText().clear();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
