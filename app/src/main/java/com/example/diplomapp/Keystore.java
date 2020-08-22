package com.example.diplomapp;

public interface Keystore {

    boolean hasPin(); //проверка наличия pin

    boolean checkPin(String pin); // проверка pin

    void saveNew(String pin); //запись нового pin
}
