package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

public class AddNoteActivity extends BaseActivity implements View.OnClickListener {
    protected EditText title;
    protected EditText content;
    protected TextView datetime;
    protected Button add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        initView();
    }

    String noteTime= " ";
    int hours,mins;
    public void onClick(View view) {
        if (view.getId() == R.id.add) {
            String titleS = title.getText().toString();
            String contentS = content.getText().toString();
            Log.e("title",titleS );

            if(titleS.isEmpty() || contentS.isEmpty() ||datetime.getText().toString().isEmpty())
            {
                showMessage("Please fill all required fields ","Ok");
            }

            else{
                Note note =new Note(titleS,contentS,noteTime);
                MyDataBase.getInstance(this)
                        .notesDao()
                        .addNote(note);
                addNoteAlarm();
                showMessage(R.string.note_added_successfully, R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                            }
                        },false);
            }



        } else if (view.getId() == R.id.datetime) {
            Calendar calendar = Calendar.getInstance();
            TimePickerDialog datePickerDialog =new TimePickerDialog(
                    this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    noteTime= hourOfDay+ " : "+minute;
                    datetime.setText(noteTime);
                    hours = hourOfDay ;
                    mins = minute;
                }
            },calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE)
                    ,false);
            datePickerDialog.show();
        }
    }

    private void addNoteAlarm() {
        Intent alarmIntent = new Intent(this,NoteAlarmBroadCastReciever.class);
        alarmIntent.putExtra("title",title.getText().toString());
        alarmIntent.putExtra("content",content.getText().toString());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,alarmIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = ((AlarmManager) getSystemService(ALARM_SERVICE));
        Calendar calendar  = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,hours);
        calendar.set(Calendar.MINUTE,mins);
        long difference = calendar.getTimeInMillis() - System.currentTimeMillis();
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+difference,pendingIntent);
    }

    private void initView() {
        title = (EditText) findViewById(R.id.title);
        content = (EditText) findViewById(R.id.content);
        datetime = (TextView) findViewById(R.id.datetime);
        datetime.setOnClickListener( AddNoteActivity.this);
        add = (Button) findViewById(R.id.add);
        add.setOnClickListener(AddNoteActivity.this);
    }
}
