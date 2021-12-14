package com.example.teamproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ReviewActivity extends AppCompatActivity {
    String name;
    ListView myList;
    ArrayList<HashMap<String, String>> list;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        Intent getName = getIntent();
        name = getName.getStringExtra("destination");
        myList = findViewById(R.id.reviewList);
        list = new ArrayList<HashMap<String, String>>();

        ReadReviewFile(name + " review");
    }

    @SuppressLint("SdCardPath")
    void ReadReviewFile(String fn){
        File fp = new File("/data/data/com.example.teamproject/files" + "/" + fn);
        if(!fp.exists()) return;

        String txt = "";
        String[] reviews;

        try{
            FileInputStream fis = openFileInput(fn);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            txt = new String(buffer);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        reviews = txt.split("\n");
        SetAdapter(reviews);
    }

    void SetAdapter(String[] reviews){
        for(int i=0;i<reviews.length;i++){
            HashMap<String, String> item = new HashMap<String, String>();
            item.put("review", reviews[i].split("//")[0]);
            item.put("info", "★ " + RegionInfoActivity.ratings[i] + " | " + reviews[i].split("//")[1]);

            list.add(item);
        }
        String[] from = {"review", "info"};
        int[] to = new int[] {android.R.id.text1, android.R.id.text2};

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, list, android.R.layout.simple_list_item_2, from, to);
        myList.setAdapter(simpleAdapter);
    }
}
