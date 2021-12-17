package com.example.teamproject;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class FullImageActivity extends AppCompatActivity {
    private ImageView imageView;
    private int position;
    public static CustomImageAdapter imageAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);

        // get intent data
        Intent intent = getIntent();

        // Selected image id
        position = intent.getExtras().getInt("id");

        //ImgAdapterFactory factory = new ImgAdapterFactory();
        //imageAdapter = factory.makeAdapter(getApplicationContext(), getIntent().getStringExtra("name"));

        //imageAdapter = makeAdapter(getApplicationContext(), getIntent().getStringExtra("name"));

        imageView = (ImageView) findViewById(R.id.full_image_view);
        imageView.setImageResource(imageAdapter.mThumbIds.get(position));
    }


    public void onRightArrowClick(View v){
        if(position + 1 >= imageAdapter.getCount()) {
            Toast.makeText(getApplicationContext(), "마지막 사진입니다.", Toast.LENGTH_SHORT).show();
            return;
        }
        imageView.setImageResource(imageAdapter.mThumbIds.get(++position));
    }

    public void onLeftArrowClick(View v) {
        if (position - 1 < 0) {
            Toast.makeText(getApplicationContext(), "첫번째 사진입니다.", Toast.LENGTH_SHORT).show();
            return;
        }
        imageView.setImageResource(imageAdapter.mThumbIds.get(--position));
    }
}
