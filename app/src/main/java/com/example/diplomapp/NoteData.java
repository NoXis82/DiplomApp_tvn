package com.example.diplomapp;

import java.util.Date;

public class NoteData {
    private String id;
    private String title;
    private String subtitle;
    private Date deadline;
    private boolean checkDeadline;
    private Date lastChangeFile;

    public NoteData (String id,
                     String title,
                     String subtitle,
                     boolean checkDeadline,
                     Date deadline,
                     Date lastChangeFile) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.checkDeadline = checkDeadline;
        this.deadline = deadline;
        this.lastChangeFile = lastChangeFile;

    }
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public boolean getCheckDeadline() {
        return checkDeadline;
    }

    public Date getDeadline() {
        return deadline;
    }

    public Date getLastChangeFile() {
        return lastChangeFile;
    }




    @Override
    public String toString() {
        return "NoteData{" +
                "title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", deadline='" + deadline + '\'' +
                ", id='" + id + '\'' +
                ", checkDeadline='" + checkDeadline + '\'' +
                ", lastChangeFile='" + lastChangeFile + '\'' +
                '}';
    }
}

