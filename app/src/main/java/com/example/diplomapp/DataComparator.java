package com.example.diplomapp;

import java.util.Comparator;

public class DataComparator implements Comparator<NoteData> {

    @Override
    public int compare(NoteData o1, NoteData o2) {
        return o1.getDeadline().compareTo(o2.getDeadline());
    }
}
