package com.example.diplomapp;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class FileNoteRepository implements NoteRepository {

    private static final String LIST_FILES = "filenames";
    private static final String KEY_NAME_FILES = "id";
    private static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm";
    private Context mContext;
    private SharedPreferences listFiles;
    private List<NoteData> readNotes;
    private Set<String> listFileNotes;

    public FileNoteRepository(Application app) {
        mContext = app.getApplicationContext();
        listFiles = app.getSharedPreferences(LIST_FILES, Context.MODE_PRIVATE);
    }

    @Override
    public NoteData getNoteById(String id) {
        for (NoteData readNote : readNotes) {
            if (readNote.getId().equals(id)) {
                return readNote;
            }
        }
        return null;
    }

    @Override
    public List<NoteData> getNotes() throws IOException {
        readNotes = new ArrayList<>();
        listFileNotes = new HashSet<>();
        Date dateNote;
        DateFormat df = new SimpleDateFormat(DATE_TIME_FORMAT, Locale.ENGLISH);
        Set<String> ret = listFiles.getStringSet(KEY_NAME_FILES, new HashSet<String>());
        for (String fileName : ret) {
            try (FileInputStream fileInputStream = mContext.openFileInput(fileName)) {
                InputStreamReader streamReader = new InputStreamReader(fileInputStream);
                Gson gson = new Gson();
                NoteDataStr readNoteStr = gson.fromJson(streamReader, NoteDataStr.class);
                if (Boolean.parseBoolean(readNoteStr.getCheckDeadline())) {
                    dateNote = df.parse(readNoteStr.getDeadline());
                } else {
                    dateNote = new Date();
                }
                NoteData readNote = new NoteData(
                        readNoteStr.getId(),
                        readNoteStr.getTitle(),
                        readNoteStr.getSubtitle(),
                        Boolean.parseBoolean(readNoteStr.getCheckDeadline()),
                        dateNote,
                        df.parse(readNoteStr.getLastChangeFile())
                );
                readNotes.add(readNote);
                listFileNotes.add(fileName);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return readNotes;
    }

    @Override
    public void saveNote(NoteDataStr noteStr) throws IOException {
        Gson gson = new Gson();
        String jsonString = gson.toJson(noteStr);
        String fileName = noteStr.getId() + ".json";
        if (listFileNotes.contains(fileName)) {
            mContext.deleteFile(fileName);
        } else {
            listFileNotes.add(fileName);
            SharedPreferences.Editor e = listFiles.edit();
            e.putStringSet(KEY_NAME_FILES, listFileNotes);
            e.apply();
        }
        try (FileOutputStream fileOutputStream =
                     mContext.openFileOutput(fileName, Context.MODE_PRIVATE)) {
            fileOutputStream.write(jsonString.getBytes());
        }
    }

    @Override
    public void deleteById(String id) {
        String fileName = id + ".json";
        listFileNotes.remove(fileName);
        SharedPreferences.Editor e = listFiles.edit();
        e.putStringSet(KEY_NAME_FILES, listFileNotes);
        e.apply();
        mContext.deleteFile(fileName);
    }
}

