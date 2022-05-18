package ru.fmcs.hse.amisquestions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import ru.fmcs.hse.amisquestions.databinding.ActivityMainBinding;
import ru.fmcs.hse.database.Controller;
import ru.fmcs.hse.database.User;

public class MainActivity extends AppCompatActivity {
    static Controller controller = new Controller();

    public static final String ANONYMOUS = "anonymous";
    private GoogleSignInClient mSignInClient;
    private ActivityMainBinding mBinding;
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mFirebaseAuth = FirebaseAuth.getInstance();

        if (mFirebaseAuth.getCurrentUser() == null) {
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return;
        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mSignInClient = GoogleSignIn.getClient(this, gso);
        //подсвечивается красным но НИЧЕГО СТРАШНОГО :))))))))
        mFirebaseAuth = FirebaseAuth.getInstance();
        Controller.addUser(new User(getUserName(),
                getUserMail(),
                getUserPhotoUrl()), getUserId());

    }

    private void signOut() {
        mFirebaseAuth.signOut();
        mSignInClient.signOut();
        startActivity(new Intent(this, SignInActivity.class));
        finish();
    }

    private String getUserId() {
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        if (user != null && user.getUid() != null) {
            return user.getUid();
        }
        return null;
    }

    private String getUserMail() {
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        if (user != null && user.getEmail() != null) {
            return user.getEmail();
        }
        return null;
    }

    private String getUserName() {
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        if (user != null && user.getDisplayName() != null) {
            return user.getDisplayName();
        }
        return null;
    }

     private String getUserPhotoUrl() {
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        if (user != null && user.getPhotoUrl() != null) {
            return user.getPhotoUrl().toString();
        }
        return null;
    }

}