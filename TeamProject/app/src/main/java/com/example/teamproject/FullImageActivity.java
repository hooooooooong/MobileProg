package com.example.teamproject;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class FullImageActivity extends AppCompatActivity {
    private ImageView imageView;
    private int position;
    private CustomImageAdapter imageAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);

        // get intent data
        Intent i = getIntent();

        // Selected image id
        position = i.getExtras().getInt("id");
        imageAdapter = new CustomImageAdapter(this);

        imageView = (ImageView) findViewById(R.id.full_image_view);
        imageView.setImageResource(imageAdapter.mThumbIds[position]);
    }


    public void onRightArrowClick(View v){
        Toast.makeText(getApplicationContext(), "next", Toast.LENGTH_SHORT).show();
        if(position + 1 >= imageAdapter.getCount()) {
            Toast.makeText(getApplicationContext(), "마지막 사진입니다.", Toast.LENGTH_SHORT).show();
            return;
        }
        imageView.setImageResource(imageAdapter.mThumbIds[++position]);
    }

    public void onLeftArrowClick(View v){
        Toast.makeText(getApplicationContext(), "prev", Toast.LENGTH_SHORT).show();
        if(position - 1 < 0) {
            Toast.makeText(getApplicationContext(), "첫번째 사진입니다.", Toast.LENGTH_SHORT).show();
            return;
        }
        imageView.setImageResource(imageAdapter.mThumbIds[--position]);
    }
}
