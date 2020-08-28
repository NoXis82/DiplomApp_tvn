package com.example.diplomapp;

import java.util.Comparator;

public class CheckDeadlineComparator implements Comparator<NoteData> {

    @Override
    public int compare(NoteData o1, NoteData o2) {
        if(Boolean.parseBoolean(o1.getCheckDeadline())
                && Boolean.parseBoolean(o2.getCheckDeadline())) {
            return 1;
        }
       return 0;
    }
}
