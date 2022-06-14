package ru.fmcs.hse.amisquestions;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.messaging.FirebaseMessaging;
import ru.fmcs.hse.amisquestions.databinding.FragmentSubscribeBinding;
import ru.fmcs.hse.database.Controller;

public class SubscribeFragment extends Fragment {

    private FragmentSubscribeBinding mBinding;
    TextView subscribeMessage;
    TagsList tagsList;
    Button subscribeButton;
    Button unsubscribeButton;

    public SubscribeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentSubscribeBinding.inflate(getLayoutInflater());
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        subscribeMessage = view.findViewById(R.id.textView_sub);
        tagsList = view.findViewById(R.id.tags_list_subscribe);
        subscribeButton = view.findViewById(R.id.button_sub);
        unsubscribeButton = view.findViewById(R.id.button_sub_2);

        subscribeMessage.setText("Выберите теги, на которые вы хотите подписаться или отписаться");
        //TODO я бы тут просто текстом хотела получать теги, на которые подписан пользователь и писать их текстом в сообщении выше

        Controller.getAllTags((list) -> {
            tagsList.setTags(list);
        });

        subscribeButton.setOnClickListener(view1 -> {
            for (String tag : tagsList.getMarkedTags()) {
                //TODO
                FirebaseMessaging.getInstance().subscribeToTopic(tag);
            }
        });

        unsubscribeButton.setOnClickListener(view1 -> {
            for (String tag : tagsList.getMarkedTags()) {
                FirebaseMessaging.getInstance().unsubscribeFromTopic(tag);
            }
        });

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}