package com.moves.network.ResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.moves.Model.Genres;
import com.moves.Model.Move;

import java.util.ArrayList;

public class GenresResponse {


    @SerializedName("genres")
    @Expose
    private ArrayList<Genres> results;

    public ArrayList<Genres> getResults() {
        return results;
    }

    public void setResults(ArrayList<Genres> results) {
        this.results = results;
    }
}
