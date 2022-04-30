package ru.fmcs.hse.amisquestions;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import ru.fmcs.hse.database.Controller;

public class MainActivity extends AppCompatActivity {
    static Controller controller = new Controller();
    static FirebaseUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}