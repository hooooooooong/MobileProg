package com.example.teamproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class InfoActivity extends AppCompatActivity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent dataIntent = getIntent();
        String region = dataIntent.getStringExtra("region");

        setContentView(R.layout.activity_region_list);
        RegionList(region);
    }

    public void RegionList(String region){
        ListView regionListView = findViewById(R.id.regionListView);
        ArrayAdapter<String> adapter;
        ArrayList<String> destinations = new ArrayList<>();

        if(region.equals("Seoul")){
            destinations.addAll(Arrays.asList("서울숲", "낙산공원", "하늘공원"));
        }
        else if(region.equals("Gyeonggi")){
            destinations.addAll(Arrays.asList("대부도 바다향기 수목원", "평강랜드", "물의정원"));
        }
        else if(region.equals("Chungcheong")){
            destinations.addAll(Arrays.asList("구담봉", "옥순봉", "단양강 잔도", "제천 의림지와 제림", "예당호 출렁다리", "태안 빛축제"));
        }
        else if(region.equals("Gangwon")){
            destinations.addAll(Arrays.asList("원대리 자작나무 숲", "곰배령", "상도문 돌담마을"));
        }
        else if(region.equals("Jeolla")){
            destinations.addAll(Arrays.asList("강천산 단월야행", "선유도", "채석강", "황룡강 생태공원", "순천만 습지", "화순 세량지"));
        }
        else if(region.equals("Jeju")){
            destinations.addAll(Arrays.asList("사려니숲길", "성산일출봉", "섭지코지"));
        }
        else if(region.equals("Gyeongsang")){
            destinations.addAll(Arrays.asList("호미반도 해안둘레길", "국제 밤하늘 보호공원", "곤륜산 활공장", "보물섬 전망대", "바람의 언덕"));
        }
        else if(region.equals("ETC")){
            destinations.addAll(Arrays.asList("인천 굴업도", "대전 상소동 산림욕장", "대구 달성공원", "광주 지산유원지", "부산 용소 웰빙공원", "울산 슬도"));
        }

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
