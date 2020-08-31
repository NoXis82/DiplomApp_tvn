package com.example.diplomapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent;
        if(App.getPasswordStorage().hasPin()){
            intent = new Intent(getApplicationContext(), PinActivity.class);
        } else {
            intent = new Intent(getApplicationContext(), SettingsActivity.class);
        }
        startActivity(intent);
        finish();
    }
}