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

import butterknife.InjectView;
import ch.citux.td.R;
import ch.citux.td.data.model.Game;
import ch.citux.td.data.model.TopGames;
import ch.citux.td.data.worker.TDTaskManager;
import ch.citux.td.ui.adapter.GamerOverviewAdapter;
import ch.citux.td.ui.widget.EmptyView;
import ch.citux.td.ui.widget.GridView;
import ch.citux.td.util.Log;

public class GameOverviewFragment extends TDFragment<TopGames> implements GridView.OnLastItemVisibleListener {

    private static final int LIMIT = 15;

    @InjectView(android.R.id.empty) EmptyView emptyView;
    @InjectView(R.id.gridview) GridView gridView;

    private GamerOverviewAdapter adapter;
    private int offset;

    @Override
    protected int onCreateView() {
        return R.layout.game_overview;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new GamerOverviewAdapter(getActivity());
        gridView.setAdapter(adapter);
        gridView.setOnLastItemVisibleListener(this);
    }

    @Override
    public void loadData() {
        TDTaskManager.getTopGames(this, LIMIT, offset);
    }

    @Override
    public void onResponse(TopGames response) {
        for (Game game : response.getGames()) {
            Log.d(this, game.getName());
        }
        adapter.addData(response);
        emptyView.setVisibility(View.GONE);
    }

    @Override
    public void onLastItemVisible() {
        offset += LIMIT;
        if (adapter.getTotalCount() > offset + LIMIT) {
            loadData();
        }
    }
}
