package com.moves.Model.Filter;

import com.moves.Model.Move;

import java.util.ArrayList;

public class RateFilter extends Filter<Double> implements Filterable {

    @Override
    public ArrayList<Move> applyFilter(ArrayList<Move> arrayList) {
        try {
            if (value == 0.0) {
                return arrayList;
            } else {
                ArrayList<Move> newArrayList = new ArrayList<>();
                for (int i = 0; i < arrayList.size(); i++) {
                    double tmp = arrayList.get(i).getVoteAverage();
                    if (value <= tmp)
                        newArrayList.add(arrayList.get(i));
                }
                return newArrayList;
            }
        } catch (Exception e) {
            return arrayList;
        }

    }
}
