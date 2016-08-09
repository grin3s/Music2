package ru.yandex.yamblz.api;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

import ru.yandex.yamblz.models.Artist;

/**
 * Created by grin3s on 09.08.16.
 */

public class ArtistApi {
    public static List<Artist> loadArtistsFromJson(InputStream inputStream) throws IOException {
        Type collectionType = new TypeToken<List<Artist>>() {}.getType();
        try {
            List<Artist> resList = new Gson().fromJson(new BufferedReader(new InputStreamReader(inputStream)), collectionType);
            return resList;
        }
        catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
}
