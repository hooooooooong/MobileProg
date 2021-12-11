package com.example.teamproject;

import static com.example.teamproject.CalendarUtilsActivity.daysInMonthArray;
import static com.example.teamproject.CalendarUtilsActivity.daysInWeekArray;
import static com.example.teamproject.CalendarUtilsActivity.monthYearFromDate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarWeekViewActivity extends AppCompatActivity implements CalendarAdapterActivity.OnItemListener {

    // 초기 설정
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private ListView scheduleListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendarweekview);
        initWidgets();
        setWeekView();
    }

    // 레이아웃 요소 가져오기
    private void initWidgets() {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
        scheduleListView = findViewById(R.id.scheduleListView);
    }

    // 월 달력 셀 구성
    private void setWeekView() {
        monthYearText.setText(monthYearFromDate(CalendarUtilsActivity.selectedDate));
        ArrayList<LocalDate> days = daysInWeekArray(CalendarUtilsActivity.selectedDate);

        CalendarAdapterActivity calendarAdapterActivity = new CalendarAdapterActivity(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapterActivity);
        setScheduleAdapter();
    }

    public void previousWeekAction(View view) {
        CalendarUtilsActivity.selectedDate = CalendarUtilsActivity.selectedDate.minusWeeks(1);
        setWeekView();
    }

    public void nextWeekAction(View view) {
        CalendarUtilsActivity.selectedDate = CalendarUtilsActivity.selectedDate.plusWeeks(1);
        setWeekView();
    }

    // 일 선택
    @Override
    public void onItemClick(int position, LocalDate date) {
        CalendarUtilsActivity.selectedDate = date;
        setWeekView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setScheduleAdapter();
    }

    private void setScheduleAdapter() {
        ArrayList<CalendarSaveScheduleActivity> dailySchedules = CalendarSaveScheduleActivity.schedulesForDate(CalendarUtilsActivity.selectedDate);
        CalendarScheduleAdapterActivity scheduleAdapter = new CalendarScheduleAdapterActivity(getApplicationContext(), dailySchedules);
        scheduleListView.setAdapter(scheduleAdapter);
    }

    public void newScheduleAction(View view) {
        startActivity(new Intent(this, CalendarScheduleActivity.class));
    }
}
