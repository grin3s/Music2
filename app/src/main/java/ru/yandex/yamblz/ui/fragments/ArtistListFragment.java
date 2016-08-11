package ru.yandex.yamblz.ui.fragments;


import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.lang.ref.WeakReference;

import ru.yandex.yamblz.R;
import ru.yandex.yamblz.ui.activities.MainActivity;
import ru.yandex.yamblz.ui.adapters.ArtistListAdapter;

/**
 * Created by grin3s on 20.07.16.
 */
public class ArtistListFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<Cursor>{
    ArtistListAdapter mAdapter;
    ListView mListView;

    private static Parcelable mListViewState = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //list view holding all artists
        View rootView = inflater.inflate(R.layout.artist_list, container, false);
        mListView = (ListView) rootView.findViewById(R.id.artist_list);

        //creating the adapter for the list of artists
        mAdapter = new ArtistListAdapter(getContext(), null, 0);

        mListView.setAdapter(mAdapter);

        getLoaderManager().initLoader(0, null, this);
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        mListViewState = mListView.onSaveInstanceState();
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getContext(),
                Uri.parse("content://ru.yandex.yamblz.provider/artists"),
                null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (mListViewState != null) {
            mListView.onRestoreInstanceState(mListViewState);
            mListViewState = null;
        }
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(false);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Artists");
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}
