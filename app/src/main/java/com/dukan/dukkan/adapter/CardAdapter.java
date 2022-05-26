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
import com.dukan.dukkan.pojo.CartMain;
import com.dukan.dukkan.pojo.CartMain.Cart;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CardAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflter;
    private List<CartMain.Cart> mValues;

    public CardAdapter(
            Context applicationContext,List<CartMain.Cart> values
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
            view = inflter.inflate(R.layout.cart_item, null);
            viewHolder = new CardAdapter.ViewHolder();
            viewHolder.image = view.findViewById(R.id.image);
            viewHolder.product_color = view.findViewById(R.id.product_color);
            viewHolder.product_name = view.findViewById(R.id.product_name);
            viewHolder.product_count = view.findViewById(R.id.product_count);
            viewHolder.product_price = view.findViewById(R.id.product_price);
            viewHolder.relative_plus = view.findViewById(R.id.relative_plus);
            viewHolder.relative_minus = view.findViewById(R.id.relative_minus);
            viewHolder.relative_delete = view.findViewById(R.id.relative_delete);

            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if(mValues.get(i).product.name.length()>24)
            viewHolder.product_name.setText(mValues.get(i).product.name.substring(0,24)+"...");
        else
            viewHolder.product_name.setText(mValues.get(i).product.name);

        viewHolder. product_price.setText(Float.toString(mValues.get(i).product.price));
        viewHolder.product_count.setText(Integer.toString(mValues.get(i).qty));
        Picasso.get()
                .load(mValues.get(i).product.image)
                .into(viewHolder.image);

        return view;
    }
    private class ViewHolder {
       TextView product_name,product_count,product_price;
        ImageView image,product_color;
        RelativeLayout relative_plus,relative_minus,relative_delete;
    }


}
