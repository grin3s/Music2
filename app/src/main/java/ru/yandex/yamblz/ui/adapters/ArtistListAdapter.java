package ru.yandex.yamblz.ui.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import ru.yandex.yamblz.R;
import ru.yandex.yamblz.models.Artist;
import ru.yandex.yamblz.provider.ArtistsBuilder;
import ru.yandex.yamblz.ui.activities.MainActivity;

/**
 * Created by grin on 4/21/16.
 */
public class ArtistListAdapter extends CursorAdapter {
    public static final String TAG = "ArtistListAdapter";

    // the string template to write number of albums and tracks
    public static String sAlbumsTracksTemplate;

    // viewholder to cache the views
    public static class ViewHolder {
        public final ImageView coverView;
        public final ProgressBar progressView;
        public final TextView nameView;
        public final TextView genresView;
        public final TextView albumsTracksView;
        public Artist artist;


        public ViewHolder(View view) {
            coverView = (ImageView) view.findViewById(R.id.list_image_view);
            progressView = (ProgressBar) view.findViewById(R.id.list_image_progress_bar);
            nameView = (TextView) view.findViewById(R.id.list_artist_name);
            genresView = (TextView) view.findViewById(R.id.list_artist_genres);
            albumsTracksView = (TextView) view.findViewById(R.id.list_artist_albums_tracks);
        }
    }

    public ArtistListAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        sAlbumsTracksTemplate = context.getResources().getString(R.string.albums_and_tracks_template);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(R.layout.artist_list_content, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        final ViewHolder viewHolder = (ViewHolder) view.getTag();
        // making the progress bar visible every time the data is starting to load
        viewHolder.progressView.setVisibility(View.VISIBLE);

        Artist curArtist = ArtistsBuilder.createArtistFromCursor(cursor);
        viewHolder.artist = curArtist;

        Picasso.with(context).load(curArtist.getCover().getSmall()).into(viewHolder.coverView, new Callback() {
            @Override
            public void onSuccess() {
                viewHolder.progressView.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                Log.e(TAG, "Picasso error");
            }
        });
        viewHolder.nameView.setText(curArtist.getName());
        viewHolder.genresView.setText(TextUtils.join(",", curArtist.getGenres()));
        viewHolder.albumsTracksView.setText(String.format(sAlbumsTracksTemplate, curArtist.getAlbums(), curArtist.getTracks()));
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }
}
