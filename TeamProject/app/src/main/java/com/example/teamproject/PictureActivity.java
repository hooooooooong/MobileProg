package com.example.teamproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class PictureActivity extends AppCompatActivity {
    Map<String, String> pictures;
    ImageView pictureImg;
    String region;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_picture);

        pictures = new HashMap<>();
        pictureImg = findViewById(R.id.img);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Intent dataIntent = getIntent();
        region = dataIntent.getStringExtra("region");

        ImageView arrow = findViewById(R.id.arrow);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate);
        arrow.startAnimation(animation);

        SetImage(pictures);

        arrow.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                finish();
                Intent intent = new Intent(PictureActivity.this, InfoActivity.class); //전환할 액티비티
                intent.putExtra("region", region);
                startActivity(intent); //액티비티 전환
                finish();
            }
        });
    }

    public void SetImage(Map<String, String> map){
        String txt = "";
        String[] pictureText;
        int line;

        InputStream inputStream = getResources().openRawResource(R.raw.picture);
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

        pictureText = txt.split("\n");
        for(String s : pictureText){
            map.put(s.split("-")[0].trim(), s.split("-")[1].trim());
        }
        pictureImg.setImageResource(getResources().getIdentifier(map.get(region), "drawable", getPackageName()));
    }
}
