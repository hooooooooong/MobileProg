package com.example.teamproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view){
        Intent intent = new Intent(MainActivity.this, PictureActivity.class);
        intent.putExtra("type", "Mountain");
        startActivity(intent);
    }

    public void JJonClick(View view){
        Intent intent_JJ = new Intent(MainActivity.this, PictureActivity.class);
        intent_JJ.putExtra("type", "Jeju");
        startActivity(intent_JJ);
    }

    public void CheckOnClick(View view){
        Intent intent_Check = new Intent(MainActivity.this, InfoActivity.class);
        intent_Check.putExtra("type", 1);
        startActivity(intent_Check);
    }
    // first Test
    // second Test
    // third Test
}