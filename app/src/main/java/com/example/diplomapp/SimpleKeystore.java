package com.example.diplomapp;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;


public class SimpleKeystore implements Keystore {

    private static final String APP_KEY = "keystore";
    private static final String SALT = "salt";
    private static final String HASH = "hash";
    private SharedPreferences mSettings;
    private HashingPin hashingPin = new HashingPin();
    private String salt;
    private String hashPin;

    public SimpleKeystore(Application app) {
        mSettings = app.getSharedPreferences(APP_KEY, Context.MODE_PRIVATE);
    }


    @Override
    public boolean hasPin() {
        return mSettings.contains(HASH);
    }

    @Override
    public boolean checkPin(String pin) {
        salt = mSettings.getString(SALT, "");
        String inputHashPin = pin + salt;
        hashPin = mSettings.getString(HASH, "");
        inputHashPin = hashingPin.hashingPin(inputHashPin);
        return inputHashPin.equals(hashPin);
    }

    @Override
    public void saveNew(String pin) {
        salt = new GenerateSalt().randomGenerate();
        hashPin = pin + salt;
        hashPin = hashingPin.hashingPin(hashPin);
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(SALT, salt);
        editor.putString(HASH, hashPin);
        editor.apply();
    }
}
