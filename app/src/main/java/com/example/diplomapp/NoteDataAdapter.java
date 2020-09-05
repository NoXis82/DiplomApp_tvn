package com.example.diplomapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NoteDataAdapter extends BaseAdapter {

    private List<NoteData> notes;
    private LayoutInflater inflater;
    private View layoutNote;
    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm";

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
        layoutNote = view.findViewById(R.id.layout_note);
        layoutNote.setBackgroundResource(R.color.colorBackground);
        TextView title = view.findViewById(R.id.title);
        TextView subtitle = view.findViewById(R.id.subtitle);
        TextView deadline = view.findViewById(R.id.deadline);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT, Locale.ENGLISH);
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
        if (!noteData.getCheckDeadline()) {
            deadline.setVisibility(View.GONE);
        } else {
            deadline.setVisibility(View.VISIBLE);
            try {
                setBackgroundViewNote(noteData);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            deadline.setText(sdf.format(noteData.getDeadline()));
        }
        return view;
    }

    private void setBackgroundViewNote(NoteData noteData) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
        String formatNoteDate = sdf.format(noteData.getDeadline());
        String[] separatedDate = formatNoteDate.split(" ");
        String strNoteDate = separatedDate[0];
        String strValidDate = sdf.format(new Date().getTime());
        Date validDate = sdf.parse(strValidDate);
        Date deadlineDate = sdf.parse(strNoteDate);
        assert validDate != null;
        if (validDate.equals(deadlineDate)) {
            layoutNote.setBackgroundResource(R.color.today_deadline);
        } else if (validDate.after(deadlineDate)) {
            layoutNote.setBackgroundResource(R.color.after_deadline);
        } else {
            layoutNote.setBackgroundResource(R.color.colorBackground);
        }
    }

}
