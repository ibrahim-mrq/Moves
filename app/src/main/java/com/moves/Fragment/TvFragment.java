package com.moves.Fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ProgressBar;

import com.moves.Activity.FilterActivity;
import com.moves.Adapter.MoveAdapter;
import com.moves.Adapter.TvAdapter;
import com.moves.Model.Filter.FilterManager;
import com.moves.Model.Filter.GenreFilter;
import com.moves.Model.Filter.Operation;
import com.moves.Model.Filter.RateFilter;
import com.moves.Model.Filter.YearFilter;
import com.moves.Model.Move;
import com.moves.Model.PaginationListener;
import com.moves.Model.Tv;
import com.moves.R;
import com.moves.network.ResponseModel.MoveResponse;
import com.moves.network.ResponseModel.TvResponse;
import com.moves.network.operations.GetData;

import java.util.ArrayList;
import java.util.function.Consumer;

public class TvFragment extends Fragment {

    private View v;
    private ProgressBar progressBar;
    private RecyclerView rv;
    private TvAdapter adapter;
    private ArrayList<Tv> list;
    private CheckBox genres_filter, rating_filter, year_filter;
    private boolean isLoading = false;
    private ArrayList<Tv> filteredList = new ArrayList<>();
    private Operation operation;
    private int p = 1;
    private int currentPage = p;
    private boolean isLastPage = false;
    private int totalPage;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_tv, container, false);

        rv = v.findViewById(R.id.tv_rv);
        progressBar = v.findViewById(R.id.tv_progressBar);

        genres_filter = v.findViewById(R.id.tv_genres_filter);
        rating_filter = v.findViewById(R.id.tv_rating_filter);
        year_filter = v.findViewById(R.id.tv_year_filter);

        list = new ArrayList<>();
        progressBar.setVisibility(View.VISIBLE);
        rv.setHasFixedSize(true);
        getMovie();
        operation = Operation.getInstance();

        genres_filter.setOnClickListener(v -> {
            if (genres_filter.isChecked()) {
                Intent intent = new Intent(getContext(), FilterActivity.class);
                intent.putExtra("intent", 1);
                intent.putExtra("type", "tv");
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

//        filteredList = new FilterManager().applyFilters(list, rateFilter, yearFilter, genreFilter);

        if (filteredList.isEmpty()) {
            adapter = new TvAdapter(list, getContext(), 1);
        } else {
            adapter = new TvAdapter(filteredList, getActivity(), 1);
        }
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getMovie() {
        GetData.getTv(getContext(), currentPage, new Consumer<TvResponse>() {
            @Override
            public void accept(TvResponse moveResponse) {
                list.addAll(moveResponse.getResults());
                progressBar.setVisibility(View.GONE);
            }
        });
    }


}