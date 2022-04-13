package ru.fmcs.hse.amisquestions;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import ru.fmcs.hse.amisquestions.databinding.FragmentLoginPageBinding;

public class LoginPage extends Fragment {

    private FragmentLoginPageBinding mBinding;
    private EditText login, password1;
    private Button loginButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_login_page, container, false);
        mBinding = FragmentLoginPageBinding.inflate(getLayoutInflater());
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        login = view.findViewById(R.id.mail);
        password1 = view.findViewById(R.id.passord);
        loginButton = view.findViewById(R.id.login_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = login.getText().toString();
                String password = password1.getText().toString();
                boolean isLoggedIn = true;

                // foo

                if (isLoggedIn) {
                    Navigation.findNavController(view).navigate(R.id.logged_in_action);
                }

            }
        });
    }
}