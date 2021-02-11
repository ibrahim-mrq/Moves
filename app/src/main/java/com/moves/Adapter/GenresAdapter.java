package com.moves.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.moves.Model.Genres;
import com.moves.R;


import java.util.ArrayList;

public class GenresAdapter extends BaseAdapter {

    private Activity activity;
    public static ArrayList<Genres> data;

    public GenresAdapter(Activity activity, ArrayList<Genres> data) {
        this.activity = activity;
        GenresAdapter.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View root = LayoutInflater.from(activity).inflate(R.layout.custom_genre_design, null, false);


        final TextView name = root.findViewById(R.id.genre_tv);

        name.setText(data.get(position).getName());

        return root;
    }
}
