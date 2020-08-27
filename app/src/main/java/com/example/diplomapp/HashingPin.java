package com.example.diplomapp;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashingPin {

    public HashingPin() {
    }

    public String hashingPin(String saltPin) {
        try {
            MessageDigest digest = java.security.MessageDigest
                    .getInstance("MD5");
            digest.reset();
            digest.update(saltPin.getBytes());
            byte[] pinDigest = digest.digest();
            BigInteger bigInt = new BigInteger(1, pinDigest);
            String hashPin = bigInt.toString(16);
            while (hashPin.length() < 32) {
                hashPin = "0" + hashPin;
            }
            return hashPin;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
