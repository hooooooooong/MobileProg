package com.example.teamproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class PictureActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_picture);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Intent dataIntent = getIntent();
        String type = dataIntent.getStringExtra("type");

        ShowImage(type);

        ImageView arrow = findViewById(R.id.arrow);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate);
        arrow.startAnimation(animation);

        arrow.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                finish();
                Intent intent = new Intent(PictureActivity.this, InfoActivity.class); //전환할 액티비티
                intent.putExtra("region", type);
                startActivity(intent); //액티비티 전환
                finish();
            }
        });
    }

    public void ShowImage(String type){
        ImageView picture = findViewById(R.id.img);
        if(type.equals("Seoul")){
            picture.setImageResource(R.drawable.seoul);
        }
        else if(type.equals("Gyeonggi")){
            picture.setImageResource(R.drawable.gyeonggi);
        }
        else if(type.equals("Chungcheong")){
            picture.setImageResource(R.drawable.chungcheong);
        }
        else if(type.equals("Gangwon")){
            picture.setImageResource(R.drawable.gangwon);
        }
        else if(type.equals("Jeolla")){
            picture.setImageResource(R.drawable.pinkmuhly);
        }
        else if(type.equals("Jeju")){
            picture.setImageResource(R.drawable.middle_jeju);
        }
        else if(type.equals("Gyeongsang")){
            picture.setImageResource(R.drawable.hillofthewind);
        }
        else if(type.equals("ETC")){
            picture.setImageResource(R.drawable.dokdo);
        }
    }
}
