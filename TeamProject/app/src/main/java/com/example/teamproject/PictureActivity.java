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

        arrow.setOnClickListener(new View.OnClickListener(){ //세 번째 포스터가 클릭 되었을 때
            public void onClick(View view){
                Intent intent = new Intent(PictureActivity.this, InfoActivity.class); //전환할 액티비티
                intent.putExtra("region", type);
                startActivity(intent); //액티비티 전환
            }
        });
    }

    public void ShowImage(String type){
        ImageView picture = findViewById(R.id.img);
        switch(type){
/*            case "Seoul":
                picture.setImageResource(R.drawable.middle_jeju);
                break;
            case "Gyeonggi":
                picture.setImageResource(R.drawable.middle_jeju);
                break;
            case "Chungcheong":
                picture.setImageResource(R.drawable.middle_jeju);
                break;
            case "Gangwon":
                picture.setImageResource(R.drawable.middle_jeju);
                break;*/
            case "Jeolla":
                picture.setImageResource(R.drawable.pinkmuhly);
                break;
            case "Jeju":
                picture.setImageResource(R.drawable.middle_jeju);
                break;
            case "Gyeongsang":
                picture.setImageResource(R.drawable.hillofthewind);
                break;
            case "ETC": //이미지 바꿔야함
                picture.setImageResource(R.drawable.dokdo);
                break;
        }
    }
}
