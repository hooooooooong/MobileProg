package com.example.teamproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CalendarScheduleAdapterActivity extends ArrayAdapter<CalendarSaveScheduleActivity> {

    public CalendarScheduleAdapterActivity(@NonNull Context context, List<CalendarSaveScheduleActivity> schedules) {
        super(context, 0, schedules);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        CalendarSaveScheduleActivity schedule = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_calendarschedulecell, parent, false);
        }
        TextView scheduleCell = convertView.findViewById(R.id.scheduleCell);
        String scheduleTitle = schedule.getName() + " " + CalendarUtilsActivity.formattedDate(schedule.getDate());
        scheduleCell.setText(scheduleTitle);

        return convertView;
    }
}
