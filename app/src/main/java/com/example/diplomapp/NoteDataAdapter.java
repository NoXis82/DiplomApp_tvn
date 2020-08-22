package com.example.diplomapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NoteDataAdapter extends BaseAdapter {

    private List<NoteData> notes;
    private LayoutInflater inflater;

    public NoteDataAdapter(Context context, List<NoteData> notes) {
        if (notes == null) {
            this.notes = new ArrayList<>();

        } else {
            this.notes = notes;
        }
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
        title.setText(noteData.getTitle());
        subtitle.setText(noteData.getSubtitle());
        deadline.setText(noteData.getDeadline());
        return view;

    }


}
