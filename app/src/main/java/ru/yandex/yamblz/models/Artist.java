package ru.yandex.yamblz.models;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by grin3s on 09.08.16.
 */

public class Artist {
    public static class Cover {
        String small;
        String big;

        public Cover(String small, String big) {
            this.small = small;
            this.big = big;
        }

        public String getSmall() {
            return small;
        }

        @Override
        public String toString() {
            return "Cover{" +
                    "small='" + small + '\'' +
                    ", big='" + big + '\'' +
                    '}';
        }

        public String getLarge() {
            return big;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Cover cover = (Cover) o;

            if (!small.equals(cover.small)) return false;
            return big.equals(cover.big);

        }

        @Override
        public int hashCode() {
            int result = small.hashCode();
            result = 31 * result + big.hashCode();
            return result;
        }
    }

    int id;
    String name;
    List<String> genres;
    int tracks;

    int albums;
    String link;
    String description;
    Cover cover;

    public int getId() {
        return id;
    }

    public int getTracks() {
        return tracks;
    }

    public int getAlbums() {
        return albums;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }



    public List<String> getGenres() {
        return genres;
    }

    public String getName() {
        return name;
    }

    public Cover getCover() {
        return cover;
    }

    public Artist(int id, String name, List<String> genres, int tracks, int albums, String link, String description, Cover cover) {
        this.id = id;
        this.name = name;
        this.genres = genres;
        this.tracks = tracks;
        this.albums = albums;
        this.link = link;
        this.description = description;
        this.cover = cover;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Artist artist = (Artist) o;

        if (id != artist.id) return false;
        if (tracks != artist.tracks) return false;
        if (albums != artist.albums) return false;
        if (!name.equals(artist.name)) return false;
        //if (genres != null ? !genres.equals(artist.genres) : artist.genres != null) return false;
        if (link != null ? !link.equals(artist.link) : artist.link != null) return false;
        if (description != null ? !description.equals(artist.description) : artist.description != null)
            return false;
        return cover != null ? cover.equals(artist.cover) : artist.cover == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        //result = 31 * result + (genres != null ? genres.hashCode() : 0);
        result = 31 * result + tracks;
        result = 31 * result + albums;
        result = 31 * result + (link != null ? link.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (cover != null ? cover.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", genres=" + genres +
                ", tracks=" + tracks +
                ", albums=" + albums +
                ", link='" + link + '\'' +
                ", description='" + description + '\'' +
                ", cover=" + cover +
                '}';
    }
}
