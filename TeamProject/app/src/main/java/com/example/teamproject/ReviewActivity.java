package com.example.teamproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
        String reviewFile = name + " review";
        String ratingFile = name + " rating";
        ResetFiles(reviewFile); //파일 내용 초기화
        ResetFiles(ratingFile);

        for(int i=reviews.length-1;i>=0;i--){
            if(reviews.length - i > 40) return; //최신순으로 40개만 출력

            UpdateReviewFile(reviewFile, reviews[i]);
            UpdateRatingFile(ratingFile, RegionInfoActivity.ratings[i]);
            //평점과 후기는 40개까지만 파일에 유지

            Log.v("reivew", reviews[i]);
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

    void ResetFiles(String fn){
        try{
            FileOutputStream fos = openFileOutput(fn, Context.MODE_PRIVATE);
            fos.write("".getBytes());
            fos.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    void UpdateReviewFile(String fn, String txt){
        try{
            FileOutputStream fos = openFileOutput(fn, Context.MODE_APPEND);
            fos.write(txt.getBytes());
            fos.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    void UpdateRatingFile(String fn, String txt){
        try{
            FileOutputStream fos = openFileOutput(fn, Context.MODE_APPEND);
            fos.write(txt.getBytes());
            fos.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
