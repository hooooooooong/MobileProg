package com.example.teamproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class RegionInfoActivity extends AppCompatActivity {
    public TextView regionName;
    public TextView desc;
    public ImageView sampleImg01;
    public ImageView sampleImg02;

    @SuppressLint("SetTextI18n")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_region_info);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        setTitle(name);

        regionName = findViewById(R.id.Name);
        desc = findViewById(R.id.Description);
        sampleImg01 = findViewById(R.id.sampleImg01);
        sampleImg02 = findViewById(R.id.sampleImg02);

        editInfo(name);

        TextView link = findViewById(R.id.Link);

        link.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent urlIntent;
                Map<String, String> urls = new HashMap<String, String>();
                urls.put("서울숲", "https://www.youtube.com/watch?v=fTrdYm_h-3Q");
                urlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urls.get(name)));
                if(urlIntent.resolveActivity(getPackageManager()) != null) startActivity(urlIntent);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void editInfo(String name){
        regionName.setText(name);
        if(name.equals("서울숲")){
            desc.setText("'스스로 탑험하며 자연에 가까이 다가가는 시간'\n" +
                    "서울숲은 문화예술공원, 체험학습원, 생태숲, 습지생태원\n" +
                    "네 가지의 특색 있는 공간들로 구성되어 있으며\n" +
                    "한강과 맞닿아 있어 다양한 문화여가공간을 제공합니다.");

            sampleImg01.setImageResource(R.drawable.seoulforest01);
            sampleImg02.setImageResource(R.drawable.seoulforest02);
        }
        if(name.equals("여행맛")){
            desc.setText("'스스로 탑험하며 자연에 가까이 다가가는 시간'\n" +
                    "서울숲은 문화예술공원, 체험학습원, 생태숲, 습지생태원\n" +
                    "네 가지의 특색 있는 공간들로 구성되어 있으며\n" +
                    "한강과 맞닿아 있어 다양한 문화여가공간을 제공합니다.");

            sampleImg01.setImageResource(R.drawable.seoulforest01);
            sampleImg02.setImageResource(R.drawable.seoulforest02);
        }
        if(name.equals("하늘공원")){
            desc.setText("'스스로 탑험하며 자연에 가까이 다가가는 시간'\n" +
                    "서울숲은 문화예술공원, 체험학습원, 생태숲, 습지생태원\n" +
                    "네 가지의 특색 있는 공간들로 구성되어 있으며\n" +
                    "한강과 맞닿아 있어 다양한 문화여가공간을 제공합니다.");

            sampleImg01.setImageResource(R.drawable.seoulforest01);
            sampleImg02.setImageResource(R.drawable.seoulforest02);
        }
    }
}
