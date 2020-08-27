package com.example.diplomapp;

import java.util.Comparator;

public class LastChangeComparator implements Comparator<NoteData> {

    @Override
    public int compare(NoteData o1, NoteData o2) {
        return o2.getLastChangeFile().compareTo(o1.getLastChangeFile());
    }
}
