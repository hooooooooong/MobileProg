package com.example.teamproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class InfoActivity extends AppCompatActivity {
    private ListView regionListView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> listItem;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent dataIntent = getIntent();
        String region = dataIntent.getStringExtra("region");

        setContentView(R.layout.activity_region_list);
        RegionList(region);
    }

    public void RegionList(String region){
        listItem = new ArrayList<String>();
        switch (region){
            case "Jeolla":

                break;
            case "Jeju":

                break;
            case "Gyeongsang":

                break;
            case "ETC": //이미지 바꿔야함

                break;
        }
   }
}
