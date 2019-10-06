package com.example.notesapp;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.notesapp.DataBase.Model.Note;
import com.example.notesapp.DataBase.MyDataBase;
import com.example.notesapp.base.BaseActivity;

import java.util.Calendar;

public class EditNoteActivity extends BaseActivity implements View.OnClickListener {
    protected EditText title;
    protected EditText content;
    protected TextView datetime;
    protected Button add;
    Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_edit_note);
        this.note = NotesActivity.commingNote;
        initView();
        title.setHint(this.note.getTitle());
        content.setHint(this.note.getContent());

    }

    String noteTime = " ";

    public void onClick(View view) {
        if (view.getId() == R.id.edit) {
            String titleS = title.getText().toString();
            String contentS = content.getText().toString();
            Log.e("title", titleS);

            if (titleS.isEmpty()) {
                titleS = this.note.getTitle();
            }
            if (contentS.isEmpty() || datetime.getText().toString().isEmpty()) {
                contentS = this.note.getContent();
            }
            Log.e("dateTime", datetime.getText().toString());
            if (datetime.getText().toString().isEmpty()) {
                noteTime = this.note.getDateTime();
            }

            {
                Note note = new Note(titleS, contentS, noteTime);
                MyDataBase.getInstance(this)
                        .notesDao()
                        .updateNote(note.getTitle(), note.getContent(), note.getDateTime());
                showMessage(R.string.note_edited_successfully, R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                            }
                        }, false);
            }


        } else if (view.getId() == R.id.datetime) {
            Calendar calendar = Calendar.getInstance();
            TimePickerDialog datePickerDialog = new TimePickerDialog(
                    this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    noteTime = hourOfDay + " : " + minute;
                    datetime.setText(noteTime);
                }
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE)
                    , false);
            datePickerDialog.show();
        }
    }

    private void initView() {
        title = (EditText) findViewById(R.id.title);
        content = (EditText) findViewById(R.id.content);
        datetime = (TextView) findViewById(R.id.datetime);
        datetime.setOnClickListener(EditNoteActivity.this);
        add = (Button) findViewById(R.id.edit);
        add.setOnClickListener(EditNoteActivity.this);
    }

}
