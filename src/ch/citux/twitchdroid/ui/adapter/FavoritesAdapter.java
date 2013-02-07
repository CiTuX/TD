package ch.citux.twitchdroid.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import ch.citux.twitchdroid.R;
import ch.citux.twitchdroid.data.model.Channel;
import ch.citux.twitchdroid.data.model.Logo;
import com.yixia.zi.utils.ImageFetcher;

import java.util.ArrayList;

public class FavoritesAdapter extends BaseAdapter {

    private ArrayList<Channel> data;
    private LayoutInflater inflater;
    private ImageFetcher imageFetcher;

    public FavoritesAdapter(Context context) {
        init(context, new ArrayList<Channel>());
    }

    public FavoritesAdapter(Context context, ArrayList<Channel> data) {
        init(context, data);
    }

    private void init(Context context, ArrayList<Channel> data) {
        this.data = data;
        inflater = LayoutInflater.from(context);
        imageFetcher = new ImageFetcher(context);
    }

    public void setData(ArrayList<Channel> data) {
        this.data = data;
        notifyDataSetChanged();
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
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null || convertView.getTag() == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.favorites_list_item, null);
            holder.imgChannelLogo = (ImageView) convertView.findViewById(R.id.imgChannelLogo);
            holder.txtChannelTitle = (TextView) convertView.findViewById(R.id.txtChannelTitle);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Channel channel = getItem(position);
        imageFetcher.loadThumbnailImage(channel.getLogo(Logo.SMALL), holder.imgChannelLogo, R.drawable.default_channel_logo_small);
        holder.txtChannelTitle.setText(channel.getTitle());

        return convertView;
    }

    private static class ViewHolder {
        ImageView imgChannelLogo;
        TextView txtChannelTitle;
    }

}
