package ru.yandex.yamblz.provider;

import android.content.ContentResolver;
import android.net.Uri;

/**
 * Created by grin3s on 08.08.16.
 */

public interface ArtistsContract {
    String DB_NAME = "main.sqlite";

    // Content provider authority.
    String CONTENT_AUTHORITY = "ru.yandex.yamblz.provider";

    // base uri
    Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    String PATH_ARTISTS = "artists";
    String PATH_GENRES = "genres";


    String ARTISTS = "artists";
    interface Artists {
        String ID = "rowid";
        String ARTIST_ID = "artist_id";
        String NAME = "name";
        String SMALL_COVER = "small_cover";
        String LARGE_COVER = "large_cover";
        String ALBUMS = "albums";
        String TRACKS = "tracks";
        String LINK = "link";
        String DESCRIPTION = "description";
    }

    String IMAGES = "images";
    interface Images {
        String ID = "rowid";
        String PATH = "path";
    }

    String GENRES = "genres";
    interface Genres {
        String ID = "rowid";
        String NAME = "name";
    }

    String ARTISTS_GENRES = "artists_genres";
    interface ArtistsGenres {
        String ID = "rowid";
        String ARTIST_ROWID = "artist_rowid";
        String GENRE_ROWID = "genre_rowid";
    }

    interface ArtistMainView {
        String ID = "_id";
        String ARTIST_ID = "artist_id";
        String NAME = "name";
        String SMALL_COVER = "small_cover";
        String LARGE_COVER = "large_cover";
        String ALBUMS = "albums";
        String TRACKS = "tracks";
        String LINK = "link";
        String DESCRIPTION = "description";
        String GENRES = "genres";

        String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ARTISTS;
        String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ARTISTS;
    }

}
