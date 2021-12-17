package com.example.teamproject;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarAdapterActivity extends RecyclerView.Adapter<CalendarViewHolderActivity> {

    // 초기 설정
    private final ArrayList<LocalDate> days;
    private final OnItemListener onItemListener;
    public CalendarAdapterActivity(ArrayList<LocalDate> days, OnItemListener onItemListener) {
        this.days = days;
        this.onItemListener = onItemListener;
    }

    // 일 선택이 가능하도록 CalendarViewHolderAcitivity 구성
    @NonNull
    @Override
    public CalendarViewHolderActivity onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.activity_calendarcell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if(days.size() > 15) {
            layoutParams.height = (int) (parent.getHeight() * 0.166666666);
        } else {
            layoutParams.height = (int) parent.getHeight();
        }

        return new CalendarViewHolderActivity(view, onItemListener, days);
    }

    // 선택된 일 전달
    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolderActivity holder, int position) {
        final LocalDate date = days.get(position);
        if(date == null) {
            holder.dayOfMonth.setText("");
        } else {
            String[] fn = CalendarActivity.filesName;
            holder.dayOfMonth.setText(String.valueOf(date.getDayOfMonth()));
            for(int i=0 ; i< fn.length ; i++) {
                if(date.toString().equals(fn[i].substring(0, 10))) {

                    holder.dayOfMonth.setTextColor(Color.GREEN);
                }
            }
            if (date.equals(CalendarUtilsActivity.selectedDate)) {
                holder.parentView.setBackgroundColor(Color.LTGRAY);
            }
        }
    }

    // 월별 일 수
    @Override
    public int getItemCount() {
        return days.size();
    }

    // 선택된 일 관련 값 전달
    public interface OnItemListener {
        void onItemClick(int position, LocalDate date);
    }
}
