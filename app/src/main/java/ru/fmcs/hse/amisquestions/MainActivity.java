package ru.fmcs.hse.amisquestions;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ru.fmcs.hse.amisquestions.databinding.ActivityMainBinding;
import ru.fmcs.hse.database.Controller;

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
        //подсвечивается красным но НИЧЕГО СТРАШНОГО :))))))))
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void signOut() {
        mFirebaseAuth.signOut();
        mSignInClient.signOut();
        startActivity(new Intent(this, SignInActivity.class));
        finish();
    }

    /*private String getUserPhotoUrl() {
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        if (user != null && user.getPhotoUrl() != null) {
            return user.getPhotoUrl().toString();
        }
        return null;
    }*/

}