package com.example.teamproject;

import static com.example.teamproject.CalendarUtilsActivity.daysInWeekArray;
import static com.example.teamproject.CalendarUtilsActivity.monthYearFromDate;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class CalendarWeekViewActivity extends AppCompatActivity implements CalendarAdapterActivity.OnItemListener {

    // 초기 설정
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private ListView scheduleListView;
    ArrayAdapter<String> mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendarweekview);
        initWidgets();
        setWeekView();
    }
    private void initWidgets() {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
        scheduleListView = findViewById(R.id.scheduleListView);
    }

    // 삭제 알림 대화상자 정의
    public static class DeleteDialogFragment extends DialogFragment {
        String s1, s2, fileName;
        public void getInfo(String item, LocalDate date) {
            s1 = item;
            s2 = date.toString();
        }
        // 알림 대화상자 구성 / 파일 삭제 수행('네' 누르면)
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("일정 삭제")
                    .setMessage(s1 + " (" + s2 + ")")
                    .setPositiveButton("네", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            fileName = s2 + s1.substring(0, 11) + s1.substring(14, s1.length()) + ".txt";
                            getContext().deleteFile(fileName);
                            getActivity().finish();
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            return builder.create();
        }
    }

    // 주 단위 캘린더 구성
    private void setWeekView() {
        monthYearText.setText(monthYearFromDate(CalendarUtilsActivity.selectedDate));
        ArrayList<LocalDate> days = daysInWeekArray(CalendarUtilsActivity.selectedDate);

        CalendarAdapterActivity calendarAdapterActivity = new CalendarAdapterActivity(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapterActivity);
        setScheduleAdapter();
    }

    // 이전, 이후 주로 이동
    public void previousWeekAction(View view) {
        CalendarUtilsActivity.selectedDate = CalendarUtilsActivity.selectedDate.minusWeeks(1);
        setWeekView();
    }
    public void nextWeekAction(View view) {
        CalendarUtilsActivity.selectedDate = CalendarUtilsActivity.selectedDate.plusWeeks(1);
        setWeekView();
    }

    // 주 단위 캘린더에서 일 선택
    @Override
    public void onItemClick(int position, LocalDate date) {
        CalendarUtilsActivity.selectedDate = date;
        setWeekView();
    }

    // 일정 목록을 표시하기 위해 어댑터 뷰-리스트 뷰 사용
    @Override
    protected void onResume() {
        super.onResume();
        setScheduleAdapter();
    }
    private void setScheduleAdapter() {
        String fileContent = null;
        String[] filesName = fileList();
        ArrayList<String> filterFiles = new ArrayList<String>();
        ArrayList<String> scheduleList = new ArrayList<String>();
        // 날짜 및 시간 순으로 파일 정렬 후 선택된 날짜에 해당하는 파일만 골라내는 작업 수행
        Arrays.sort(filesName);
        for(int i=0 ; i< fileList().length ; i++) {
            if(filesName[i].substring(0, 10).equals(CalendarUtilsActivity.selectedDate.toString())) {
                filterFiles.add(filesName[i]);
            }
        }
        // 선택된 날짜에 해당하는 파일을 읽어 리스트에 저장
        File saveFile = getFilesDir();
        for(int i=0 ; i< filterFiles.size() ; i++) {
            try {
                BufferedReader buf = new BufferedReader(new FileReader(saveFile + "/" + filterFiles.get(i)));
                fileContent = buf.readLine();
                scheduleList.add(fileContent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 파일 내용으로 어댑터 뷰-리스트 뷰 구성하여 일정 목록 완성
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, scheduleList);
        scheduleListView.setAdapter(mAdapter);
        scheduleListView.setOnItemClickListener(onClickListItem);
        //ArrayList<CalendarSaveScheduleActivity> dailySchedules = CalendarSaveScheduleActivity.schedulesForDate(CalendarUtilsActivity.selectedDate);
        //CalendarScheduleAdapterActivity scheduleAdapter = new CalendarScheduleAdapterActivity(getApplicationContext(), dailySchedules);
        //scheduleListView.setAdapter(scheduleAdapter);
    }

    // 일정 목록 항목 선택 시 삭제 알림 대화상자 표시
    private AdapterView.OnItemClickListener onClickListItem = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            String item = mAdapter.getItem(position);

            DeleteDialogFragment ddf = new DeleteDialogFragment();
            ddf.getInfo(item, CalendarUtilsActivity.selectedDate);
            ddf.show(getSupportFragmentManager(), "DeleteDialog");
        }
    };

    // '일정 추가' 버튼 클릭 시 일정을 추가하는 화면으로 이동
    public void newScheduleAction(View view) {
        startActivity(new Intent(this, CalendarScheduleActivity.class));
    }
}
