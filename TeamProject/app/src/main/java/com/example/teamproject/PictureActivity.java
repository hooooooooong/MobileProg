package com.example.teamproject;

import android.content.Intent;
import android.os.Bundle;
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

        Intent dataIntent = getIntent();
        String type = dataIntent.getStringExtra("type");

        ShowImage(type);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        ImageView iv01 = findViewById(R.id.arrow);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.translate);
        iv01.startAnimation(animation);

        iv01.setOnClickListener(new View.OnClickListener(){ //세 번째 포스터가 클릭 되었을 때
            public void onClick(View view){
                Intent intent = new Intent(PictureActivity.this, InfoActivity.class); //전환할 액티비티
                intent.putExtra("type", 0);
                startActivity(intent); //액티비티 전환
            }
        });
    }

    public void ShowImage(String type){
        ImageView iv = findViewById(R.id.img);

        if(type.equals("Mountain")) iv.setImageResource(R.drawable.middle_mountain);
        else if(type.equals("Jeju")) iv.setImageResource(R.drawable.middle_jeju);
    }
}
