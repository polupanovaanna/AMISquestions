package ru.fmcs.hse.amisquestions;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.stream.Stream;

import ru.fmcs.hse.amisquestions.databinding.FragmentTestButtonBinding;
import ru.fmcs.hse.database.Controller;
import ru.fmcs.hse.database.Post;
import ru.fmcs.hse.database.User;

public class TestButton extends Fragment {
    FragmentTestButtonBinding mBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_test_button, container, false);
        mBinding = FragmentTestButtonBinding.inflate(getLayoutInflater());
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button but = view.findViewById(R.id.applytext);
        EditText input = view.findViewById(R.id.enterText);
        TextView res = view.findViewById(R.id.result);

        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String s = input.getText().toString();
                Controller c = new Controller();
                c.getOnePost(res);
            }
        });
    }
}