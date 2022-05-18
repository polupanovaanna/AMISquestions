package ru.fmcs.hse.amisquestions;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

import ru.fmcs.hse.amisquestions.databinding.FragmentMyProfileBinding;
import ru.fmcs.hse.amisquestions.databinding.FragmentNewPostsBinding;
import ru.fmcs.hse.database.Controller;


public class MyProfileFragment extends Fragment {

    TextView userName;
    TextView userRole;
    TextView userMail;
    ImageView userPhoto;
    FloatingActionButton editProfileButton;
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

    private @NotNull String getUserId() {
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        if (user != null) {
            return user.getUid();
        }
        return null;
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
        Controller.getUserAndApply(getUserId(), (user)->{userName.setText(user.name);});
        Controller.getUserAndApply(getUserId(), (user)->{userMail.setText(user.email);});
        Controller.displayProfilePhotoAndRole(getUserId(), this, mBinding.userPic ,userRole);
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext());

        editProfileButton = view.findViewById(R.id.edit_profile_button);
        editProfileButton.setOnClickListener(view1 -> {
            NavController navController = Navigation.findNavController(view1);
            navController.navigate(R.id.mainPages_to_editProfile);
        });

        userMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(android.content.Intent.ACTION_SEND);
                i.setType("plain/text");
                i.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{getUserMail()});
                i.putExtra(android.content.Intent.EXTRA_SUBJECT, "i'm....");
                i.putExtra(android.content.Intent.EXTRA_TEXT, "hi");
                getContext().startActivity(Intent.createChooser(i, "Send mail..."));
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }


}