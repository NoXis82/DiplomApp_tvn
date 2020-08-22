package com.example.diplomapp;

import java.util.List;

public class DataNotes {
    private List<NoteData> notes;

    List<NoteData> getNotes() {
        return notes;
    }
    void setNotes (List<NoteData> notes) {
        this.notes = notes;
    }

}
