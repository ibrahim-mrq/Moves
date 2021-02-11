package com.moves.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.moves.Adapter.GenresAdapter;
import com.moves.Model.Filter.Operation;
import com.moves.Model.Genres;
import com.moves.R;
import com.moves.network.operations.GetData;

import java.util.ArrayList;
import java.util.List;

public class FilterActivity extends AppCompatActivity {

    ListView listView;
    List<Integer> rates;
    List<Integer> years;
    ArrayAdapter<Integer> arrayAdapter;
    int filterType;
    Operation singletonModel;

    GenresAdapter genresAdapter;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);


        Intent intent = getIntent();
        filterType = intent.getIntExtra("intent", 0);
        listView = findViewById(R.id.listView);
        singletonModel = Operation.getInstance();

        if (filterType == 1) {
            GetData.GET_GENRES(this, genres -> {
                ArrayList<Genres> list = new ArrayList<>();
                list.addAll(genres.getResults());
                genresAdapter = new GenresAdapter(this, list);
                listView.setAdapter(genresAdapter);
            });

            listView.setOnItemClickListener((parent, view, position, id) -> {
                singletonModel.setGenreId(GenresAdapter.data.get(position).getId());
                onBackPressed();
            });
        } else if (filterType == 2) {
            rates = new ArrayList<>();
            rates.add(0);
            rates.add(1);
            rates.add(2);
            rates.add(3);
            rates.add(4);
            rates.add(5);
            rates.add(6);
            rates.add(7);
            rates.add(8);
            rates.add(9);
            arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, rates);
            listView.setAdapter(arrayAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    singletonModel.setRate(rates.get(position));
                    onBackPressed();
                }
            });

        } else if (filterType == 3) {
            years = new ArrayList<>();
            years.add(1910);
            years.add(1920);
            years.add(1930);
            years.add(1940);
            years.add(1950);
            years.add(1960);
            years.add(1970);
            years.add(1980);
            years.add(1990);
            years.add(2000);
            years.add(2010);
            years.add(2020);
            arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, years);
            listView.setAdapter(arrayAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    singletonModel.setYear(years.get(position));
                    onBackPressed();
                }
            });
        }

    }
}