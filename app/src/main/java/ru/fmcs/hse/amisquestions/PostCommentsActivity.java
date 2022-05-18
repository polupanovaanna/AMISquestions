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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mikepenz.materialdrawer.Drawer;

import ru.fmcs.hse.amisquestions.databinding.ActivityPostCommentsBinding;
import ru.fmcs.hse.database.Comment;
import ru.fmcs.hse.database.Controller;

public class PostCommentsActivity extends AppCompatActivity {

    public final static String postId = "ru.hse.fcms.post_id";
    public final static String postText = "ru.hse.fcms.post_text";
    public final static String authorName = "ru.hse.fcms.post_author"; //по этоиу получим id пользователя создавшего пост

    private String userId;

    private ActivityPostCommentsBinding mPostCommentsBinding;
    protected RecyclerView mRecyclerView;
    private CommentViewAdapter adapter;
    Toolbar mToolbar;
    private Drawer mDrawer;
    Button addCommentButton;
    EditText commentText;

    private PostItemView post;
    String returnedPostId;

    FirebaseAuth mFirebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_comments);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mPostCommentsBinding = ActivityPostCommentsBinding.inflate(getLayoutInflater());
        post = findViewById(R.id.post_item);
        post.setPostText(getIntent().getStringExtra("ru.hse.fcms.post_text"));
        userId = getIntent().getStringExtra("ru.hse.fcms.post_author");
        Controller.getUserAndApply(userId, (user)->post.setAuthor(user.name));
        returnedPostId = getIntent().getStringExtra("ru.hse.fcms.post_id");
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
        adapter = new CommentViewAdapter(100, returnedPostId);
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
                Controller.getUserAndApply(userId, (user)->{Controller.addComment(returnedPostId, new Comment(user.name, comment));});
                commentText.getText().clear();
            }
        });

        post.authorsName.setOnClickListener(view -> {
            Intent intent = new Intent(post.authorsName.getContext(), PostCommentsActivity.class);
            intent.putExtra("ru.hse.fcms.other_user_id", authorName);
            view.getContext().startActivity(intent);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
