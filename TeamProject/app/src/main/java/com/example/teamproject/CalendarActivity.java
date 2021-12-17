package com.example.teamproject;

import static com.example.teamproject.CalendarUtilsActivity.daysInMonthArray;
import static com.example.teamproject.CalendarUtilsActivity.monthYearFromDate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CalendarActivity extends AppCompatActivity implements CalendarAdapterActivity.OnItemListener {

    // 초기 설정
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    public static String[] filesName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        initWidgets();
        CalendarUtilsActivity.selectedDate = LocalDate.now();
        setMonthView();
    }
    private void initWidgets() {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
    }

    // 월 단위 캘린더 구성
    private void setMonthView() {
        monthYearText.setText(monthYearFromDate(CalendarUtilsActivity.selectedDate));
        ArrayList<LocalDate> daysInMonth = daysInMonthArray(CalendarUtilsActivity.selectedDate);

        readFile();
        CalendarAdapterActivity calendarAdapterActivity = new CalendarAdapterActivity(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapterActivity);
    }

    // 이전, 이후 달로 이동
    public void previousMonthAction(View view) {
        CalendarUtilsActivity.selectedDate = CalendarUtilsActivity.selectedDate.minusMonths(1);
        setMonthView();
    }
    public void nextMonthAction(View view) {
        CalendarUtilsActivity.selectedDate = CalendarUtilsActivity.selectedDate.plusMonths(1);
        setMonthView();
    }

    // 월 단위 캘린더에서 일 선택
    @Override
    public void onItemClick(int position, LocalDate date) {
        if(date != null) {
            CalendarUtilsActivity.selectedDate = date;
            setMonthView();
        }
    }

    // '일정 확인' 버튼 클릭 시 주 단위 캘린더로 이동
    public void weeklyAction(View view) {
        startActivity(new Intent(this, CalendarWeekViewActivity.class));
    }

    public void readFile() {
        filesName = fileList();
    }
}
