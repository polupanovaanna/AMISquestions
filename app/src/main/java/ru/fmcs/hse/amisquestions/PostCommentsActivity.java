package ru.fmcs.hse.amisquestions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.Navigation;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mikepenz.materialdrawer.Drawer;

import ru.fmcs.hse.amisquestions.databinding.ActivityPostCommentsBinding;
import ru.fmcs.hse.database.Comment;
import ru.fmcs.hse.database.Controller;
import ru.fmcs.hse.database.Post;

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
    FloatingActionButton fab;

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
        //System.out.println(getIntent().getStringExtra("ru.hse.fcms.post_text"));
        post.setPostText(getIntent().getStringExtra("ru.hse.fcms.post_text"));
        post.setPostDate(getIntent().getStringExtra("ru.hse.fcms.post_date")); // TODO change to date, not text
        userId = getIntent().getStringExtra("ru.hse.fcms.post_author");
        Controller.getUserAndApply(userId, (user) -> post.setAuthor(user.name));
        Controller.displayProfilePhoto(userId, this, this.post.avatarImage);
        returnedPostId = getIntent().getStringExtra("ru.hse.fcms.post_id");
        mPostCommentsBinding = ActivityPostCommentsBinding.inflate(getLayoutInflater());
    }

    String[] data = {"one", "two", "three", "four", "five"};

    @Override
    public void onStart() {
        super.onStart();

        mRecyclerView = findViewById(R.id.RecyclerViewComments);
        mToolbar = findViewById(R.id.toolbar_pc);
        addCommentButton = findViewById(R.id.add_comment_button);
        commentText = findViewById(R.id.comment_input);
        spinner = findViewById(R.id.spinner);
        spinner.setPrompt("Tags");
        fab = findViewById(R.id.edit_post);

        // TODO false swap to owner or admin, and my userid don't work
        if (false || getUserId() == "e6Lrx64pwwat8ANzR6kuGH6k1rw2")
            fab.setVisibility(View.VISIBLE);
        else
            fab.setVisibility(View.INVISIBLE);
        fab.setVisibility(View.VISIBLE); // TODO delete
        fab.setOnClickListener(Navigation.createNavigateOnClickListener(
                R.id.action_postCommentsFragment_to_createNewPost
        )); // TODO fix fail or make activity same to create new post
        // Better is activity, cause need to add delete button
        // Maybe add button send to author to refactor

        Controller.getSomethingAndApply(returnedPostId, (post_) -> {
            ArrayAdapter<String> sp_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, (((Post) post_).tags.keySet().toArray(new String[((Post)post_).tags.size()])));
            sp_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(sp_adapter);
            System.out.println(((Post) post_).timeCreated);
            post.setPostDate(((Post) post_).timeCreated != null ? ((Post) post_).timeCreated : "Unspecified date");
        }, Post.class);

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
