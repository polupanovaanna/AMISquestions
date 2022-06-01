package ru.fmcs.hse.amisquestions;

import static java.lang.Thread.sleep;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;
import java.util.List;

import ru.fmcs.hse.amisquestions.databinding.FragmentCreateNewPostBinding;
import ru.fmcs.hse.database.Controller;
import ru.fmcs.hse.database.User;

public class CreateNewPost extends Fragment {

    private FragmentCreateNewPostBinding mBinding;
    Toolbar mToolbar;
    private Drawer mDrawer;
    MarkdownTextView MTV;
    Button postButton;

    TagsList tags;

    private FirebaseAuth mFirebaseAuth;

    public String getUserId() {
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        if (user != null) {
            return user.getUid();
        }
        return "err";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFirebaseAuth = FirebaseAuth.getInstance();
        mBinding = FragmentCreateNewPostBinding.inflate(getLayoutInflater());
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mToolbar = view.findViewById(R.id.toolbar2);
        MTV = view.findViewById(R.id.markdown_text);
        tags = view.findViewById(R.id.tags_list_add);
        postButton = view.findViewById(R.id.post_button);
        Controller.getAllTags((list) -> {
            tags.setTags(list);
            System.out.println(tags.langArray.length);
            postButton.setOnClickListener(view1 -> {
                String post = MTV.getText();
                String id = Controller.addPost(post, getUserId());
                for (String tag : tags.getMarkedTags()) {
                    Controller.addTag(id, tag);
                }
                Navigation.findNavController(view1).navigate(R.id.mainPages);
            });

        });
        mToolbar.setTitle("Добавление поста");

        mToolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());
    }

    private ActionBar getSupportActionBar() {
        return ((AppCompatActivity) getActivity()).getSupportActionBar();
    }

    private void createDrawer() {

        mDrawer = new DrawerBuilder()
                .withActivity(((AppCompatActivity) getActivity()))
                .withToolbar(mToolbar)
                .build();
    }
}