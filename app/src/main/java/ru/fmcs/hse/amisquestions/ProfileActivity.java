package ru.fmcs.hse.amisquestions;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import ru.fmcs.hse.amisquestions.databinding.ActivityProfileBinding;

public class ProfileActivity extends AppCompatActivity {

    public final static String otherUserId = "ru.hse.fcms.other_user_id";
    private String id;
    private ActivityProfileBinding binding;
    TextView userName;
    TextView userRole;
    TextView userMail;
    ImageView userPhoto;
    private FirebaseAuth mFirebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        id = getIntent().getStringExtra("ru.hse.fcms.other_user_id");
        userName.setText(getUserName());
        userMail.setText(getUserEmail());
        //TODO или здесь вызывать какой-то метод из твоей бд?..
    }

    private String getUserName() {
        //TODO база данных по id который я уже получила
        return null;
    }

    private String getUserEmail() {
        //TODO база данных по id который я уже получила
        return null;
    }

    private String getUserPhoto() {
        //TODO база данных по id который я уже получила
        return null;
    }


}