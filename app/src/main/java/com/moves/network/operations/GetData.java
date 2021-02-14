package com.moves.network.operations;

import android.content.Context;
import android.os.Build;
import android.util.ArrayMap;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.moves.Model.Genres;
import com.moves.R;
import com.moves.network.ResponseModel.GenresResponse;
import com.moves.network.ResponseModel.MoveResponse;
import com.moves.network.ResponseModel.TrendingResponse;
import com.moves.network.ResponseModel.TvResponse;
import com.moves.network.ResponseModel.VideoResponse;
import com.moves.network.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetData extends BaseOperation {

    public static void getMovie(final Context context, String api_key, int page, final Consumer<MoveResponse> consumer) {
        apiService.movie(api_key, page).enqueue(new Callback<MoveResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<MoveResponse> call, Response<MoveResponse> response) {
                if (response.isSuccessful()) {
                    consumer.accept(response.body());
                }
            }

            @Override
            public void onFailure(Call<MoveResponse> call, Throwable t) {
                Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void getVideo(final Context context, String media_type, int movie_id, final Consumer<VideoResponse> consumer) {
        apiService.videos(media_type, movie_id, RetrofitClient.API_KEY).enqueue(new Callback<VideoResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<VideoResponse> call, Response<VideoResponse> response) {
                if (response.isSuccessful()) {
                    consumer.accept(response.body());
                }
            }

            @Override
            public void onFailure(Call<VideoResponse> call, Throwable t) {
                Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void GET_GENRES(final Context context, String media_type, final Consumer<GenresResponse> consumer) {
        apiService.GET_GENRES(media_type, RetrofitClient.API_KEY).enqueue(new Callback<GenresResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<GenresResponse> call, Response<GenresResponse> response) {
                if (response.isSuccessful()) {
                    consumer.accept(response.body());
                }
            }

            @Override
            public void onFailure(Call<GenresResponse> call, Throwable t) {
                Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void getTv(final Context context, int page, final Consumer<TvResponse> consumer) {
        apiService.tv(RetrofitClient.API_KEY, page).enqueue(new Callback<TvResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<TvResponse> call, Response<TvResponse> response) {
                if (response.isSuccessful()) {
                    consumer.accept(response.body());
                }
            }

            @Override
            public void onFailure(Call<TvResponse> call, Throwable t) {
                Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void getTrending(final Context context, final Consumer<TrendingResponse> consumer) {
        apiService.Trending(RetrofitClient.API_KEY).enqueue(new Callback<TrendingResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<TrendingResponse> call, Response<TrendingResponse> response) {
                if (response.isSuccessful()) {
                    consumer.accept(response.body());
                }
            }

            @Override
            public void onFailure(Call<TrendingResponse> call, Throwable t) {
                Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
