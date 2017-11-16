package com.greatehits90.greatehitsofthe90.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.greatehits90.greatehitsofthe90.R;
import com.greatehits90.greatehitsofthe90.common.LoadMoreItem;
import com.greatehits90.greatehitsofthe90.object.ObjectRadio;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChannelListViewAdapter extends BaseAdapter {
    private Context mContext;
    private int resource;
    private ArrayList<ObjectRadio> arrContext;
    private LoadMoreItem loadMoreItem;

    public ChannelListViewAdapter(Context mContext, ArrayList<ObjectRadio> arrContext, LoadMoreItem loadMoreItem) {
        this.mContext = mContext;
        this.arrContext = arrContext;
        this.loadMoreItem = loadMoreItem;
    }

    @Override
    public int getCount() {
        return arrContext.size();
    }

    @Override
    public ObjectRadio getItem(int position) {
        return arrContext.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

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
        ObjectRadio channel = arrContext.get(position);
        Glide.with(mContext).load(channel.getLinkImg()).centerCrop().into(viewHolder.ivImage);
        viewHolder.tvTitle.setText(channel.getName());
        viewHolder.tvArtist.setText(channel.getContent());
        loadMoreItem.numberItem(position);
        return convertView;
    }

    class ViewHolder {
        CircleImageView ivImage;
        TextView tvTitle, tvArtist;
    }
}
