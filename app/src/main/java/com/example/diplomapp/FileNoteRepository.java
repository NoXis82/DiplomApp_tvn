package com.example.diplomapp;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FileNoteRepository implements NoteRepository {

    private static final String LIST_FILES = "filesnotes";
    private static final String KEY_NAME_FILES = "id";
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
    public List<NoteData> getNotes() {
        InputStreamReader streamReader = null;
        FileInputStream fileInputStream = null;
        readNotes = new ArrayList<>();
        listFileNotes = new HashSet<>();
        try {
            Set<String> ret = listFiles.getStringSet(KEY_NAME_FILES, new HashSet<String>());
            for (String fileName : ret) {
                fileInputStream = mContext.openFileInput(fileName);
                streamReader = new InputStreamReader(fileInputStream);
                Gson gson = new Gson();
                NoteData readNote = gson.fromJson(streamReader, NoteData.class);
                readNotes.add(readNote);
                listFileNotes.add(fileName);
            }
            return readNotes;
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (streamReader != null) {
                try {
                    streamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    public void saveNote(NoteData note) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(note);
        FileOutputStream fileOutputStream = null;
        try {
            String fileName = note.getId() + ".json";
            if (listFileNotes.contains(fileName)) {
                mContext.deleteFile(fileName);
            } else {
                listFileNotes.add(fileName);
                SharedPreferences.Editor e = listFiles.edit();
                e.putStringSet(KEY_NAME_FILES, listFileNotes);
                e.apply();
            }
            fileOutputStream = mContext.openFileOutput(fileName, Context.MODE_PRIVATE);
            fileOutputStream.write(jsonString.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
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

