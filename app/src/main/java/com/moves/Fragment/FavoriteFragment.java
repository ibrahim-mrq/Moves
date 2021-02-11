package com.moves.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.moves.Activity.DetailActivity;
import com.moves.Adapter.MoveAdapter;
import com.moves.DB.Database;
import com.moves.Model.Move;
import com.moves.R;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class FavoriteFragment extends Fragment {

    private View v;
    private ProgressBar progressBar;
    private RecyclerView rv;
    private MoveAdapter adapter;
    private ArrayList<Move> list;
    Realm mRealm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_favorite, container, false);
        mRealm = Realm.getDefaultInstance();
        rv = v.findViewById(R.id.fav_rv);
        progressBar = v.findViewById(R.id.fav_progressBar);
        list = new ArrayList<>();
        progressBar.setVisibility(View.VISIBLE);

        read();
        adapter = new MoveAdapter(list, getContext(), 2);

        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setHasFixedSize(true);
        return v;
    }

    private void read() {

        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                RealmResults<Move> results = realm.where(Move.class).findAll();
                list.addAll(results);
                progressBar.setVisibility(View.GONE);
            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mRealm != null) {
            mRealm.close();
        }
    }


}