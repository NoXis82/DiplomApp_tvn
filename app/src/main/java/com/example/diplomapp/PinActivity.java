package com.example.diplomapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PinActivity extends AppCompatActivity {
    private TextView pinTextView;
    private List<Button> mButtons;
    private List<ImageView> mViewPin;
    private int pinLength = 0;
    private StringBuilder pinEnter = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);
        initView();
        clickKeyboardButton();

    }

    private void initView() {
        pinTextView = findViewById(R.id.pin_textview);
        mViewPin = new ArrayList<>();
        mViewPin.add((ImageView) findViewById(R.id.digit_pin_view_1));
        mViewPin.add((ImageView) findViewById(R.id.digit_pin_view_2));
        mViewPin.add((ImageView) findViewById(R.id.digit_pin_view_3));
        mViewPin.add((ImageView) findViewById(R.id.digit_pin_view_4));
        mButtons = new ArrayList<>();
        mButtons.add((Button) findViewById(R.id.digitBtn_1));
        mButtons.add((Button) findViewById(R.id.digitBtn_2));
        mButtons.add((Button) findViewById(R.id.digitBtn_3));
        mButtons.add((Button) findViewById(R.id.digitBtn_4));
        mButtons.add((Button) findViewById(R.id.digitBtn_5));
        mButtons.add((Button) findViewById(R.id.digitBtn_6));
        mButtons.add((Button) findViewById(R.id.digitBtn_7));
        mButtons.add((Button) findViewById(R.id.digitBtn_8));
        mButtons.add((Button) findViewById(R.id.digitBtn_9));
        mButtons.add((Button) findViewById(R.id.digitBtn_0));
        mButtons.add((Button) findViewById(R.id.btn_delete));
    }

    public void clickKeyboardButton() {
        for (View button : mButtons) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = v.getId();
                    pinTextView.setText(R.string.text_input_pin);
                    switch (id) {
                        case R.id.digitBtn_0:
                            countPin(KeyboardButtonEnum.BUTTON_0.getButtonValue());
                            break;
                        case R.id.digitBtn_1:
                            countPin(KeyboardButtonEnum.BUTTON_1.getButtonValue());
                            break;
                        case R.id.digitBtn_2:
                            countPin(KeyboardButtonEnum.BUTTON_2.getButtonValue());
                            break;
                        case R.id.digitBtn_3:
                            countPin(KeyboardButtonEnum.BUTTON_3.getButtonValue());
                            break;
                        case R.id.digitBtn_4:
                            countPin(KeyboardButtonEnum.BUTTON_4.getButtonValue());
                            break;
                        case R.id.digitBtn_5:
                            countPin(KeyboardButtonEnum.BUTTON_5.getButtonValue());
                            break;
                        case R.id.digitBtn_6:
                            countPin(KeyboardButtonEnum.BUTTON_6.getButtonValue());
                            break;
                        case R.id.digitBtn_7:
                            countPin(KeyboardButtonEnum.BUTTON_7.getButtonValue());
                            break;
                        case R.id.digitBtn_8:
                            countPin(KeyboardButtonEnum.BUTTON_8.getButtonValue());
                            break;
                        case R.id.digitBtn_9:
                            countPin(KeyboardButtonEnum.BUTTON_9.getButtonValue());
                            break;
                        case R.id.btn_delete:
                            countPin(KeyboardButtonEnum.BUTTON_DEL.getButtonValue());
                            break;
                    }
                }
            });
        }
    }

    private void countPin(int buttonValue) {
        if (buttonValue >= 0) {
            if (pinLength < 4) {
                mViewPin.get(pinLength).setBackgroundResource(R.drawable.pin_code_round_full);
                pinLength++;
                pinEnter.append(buttonValue);
                if (pinLength == 4) {
                    if (App.getPasswordStorage().checkPin(pinEnter.toString())) {
                        pinTextView.setText(R.string.pin_correct);
                        Intent intent = new Intent(getApplicationContext(), NotesList.class);
                        startActivity(intent);
                        finish();
                    } else {
                        pinTextView.setText(R.string.pin_error);
                        pinLength = 0;
                        pinEnter.setLength(0);
                        for (ImageView pin : mViewPin) {
                            pin.setBackgroundResource(R.drawable.pin_code_round_empty);
                        }
                    }
                }
            }
        } else if (pinLength > 0) {
            mViewPin.get(pinLength - 1).setBackgroundResource(R.drawable.pin_code_round_empty);
            pinEnter.deleteCharAt(pinLength - 1);
            pinLength--;
        }
    }
}