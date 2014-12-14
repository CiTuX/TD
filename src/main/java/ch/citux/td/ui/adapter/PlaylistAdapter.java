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
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ch.citux.td.R;
import ch.citux.td.data.model.TwitchChunk;

public class PlaylistAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<TwitchChunk> videos;
    private ArrayList<Integer> played;

    public PlaylistAdapter(Context context, List<TwitchChunk> videos) {
        super();
        this.inflater = LayoutInflater.from(context);
        this.videos = videos;
        this.played = new ArrayList<Integer>(videos.size());
    }

    public void setVideos(List<TwitchChunk> videos) {
        this.videos = videos;
        if (videos != null && videos.size() > 0) {
            notifyDataSetChanged();
        } else {
            notifyDataSetInvalidated();
        }
    }

    public void setPlayed(int position) {
        played.add(position);
    }

    @Override
    public int getCount() {
        return videos.size();
    }

    @Override
    public TwitchChunk getItem(int position) {
        if (position < videos.size()) {
            return videos.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TwitchChunk video = getItem(position);
        ViewHolder holder;
        if (convertView == null || convertView.getTag() == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.list_item_playlist, parent, false);
            if (convertView != null) {
                ButterKnife.inject(holder, convertView);
            }
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.lblPartIndex.setText(String.valueOf(position + 1));
        holder.lblPartCount.setText(String.valueOf(getCount()));
        //TODO holder.lblTitle.setText(video.());
        holder.chkPlayed.setChecked(played.contains(position));
        return convertView;
    }

    class ViewHolder {
        @InjectView(R.id.lblPartIndex) TextView lblPartIndex;
        @InjectView(R.id.lblPartCount) TextView lblPartCount;
        @InjectView(R.id.lblTitle) TextView lblTitle;
        @InjectView(R.id.chkPlayed) CheckBox chkPlayed;
    }
}
