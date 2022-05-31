package ru.fmcs.hse.amisquestions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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
    Spinner spinner;

    private PostItemView post;
    String returnedPostId;

    FirebaseAuth mFirebaseAuth;

    private String getUserId() {
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        if (user != null && user.getUid() != null) {
            return user.getUid();
        }
        return null;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_comments);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mPostCommentsBinding = ActivityPostCommentsBinding.inflate(getLayoutInflater());
        post = findViewById(R.id.post_item);
        System.out.println(getIntent().getStringExtra("ru.hse.fcms.post_text"));
        post.setPostText(getIntent().getStringExtra("ru.hse.fcms.post_text"));
        userId = getIntent().getStringExtra("ru.hse.fcms.post_author");
        Controller.getUserAndApply(userId, (user)->post.setAuthor(user.name));
        Controller.displayProfilePhoto(userId, this, this.post.avatarImage);
        returnedPostId = getIntent().getStringExtra("ru.hse.fcms.post_id");
        mPostCommentsBinding = ActivityPostCommentsBinding.inflate(getLayoutInflater());
    }

    String[] data = {"one", "two", "three", "four", "five"};
    @Override
    public void onStart() {
        super.onStart();

        //TODO получить
        ArrayAdapter<String> sp_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);
        sp_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mRecyclerView = findViewById(R.id.RecyclerViewComments);
        mToolbar = findViewById(R.id.toolbar_pc);
        addCommentButton = findViewById(R.id.add_comment_button);
        commentText = findViewById(R.id.comment_input);
        spinner = findViewById(R.id.spinner);
        spinner.setAdapter(sp_adapter);
        spinner.setPrompt("Tags");

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Toast.makeText(getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

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
                Controller.addComment(returnedPostId, new Comment(getUserId(), comment));
                commentText.getText().clear();
            }
        });

        post.authorsName.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), ProfileActivity.class);
            intent.putExtra("ru.hse.fcms.other_user_id", userId);
            view.getContext().startActivity(intent);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
