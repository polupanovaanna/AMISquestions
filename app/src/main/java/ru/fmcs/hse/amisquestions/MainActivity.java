package ru.fmcs.hse.amisquestions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

import ru.fmcs.hse.amisquestions.databinding.ActivityMainBinding;
import ru.fmcs.hse.database.Controller;
import ru.fmcs.hse.database.User;

public class MainActivity extends AppCompatActivity implements ImageInputHelper.ImageActionListener {
    static Controller controller = new Controller();

    private static final String TAG = "MainActivity";
    public static final String ANONYMOUS = "anonymous";
    private GoogleSignInClient mSignInClient;
    private ActivityMainBinding mBinding;
    private FirebaseAuth mFirebaseAuth;
    public static ImageInputHelper imageInputHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        imageInputHelper = new ImageInputHelper(this);
        imageInputHelper.setImageActionListener(this);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        System.out.println(token);
                    }
                });
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

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        String token = task.getResult();
                        //TODO внести токен к пользователю в бд
                    }
                });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));
        }
        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d(TAG, "Key: " + key + " Value: " + value);
            }
        }

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageInputHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onImageSelectedFromGallery(Uri uri, File imageFile) {
        ImageView img = new ImageView(getBaseContext(), null);
        img.setImageURI(uri);
        // TODO load img
    }

    @Override
    public void onImageTakenFromCamera(Uri uri, File imageFile) {
        // idk
    }

    @Override
    public void onImageCropped(Uri uri, File imageFile) {
        // idk
    }
}