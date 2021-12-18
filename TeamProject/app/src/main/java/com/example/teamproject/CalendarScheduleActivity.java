package com.example.teamproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Calendar;

public class CalendarScheduleActivity extends AppCompatActivity {

    // 초기 설정
    private EditText scheduleName;
    private TextView scheduleDate, scheduleStartTime, scheduleEndTime;
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
        scheduleStartTime = findViewById(R.id.startTime);
        scheduleEndTime = findViewById(R.id.endTime);
        //scheduleTime = findViewById(R.id.scheduleTime);
    }

    // 시간선택 대화상자 정의
    public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
        TextView mT;
        public void setTextView(TextView time) { mT = time; }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Calendar cal = Calendar.getInstance();
            TimePickerDialog tpd = new TimePickerDialog(getActivity(), this, cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE), true);
            return tpd;
        }
        // 선택한 시간 텍스트 뷰에 출력
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            if(hourOfDay < 10) {
                if(minute < 10) {
                    mT.setText("0" + hourOfDay + ":0" + minute);
                } else {
                    mT.setText("0" + hourOfDay + ":" + minute);
                }
            } else {
                if(minute < 10) {
                    mT.setText(hourOfDay + ":0" + minute);
                } else {
                    mT.setText(hourOfDay + ":" + minute);
                }
            }
        }
    }

    // 저장 알림 대화상자 정의
    public static class SaveDialogFragment extends DialogFragment {

        String sDate, sName, sTime;
        public void getContent(String name, LocalDate date, String time) {
            sName = name;
            sDate = date.toString();
            sTime = time;
        }
        // 알림 대화상자 구성 / 파일 저장 수행('네' 누르면)
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("일정 저장")
                    .setMessage("날짜 : " + sDate + "\n" + "시간 : " + sTime + "\n" + "여행지 : " + sName)
                    .setPositiveButton("네", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                FileOutputStream fos = getActivity().openFileOutput(sDate+sTime+sName+".txt", Context.MODE_PRIVATE);
                                fos.write((sTime + " : " + sName).getBytes());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            getActivity().finish();
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            dialog.dismiss();
                        }
                    });
            return builder.create();
        }
    }

    // '시작 시간 선택' 버튼 클릭 시 시간선택 대화상자 표시
    public void selectStartTimeAction(View view) {
        TextView startTimeText = findViewById(R.id.startTime);

        TimePickerFragment tpf = new TimePickerFragment();
        tpf.setTextView(startTimeText);
        tpf.show(getSupportFragmentManager(), "StartTimePicker");
    }
    // '종료 시간 선택' 버튼 클릭 시 시간선택 대화상자 표시
    public void selectEndTimeAction(View view) {
        TextView endTimeText = findViewById(R.id.endTime);

        TimePickerFragment tpf = new TimePickerFragment();
        tpf.setTextView(endTimeText);
        tpf.show(getSupportFragmentManager(), "EndTimePicker");
    }

    // '일정 저장' 버튼 클릭 시 알림 대화상자 표시
    public void saveScheduleAction(View view) {
        String name = scheduleName.getText().toString();
        String startTime = scheduleStartTime.getText().toString();
        String endTime = scheduleEndTime.getText().toString();
        if(name.equals("") || startTime.equals("") || endTime.equals("")) {
            Toast.makeText(getApplicationContext(),"모든 값을 입력해주세요", Toast.LENGTH_LONG).show();
        } else {
            String time = startTime + "-" + endTime;
            //CalendarSaveScheduleActivity newSchedule = new CalendarSaveScheduleActivity(name, CalendarUtilsActivity.selectedDate, time);
            //CalendarSaveScheduleActivity.schedulesList.add(newSchedule);
            //finish();
            SaveDialogFragment sdf = new SaveDialogFragment();
            sdf.getContent(name, CalendarUtilsActivity.selectedDate, time);
            sdf.show(getSupportFragmentManager(), "SaveDialog");
        }
    }
}
