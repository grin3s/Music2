package ru.yandex.yamblz.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.VisibleForTesting;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import ru.yandex.yamblz.provider.ArtistsContract;

/**
 * Created by grin3s on 08.08.16.
 */

public class DbBackend implements ArtistsContract {
    private final DbOpenHelper mDbOpenHelper;

    private String IMAGE_ID_FROM_PATH_QUERY_TEMPLATE = "(SELECT " + Images.ID + " FROM " + IMAGES + " WHERE" + Images.PATH + " = " + "?)";

    private String ARTIST_INSERT_TEMPLATE = "INSERT INTO " + ARTISTS + " ("
            + Artists.ARTIST_ID + ","
            + Artists.NAME + ","
            + Artists.SMALL_COVER + ","
            + Artists.LARGE_COVER + ","
            + Artists.ALBUMS + ","
            + Artists.TRACKS + ","
            + Artists.LINK + ","
            + Artists.DESCRIPTION + ") "
            + "VALUES " + "("
            + "?,"
            + "?,"
            + IMAGE_ID_FROM_PATH_QUERY_TEMPLATE + ","
            + IMAGE_ID_FROM_PATH_QUERY_TEMPLATE + ","
            + "?,"
            + "?,"
            + "?,"
            + "?)";

    private static final String ARTISTS_JOIN_IMAGES_ON_SMALL_COVER_QUERY
            = "SELECT " + ARTISTS + "." + Artists.ID + " as id, "
            + IMAGES + "." + Images.PATH + " as small_cover, "
            + ARTISTS + "." + Artists.LARGE_COVER + " as large_cover_id, "
            + ARTISTS + "." + Artists.ARTIST_ID + " as artist_id, "
            + ARTISTS + "." + Artists.NAME + " as artist_name, "
            + ARTISTS + "." + Artists.ALBUMS + " as artist_albums, "
            + ARTISTS + "." + Artists.TRACKS + " as artist_tracks, "
            + ARTISTS + "." + Artists.LINK + " as artist_link, "
            + ARTISTS + "." + Artists.DESCRIPTION + " as artist_description FROM "
            + ARTISTS + " LEFT JOIN " + IMAGES + " ON "
            + ARTISTS + "." + Artists.SMALL_COVER + "=" + IMAGES + "." + Images.ID;

    private static final String ARTISTS_JOIN_IMAGES_ON_BOTH_COVERS
            = "SELECT T.id as id, T.small_cover as small_cover, " + IMAGES + "." + Images.PATH + " as large_cover, "
            + "artist_id, artist_name, artist_albums, artist_tracks, artist_link, artist_description "
            + "FROM (" + ARTISTS_JOIN_IMAGES_ON_SMALL_COVER_QUERY + ") T "
            + "LEFT JOIN " + IMAGES + " ON " + "T.large_cover_id = " + IMAGES + "." + Images.ID;

    private static final String ARTISTSGENRES_JOIN_GENRES
            = "SELECT " + ArtistsGenres.ARTIST_ROWID  + " as artist_rowid, " + GENRES + "." + Genres.NAME + " as genre FROM "
            + ARTISTS_GENRES + " LEFT JOIN " + GENRES + " ON "
            + ARTISTS_GENRES + "." + ArtistsGenres.GENRE_ROWID + "=" + GENRES + "." + Genres.ID;


    public static final String GET_ALL_ARTISTS_QUERY
            = "SELECT artist_id as " + ArtistMainView.ARTIST_ID + ","
            + "artist_name as " + ArtistMainView.NAME + ","
            + "X1.small_cover as " + ArtistMainView.SMALL_COVER + ","
            + "X1.large_cover as " + ArtistMainView.LARGE_COVER + ","
            + "artist_albums as " + ArtistMainView.ALBUMS + ","
            + "artist_tracks as " + ArtistMainView.TRACKS + ","
            + "artist_link as " + ArtistMainView.LINK + ","
            + "artist_description as " + ArtistMainView.DESCRIPTION + ","
            + "group_concat(genre) as " + ArtistMainView.GENRES
            + " FROM (" + ARTISTS_JOIN_IMAGES_ON_BOTH_COVERS
            + " ) X1 LEFT JOIN " + "(" + ARTISTSGENRES_JOIN_GENRES + ") X2 ON "
            + " X1.id = X2.artist_rowid group by X1.id";


    public DbBackend(Context context) {
        mDbOpenHelper = new DbOpenHelper(context);
    }

