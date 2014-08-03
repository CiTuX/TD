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
import android.text.TextUtils;
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
import ch.citux.td.data.model.Stream;
import ch.citux.td.util.FormatUtils;

public class SearchAdapter extends BaseAdapter {

    private ArrayList<Stream> data;
    private LayoutInflater inflater;
    private Picasso picasso;

    public SearchAdapter(Context context) {
        init(context, new ArrayList<Stream>());
    }

    public SearchAdapter(Context context, ArrayList<Stream> data) {
        init(context, data);
    }

    private void init(Context context, ArrayList<Stream> data) {
        this.data = data;
        this.inflater = LayoutInflater.from(context);
        this.picasso = Picasso.with(context);
    }

    public void setData(ArrayList<Stream> data) {
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
        if (data != null) {
            return data.size();
        }
        return 0;
    }

    @Override
    public Stream getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Stream stream = data.get(position);
        ViewHolder holder;
        if (convertView == null || convertView.getTag() == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.list_item_streams, parent, false);
            if (convertView != null) {
                ButterKnife.inject(holder, convertView);
            }
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        picasso.load(stream.getThumbnail()).placeholder(R.drawable.default_archive_thumbnail).into(holder.imgThumbnail);
        holder.lblTitle.setText(stream.getStatus());
        setupMarquee(holder.lblChannel, stream.getChannel().getName());
        setupMarquee(holder.lblGame, FormatUtils.formatGame(stream.getGame()));
        setupMarquee(holder.lblViewers, FormatUtils.formatNumber(stream.getViewers()));
        return convertView;
    }

    private void setupMarquee(TextView textView, String text) {
        textView.setText(text);
        textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        textView.setSingleLine();
        textView.setSelected(true);
    }

    static class ViewHolder {
        @InjectView(R.id.imgThumbnail) ImageView imgThumbnail;
        @InjectView(R.id.lblTitle) TextView lblTitle;
        @InjectView(R.id.lblChannel) TextView lblChannel;
        @InjectView(R.id.lblGame) TextView lblGame;
        @InjectView(R.id.lblViewers) TextView lblViewers;
    }
}
