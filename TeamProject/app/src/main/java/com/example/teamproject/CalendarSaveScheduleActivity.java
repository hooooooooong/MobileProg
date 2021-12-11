package com.example.teamproject;

import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarSaveScheduleActivity {

    public static ArrayList<CalendarSaveScheduleActivity> schedulesList = new ArrayList<>();
    public static ArrayList<CalendarSaveScheduleActivity> schedulesForDate(LocalDate date) {
        ArrayList<CalendarSaveScheduleActivity> schedules = new ArrayList<>();

        for(CalendarSaveScheduleActivity schedule : schedulesList) {
            if(schedule.getDate().equals(date)) {
                schedules.add(schedule);
            }
        }

        return schedules;
    }

    private String name;
    private LocalDate date;
    private String time;

    public CalendarSaveScheduleActivity(String name, LocalDate date, String time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
