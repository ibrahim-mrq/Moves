package com.moves.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ProgressBar;

import com.moves.Activity.FilterActivity;
import com.moves.Adapter.MoveAdapter;
import com.moves.Model.Filter.FilterManager;
import com.moves.Model.Filter.GenreFilter;
import com.moves.Model.Filter.RateFilter;
import com.moves.Model.Filter.Operation;
import com.moves.Model.Filter.YearFilter;
import com.moves.Model.Move;
import com.moves.Model.PaginationListener;
import com.moves.R;
import com.moves.network.operations.GetData;
import com.moves.network.retrofit.RetrofitClient;

import java.util.ArrayList;

import io.realm.Realm;

public class MoveFragment extends Fragment {

    private View v;
    private ProgressBar progressBar;
    private RecyclerView rv;
    private MoveAdapter adapter;
    private ArrayList<Move> list;
    private int p = 1;
    private int currentPage = p;
    private boolean isLastPage = false;
    private int totalPage;
    private boolean isLoading = false;
    int itemCount = 0;
    Realm mRealm;
    CheckBox genres_filter, rating_filter, year_filter;
    ArrayList<Move> filteredList;
    Operation operation;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_move, container, false);

        rv = v.findViewById(R.id.move_rv);
        progressBar = v.findViewById(R.id.move_progressBar);

        genres_filter = v.findViewById(R.id.genres_filter);
        rating_filter = v.findViewById(R.id.rating_filter);
        year_filter = v.findViewById(R.id.year_filter);

        list = new ArrayList<>();
        progressBar.setVisibility(View.VISIBLE);
        rv.setHasFixedSize(true);

        operation = Operation.getInstance();

        getMovie();

        genres_filter.setOnClickListener(v -> {
            if (genres_filter.isChecked()) {
                Intent intent = new Intent(getContext(), FilterActivity.class);
                intent.putExtra("intent", 1);
                startActivity(intent);
            } else {
                operation.setGenreId(0);
                asd();
            }
        });

        rating_filter.setOnClickListener(v -> {
            if (rating_filter.isChecked()) {
                Intent intent = new Intent(getContext(), FilterActivity.class);
                intent.putExtra("intent", 2);
                startActivity(intent);
            } else {
                operation.setRate(0.0);
                asd();
            }
        });

        year_filter.setOnClickListener(v -> {
            if (year_filter.isChecked()) {
                Intent intent = new Intent(getContext(), FilterActivity.class);
                intent.putExtra("intent", 3);
                startActivity(intent);
            } else {
                operation.setYear(0);
                asd();
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(layoutManager);

        rv.addOnScrollListener(new PaginationListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage = currentPage + 1;
                asd();
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });


        return v;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onResume() {
        super.onResume();
        asd();
    }

    private void asd() {

        RateFilter rateFilter = new RateFilter();
        rateFilter.setValue(operation.getRate());
        YearFilter yearFilter = new YearFilter();
        yearFilter.setValue(operation.getYear());
        GenreFilter genreFilter = new GenreFilter();
        genreFilter.setValue(operation.getGenreId());

        filteredList = new FilterManager().applyFilters(list, rateFilter, yearFilter, genreFilter);

        if (filteredList.isEmpty()) {
            adapter = new MoveAdapter(list, getContext(), 1);
        } else {
            adapter = new MoveAdapter(filteredList, getActivity(), 1);
        }
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getMovie() {
        GetData.getMovie(getActivity(), RetrofitClient.API_KEY + "", currentPage, moveResponse -> {
            totalPage = moveResponse.getTotalPages();
            list.addAll(moveResponse.getResults());
            progressBar.setVisibility(View.GONE);


        });

    }


}