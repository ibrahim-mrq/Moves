package com.moves.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.moves.Model.Move;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {

    public static final String DB_NAME = "Move.db";
    public static final int DB_VERSION = 2;

    /* TODO : >> Menu DB */
    public static final String MOVE_TB_NAME = "move";
    //    public static final String MOVE_COLUMN_adult = "adult";
//    public static final String MOVE_COLUMN_backdropPath = "backdropPath";
    public static final String MOVE_COLUMN_id = "id";
    //    public static final String MOVE_COLUMN_genreIds = "genreIds";
    public static final String MOVE_COLUMN_originalLanguage = "originalLanguage";
    public static final String MOVE_COLUMN_originalTitle = "originalTitle";
    public static final String MOVE_COLUMN_overview = "overview";
    //    public static final String MOVE_COLUMN_popularity = "popularity";
    public static final String MOVE_COLUMN_posterPath = "posterPath";
    public static final String MOVE_COLUMN_releaseDate = "releaseDate";
    public static final String MOVE_COLUMN_title = "title";
    //    public static final String MOVE_COLUMN_video = "video";
    public static final String MOVE_COLUMN_voteAverage = "voteAverage";
//    public static final String MOVE_COLUMN_voteCount = "voteCount";

    public Database(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + MOVE_TB_NAME + " " + "(" + MOVE_COLUMN_id + " INTEGER ,"
                + MOVE_COLUMN_originalLanguage + " TEXT ,"
                + MOVE_COLUMN_originalTitle + " TEXT,"
                + MOVE_COLUMN_overview + " TEXT,"
                + MOVE_COLUMN_posterPath + " TEXT,"
                + MOVE_COLUMN_releaseDate + " TEXT,"
                + MOVE_COLUMN_title + " TEXT,"
                + MOVE_COLUMN_voteAverage + " TEXT )");


//        db.execSQL("CREATE TABLE " + MOVE_TB_NAME + " " + "(" + MOVE_COLUMN_adult +
//                " TEXT ," + MOVE_COLUMN_backdropPath + " TEXT ,"
//                + MOVE_COLUMN_id + " INTEGER," + MOVE_COLUMN_genreIds + " TEXT," +
//                MOVE_COLUMN_originalLanguage + " TEXT," + MOVE_COLUMN_originalTitle
//                + MOVE_COLUMN_overview + " TEXT," + MOVE_COLUMN_popularity + " TEXT," + MOVE_COLUMN_posterPath
//                + MOVE_COLUMN_releaseDate + " TEXT," + MOVE_COLUMN_title + " TEXT," + MOVE_COLUMN_video
//                + MOVE_COLUMN_voteAverage + " TEXT," + MOVE_COLUMN_voteCount + " TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MOVE_TB_NAME);
        onCreate(db);
    }

    /// TODO : >>> Menu

    public boolean insert(Move model) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
//        cv.put(MOVE_COLUMN_adult, model.getAdult() + "");
//        cv.put(MOVE_COLUMN_backdropPath, model.getBackdropPath() + "");
        cv.put(MOVE_COLUMN_id, model.getId());
//        cv.put(MOVE_COLUMN_genreIds, model.getGenreIds() + "");
        cv.put(MOVE_COLUMN_originalLanguage, model.getOriginalLanguage() + "");
        cv.put(MOVE_COLUMN_originalTitle, model.getOriginalTitle() + "");
        cv.put(MOVE_COLUMN_overview, model.getOverview() + "");
//        cv.put(MOVE_COLUMN_popularity, model.getPopularity() + "");
        cv.put(MOVE_COLUMN_posterPath, model.getPosterPath() + "");
        cv.put(MOVE_COLUMN_releaseDate, model.getReleaseDate() + "");
        cv.put(MOVE_COLUMN_title, model.getTitle() + "");
//        cv.put(MOVE_COLUMN_video, model.getVideo() + "");
        cv.put(MOVE_COLUMN_voteAverage, model.getVoteAverage() + "");
//        cv.put(MOVE_COLUMN_voteCount, model.getVoteCount() + "");
        long result = db.insert(MOVE_TB_NAME, null, cv);
        return result != -1;
    }

    public ArrayList<Move> getAll() {
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<Move> models = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM " + Database.MOVE_TB_NAME, null);
        if (c != null && c.moveToFirst()) {
            do {
                //                int id = c.getInt(c.getColumnIndex(MOVE_COLUMN_adult));
//                String name = c.getString(c.getColumnIndex(MOVE_COLUMN_backdropPath));
                int id = c.getInt(c.getColumnIndex(MOVE_COLUMN_id));
//                String price = c.getString(c.getColumnIndex(MOVE_COLUMN_genreIds));
                String originalLanguage = c.getString(c.getColumnIndex(MOVE_COLUMN_originalLanguage));
                String originalTitle = c.getString(c.getColumnIndex(MOVE_COLUMN_originalTitle));
                String overview = c.getString(c.getColumnIndex(MOVE_COLUMN_overview));
//                int images = c.getInt(c.getColumnIndex(MOVE_COLUMN_popularity));
                String posterPath = c.getString(c.getColumnIndex(MOVE_COLUMN_posterPath));
                String releaseDate = c.getString(c.getColumnIndex(MOVE_COLUMN_releaseDate));
                String title = c.getString(c.getColumnIndex(MOVE_COLUMN_title));
//                int images = c.getInt(c.getColumnIndex(MOVE_COLUMN_video));
                float voteAverage = c.getFloat(c.getColumnIndex(MOVE_COLUMN_voteAverage));
//                int images = c.getInt(c.getColumnIndex(MOVE_COLUMN_voteCount));
                Move model = new Move();
                model.setId(id);
                model.setOriginalLanguage(originalLanguage);
                model.setOriginalTitle(originalTitle);
                model.setOverview(overview);
                model.setPosterPath(posterPath);
                model.setTitle(title + "");
                model.setReleaseDate(releaseDate);
                model.setVoteAverage(voteAverage);
                models.add(model);
            } while (c.moveToNext());
            c.close();
        }
        return models;
    }


}
