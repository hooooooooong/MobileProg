package com.example.teamproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class RegionInfoActivity extends AppCompatActivity {
    public static String[] ratings;
    TextView regionName;
    TextView desc;
    TextView ratingValue;
    ImageView sampleImg01;
    ImageView sampleImg02;
    Button ratingButton;
    RatingBar ratingBar;

    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n", "DefaultLocale"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_region_info);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        setTitle(name);
        float rating = ReadRatingFile(name + " rating");

        regionName = findViewById(R.id.Name);
        desc = findViewById(R.id.Description);
        ratingValue = findViewById(R.id.ratingValue);
        sampleImg01 = findViewById(R.id.sampleImg01);
        sampleImg02 = findViewById(R.id.sampleImg02);
        ratingButton = findViewById(R.id.ratingButton);
        ratingBar = findViewById(R.id.ratingBar);

        ratingBar.setRating(rating);
        ratingValue.setText(String.format("%.2f", rating) + "/5.0");

        TextView link = findViewById(R.id.Link);

        SetInfos(name);

        ratingBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    Intent reviewIntent = new Intent(getApplicationContext(), ReviewActivity.class);
                    reviewIntent.putExtra("destination", name);
                    startActivity(reviewIntent);
                }
                return true;
            }
        });

        Map<String, String> urls = new HashMap<String, String>();
        SetUrls(urls);
        link.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent urlIntent;
                Map<String, String> urls = new HashMap<String, String>();
                urlIntent = GetUrls(urls, name);
                if(urlIntent.resolveActivity(getPackageManager()) != null) startActivity(urlIntent);
            }
        });

        TextView morePictures = findViewById(R.id.morePictures);
        morePictures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gridLayoutIntent = new Intent(getApplicationContext(), GridLayoutActivity.class);
                gridLayoutIntent.putExtra("name", getIntent().getStringExtra("name"));
                startActivity(gridLayoutIntent);
            }
        });

        ratingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ratingIntent = new Intent(getApplicationContext(), RatingActivity.class);
                ratingIntent.putExtra("destination", name);
                startActivity(ratingIntent);
            }
        });
    }

    @SuppressLint("SdCardPath")
    float ReadRatingFile(String fn){
        File fp = new File("/data/data/com.example.teamproject/files" + "/" + fn);
        if(!fp.exists()) return 0.0f;

        String txt = "";

        try{
            FileInputStream fis = openFileInput(fn);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            txt = new String(buffer);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        ratings = txt.split("\n");
        return GetAvg(ratings);
    }

    float GetAvg(String[] ratings){
        float sum = 0.0f;
        float avg;

        for (String rating : ratings) {
            sum += Float.parseFloat(rating);
        }
        avg = sum/ratings.length;
        return avg;
    }

    Intent GetUrls(Map<String, String> urls, String name){
        SetUrls(urls);
        Intent urlIntent;
        urlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urls.get(name)));
        urls.clear();
        return urlIntent;
    }

    void SetUrls(Map<String, String> urls){
        String txt = "";
        String[] urlText;
        int line;
        InputStream inputStream = getResources().openRawResource(R.raw.urls);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try{
            line = inputStream.read();
            while(line != -1){
                byteArrayOutputStream.write(line);
                line = inputStream.read();
            }
            txt = byteArrayOutputStream.toString("UTF-8");
            inputStream.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        urlText = txt.split("\n");
        for (String s : urlText) {
            urls.put(s.split("-")[0], s.split("-")[1]);
        }
    }

    void ReadInfoFile(Map<String, String> text, Map<String, String> img01, Map<String, String> img02){
        String txt = "";
        String[] infos;
        int line;
        InputStream inputStream = getResources().openRawResource(R.raw.infos);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try{
            line = inputStream.read();
            while(line != -1){
                byteArrayOutputStream.write(line);
                line = inputStream.read();
            }
            txt = byteArrayOutputStream.toString("UTF-8");
            inputStream.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        infos = txt.split("newLine");
        for (String s : infos) {
            text.put(s.split("-")[0].trim(), s.split("-")[1].trim());
            img01.put(s.split("-")[0].trim(), s.split("-")[2].trim());
            img02.put(s.split("-")[0].trim(), s.split("-")[3].trim());
        }
    }

    void SetInfos(String name){
        regionName.setText(name);
        Map<String, String> text = new HashMap<>();
        Map<String, String> img01 = new HashMap<>();
        Map<String, String> img02 = new HashMap<>();
        ReadInfoFile(text, img01, img02);
        String txt = text.get(name);
        desc.setText(txt);
        sampleImg01.setImageResource(getResources().getIdentifier(img01.get(name), "drawable", getPackageName()));
        sampleImg02.setImageResource(getResources().getIdentifier(img02.get(name), "drawable", getPackageName()));
    }
}