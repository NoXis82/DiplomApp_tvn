package com.example.diplomapp;

import java.util.Comparator;

public class CheckDeadlineComparator implements Comparator<NoteData> {

    @Override
    public int compare(NoteData o1, NoteData o2) {
        return o1.getCheckDeadline().compareTo(o2.getCheckDeadline());
    }
}
