package ru.fmcs.hse.amisquestions;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.Notification;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;

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
        });

        postButton.setOnClickListener(view1 -> {
            String post = MTV.getText();
            String id = Controller.addPost(post, getUserId());
            StringBuilder stringBuilder = null;
            for (String tag : tags.getMarkedTags()) {
                Controller.addTag(id, tag);
                stringBuilder.append("'" + tag + "'" + " in topics || ");
                stringBuilder.setLength(stringBuilder.length() - 4);
                //это посылка уведомления всем подписанным на тег при создании поста
            }
            if (stringBuilder != null) {
                Message message = Message.builder()
                        .putData("Push", "new post on your favourite tag")
                        .setCondition(stringBuilder.toString())
                        .build();
                try {
                    String response = FirebaseMessaging.getInstance().send(message);
                } catch (FirebaseMessagingException e) {
                    e.printStackTrace();
                }
            }
            Navigation.findNavController(view1).navigate(R.id.mainPages);
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