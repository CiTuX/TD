package ch.citux.td.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ch.citux.td.R;
import ch.citux.td.data.model.Channel;
import ch.citux.td.data.model.Logo;
import ch.citux.td.data.model.Status;

public class FavoritesAdapter extends BaseAdapter {

    private static final int ANIMATION_DURATION = 500;

    private ArrayList<Channel> data;
    private LayoutInflater inflater;
    private Picasso picasso;
    private Animation fadeOut;
    private Animation fadeIn;

    public FavoritesAdapter(Context context) {
        init(context, new ArrayList<Channel>());
    }

    public FavoritesAdapter(Context context, ArrayList<Channel> data) {
        init(context, data);
    }

    private void init(Context context, ArrayList<Channel> data) {
        this.data = data;
        this.inflater = LayoutInflater.from(context);
        this.picasso = Picasso.with(context);
    }

    private void initAnimation() {
        fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setDuration(ANIMATION_DURATION);

        fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setDuration(ANIMATION_DURATION);
        fadeIn.setStartOffset(ANIMATION_DURATION);
        fadeIn.setFillBefore(false);
        fadeIn.setFillAfter(true);
    }

    public void setData(ArrayList<Channel> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public ArrayList<Channel> getData() {
        return data;
    }

    public void clear() {
        data.clear();
        notifyDataSetInvalidated();
    }

    public void updateChannel(Channel channel) {
        for (int i = 0; i != data.size(); i++) {
            Channel item = data.get(i);
            if (item.getName().equals(channel.getName())) {
                item.setStatus(channel.getStatus());
                data.set(i, item);
            }
        }
        notifyDataSetInvalidated();
    }

    private void updateStatus(Status status, ViewHolder holder) {
        if (status == null || holder == null) {
            return;
        }

        //UNKNOWN
        int color = R.color.status_unknown;
        boolean visibilityLbl = false;
        boolean visibilityPrg = true;
        int text = R.string.channel_offline;
        initAnimation();

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
        if (status == Status.UNKNOWN) {
            holder.lblStatus.setAnimation(visibilityLbl ? fadeIn : fadeOut);
            holder.statusIndicator.setAnimation(fadeIn);
        }

    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Channel getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Channel channel = getItem(position);
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.list_item_favorites, null);
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

        picasso.load(channel.getLogo(Logo.SMALL)).placeholder(R.drawable.default_channel_logo_small).into(holder.imgLogo);
        holder.lblTitle.setText(channel.getTitle());
        updateStatus(channel.getStatus(), holder);

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
