package ru.fmcs.hse.amisquestions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import ru.fmcs.hse.amisquestions.databinding.ActivityPostCommentsBinding;

public class PostCommentsActivity extends AppCompatActivity {

    public final static String postText = "ru.hse.fcms.post_text";
    public final static String authorName = "ru.hse.fcms.post_author";

    private ActivityPostCommentsBinding mPostCommentsBinding;
    protected RecyclerView mRecyclerView;
    private CommentViewAdapter adapter;

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
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);
        adapter = new CommentViewAdapter(100);
        mRecyclerView.setAdapter(adapter);
    }


    @Override
    public void onResume() {
        super.onResume();
    }
}


