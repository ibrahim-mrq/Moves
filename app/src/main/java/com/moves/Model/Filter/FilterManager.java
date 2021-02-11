package com.moves.Model.Filter;

import com.moves.Model.Move;

import java.util.ArrayList;

public class FilterManager {

    public ArrayList<Move> applyFilters(ArrayList<Move> arrayList, Filterable... filters) {
        ArrayList<Move> filteredList = new ArrayList<>(arrayList);
        for (Filterable f : filters) {
            filteredList = f.applyFilter(filteredList);
        }
        return filteredList;
    }
}
