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
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.holoeverywhere.LayoutInflater;

import ch.citux.td.R;
import ch.citux.td.data.model.TwitchChannel;
import ch.citux.td.data.model.TwitchLogo;

public class FavoritesAdapter extends BaseAdapter {

    private SparseArray<TwitchChannel> data;
    private LayoutInflater inflater;
    private Picasso picasso;

    public FavoritesAdapter(Context context) {
        init(context, new SparseArray<TwitchChannel>());
    }

    public FavoritesAdapter(Context context, SparseArray<TwitchChannel> data) {
        init(context, data);
    }

    private void init(Context context, SparseArray<TwitchChannel> data) {
        this.inflater = LayoutInflater.from(context);
        this.picasso = Picasso.with(context);
        setData(data);
    }

    public SparseArray<TwitchChannel> getData() {
        return data;
    }

    public void setData(SparseArray<TwitchChannel> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void clear() {
        data.clear();
        notifyDataSetInvalidated();
    }

    public void setUpdatePending(int id) {
        TwitchChannel channel = data.get(id);
        if (channel != null) {
            channel.setChannelStatus(TwitchChannel.Status.UNKNOWN);
            notifyDataSetChanged();
        }
    }

    public void updateChannelStatus(int id, boolean online) {
        TwitchChannel channel = data.get(id);
        if (channel != null) {
            channel.setChannelStatus(online ? TwitchChannel.Status.ONLINE : TwitchChannel.Status.OFFLINE);
            notifyDataSetChanged();
        }
    }

    private void updateStatus(TwitchChannel.Status status, ViewHolder holder) {
        if (status == null || holder == null) {
            return;
        }

        //UNKNOWN
        int color = R.color.status_unknown;
        boolean visibilityLbl = false;
        boolean visibilityPrg = true;
        int text = R.string.channel_offline;

        switch (status) {
            case ONLINE:
                text = R.string.channel_online;
                visibilityLbl = true;
                visibilityPrg = false;
                color = R.color.status_online;
                break;
            case OFFLINE:
                text = R.string.channel_offline;
                visibilityLbl = true;
                visibilityPrg = false;
                color = R.color.status_offline;
                break;
        }
        holder.lblStatus.setText(text);
        holder.lblStatus.setVisibility(visibilityLbl ? View.VISIBLE : View.INVISIBLE);
        holder.prgStatus.setVisibility(visibilityPrg ? View.VISIBLE : View.INVISIBLE);
        holder.statusIndicator.setBackgroundResource(color);
    }

    @Override
    public int getCount() {
        if (data != null) {
            return data.size();
        }
        return 0;
    }

    @Override
    public TwitchChannel getItem(int position) {
        return data.valueAt(position);
    }

    @Override
    public long getItemId(int position) {
        return data.keyAt(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TwitchChannel channel = getItem(position);
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.list_item_favorites);
            if (convertView != null) {
                holder.imgLogo = (ImageView) convertView.findViewById(R.id.imgLogo);
                holder.lblTitle = (TextView) convertView.findViewById(R.id.lblTitle);
                holder.lblStatus = (TextView) convertView.findViewById(R.id.lblStatus);
                holder.prgStatus = (ProgressBar) convertView.findViewById(R.id.prgStatus);
                holder.statusIndicator = convertView.findViewById(R.id.statusIndicator);
                convertView.setTag(holder);
            }
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        picasso.load(channel.getLogo().getUrl(TwitchLogo.Size.SMALL)).placeholder(R.drawable.default_channel_logo_small).into(holder.imgLogo);
        holder.lblTitle.setText(channel.getDisplay_name());
        updateStatus(channel.getChannelStatus(), holder);

        return convertView;
    }

    private static class ViewHolder {
        ImageView imgLogo;
        TextView lblTitle;
        TextView lblStatus;
        ProgressBar prgStatus;
        View statusIndicator;
    }
}
