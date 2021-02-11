package com.moves.Model.Filter;

public class Operation {
    int filterType, genreId, year;
    double rate;

    private static Operation singletonModel;

    private Operation() {
    }

    public static Operation getInstance() {
        if (singletonModel == null)
            singletonModel = new Operation();
        return singletonModel;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getFilterType() {
        return filterType;
    }

    public void setFilterType(int filterType) {
        this.filterType = filterType;
    }
}