    DbBackend(DbOpenHelper dbOpenHelper) {
        mDbOpenHelper = dbOpenHelper;
    }

    private long insertIfNotExistsImage(SQLiteDatabase db, String path) {
        Long rowid = getImageRowId(path);
        if (rowid == null) {
            //this is not in the db yet
            ContentValues tmpValues = new ContentValues();
            tmpValues.put(Images.PATH, path);
            return db.insert(IMAGES, null, tmpValues);
        }
        else {
            return rowid;
        }
    }

    private long insertIfNotExistsGenre(SQLiteDatabase db, String genre) {
        Long rowid = getGenreRowid(genre);
        if (rowid == null) {
            //genre is not in db
            ContentValues tmpValues = new ContentValues();
            tmpValues.put(Genres.NAME, genre);
            return db.insert(GENRES, null, tmpValues);
        }
        else {
            return rowid;
        }

    }

    private long insertArtistsGenre(SQLiteDatabase db, long artistRowId, long genreRowId) {
        ContentValues tmpValues = new ContentValues();
        tmpValues.put(ArtistsGenres.ARTIST_ROWID, artistRowId);
        tmpValues.put(ArtistsGenres.GENRE_ROWID, genreRowId);
        return db.insert(ARTISTS_GENRES, null, tmpValues);
    }

    public void insertNewArtist(ContentValues values) {
        ContentValues tmpValues = new ContentValues();
        SQLiteDatabase db = mDbOpenHelper.getWritableDatabase();
        db.beginTransaction();

        try {
            //insert small_cover
            String small_cover = values.getAsString(ArtistMainView.SMALL_COVER);
            long smallCoverRowid = insertIfNotExistsImage(db, small_cover);

            //insert large_cover
            String large_cover = values.getAsString(ArtistMainView.LARGE_COVER);
            long largeCoverRowid = insertIfNotExistsImage(db, large_cover);

            List<Long> genresRowIds = new ArrayList<>();
            //insert genres
            String genresStr = values.getAsString(ArtistMainView.GENRES);
            for (String genre : genresStr.split(",")) {
                long rowid = insertIfNotExistsGenre(db, genre);
                Log.d("ARTIST GENRE", genre + " " + Long.toString(rowid));
                genresRowIds.add(rowid);
            }

            //inserting artists
            tmpValues.put(Artists.ARTIST_ID, values.getAsString(ArtistMainView.ARTIST_ID));
            tmpValues.put(Artists.NAME, values.getAsString(ArtistMainView.NAME));
            tmpValues.put(Artists.SMALL_COVER, smallCoverRowid);
            tmpValues.put(Artists.LARGE_COVER, largeCoverRowid);
            tmpValues.put(Artists.ALBUMS, values.getAsInteger(ArtistMainView.ALBUMS));
            tmpValues.put(Artists.TRACKS, values.getAsInteger(ArtistMainView.TRACKS));
            tmpValues.put(Artists.LINK, values.getAsString(ArtistMainView.LINK));
            tmpValues.put(Artists.DESCRIPTION, values.getAsString(ArtistMainView.DESCRIPTION));

            long artistRowId = db.insertOrThrow(ARTISTS, null, tmpValues);
            Log.d("ARTIST_ROW", Long.toString(artistRowId));

            //inserting ArtistsGenres
            for (long genreRowId : genresRowIds) {
                insertArtistsGenre(db, artistRowId, genreRowId);
            }

            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }

    }

    public Cursor getAllArtists() {
        return mDbOpenHelper.getReadableDatabase().rawQuery(GET_ALL_ARTISTS_QUERY, null);
    }

    private Long getGenreRowid(String genre) {
        Cursor c = mDbOpenHelper.getReadableDatabase().query(GENRES,
                new String[] {Genres.ID},
                Genres.NAME + "=?",
                new String[] {genre},
                null,
                null,
                null);

        return DbUtils.getResultLongAndClose(c);
    }

    private Long getImageRowId(String imagePath) {
        Cursor c = mDbOpenHelper.getReadableDatabase().query(IMAGES,
                new String[] {Images.ID},
                Images.PATH + "=?",
                new String[] {imagePath},
                null,
                null,
                null);

        return DbUtils.getResultLongAndClose(c);
    }


    public void deleteImage() {
        mDbOpenHelper.getWritableDatabase().execSQL("delete from images where rowid=1");
        Log.d("ARTIST", "DELETE IMAGE");
    }
}
