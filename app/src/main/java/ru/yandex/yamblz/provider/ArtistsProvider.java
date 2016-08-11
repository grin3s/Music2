package ru.yandex.yamblz.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import ru.yandex.yamblz.api.ArtistApi;
import ru.yandex.yamblz.db.DbBackend;
import ru.yandex.yamblz.models.Artist;

/**
 * Created by grin3s on 09.08.16.
 */

// Content Provider class, that provides data about artists
public class ArtistsProvider extends ContentProvider implements ArtistsContract{
    public final static String TAG = "ArtistsProvider";

    private DbBackend mBackend;

    // route ids that are matched with URIs
    public static final int ROUTE_ARTISTS = 1;
    public static final int ROUTE_ARTIST_BY_ID = 2;

    private Executor mExecutor = Executors.newSingleThreadExecutor();

    Context mContext;

    // uri matcher
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sUriMatcher.addURI(CONTENT_AUTHORITY, PATH_ARTISTS, ROUTE_ARTISTS);
        sUriMatcher.addURI(CONTENT_AUTHORITY, PATH_ARTISTS + "/*", ROUTE_ARTIST_BY_ID);
    }

    @Override
    public boolean onCreate() {
        mContext = getContext();
        mBackend = new DbBackend(getContext());
        mExecutor.execute(() -> {
            try {
                List<Artist> artistList = artistList = ArtistApi.loadArtistsFromJson(mContext.getApplicationContext().getAssets().open("artists.json"));
                ContentValues[] values = new ContentValues[artistList.size()];
                for (int i = 0; i < artistList.size(); i++) {
                    values[i] = ArtistsBuilder.artistToContentValues(artistList.get(i));
                }
                mBackend.insertManyArtists(values);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return true;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case ROUTE_ARTISTS:
                return ArtistMainView.CONTENT_TYPE;
            case ROUTE_ARTIST_BY_ID:
                return ArtistMainView.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Cursor query(
            Uri uri,
            String[] projection,
            String selection,
            String[] selectionArgs,
            String sortOrder) {

        Log.i(TAG, "performing query");
        int uriMatch = sUriMatcher.match(uri);
        Cursor c;
        switch (uriMatch) {
            case ROUTE_ARTISTS:
                c = mBackend.getAllArtists();
                return c;
            case ROUTE_ARTIST_BY_ID:
                c = mBackend.getArtistById();
                return c;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(
            @NonNull Uri uri,
            ContentValues values,
            String selection,
            String[] selectionArgs) {
        return 0;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        Log.i(TAG, "performing bulk insert");
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case ROUTE_ARTISTS:
                mBackend.insertManyArtists(values);
                getContext().getContentResolver().notifyChange(uri, null);
                return values.length;
            default:
                return super.bulkInsert(uri, values);
        }
    }
}
