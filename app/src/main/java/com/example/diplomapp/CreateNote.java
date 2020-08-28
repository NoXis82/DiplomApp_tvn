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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

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
    private String lastChangeFile;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_note);
        initView();
        setDateNow();
        trackCheckDate();
        showDialogSetDate();
        checkIntentExtra();
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

    private void setDateNow() {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.ENGLISH);
        lastChangeFile = df.format(Calendar.getInstance().getTime());

    }

    private void checkIntentExtra() {
        intent = getIntent();
        if (intent.getExtras() != null) {
            titleEdit.setText(intent.getStringExtra("title"));
            bodyNote.setText(intent.getStringExtra("subtitle"));
            if (Objects.equals(intent.getStringExtra("checkDeadline"), "true")) {
                checkDeadline.setChecked(true);
                dateSetting.setText(intent.getStringExtra("deadline"));
            }
        }
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
                                String minuteStr = String.valueOf(minute);
                                if (minute < 10) {
                                    minuteStr = 0 + minuteStr;
                                }
                                editTextTimeParam = hourOfDay + ":" + minuteStr;
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
                            public void onDateSet(DatePicker view,
                                                  int year,
                                                  int month,
                                                  int dayOfMonth) {
                                    String dayStr = String.valueOf(dayOfMonth);
                                    String monthStr = String.valueOf(month + 1);
                                    if (dayOfMonth < 10) {
                                        dayStr = 0 + dayStr;
                                    }
                                    if (month < 10) {
                                        monthStr = 0 + monthStr;
                                    }
                                    editTextDateParam = dayStr + "-" + monthStr + "-" + year;
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
                if (isChecked) {
                    dateLine.setVisibility(View.VISIBLE);
                } else {
                    dateLine.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(getApplicationContext(), NotesList.class);
            startActivity(intent);
            finish();
        }
        if (item.getItemId() == R.id.action_save) {
            NoteData noteSave;
            String idNote;
            if (intent.getStringExtra("id") != null) {
                idNote = intent.getStringExtra("id");
            } else {
                GenerateSalt generateId = new GenerateSalt();
                idNote = generateId.randomGenerate();
            }
            noteSave = new NoteData(idNote,
                    titleEdit.getText().toString(),
                    bodyNote.getText().toString(),
                    String.valueOf(checkDeadline.isChecked()),
                    dateSetting.getText().toString(), lastChangeFile);
            App.getNoteRepository().saveNote(noteSave);
            Intent intent = new Intent(getApplicationContext(), NotesList.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_menu, menu);
        return true;
    }
}