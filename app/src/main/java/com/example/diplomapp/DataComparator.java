package com.example.diplomapp;

import java.util.Comparator;

public class DataComparator implements Comparator<NoteData> {

    @Override
    public int compare(NoteData o1, NoteData o2) {
        if (o1.getCheckDeadline() && o2.getCheckDeadline()) {
            if (o1.getDeadline().equals(o2.getDeadline())) {
                return o2.getLastChangeFile().compareTo(o1.getLastChangeFile());
            } else {
                return o1.getDeadline().compareTo(o2.getDeadline());
            }
        } else if (!o1.getCheckDeadline()
                && !o2.getCheckDeadline()) {
            return o2.getLastChangeFile().compareTo(o1.getLastChangeFile());
        } else if (!o1.getCheckDeadline()) {
            return 1;
        } else {
            return -1;
        }
    }
}
