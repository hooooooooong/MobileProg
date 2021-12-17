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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegionInfoActivity extends AppCompatActivity {
    TextView regionName;
    TextView desc;
    TextView ratingValue;
    TextView link;
    ImageView sampleImg01;
    ImageView sampleImg02;
    Button ratingButton;
    RatingBar ratingBar;
    DatabaseReference mDatabase;
    ArrayList<String> keys;
    String name;
    double rating;

    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n", "DefaultLocale"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_region_info);

        regionName = findViewById(R.id.Name);
        desc = findViewById(R.id.Description);
        ratingValue = findViewById(R.id.ratingValue);
        sampleImg01 = findViewById(R.id.sampleImg01);
        sampleImg02 = findViewById(R.id.sampleImg02);
        ratingButton = findViewById(R.id.ratingButton);
        ratingBar = findViewById(R.id.ratingBar);
        link = findViewById(R.id.Link);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        keys = new ArrayList<>();

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        setTitle(name);

        mDatabase.child("reviews").child(name).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                keys.clear();
                rating = 0.0d;
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    keys.add(postSnapshot.getKey());
                    while (keys.size() > 50) { //후기/평점 데이터는 50개까지 유지
                        mDatabase.child("reviews").child(name).child(keys.get(0)).setValue(null);
                        keys.remove(0);
                    }
                    Reviews reviews = postSnapshot.getValue(Reviews.class);
                    rating += reviews.rating;
                    ratingBar.setRating((float)(rating/(double)keys.size()));
                    ratingValue.setText(String.format("%.2f", rating/(double)keys.size()) + "/5.0");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        SetInfo(name);

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
            urls.put(s.split("===")[0], s.split("===")[1]);
        }
    }

    void ReadInfoFile(Map<String, String> text, Map<String, String> img01, Map<String, String> img02){
        String txt = "";
        String[] info;
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
        info = txt.split("newLine");
        for (String s : info) {
            text.put(s.split("-")[0].trim(), s.split("-")[1].trim());
            img01.put(s.split("-")[0].trim(), s.split("-")[2].trim());
            img02.put(s.split("-")[0].trim(), s.split("-")[3].trim());
        }
    }

    void SetInfo(String name){
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