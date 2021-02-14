package com.moves.network.retrofit;

import android.util.ArrayMap;

import com.moves.Model.Genres;
import com.moves.network.ResponseModel.GenresResponse;
import com.moves.network.ResponseModel.MoveResponse;
import com.moves.network.ResponseModel.TrendingResponse;
import com.moves.network.ResponseModel.TvResponse;
import com.moves.network.ResponseModel.VideoResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitInterface {


    @GET("movie/top_rated")
    Call<MoveResponse> movie(@Query("api_key") String api_key, @Query("page") int page);

    @GET("tv/top_rated")
    Call<TvResponse> tv(@Query("api_key") String api_key, @Query("page") int page);

    @GET("{media_type}/{id}/videos")
    Call<VideoResponse> videos(@Path("media_type") String media_type, @Path("movie_id") int movie_id, @Query("api_key") String api_key);

    @GET("genre/{media_type}/list")
    Call<GenresResponse> GET_GENRES(@Path("media_type") String media_type, @Query("api_key") String api_key);


    @GET("trending/movie/day")
    Call<TrendingResponse> Trending(@Query("api_key") String api_key);

}
