package com.example.teamproject;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class CheckListActivity extends AppCompatActivity {
    private ListView checkListView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> listItem;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_checklist);
        CheckList();

    }

    public void CheckList(){
        listItem = new ArrayList<String>();
        String[] checkList = {"결제 수단", "카메라", "보조배터리", "상비약", "멀티어댑터", "티슈", "옷",
                "속옷", "잠옷", "신발", "양말", "수건", "화장품", "세면도구", "계절용품", "우산", "충전기", " 예약확인"};

        for(int i=0;i<checkList.length;i++) listItem.add(checkList[i]);

        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_multiple_choice, listItem);
        checkListView = findViewById(R.id.listView1);
        checkListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        checkListView.setAdapter(adapter);
    }
}
