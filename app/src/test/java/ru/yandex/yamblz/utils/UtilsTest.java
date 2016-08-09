package ru.yandex.yamblz.utils;

import android.database.Cursor;
import android.provider.UserDictionary;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;

import ru.yandex.yamblz.App;
import ru.yandex.yamblz.YamblzRobolectricUnitTestRunner;

import static org.junit.Assert.*;

/**
 * Created by grin3s on 08.08.16.
 */
@RunWith(YamblzRobolectricUnitTestRunner.class)
public class UtilsTest {
    @Test
    public void returnOne_shouldReturnOne() throws Exception {
        assertEquals(Utils.returnOne(), 1);
    }

    @Test
    public void test1() {
        App app = YamblzRobolectricUnitTestRunner.yamblzApp();
        Cursor mCursor = app.getContentResolver().query(
                UserDictionary.Words.CONTENT_URI,   // The content URI of the words table
                null,
                null,
                null,
                null);

        assertNotNull(mCursor);
    }

}