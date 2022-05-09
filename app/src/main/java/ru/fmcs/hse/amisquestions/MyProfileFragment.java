package ru.fmcs.hse.amisquestions;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ru.fmcs.hse.amisquestions.databinding.FragmentMyProfileBinding;
import ru.fmcs.hse.amisquestions.databinding.FragmentNewPostsBinding;


public class MyProfileFragment extends Fragment {

    TextView userName;
    TextView userRole;
    TextView userMail;
    ImageView userPhoto;
    private FirebaseAuth mFirebaseAuth;
    private FragmentMyProfileBinding mBinding;


    public String getUserName() {
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        if (user != null) {
            return user.getDisplayName();
        }
        return "err";
    }

    public String getUserMail() {
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        if (user != null) {
            return user.getEmail();
        }
        return "err";
    }

    private String getUserPhotoUrl() {
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        if (user != null && user.getPhotoUrl() != null) {
            return user.getPhotoUrl().toString();
        }
        return null;
    }

    private String getUserRole() {
        //чето с бд
        return "hehe";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentMyProfileBinding.inflate(getLayoutInflater());
        return mBinding.getRoot();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseAuth = FirebaseAuth.getInstance();
        //setContentView(R.layout.activity_profile_page);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFirebaseAuth = FirebaseAuth.getInstance();
        userName = view.findViewById(R.id.user_name);
        userRole = view.findViewById(R.id.user_role);
        userMail = view.findViewById(R.id.user_mail);
        userPhoto = view.findViewById(R.id.user_pic);
        userName.setText(getUserName());
        userMail.setText(getUserMail());
        userRole.setText("ждем бд");
        Glide.with(this).load(getUserPhotoUrl()).into(mBinding.userPic);
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext());
    }

    @Override
    public void onResume() {
        super.onResume();
    }


}