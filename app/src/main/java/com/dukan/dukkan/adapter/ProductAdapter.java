package com.dukan.dukkan.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.dukan.dukkan.R;
import com.dukan.dukkan.pojo.OrderDetail;
import com.dukan.dukkan.pojo.Store;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflter;
    private List<OrderDetail> mValues;

    public ProductAdapter(
            Context applicationContext,List<OrderDetail> values
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
            view = inflter.inflate(R.layout.products_item, null);
            viewHolder = new ViewHolder();
            viewHolder.image = (ImageView) view.findViewById(R.id.image);
            viewHolder.tv_name = (TextView) view.findViewById(R.id.tv_name);
            viewHolder.tv_price = (TextView) view.findViewById(R.id.tv_price);
            viewHolder.rateStore = (RatingBar) view.findViewById(R.id.rateStore);

            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tv_name.setText("" + mValues.get(i).productName);
        viewHolder.tv_price.setText("" + mValues.get(i).product.price);
        Picasso.get()
                .load(mValues.get(i).product.image)
                .into(viewHolder.image);
        viewHolder.rateStore.setRating(mValues.get(i).product.rate);
        return view;
    }
    private class ViewHolder {
        TextView tv_name,tv_price;
        RatingBar rateStore;
        ImageView image;
    }


}
