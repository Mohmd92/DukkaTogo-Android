package com.dukan.dukkan.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.text.format.DateUtils;
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
import com.dukan.dukkan.pojo.MultipleStore;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerStoreAdapter extends RecyclerView.Adapter<RecyclerStoreAdapter.ViewHolder> {
    List<MultipleStore.Datum> mValues;
    Context mContext;
    protected ItemListener mListener;
    private AdapterView.OnItemClickListener listener;
    int row_index=-1;
    public RecyclerStoreAdapter(Context context, List<MultipleStore.Datum> values) {

        mValues = values;
        mContext = context;
//        mListener=itemListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        MultipleStore.Datum item;
        TextView name;
        ImageView select_item_image;
        RatingBar ratebar;

        public RelativeLayout relative;
        public ViewHolder(View v) {
            super(v);

            v.setOnClickListener(this);
            select_item_image = v.findViewById(R.id.image);
            name = v.findViewById(R.id.tv_name);
            ratebar = v.findViewById(R.id.ratingBar2);

        }
        public void setData(MultipleStore.Datum item) {
            this.item = item;

            name.setText(item.name);
                Picasso.get()
                        .load(item.image)
                        .into(select_item_image);
            Log.d("TAG1112111"," item.name "+item.name);

        }
        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClick(item);
            }
        }
    }

    @Override
    public RecyclerStoreAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.store_item2, parent, false);

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
        void onItemClick(MultipleStore.Datum item);
    }
}