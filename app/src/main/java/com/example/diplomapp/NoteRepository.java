package com.example.diplomapp;

import java.io.IOException;
import java.util.List;

public interface NoteRepository {

    NoteData getNoteById(String id);

    List<NoteData> getNotes() throws IOException;

    void saveNote(NoteDataStr note) throws IOException;

    void deleteById(String id);

}
