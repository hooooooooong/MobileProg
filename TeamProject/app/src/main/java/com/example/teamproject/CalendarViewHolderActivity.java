package com.example.teamproject;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CalendarViewHolderActivity extends RecyclerView.ViewHolder implements View.OnClickListener {

    public final TextView dayOfMonth;
    private final CalendarAdapterActivity.OnItemListener onItemListener;

    public CalendarViewHolderActivity(@NonNull View itemView, CalendarAdapterActivity.OnItemListener onItemListener) {
        super(itemView);
        dayOfMonth = itemView.findViewById(R.id.cellDayText);
        this.onItemListener = onItemListener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        onItemListener.onItemClick(getAdapterPosition(), (String) dayOfMonth.getText());
    }
}
