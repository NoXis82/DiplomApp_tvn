package com.example.diplomapp;

import android.app.Application;

public class App extends Application {
    private static NoteRepository noteRepository;
    private static Keystore passwordStorage;

    @Override
    public void onCreate() {
        super.onCreate();
        noteRepository = new FileNoteRepository(this);
        passwordStorage = new SimpleKeystore(this);
    }

    public static NoteRepository getNoteRepository() {
        return noteRepository;
    }

    public static Keystore getPasswordStorage() {
        return passwordStorage;
    }

}
