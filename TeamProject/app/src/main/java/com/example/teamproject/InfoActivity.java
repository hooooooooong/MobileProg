package com.example.teamproject;

import android.app.ActionBar;
import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class InfoActivity extends AppCompatActivity {

    ListView listView1;
    ArrayAdapter<String> adapter;
    ArrayList<String> listItem;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent dataIntent = getIntent();
        int type = dataIntent.getIntExtra("type", -1);

        if(type == 0) setContentView(R.layout.activity_mountain);
        if(type == 1) {
            setContentView(R.layout.activity_checklist);
            setTitle("Check List");
            Check();
        }
    }

    public void Check(){
        listItem = new ArrayList<String>();
        String[] checkList = {"결제 수단", "카메라", "보조배터리", "상비약", "멀티어댑터", "티슈", "옷",
        "속옷", "잠옷", "신발", "양말", "수건", "화장품", "세면도구", "계절용품", "우산", "충전기", " 예약확인"};

        for(int i=0;i<checkList.length;i++) listItem.add(checkList[i]);

        adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_multiple_choice,listItem);
        listView1 = findViewById(R.id.listView1);
        listView1.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView1.setAdapter(adapter);

    }
}
