package ru.fmcs.hse.amisquestions;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

import ru.fmcs.hse.amisquestions.databinding.FragmentEditProfileBinding;
import ru.fmcs.hse.database.Controller;
import ru.fmcs.hse.database.User;

public class EditProfile extends Fragment {
    private FragmentEditProfileBinding mBinding;
    private Toolbar mToolbar;
    private EditText name, surname;
    private Button commit;
    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentEditProfileBinding.inflate(getLayoutInflater());
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mToolbar = view.findViewById(R.id.toolbar3);
        name = view.findViewById(R.id.new_name);
        surname = view.findViewById(R.id.new_surname);
        commit = view.findViewById(R.id.commit_edit);

        mToolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());

        commit.setOnClickListener(view1 -> {
            String nName = name.getText().toString();
            String nSurname = surname.getText().toString();

            User user = new User();
            user.name = nName + " " + nSurname;
            user.email = "aboba@mail.ru";//TODO email
            try {
                String userId = mFirebaseAuth.getCurrentUser().getUid();
                Controller.getAndUpdateUserField(userId, user.getClass().getDeclaredField("name"), user.name);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            getActivity().onBackPressed();
        });
    }
}