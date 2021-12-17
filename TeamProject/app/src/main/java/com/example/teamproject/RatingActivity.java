package com.example.teamproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class RatingActivity extends AppCompatActivity {
    Button complete;
    Button cancel;
    RatingBar ratingBar;
    String name;
    EditText review;
    String getDate;
    long now;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("MM-dd-EE");
    DatabaseReference mDatabase;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        complete = findViewById(R.id.completeRating);
        cancel = findViewById(R.id.cancelRating);
        ratingBar = findViewById(R.id.rating);
        review = findViewById(R.id.review);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Intent getName = getIntent();
        name = getName.getStringExtra("destination");

        now = System.currentTimeMillis();
        mDate = new Date(now);
        getDate = mFormat.format(mDate);

        InputFilter filterKor = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
                Pattern pattern = Pattern.compile("^[a-zA-Zㄱ-ㅣ가-힣0-9!?., \\u318D\\u119E\\u11A2\\u2022\\u2025a\\u00B7\\uFE55]*$");
                //영어, 한글, 일부 특수문자만 입력가능(천지인 키보드의 중간점도 입력 가능)
                if(!pattern.matcher(charSequence).matches()) return "";
                return null;
            }
        };

        review.setFilters(new InputFilter[]{filterKor});

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ratingBar.getRating() > 0.0 && review.getText().length() > 0){
                    finish();
                    SaveReviews();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "평점/한줄평이 저장되었습니다.", Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(getApplicationContext(), "평점/한줄평을 부여해주세요.", Toast.LENGTH_SHORT).show();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Toast.makeText(getApplicationContext(), "평점 부여가 취소되었습니다", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void SaveReviews(){
        float rating = ratingBar.getRating();
        String reviewText = review.getText().toString();
        Reviews reviews = new Reviews(rating, reviewText, getDate);

        mDatabase.child("reviews").child(name).push().setValue(reviews);
    }
}