package com.example.teamproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
public class CustomImageAdapter extends BaseAdapter {
    private Context mContext;

    // Keep all Images in array
    public Integer[] mThumbIds = {
            R.drawable.seoulforest01, R.drawable.seoulforest02,
            R.drawable.seoulforest03, R.drawable.seoulforest02,
            R.drawable.seoulforest03, R.drawable.seoulforest02,
            R.drawable.seoulforest03, R.drawable.seoulforest02,
            R.drawable.seoulforest03, R.drawable.seoulforest02,
            R.drawable.seoulforest03, R.drawable.seoulforest02,
            R.drawable.seoulforest03, R.drawable.seoulforest02,
            R.drawable.seoulforest03
    };

    // Constructor
    public CustomImageAdapter(Context c){
        mContext = c;
    }

    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int position) {
        return mThumbIds[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(mThumbIds[position]);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
        return imageView;
    }

}
