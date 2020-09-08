package com.example.diplomapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NotesList extends AppCompatActivity {

    private FloatingActionButton myFabAdd;
    private static final int EDIT_REQUEST_CODE = 345;
    private static final int ADD_REQUEST_CODE = 346;

    private static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm";
    private List<NoteData> notes = new ArrayList<>();
    private NoteDataAdapter noteDataAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);
        initView();
        clickAddFab();
        clickOnItem();
        clickLongItem();
    }

    private void initView() {
        listView = findViewById(R.id.list_item);
        myFabAdd = findViewById(R.id.fab_add);
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        try {
            notes.addAll(App.getNoteRepository().getNotes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        noteDataAdapter = new NoteDataAdapter(this, notes);
        listView.setAdapter(noteDataAdapter);
    }

    private void clickOnItem() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String idNote = notes.get(position).getId();
                Intent intent = new Intent(getApplicationContext(), CreateNote.class);
                writeToIntent(intent, App.getNoteRepository().getNoteById(idNote));
                startActivityForResult(intent, EDIT_REQUEST_CODE);
            }
        });
    }

    static void writeToIntent(Intent intent, NoteData note) {
        DateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT, Locale.ENGLISH);
        intent.putExtra("id", note.getId());
        intent.putExtra("title", note.getTitle());
        intent.putExtra("subtitle", note.getSubtitle());
        intent.putExtra("checkDeadline", note.getCheckDeadline());
        intent.putExtra("deadline", dateFormat.format(note.getDeadline()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == EDIT_REQUEST_CODE &&
                resultCode == RESULT_OK && data != null) {
            for (NoteData id : notes) {
                if (id.getId().equals(data.getStringExtra("id"))) {
                    notes.set(notes.indexOf(id),
                            readFromIntent(data));
                }
            }
        }
        if (requestCode == ADD_REQUEST_CODE &&
                resultCode == RESULT_OK && data != null) {
            notes.add(readFromIntent(data));
        }
        Comparator<NoteData> myDataComparator = new DataComparator();
        notes.sort(myDataComparator);
        noteDataAdapter.notifyDataSetChanged();
        try {
            App.getNoteRepository().getNotes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    static NoteData readFromIntent(Intent intent) {
        DateFormat df = new SimpleDateFormat(DATE_TIME_FORMAT, Locale.ENGLISH);
        Date noteDate;
        Date lastChange;
        NoteData noteDataIntent = null;
        try {
            if (Boolean.parseBoolean(intent.getStringExtra("checkDeadline"))) {
                noteDate = df.parse(intent.getStringExtra("deadline"));
            } else {
                noteDate = new Date();
            }
            lastChange = df.parse(intent.getStringExtra("lastChange"));
            noteDataIntent = new NoteData(
                    intent.getStringExtra("id"),
                    intent.getStringExtra("title"),
                    intent.getStringExtra("subtitle"),
                    Boolean.parseBoolean(intent.getStringExtra("checkDeadline")),
                    noteDate,
                    lastChange
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return noteDataIntent;
    }

    private void clickLongItem() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent,
                                           final View view,
                                           final int position,
                                           long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle(R.string.dialog_warning);
                builder.setMessage(R.string.dialog_message);
                builder.setPositiveButton(R.string.dialog_delete,
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String idNote = notes.get(position).getId();
                        App.getNoteRepository().deleteById(idNote);
                        notes.remove(position);
                        noteDataAdapter = new NoteDataAdapter(view.getContext(), notes);
                        listView.setAdapter(noteDataAdapter);
                    }
                });
                builder.setNegativeButton(R.string.dialog_cancel,
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.setCancelable(false);
                builder.create().show();
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_setting) {
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting_menu, menu);
        return true;
    }

    private void clickAddFab() {
        myFabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateNote.class);
                startActivityForResult(intent, ADD_REQUEST_CODE);
            }
        });
    }
}