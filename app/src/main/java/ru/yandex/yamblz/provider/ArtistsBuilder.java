package ru.yandex.yamblz.provider;

import android.content.ContentValues;
import android.database.Cursor;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;

import ru.yandex.yamblz.models.Artist;

/**
 * Created by grin3s on 09.08.16.
 */

public class ArtistsBuilder implements ArtistsContract{
    public static Artist createArtistFromCursor(Cursor cursor) {
        return new Artist(
                cursor.getInt(cursor.getColumnIndex(ArtistMainView.ARTIST_ID)),
                cursor.getString(cursor.getColumnIndex(ArtistMainView.NAME)),
                new ArrayList<String>(Arrays.asList(cursor.getString(cursor.getColumnIndex(ArtistMainView.GENRES)).split(","))),
                cursor.getInt(cursor.getColumnIndex(ArtistMainView.TRACKS)),
                cursor.getInt(cursor.getColumnIndex(ArtistMainView.ALBUMS)),
                cursor.getString(cursor.getColumnIndex(ArtistMainView.LINK)),
                cursor.getString(cursor.getColumnIndex(ArtistMainView.DESCRIPTION)),
                new Artist.Cover(cursor.getString(cursor.getColumnIndex(ArtistMainView.SMALL_COVER)),
                        cursor.getString(cursor.getColumnIndex(ArtistMainView.LARGE_COVER)))
                );
    }

    public static ContentValues artistToContentValues(Artist artist) {
        ContentValues resValues = new ContentValues();
        resValues.put(ArtistMainView.ARTIST_ID, artist.getId());
        resValues.put(ArtistMainView.NAME, artist.getName());
        resValues.put(ArtistMainView.SMALL_COVER, artist.getCover().getSmall());
        resValues.put(ArtistMainView.LARGE_COVER, artist.getCover().getLarge());
        resValues.put(ArtistMainView.ALBUMS, artist.getAlbums());
        resValues.put(ArtistMainView.TRACKS, artist.getTracks());
        resValues.put(ArtistMainView.LINK, artist.getLink());
        resValues.put(ArtistMainView.DESCRIPTION, artist.getDescription());
        resValues.put(ArtistMainView.GENRES, TextUtils.join(",", artist.getGenres()));
        return resValues;
    }
}
