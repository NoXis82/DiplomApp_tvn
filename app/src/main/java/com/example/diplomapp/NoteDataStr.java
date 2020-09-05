package com.example.diplomapp;

public class NoteDataStr {
    private String title;
    private String subtitle;
    private String deadline;
    private String id;
    private String checkDeadline;
    private String lastChangeFile;

    public NoteDataStr (String id,
                     String title,
                     String subtitle,
                     String checkDeadline,
                     String deadline,
                     String lastChangeFile) {
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

    public String getCheckDeadline() {
        return checkDeadline;
    }

    public String getDeadline() {
        return deadline;
    }

    public String getLastChangeFile() {
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
