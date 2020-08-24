package com.example.diplomapp;

public class NoteData {
    private String title;
    private String subtitle;
    private String deadline;
    private String id;
    private String checkDeadline;

    public NoteData (String id, String title, String subtitle, String checkDeadline, String deadline) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.checkDeadline = checkDeadline;
        this.deadline = deadline;

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


    @Override
    public String toString() {
        return "NoteData{" +
                "id='" + id + '\'' +
                "title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", deadline='" + deadline + '\'' +
                '}';
    }
}

