package com.example.teamproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void RegionClick(View view){
        String name = view.getResources().getResourceEntryName(view.getId());
        Intent intent = new Intent(MainActivity.this, PictureActivity.class);
        intent.putExtra("region", name); //이름 전달
        startActivity(intent);
    }

    public void CheckOnClick(View view){
        Intent intent = new Intent(MainActivity.this, CheckListActivity.class);
        startActivity(intent);
    }

    public void CalOnClick(View view){
        Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
        startActivity(intent);
    }

    public void LogOutonClick(View view){ //로그아웃
        FirebaseAuth.getInstance().signOut();
        finish();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}