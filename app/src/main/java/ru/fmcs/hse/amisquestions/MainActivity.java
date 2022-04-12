package ru.fmcs.hse.amisquestions;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.database.FirebaseDatabase;

import ru.fmcs.hse.database.Controller;

public class MainActivity extends AppCompatActivity {
    static Controller controller = new Controller();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}