package com.example.teamproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    //private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void RegionClick(View view){
        String name = view.getResources().getResourceEntryName(view.getId());
        Intent intent = new Intent(MainActivity.this, PictureActivity.class);
        intent.putExtra("type", name); //이름 전달
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

    public void onClick(View view){ //로그아웃
        FirebaseAuth.getInstance().signOut();
        finish();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}