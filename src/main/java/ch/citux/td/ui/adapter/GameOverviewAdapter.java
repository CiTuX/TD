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
package ch.citux.td.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ch.citux.td.R;
import ch.citux.td.data.model.TwitchGames;
import ch.citux.td.data.model.TwitchGamesElement;

public class GameOverviewAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Picasso picasso;
    private TwitchGames topGames;
    private ArrayList<TwitchGamesElement> data;

    public GameOverviewAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        picasso = Picasso.with(context);
        data = new ArrayList<TwitchGamesElement>();
    }

    public void addData(TwitchGames topGames) {
        this.topGames = topGames;
        this.data.addAll(topGames.getTop());
        notifyDataSetChanged();
    }

    public int getTotalCount() {
        if (topGames != null) {
            return topGames.get_total();
        }
        return 0;
    }

    @Override
    public int getCount() {
        if (data != null) {
            return data.size();
        }
        return 0;
    }

    @Override
    public TwitchGamesElement getItem(int position) {
        if (data != null && data.size() > position) {
            return data.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        TwitchGamesElement game = getItem(position);
        if (game != null) {
            return game.getGame().get_id();
        }
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TwitchGamesElement game = getItem(position);
        ViewHolder holder;
        if (convertView == null || convertView.getTag() == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.game_item, parent, false);
            if (convertView != null) {
                ButterKnife.inject(holder, convertView);
            }
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        picasso.load(game.getGame().getBox().getMedium()).placeholder(R.drawable.default_game_box_medium).into(holder.imgBox);
        holder.lblName.setText(game.getGame().getName());
        holder.lblViewers.setText(String.valueOf(game.getViewers()));
        return convertView;
    }

    class ViewHolder {
        @InjectView(R.id.imgBox) ImageView imgBox;
        @InjectView(R.id.lblName) TextView lblName;
        @InjectView(R.id.lblViewers) TextView lblViewers;
    }
}
