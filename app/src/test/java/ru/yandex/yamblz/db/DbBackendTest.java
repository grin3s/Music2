package ru.yandex.yamblz.db;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;

import ru.yandex.yamblz.App;
import ru.yandex.yamblz.YamblzRobolectricUnitTestRunner;

import static org.junit.Assert.*;

/**
 * Created by grin3s on 09.08.16.
 */
@RunWith(YamblzRobolectricUnitTestRunner.class)
public class DbBackendTest {
    DbBackend dbBackend;

    @Before
    public void setUp() throws Exception {
        //dbBackend = new DbBackend(RuntimeEnvironment.application.getApplicationContext());
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void insertNewArtist() throws Exception {
        System.out.println(DbOpenHelper.CREATE_ARTISTS);
        System.out.println(DbOpenHelper.CREATE_GENRES);
        System.out.println(DbOpenHelper.CREATE_IMAGES);
        System.out.println(DbOpenHelper.CREATE_ARTISTS_GENRES);
        System.out.println(DbBackend.GET_ALL_ARTISTS_QUERY);
    }

    @Test
    public void getAllArtists() throws Exception {
//        dbBackend.getAllArtists();
    }

}