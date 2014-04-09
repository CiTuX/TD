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

import ch.citux.td.R;
import ch.citux.td.data.model.Video;
import ch.citux.td.util.DateUtils;

public class ArchiveAdapter extends BaseAdapter {

    private ArrayList<Video> data;
    private LayoutInflater inflater;
    private Picasso picasso;

    public ArchiveAdapter(Context context) {
        init(context, new ArrayList<Video>());
    }

    public ArchiveAdapter(Context context, ArrayList<Video> data) {
        init(context, data);
    }

    private void init(Context context, ArrayList<Video> data) {
        this.data = data;
        this.inflater = LayoutInflater.from(context);
        this.picasso = Picasso.with(context);
    }

    public void setData(ArrayList<Video> data) {
        if (data != null) {
            this.data = data;
            notifyDataSetChanged();
        }
    }

    public void clear() {
        data.clear();
        notifyDataSetInvalidated();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Video getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Video video = data.get(position);
        ViewHolder holder;
        if (convertView == null || convertView.getTag() == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.list_item_videos, null);
            if (convertView != null) {
                holder.imgThumbnail = (ImageView) convertView.findViewById(R.id.imgThumbnail);
                holder.lblTitle = (TextView) convertView.findViewById(R.id.lblTitle);
                holder.lblDate = (TextView) convertView.findViewById(R.id.lblDate);
                holder.lblDuration = (TextView) convertView.findViewById(R.id.lblDuration);
            }
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        picasso.load(video.getThumbnail()).placeholder(R.drawable.default_archive_thumbnail).into(holder.imgThumbnail);
        holder.lblTitle.setText(video.getTitle());
        holder.lblDate.setText(DateUtils.formateDate(video.getDate()));
        holder.lblDuration.setText(DateUtils.formatTime(video.getDuration()));
        return convertView;
    }

    private class ViewHolder {
        ImageView imgThumbnail;
        TextView lblTitle;
        TextView lblDate;
        TextView lblDuration;
    }

}
