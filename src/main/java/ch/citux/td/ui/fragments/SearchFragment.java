/*
 * Copyright 2013-2014 Paul Stöhr
 *
 * This file is part of TD.
 *
 * TD is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package ch.citux.td.ui.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import ch.citux.td.R;
import ch.citux.td.data.model.SearchStreams;
import ch.citux.td.data.model.Stream;
import ch.citux.td.data.worker.TDTaskManager;
import ch.citux.td.ui.adapter.SearchAdapter;
import ch.citux.td.ui.widget.EmptyView;
import ch.citux.td.util.Log;
import ch.citux.td.util.VideoPlayer;

public class SearchFragment extends TDFragment<SearchStreams> implements AdapterView.OnItemClickListener {

    public static final String QUERY = "query";
    public static final String OFFSET = "offset";

    private SearchAdapter adapter;
    private EmptyView emptyView;
    private String query;
    private int offset;

    @Override
    protected int onCreateView() {
        return R.layout.list;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState != null) {
            query = savedInstanceState.getString(QUERY);
            offset = savedInstanceState.getInt(OFFSET);
        }

        if (adapter == null || adapter.getCount() == 0) {
            adapter = new SearchAdapter(getActivity());

            setListAdapter(adapter);
        }
        setOnItemClickListener(this);

        emptyView = (EmptyView) getListView().getEmptyView();
        if (emptyView != null) {
            emptyView.setText(R.string.search_no_result);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(QUERY, query);
        outState.putInt(OFFSET, offset);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void loadData() {
        if (emptyView != null) {
            emptyView.showProgress();
        }
        TDTaskManager.searchStreams(this, query, offset);
    }

    @Override
    public void refreshData() {
        adapter.clear();
        loadData();
    }

    @Override
    public void onResponse(SearchStreams response) {
        adapter.setData(response.getResult());
        if (emptyView != null) {
            emptyView.showText();
        }
        Log.d(this, "SearchResult: " + response.getResult().size());
    }

    public void setQuery(String query) {
        this.query = query;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Stream stream = adapter.getItem(i);
        if (stream != null) {
            TDTaskManager.getStreamPlaylist(new VideoPlayer.StreamPlaylistCallback(this, stream.getStatus()), stream.getChannel().getName());
        }
    }
}
