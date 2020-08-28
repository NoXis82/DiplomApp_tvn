package com.example.diplomapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class NoteDataAdapter extends BaseAdapter {

    private List<NoteData> notes;
    private LayoutInflater inflater;

    public NoteDataAdapter(Context context, List<NoteData> notes) {
        Comparator<NoteData> myDataComparator = new DataComparator();
        if (notes == null) {
            this.notes = new ArrayList<>();
        } else {
            this.notes = notes;
        }
        this.notes.sort(myDataComparator);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public Object getItem(int position) {
        if (position < notes.size()) {
            return notes.get(position);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.note_list_view, parent, false);
        }
        NoteData noteData = notes.get(position);
        TextView title = view.findViewById(R.id.title);
        TextView subtitle = view.findViewById(R.id.subtitle);
        TextView deadline = view.findViewById(R.id.deadline);
        if (noteData.getTitle().equals("")) {
            title.setVisibility(View.GONE);
        } else {
            title.setVisibility(View.VISIBLE);
            title.setText(noteData.getTitle());
        }
        if (noteData.getSubtitle().equals("")) {
            subtitle.setVisibility(View.GONE);
        } else {
            subtitle.setVisibility(View.VISIBLE);
            subtitle.setText(noteData.getSubtitle());
        }
        if (noteData.getCheckDeadline().equals("false")) {
            deadline.setVisibility(View.GONE);
        } else {
            deadline.setVisibility(View.VISIBLE);
            deadline.setText(noteData.getDeadline());
        }
        return view;
    }
}
