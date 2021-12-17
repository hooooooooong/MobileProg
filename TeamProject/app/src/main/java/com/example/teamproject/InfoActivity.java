package com.example.teamproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class InfoActivity extends AppCompatActivity{
    Map<String, String> regionMap;
    ListView regionListView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_region_list);

        Intent dataIntent = getIntent();
        String region = dataIntent.getStringExtra("region");

        regionMap = new HashMap<>();
        regionListView = findViewById(R.id.regionListView);

        ReadFile(regionMap, region);
    }

    void ReadFile(Map<String, String> map, String region){
        String txt = "";
        String[] regionsText;
        int line;
        InputStream inputStream = getResources().openRawResource(R.raw.regions);
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
        regionsText = txt.split("\n");
        for(String s:regionsText){
            map.put(s.split("-")[0].trim(), s.split("-")[1].trim());
        }
        SetAdapter(map, region);
    }

    void SetAdapter(Map<String, String> map, String region){
        String[] regions = map.get(region).split(",");
        regionListView = findViewById(R.id.regionListView);
        ArrayAdapter<String> adapter;
        ArrayList<String> destinations = new ArrayList<>();
        destinations.addAll(Arrays.asList(regions));

        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, destinations);
        regionListView.setAdapter(adapter);

        regionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(InfoActivity.this, RegionInfoActivity.class);
                intent.putExtra("name", adapter.getItem(position));
                startActivity(intent);
            }
        });
    }
}
