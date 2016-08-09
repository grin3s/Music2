package ru.yandex.yamblz.provider;

/**
 * Created by grin3s on 08.08.16.
 */

public interface ArtistsContract {
    String DB_NAME = "main.sqlite";


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
        String ARTIST_ID = "artist_id";
        String NAME = "name";
        String SMALL_COVER = "small_cover";
        String LARGE_COVER = "large_cover";
        String ALBUMS = "albums";
        String TRACKS = "tracks";
        String LINK = "link";
        String DESCRIPTION = "description";
        String GENRES = "genres";
    }

//    enum ArtistMainView {
//        ARTIST_ID(0, "artist_id"),
//        NAME(1, "name"),
//        SMALL_COVER(2, "small_cover"),
//        LARGE_COVER(3, "large_cover"),
//        ALBUMS(4, "albums"),
//        TRACKS(5, "tracks"),
//        LINK(6, "link"),
//        DESCRIPTION(7, "descriptio"),
//        GENRES(8, "genres");
//
//        int columnIdx;
//        String columnName;
//
//        ArtistMainView(int columnIdx, String columnName) {
//            this.columnIdx = columnIdx;
//            this.columnName = columnName;
//        }
//
//        public int getColumnIdx() {
//            return columnIdx;
//        }
//
//        public String getColumnName() {
//            return columnName;
//        }
//    }
}
