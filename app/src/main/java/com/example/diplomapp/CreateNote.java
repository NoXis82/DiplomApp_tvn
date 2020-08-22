package com.example.diplomapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class CreateNote extends AppCompatActivity {

    private EditText titleEdit;
    private EditText bodyNote;
    private CheckBox checkDeadline;
    private LinearLayout dateLine;
    private EditText dateSetting;
    private Button calendarViewBtn;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private String editTextParam;
    private String editTextDateParam;
    private String editTextTimeParam;
    private Calendar todayCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_note);
        initView();
        trackCheckDate();
        showDialogSetDate();

    }

    private void initView() {
        titleEdit = findViewById(R.id.title_edit);
        bodyNote = findViewById(R.id.note_body);
        checkDeadline = findViewById(R.id.check_deadline);
        dateLine = findViewById(R.id.date_line);
        dateSetting = findViewById(R.id.set_date);
        calendarViewBtn = findViewById(R.id.btn_calendar_view);
        Toolbar myToolbarNote = findViewById(R.id.my_toolbar_note);
        setSupportActionBar(myToolbarNote);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    private void showDialogSetDate() {
        calendarViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                todayCalendar = Calendar.getInstance();
                timePickerDialog = new TimePickerDialog(
                        v.getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                editTextTimeParam = hourOfDay + ":" + minute;
                                editTextParam = editTextDateParam + " " + editTextTimeParam;
                                dateSetting.setText(editTextParam);
                            }
                        },
                        todayCalendar.get(Calendar.HOUR_OF_DAY),
                        todayCalendar.get(Calendar.MINUTE),
                        true
                );
                datePickerDialog = new DatePickerDialog(
                        v.getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                editTextDateParam = dayOfMonth + "/" + (month + 1) + "/" + year;
                                timePickerDialog.show();
                            }
                        },
                        todayCalendar.get(Calendar.YEAR),
                        todayCalendar.get(Calendar.MONTH),
                        todayCalendar.get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialog.show();
            }
        });
    }

    private void trackCheckDate() {
        checkDeadline.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    dateLine.setVisibility(View.VISIBLE);
                } else {
                    dateLine.setVisibility(View.GONE);
                    dateSetting.setText("");
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //Переделасть на case
        if(item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(getApplicationContext(), NotesList.class);
            startActivity(intent);
            this.finish();
        }
        if (item.getItemId() == R.id.action_save) {
            GenerateSalt generateId = new GenerateSalt();
            App.getNoteRepository().saveNote(
                    new NoteData(generateId.randomGenerate(), titleEdit.getText().toString(),
                            bodyNote.getText().toString(),
                            editTextParam));
            Intent intent = new Intent(getApplicationContext(), NotesList.class);
            startActivity(intent);
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_menu, menu);
        return true;
    }

}