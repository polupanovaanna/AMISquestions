package ru.fmcs.hse.amisquestions;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import ru.fmcs.hse.amisquestions.databinding.ActivityProfileBinding;
import ru.fmcs.hse.database.Controller;

public class ProfileActivity extends AppCompatActivity {

    public final static String otherUserId = "ru.hse.fcms.other_user_id";
    private String id;
    private ActivityProfileBinding binding;
    TextView userName;
    TextView userRole;
    TextView userMail;
    ImageView userPhoto;
    Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mToolbar = findViewById(R.id.toolbar_oth_user);
        id = getIntent().getStringExtra("ru.hse.fcms.other_user_id");
        userName = findViewById(R.id.oth_user_name);
        userMail = findViewById(R.id.oth_user_mail);
        userRole = findViewById(R.id.oth_user_role);
        userPhoto = findViewById(R.id.oth_user_pic);
        Controller.getUserAndApply(id, (user)->userName.setText(user.name));
        Controller.getUserAndApply(id, (user)->userMail.setText(user.email));
        Controller.displayProfilePhotoAndRole(id, this, binding.othUserPic ,userRole);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}