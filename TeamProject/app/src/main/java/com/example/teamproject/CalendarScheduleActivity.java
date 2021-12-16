package com.example.teamproject;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.time.LocalTime;

public class CalendarScheduleActivity extends AppCompatActivity {

    private EditText scheduleName, scheduleTime;
    private TextView scheduleDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendarschedule);
        initWidgets();
        scheduleDate.setText("날짜 : " + CalendarUtilsActivity.formattedDate(CalendarUtilsActivity.selectedDate));
    }
    private void initWidgets() {
        scheduleName = findViewById(R.id.scheduleName);
        scheduleDate = findViewById(R.id.scheduleDate);
        scheduleTime = findViewById(R.id.scheduleTime);
    }

    public void saveScheduleAction(View view) {
        String name = scheduleName.getText().toString();
        String time = scheduleTime.getText().toString();
        CalendarSaveScheduleActivity newSchedule = new CalendarSaveScheduleActivity(name, CalendarUtilsActivity.selectedDate, time);
        CalendarSaveScheduleActivity.schedulesList.add(newSchedule);
        finish();
    }
}
