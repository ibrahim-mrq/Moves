package com.moves.Model.Filter;

import android.content.Intent;

import com.moves.Model.Move;

import java.util.ArrayList;

public class YearFilter extends Filter<Integer> implements Filterable {

    @Override
    public ArrayList<Move> applyFilter(ArrayList<Move> arrayList) {
        if (value == 0) {
            return arrayList;
        }
        else {
            ArrayList<Move> newArrayList = new ArrayList<>();
            for (int i = 0; i < arrayList.size(); i++) {
                try {
                    String a = arrayList.get(i).getReleaseDate().substring(0, 4);
                    int as = Integer.parseInt(a);
//                int tmp = arrayList.get(i).getReleaseDate();
                    if (value <= as)
                        newArrayList.add(arrayList.get(i));
                } catch (Exception e) {
                }
            }
            return newArrayList;
        }
    }
}
