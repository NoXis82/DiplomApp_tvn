package com.example.diplomapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;


public class SettingsActivity extends AppCompatActivity {

    private EditText enterPin;
    private ImageButton viewPinBtn;
    private Button savePinBtn;
    private TextView errorView;
    private boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initView();
        checkViewPin();
        saveNewPin();
    }

    private void initView() {
        enterPin = findViewById(R.id.enter_pin);
        viewPinBtn = findViewById(R.id.view_pin);
        savePinBtn = findViewById(R.id.btn_save_pin);
        errorView = findViewById(R.id.error_view);
    }

    private void checkViewPin() {
        viewPinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) {
                    viewPinBtn.setImageResource(R.drawable.ic_view_pin);
                    enterPin.setTransformationMethod(null);
                } else {
                    viewPinBtn.setImageResource(R.drawable.ic_no_view_pin);
                    enterPin.setTransformationMethod(new PasswordTransformationMethod());
                }
                enterPin.setSelection(enterPin.length());
                flag = !flag;
            }
        });
    }

    private void saveNewPin() {
        savePinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = enterPin.getText().toString();
                if(str.equals("")) {
                    errorView.setText(R.string.enter_pin_empty);
                } else {
                    if(str.length() < 4) {
                        errorView.setText(R.string.enter_pin_short);
                    } else {
                        App.getPasswordStorage().saveNew(str);
                        errorView.setText(R.string.enter_pin_save);
                        Intent intent = new Intent(getApplicationContext(), NotesList.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
    }

}