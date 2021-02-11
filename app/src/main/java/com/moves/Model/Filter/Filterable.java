package com.moves.Model.Filter;

import com.moves.Model.Move;

import java.util.ArrayList;

public interface Filterable {
    ArrayList<Move> applyFilter(ArrayList<Move> arrayList);
}
