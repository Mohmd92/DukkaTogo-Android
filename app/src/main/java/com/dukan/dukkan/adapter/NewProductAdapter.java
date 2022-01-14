package com.dukan.dukkan.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dukan.dukkan.R;
import com.dukan.dukkan.pojo.MostWanted;
import com.dukan.dukkan.pojo.NewProduct;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewProductAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflter;
    private List<NewProduct> mValues;

    public NewProductAdapter(
            Context applicationContext,List<NewProduct> values
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
            view = inflter.inflate(R.layout.wanted_item, null);
            viewHolder = new ViewHolder();
            viewHolder.image =  view.findViewById(R.id.image);
            viewHolder.tv_name =  view.findViewById(R.id.tv_name);
            viewHolder.tv_price =  view.findViewById(R.id.tv_price);
            viewHolder.rateProduct =  view.findViewById(R.id.ratingBar2);
            viewHolder.rel_add_to_card =  view.findViewById(R.id.rel_add_to_card);
            viewHolder.rel_heart =  view.findViewById(R.id.rel_heart);

            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tv_name.setText("" + mValues.get(i).name);
        viewHolder.tv_price.setText("" + mValues.get(i).price);
        Picasso.get()
                .load(mValues.get(i).image)
                .into(viewHolder.image);
        return view;
    }
    private class ViewHolder {
        TextView tv_price,tv_name;
        RatingBar rateProduct;
        ImageView image;
        RelativeLayout rel_add_to_card,rel_heart;
    }


}
