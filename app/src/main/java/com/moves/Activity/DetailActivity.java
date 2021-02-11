package com.moves.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.moves.Adapter.MoveAdapter;
import com.moves.DB.Database;
import com.moves.Model.Move;
import com.moves.R;
import com.moves.network.ResponseModel.MoveResponse;
import com.moves.network.ResponseModel.VideoResponse;
import com.moves.network.operations.GetData;
import com.moves.network.retrofit.RetrofitClient;

import java.util.function.Consumer;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;

public class DetailActivity extends
        YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener,
        YouTubePlayer.PlayerStateChangeListener, YouTubePlayer.PlaybackEventListener {

    private int a;
    private int videoId;
    private YouTubePlayerView playerView;
    private String key;
    private TextView tv_name, tv_date, tv_langueg, tv_OriginalTitle, tv_overview;
    private RatingBar ratingBar;
    private ImageView imageView;
    Realm mRealm;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mRealm = Realm.getDefaultInstance();

        playerView = findViewById(R.id.player);
        tv_name = findViewById(R.id.detail_name);
        tv_date = findViewById(R.id.detail_date);
        tv_langueg = findViewById(R.id.detail_langueg);
        tv_overview = findViewById(R.id.detail_overview);
        tv_OriginalTitle = findViewById(R.id.detail_OriginalTitle);
        ratingBar = findViewById(R.id.detail_ratingBar);
        imageView = findViewById(R.id.detail_img);

        getData();

        if (a == 2) {
            imageView.setImageResource(R.drawable.ic_favorite2);
        } else if (a == 1) {
            imageView.setImageResource(R.drawable.ic_favorite);
        }

        getVideo();

        playerView.initialize("AIzaSyCvKNN5bT5DzMgh6SQjlCHjTxzqPm_zm0o", this);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Database database = new Database(DetailActivity.this);
//                Move move = new Move();
//                move.setId(videoId);
//                move.setOriginalLanguage(langueg);
//                move.setOriginalTitle(OriginalTitle);
//                move.setOverview(overview);
//                move.setPosterPath(img);
//                move.setReleaseDate(date);
//                move.setTitle(title + "");
//                move.setVoteAverage(ratingBars);
//                database.insert(move);

                if (a == 2) {
                    delete();
                } else if (a == 1) {
                    add();
                }
                imageView.setEnabled(false);
            }
        });

    }

    private String title, date, img, langueg, overview, OriginalTitle;
    private float ratingBars;

    private void getData() {
        Intent intent = getIntent();
        videoId = intent.getIntExtra("videoId", 1);
        a = intent.getIntExtra("a", 0);
        title = intent.getStringExtra("name");
        date = intent.getStringExtra("date");
        img = intent.getStringExtra("img");
        langueg = intent.getStringExtra("langueg");
        overview = intent.getStringExtra("overview");
        OriginalTitle = intent.getStringExtra("OriginalTitle");
        ratingBars = intent.getFloatExtra("ratingBar", 2.0f);

        tv_name.setText(title);
        tv_langueg.setText(langueg);
        tv_overview.setText(overview);
        tv_OriginalTitle.setText(OriginalTitle);
        tv_date.setText(date);
        ratingBar.setRating(ratingBars);

    }

    private void add() {
        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    try {
                        Move move = new Move();
                        move.setId(videoId);
                        move.setOriginalLanguage(langueg);
                        move.setOriginalTitle(OriginalTitle);
                        move.setOverview(overview);
                        move.setPosterPath(img);
                        move.setReleaseDate(date);
                        move.setTitle(title + "");
                        move.setVoteAverage(ratingBars);
                        realm.copyToRealm(move);
                        imageView.setImageResource(R.drawable.ic_favorite2);
                    } catch (RealmPrimaryKeyConstraintException e) {
                        Toast.makeText(Realm.getApplicationContext(), "Primary Key exists, Press Update instead", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
    }

    private void delete() {
        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmResults<Move> employees = realm.where(Move.class)
                            .equalTo(Move.PROPERTY_ID, videoId).findAll();
                    employees.deleteAllFromRealm();
                    imageView.setImageResource(R.drawable.ic_favorite);
                    onBackPressed();
                }
            });
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRealm != null) {
            mRealm.close();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getVideo() {
        GetData.getVideo(DetailActivity.this, videoId, new Consumer<VideoResponse>() {
            @Override
            public void accept(final VideoResponse videoResponse) {
                key = videoResponse.getResults().get(0).getKey();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        key = "";
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, final boolean b) {
        youTubePlayer.setPlayerStateChangeListener(this);
        youTubePlayer.setPlaybackEventListener(this);
        try {
            if (!b) {
                youTubePlayer.cueVideo(key);
            }
        } catch (Exception e) {
//            youTubePlayer.cueVideo("mjPUGem9SaY");
        }

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onLoaded(String s) {

    }

    @Override
    public void onAdStarted() {

    }

    @Override
    public void onVideoStarted() {

    }

    @Override
    public void onVideoEnded() {

    }

    @Override
    public void onError(YouTubePlayer.ErrorReason errorReason) {
    }

    @Override
    public void onPlaying() {

    }

    @Override
    public void onPaused() {

    }

    @Override
    public void onStopped() {

    }

    @Override
    public void onBuffering(boolean b) {

    }

    @Override
    public void onSeekTo(int i) {

    }
}