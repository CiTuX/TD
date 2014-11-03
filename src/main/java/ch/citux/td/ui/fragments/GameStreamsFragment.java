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

import org.apache.commons.lang3.StringUtils;

import ch.citux.td.R;
import ch.citux.td.data.model.Game;
import ch.citux.td.data.model.Stream;
import ch.citux.td.data.model.Streams;
import ch.citux.td.data.worker.TDTaskManager;
import ch.citux.td.ui.adapter.GameStreamsAdapter;
import ch.citux.td.ui.widget.ListView;
import ch.citux.td.util.VideoPlayer;

public class GameStreamsFragment extends TDListFragment<Streams> implements AdapterView.OnItemClickListener, ListView.OnLastItemVisibleListener {

    public static final String GAME = "game";

    private GameStreamsAdapter adapter;
    private Game game;
    private int offset;

    @Override
    protected int onCreateView() {
        return R.layout.list;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getArgs().containsKey(GAME)) {
            game = (Game) getArgs().getSerializable(GAME);
            offset = 0;
        }

        setOnItemClickListener(this);
    }

    @Override
    public void refreshData() {
        if (adapter != null) {
            adapter.clear();
        }
        loadData();
    }

    @Override
    public void loadData() {
        if (StringUtils.isNotEmpty(game.getName())) {
            TDTaskManager.getStreams(this, game.getName(), offset);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Stream stream = adapter.getItem(position);
        if (stream != null) {
            TDTaskManager.getStreamPlaylist(new VideoPlayer.StreamPlaylistCallback(this, stream.getStatus()), stream.getName());
        }
    }

    @Override
    public void onResponse(Streams response) {
        setOnLastItemVisibleListener(this);
        emptyView.setText(R.string.search_no_result);
        if (adapter == null) {
            adapter = new GameStreamsAdapter(getActivity(), response.getEntries());
            setListAdapter(adapter);
        } else {
            adapter.setData(response.getEntries());
        }
    }

    @Override
    public void onLastItemVisible() {
        offset += 10;
        loadData();
    }
}
