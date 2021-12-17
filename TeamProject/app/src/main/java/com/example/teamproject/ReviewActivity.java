package com.example.teamproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ReviewActivity extends AppCompatActivity {
    String name;
    ListView listView;
    ArrayList<HashMap<String, String>> list;
    HashMap<String, String> item;
    DatabaseReference mDatabase;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        Intent getName = getIntent();
        name = getName.getStringExtra("destination");
        listView = findViewById(R.id.reviewList);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("reviews").child(name).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list = new ArrayList<HashMap<String, String>>();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    item = new HashMap<String, String>();
                    Reviews reviews = postSnapshot.getValue(Reviews.class);
                    item.put("review", reviews.review);
                    item.put("rating", "★ " + Double.toString(reviews.rating) + " | " + reviews.date);
                    list.add(0, item);
                }
                SetAdapter(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    void SetAdapter(ArrayList<HashMap<String, String>> list){
        String[] from = {"review", "rating"};
        int[] to = new int[] {android.R.id.text1, android.R.id.text2};

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, list, android.R.layout.simple_list_item_2, from, to);
        listView.setAdapter(simpleAdapter);
    }
}
