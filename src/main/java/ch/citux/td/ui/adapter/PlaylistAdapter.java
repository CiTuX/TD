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
import android.widget.TextView;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.widget.CheckBox;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ch.citux.td.R;
import ch.citux.td.data.model.Video;

public class PlaylistAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<Video> videos;
    private ArrayList<Integer> played;

    public PlaylistAdapter(Context context, ArrayList<Video> videos) {
        super();
        this.inflater = LayoutInflater.from(context);
        this.videos = videos;
        this.played = new ArrayList<Integer>(videos.size());
    }

    public void setVideos(ArrayList<Video> videos) {
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
    public Video getItem(int position) {
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
        Video video = getItem(position);
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
        holder.lblTitle.setText(video.getTitle());
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
