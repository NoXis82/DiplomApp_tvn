package com.example.diplomapp;

import java.util.Random;

public class GenerateSalt {
    String[] dict = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
            .split("");

    public GenerateSalt() {
    }

    public String randomGenerate() {
        Random rnd = new Random();
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            str.append(dict[rnd.nextInt(dict.length)]);
        }
        return str.toString();
    }
}
