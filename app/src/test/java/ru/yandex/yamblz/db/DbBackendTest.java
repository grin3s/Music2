package ru.yandex.yamblz.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import org.apache.tools.ant.filters.StringInputStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;

import java.io.IOException;
import java.util.List;

import ru.yandex.yamblz.App;
import ru.yandex.yamblz.YamblzRobolectricUnitTestRunner;
import ru.yandex.yamblz.api.ArtistApi;
import ru.yandex.yamblz.models.Artist;
import ru.yandex.yamblz.provider.ArtistsBuilder;
import ru.yandex.yamblz.provider.ArtistsContract;

import static org.junit.Assert.*;

/**
 * Created by grin3s on 09.08.16.
 */

public class DbBackendTest {
    DbBackend dbBackend;
    Context mContext;

    @Before
    public void setUp() throws Exception {
        mContext = RuntimeEnvironment.application.getApplicationContext();
        dbBackend = new DbBackend(mContext);

    }

    @After
    public void tearDown() throws Exception {

    }

    private void insertArtist (Artist artist) {
        dbBackend.insertNewArtist(ArtistsBuilder.artistToContentValues(artist));
    }

//    @Test
//    public void insertAndSelectArtist_shouldBeEqual() throws IOException {
//        String artistJson = "[{\"id\":1080505,\"name\":\"Tove Lo\",\"genres\":[\"pop\",\"dance\",\"electronics\"],\"tracks\":81,\"albums\":22,\"link\":\"http://www.tove-lo.com/\",\"description\":\"шведская певица и автор песен. Она привлекла к себе внимание в 2013 году с выпуском сингла «Habits», но настоящего успеха добилась с ремиксом хип-хоп продюсера Hippie Sabotage на эту песню, который получил название «Stay High». 4 марта 2014 года вышел её дебютный мини-альбом Truth Serum, а 24 сентября этого же года дебютный студийный альбом Queen of the Clouds. Туве Лу является автором песен таких артистов, как Icona Pop, Girls Aloud и Шер Ллойд.\",\"cover\":{\"small\":\"http://avatars.yandex.net/get-music-content/dfc531f5.p.1080505/300x300\",\"big\":\"http://avatars.yandex.net/get-music-content/dfc531f5.p.1080505/1000x1000\"}}]";
//        Artist tmpArtist = ArtistApi.loadArtistsFromJson(new StringInputStream(artistJson)).get(0);
//
//        insertArtist(tmpArtist);
//        Cursor cursor = dbBackend.getAllArtists();
//        while (cursor.moveToNext()) {
//            Artist newArtist = ArtistsBuilder.createArtistFromCursor(cursor);
//            assertEquals(newArtist, tmpArtist);
//        }
//        cursor.close();
//    }

    @Test
    public void insertAndSelectTwoArtistsWithEqualNames_shouldIgnoreSecond() throws IOException {
        String artistJson = "[{\"id\":1080505,\"name\":\"Tove Lo\",\"genres\":[\"pop\",\"dance\",\"electronics\"],\"tracks\":81,\"albums\":22,\"link\":\"http://www.tove-lo.com/\",\"description\":\"шведская певица и автор песен. Она привлекла к себе внимание в 2013 году с выпуском сингла «Habits», но настоящего успеха добилась с ремиксом хип-хоп продюсера Hippie Sabotage на эту песню, который получил название «Stay High». 4 марта 2014 года вышел её дебютный мини-альбом Truth Serum, а 24 сентября этого же года дебютный студийный аhhhhhльбом Queen of the Clouds. Туве Лу является автором песен таких артистов, как Icona Pop, Girls Aloud и Шер Ллойд.\",\"cover\":{\"small\":\"http://avatars.yandex.net/get-music-content/dfc531f5.p.1080505/300x300\",\"big\":\"http://avatars.yanfgfgfdex.net/get-music-content/dfc531f5.p.1080505/1000x1000\"}}, {\"id\":1080505,\"name\":\"Tove Lo\",\"genres\":[\"pop\",\"dance\",\"electronics\"],\"tracks\":81,\"albums\":22,\"link\":\"http://www.tove-lo.com/\",\"description\":\"шведская певица и автор песен. Она привлекла к себе внимание в 2013 году с выпуском сингла «Habits», но настоящего успеха добилась с ремиксом хип-хоп продюсера Hippie Sabotage на эту песню, который получил название «Stay High». 4 марта 2014 года вышел её дебютный мини-альбом Truth Serum, а 24 сентября этого же года дебютный студийный альбом Queen of the Clouds. Туве Лу является автором песен таких артистов, как Icona Pop, Girls Aloud и Шер Ллойд.\",\"cover\":{\"small\":\"http://avatars.yandex.net/get-music-content/dfc531f5.p.10fgfg80505/300x300\",\"big\":\"http://avatars.yandex.net/get-music-content/dfc531f5.p.1080505/1000x1000\"}}]";
        List<Artist> artistList= ArtistApi.loadArtistsFromJson(new StringInputStream(artistJson));

        insertArtist(artistList.get(0));
        insertArtist(artistList.get(1));
        Cursor cursor = dbBackend.getAllArtists();
        Artist newArtist = ArtistsBuilder.createArtistFromCursor(cursor);
        assertEquals(newArtist, artistList.get(0));
        assertTrue(cursor.isLast());
        cursor.close();
    }
}