package com.example.teamproject;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GridLayoutActivity extends AppCompatActivity {
    public static Map<String, ArrayList<String>> map;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_layout);

        if(map == null){
            map = new HashMap<>();
            setMap();
        }

        GridView gridView = (GridView) findViewById(R.id.grid_view);

        CustomImageAdapter adapter = makeAdapter(getApplicationContext(), getIntent().getStringExtra("name"));
        FullImageActivity.imageAdapter = adapter;
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // Sending image id to FullScreenActivity
                Intent intent = new Intent(getApplicationContext(), FullImageActivity.class);
                intent.putExtra("name", getIntent().getStringExtra("name"));
                // passing array index
                intent.putExtra("id", position);
                startActivity(intent);
            }
        });
    }

    public CustomImageAdapter makeAdapter(Context c, String regionName) {
        CustomImageAdapter adapter = new CustomImageAdapter(c);

        ArrayList<String> list = map.get(regionName);

        for(String s: list){
            int id = getResources().getIdentifier(s, "drawable", getPackageName());
            adapter.addImg(id);
        }

        return adapter;
    }

    private void setMap(){
        String txt = "";
        String[] regionImg;
        int line;
        InputStream inputStream = getResources().openRawResource(R.raw.imgs);
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

        regionImg = txt.split("\n");
        for(String s : regionImg){
            String[] imgTxt = s.split("-");
            String key = imgTxt[0];
            ArrayList<String> list = new ArrayList<>();

            for(int i = 1; i < imgTxt.length; i++){
                list.add(imgTxt[i].trim());
            }

            map.put(key, list);
        }
    }
}
