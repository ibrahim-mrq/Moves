package com.moves.Fragment;

import android.content.Intent;
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
import com.moves.Adapter.TrendingAdapter;
import com.moves.Model.Filter.FilterManager;
import com.moves.Model.Filter.GenreFilter;
import com.moves.Model.Filter.Operation;
import com.moves.Model.Filter.RateFilter;
import com.moves.Model.Filter.YearFilter;
import com.moves.Model.Move;
import com.moves.Model.PaginationListener;
import com.moves.Model.Trending;
import com.moves.R;
import com.moves.network.ResponseModel.TrendingResponse;
import com.moves.network.operations.GetData;
import com.moves.network.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.function.Consumer;

public class TrendingFragment extends Fragment {
    private View v;
    private ProgressBar progressBar;
    private RecyclerView rv;
    private TrendingAdapter adapter;
    private ArrayList<Trending> list;
    private int p = 1;
    private int currentPage = p;
    private boolean isLastPage = false;
    private int totalPage;
    private boolean isLoading = false;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_trending, container, false);

        rv = v.findViewById(R.id.trending_rv);
        progressBar = v.findViewById(R.id.trending_progressBar);

        list = new ArrayList<>();
        progressBar.setVisibility(View.VISIBLE);
        rv.setHasFixedSize(true);

        getMovie();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(layoutManager);

        rv.addOnScrollListener(new PaginationListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage = currentPage + 1;
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
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getMovie() {
        GetData.getTrending(getActivity(), new Consumer<TrendingResponse>() {
            @Override
            public void accept(TrendingResponse trendingResponse) {
                totalPage = trendingResponse.getTotalPages();
                list.addAll(trendingResponse.getResults());
                progressBar.setVisibility(View.GONE);
                adapter = new TrendingAdapter(list, getActivity(), 1);
                rv.setAdapter(adapter);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (currentPage != p) adapter.removeLoading();
                        adapter.addItems(list);
                        if (currentPage < totalPage) {
                            adapter.addLoading();
                        } else {
                            isLastPage = true;
                        }
                        isLoading = false;
                    }
                }, 1500);

                adapter.notifyDataSetChanged();
            }
        });

    }

}