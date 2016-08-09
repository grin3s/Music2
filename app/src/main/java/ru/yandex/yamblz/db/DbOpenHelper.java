package ru.yandex.yamblz.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.Image;
import android.support.annotation.VisibleForTesting;

import ru.yandex.yamblz.provider.ArtistsContract;

/**
 * Created by grin3s on 08.08.16.
 */

public class DbOpenHelper extends SQLiteOpenHelper implements ArtistsContract {
    private static final int DB_VERSION = 1;

    public static final String CREATE_ARTISTS = "CREATE TABLE " + ARTISTS + " ("
            + Artists.ID + " INTEGER PRIMARY KEY, "
            + Artists.ARTIST_ID + " INTEGER UNIQUE NOT NULL,"
            + Artists.NAME + " TEXT UNIQUE NOT NULL, "
            + Artists.SMALL_COVER + " INTEGER REFERENCES " + IMAGES + "(" + Images.ID + "), "
            + Artists.LARGE_COVER + " INTEGER REFERENCES " + IMAGES + "(" + Images.ID + "), "
            + Artists.ALBUMS + " INTEGER, "
            + Artists.TRACKS + " INTEGER, "
            + Artists.LINK + " TEXT, "
            + Artists.DESCRIPTION + " TEXT"
            + ")";

    public static final String CREATE_IMAGES = "CREATE TABLE " + IMAGES + " ("
            + Images.ID + " INTEGER PRIMARY KEY, "
            + Images.PATH + " TEXT UNIQUE NOT NULL"
            + ")";

    public static final String CREATE_GENRES = "CREATE TABLE " + GENRES + " ("
            + Genres.ID + " INTEGER PRIMARY KEY, "
            + Genres.NAME + " TEXT UNIQUE NOT NULL"
            + ")";

    public static final String CREATE_ARTISTS_GENRES = "CREATE TABLE " + ARTISTS_GENRES + " ("
            + ArtistsGenres.ID + " INTEGER PRIMARY KEY, "
            + ArtistsGenres.ARTIST_ROWID + " INTEGER REFERENCES " + ARTISTS + "(" + Artists.ID + "), "
            + ArtistsGenres.GENRE_ROWID + " INTEGER REFERENCES " + GENRES + "(" + Genres.ID + ") "
            + ")";

    public DbOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.execSQL("PRAGMA foreign_keys = ON");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //PRAGMA FOREIGN KEYS = ON
        db.execSQL(CREATE_ARTISTS);

        db.execSQL(CREATE_IMAGES);

        db.execSQL(CREATE_GENRES);

        db.execSQL(CREATE_ARTISTS_GENRES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Updating for dev versions!
        db.execSQL("DROP TABLE " + ARTISTS);
        db.execSQL("DROP TABLE " + IMAGES);
        db.execSQL("DROP TABLE " + GENRES);
        db.execSQL("DROP TABLE " + ARTISTS_GENRES);
        onCreate(db);
    }
}
