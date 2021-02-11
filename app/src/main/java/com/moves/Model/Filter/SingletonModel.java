package com.moves.Model.Filter;

public class SingletonModel {

    int filterType, genreId, year;

    String mediaType;
    double rate;

    private static SingletonModel singletonModel;

    private SingletonModel() {}

    public static SingletonModel getInstance(){
        if(singletonModel == null)
            singletonModel = new SingletonModel();
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

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public int getFilterType() {
        return filterType;
    }

    public void setFilterType(int filterType) {
        this.filterType = filterType;
    }
}
