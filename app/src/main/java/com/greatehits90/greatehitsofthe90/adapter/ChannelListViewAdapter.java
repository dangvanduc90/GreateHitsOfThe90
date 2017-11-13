package com.greatehits90.greatehitsofthe90.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.greatehits90.greatehitsofthe90.R;
import com.greatehits90.greatehitsofthe90.object.Channel;

import java.util.ArrayList;

public class ChannelListViewAdapter extends ArrayAdapter<Channel> {
    private Context mContext;
    private int resource;
    private ArrayList<Channel> arrContext;

    public ChannelListViewAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<Channel> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.resource = resource;
        this.arrContext = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.row_item_channel, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.ivImage = convertView.findViewById(R.id.ivImage);
            viewHolder.tvTitle = convertView.findViewById(R.id.tvTitle);
            viewHolder.tvArtist = convertView.findViewById(R.id.tvArtist);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Channel channel = arrContext.get(position);

        Glide.with(mContext).load(channel.getImage()).into(viewHolder.ivImage);
        viewHolder.tvTitle.setText(channel.getTitle());
        viewHolder.tvArtist.setText(channel.getArtist());

        return convertView;
    }

    class ViewHolder {
        ImageView ivImage;
        TextView tvTitle, tvArtist;
    }
}
