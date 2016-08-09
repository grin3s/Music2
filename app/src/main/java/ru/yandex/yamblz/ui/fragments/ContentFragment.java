package ru.yandex.yamblz.ui.fragments;

import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringBufferInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import butterknife.BindView;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.api.ArtistApi;
import ru.yandex.yamblz.db.DbBackend;
import ru.yandex.yamblz.models.Artist;
import ru.yandex.yamblz.provider.ArtistsBuilder;

import static android.media.CamcorderProfile.get;

public class ContentFragment extends BaseFragment {
    @BindView(R.id.button)
    Button button;

    @BindView(R.id.buttonDelete)
    Button buttonDelete;

    Executor mExecutor = Executors.newSingleThreadExecutor();

    List<Artist> artistList = new ArrayList<>();
    Integer lastArtist = 0;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_content, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Context context = getContext();
        mExecutor.execute(() -> {
            synchronized (artistList) {
                try {
                    artistList = ArtistApi.loadArtistsFromJson(context.getApplicationContext().getAssets().open("artists.json"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        button.setOnClickListener(v -> mExecutor.execute(() -> {
            DbBackend dbBackend = new DbBackend(context);
            Artist tmpArtist = artistList.get(lastArtist++);
            Log.d("ARTIST", tmpArtist.toString());
            dbBackend.insertNewArtist(ArtistsBuilder.artistToContentValues(tmpArtist));
            Log.d("ADDED", "ARTIST");
        }));

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbBackend dbBackend = new DbBackend(context);
                dbBackend.deleteImage();
            }
        });
    }
}
