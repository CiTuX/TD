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
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.widget.TextView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ch.citux.td.R;
import ch.citux.td.data.model.Game;
import ch.citux.td.data.model.TopGames;

public class GameOverviewAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Picasso picasso;
    private TopGames topGames;
    private ArrayList<Game> data;

    public GameOverviewAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        picasso = Picasso.with(context);
        data = new ArrayList<Game>();
    }

    public void addData(TopGames topGames) {
        this.topGames = topGames;
        this.data.addAll(topGames.getGames());
        notifyDataSetChanged();
    }

    public int getTotalCount() {
        if (topGames != null) {
            return topGames.getTotal();
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
    public Game getItem(int position) {
        if (data != null && data.size() > position) {
            return data.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        Game game = getItem(position);
        if (game != null) {
            return game.getId();
        }
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Game game = getItem(position);
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
        picasso.load(game.getBox()).placeholder(R.drawable.default_game_box_medium).into(holder.imgBox);
        holder.lblName.setText(game.getName());
        holder.lblViewers.setText(String.valueOf(game.getViewers()));
        return convertView;
    }

    class ViewHolder {
        @InjectView(R.id.imgBox) ImageView imgBox;
        @InjectView(R.id.lblName) TextView lblName;
        @InjectView(R.id.lblViewers) TextView lblViewers;
    }
}
