package com.moves.Model.Filter;

import com.moves.Model.Move;

import java.util.ArrayList;

public class GenreFilter extends Filter<Integer> implements Filterable {

    @Override
    public ArrayList<Move> applyFilter(ArrayList<Move> arrayList) {
        try {
            if (value == 0) {
                return arrayList;
            } else {
                ArrayList<Move> newArrayList = new ArrayList<>();
                for (int i = 0; i < arrayList.size(); i++) {
                    int tmp = arrayList.get(i).getGenreIds().get(0);
                    if (tmp == value)
                        newArrayList.add(arrayList.get(i));
                }
                return newArrayList;
            }
        } catch (Exception e) {
            return arrayList;
        }

    }
}
