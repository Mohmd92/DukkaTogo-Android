package com.dukan.dukkan.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.dukan.dukkan.R;
import com.dukan.dukkan.pojo.Brand;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DeliveryAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflter;
    private List<Brand> mValues;

    public DeliveryAdapter(
            Context applicationContext,List<Brand> values
            ) {
        this.context = applicationContext;
        this.mValues = values;

        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getViewTypeCount() {
        return 1; // getCount();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return mValues.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint({"ViewHolder", "SetTextI18n"})
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (inflter == null)
            inflter = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = inflter.inflate(R.layout.brand_item, null);
            viewHolder = new ViewHolder();
            viewHolder.image = view.findViewById(R.id.image);

            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Picasso.get()
                .load(mValues.get(i).image)
                .into(viewHolder.image);
        return view;
    }
    private class ViewHolder {
        ImageView image;
    }


}
