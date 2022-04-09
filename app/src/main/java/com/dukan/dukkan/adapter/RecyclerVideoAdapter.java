package com.dukan.dukkan.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dukan.dukkan.R;
import com.dukan.dukkan.activity.ShowStoresActivity;
import com.dukan.dukkan.pojo.Video;
import com.dukan.dukkan.pojo.Video;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class RecyclerVideoAdapter extends RecyclerView.Adapter<RecyclerVideoAdapter.ViewHolder> {
    List<Video.Datum> mValues;
    Context mContext;
    protected ItemListener mListener;
    private AdapterView.OnItemClickListener listener;
    int row_index=-1;
    public RecyclerVideoAdapter(Context context, List<Video.Datum> values) {

        mValues = values;
        mContext = context;
//        mListener=itemListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Video.Datum item;
        TextView tv_title;
        ImageView select_item_image;
        RatingBar ratebar;

        public RelativeLayout relative;
        public ViewHolder(View v) {
            super(v);

            v.setOnClickListener(this);
            select_item_image = v.findViewById(R.id.select_item_image);
            tv_title = v.findViewById(R.id.tv_title);
            ratebar = v.findViewById(R.id.ratingBar2);

        }
        public void setData(Video.Datum item) {
            this.item = item;
            tv_title.setText(item.title);
                Picasso.get()
                        .load(item.image)
                        .into(select_item_image);

        }
        @Override
        public void onClick(View view) {
            if(item.url!=null) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.url));
                browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(browserIntent);
            }
        }
    }

    @Override
    public RecyclerVideoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.learn_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder Vholder, int position) {
        Vholder.setData(mValues.get(position));

    }
    @Override
    public int getItemCount() {

        return mValues.size();
    }
     public interface ItemListener {
        void onItemClick(Video.Datum item);
    }
}