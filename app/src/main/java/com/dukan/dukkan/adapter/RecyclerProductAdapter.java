package com.dukan.dukkan.adapter;

import android.content.Context;
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
import com.dukan.dukkan.pojo.MultipleProducts;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerProductAdapter extends RecyclerView.Adapter<RecyclerProductAdapter.ViewHolder> {
    List<MultipleProducts.Datum> mValues;
    Context mContext;
    protected ItemListener mListener;
    private AdapterView.OnItemClickListener listener;
    int row_index=-1;
    public RecyclerProductAdapter(Context context, List<MultipleProducts.Datum> values) {

        mValues = values;
        mContext = context;
//        mListener=itemListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        MultipleProducts.Datum item;
        TextView tv_price,tv_name;
        RatingBar rateProduct;
        ImageView image;
        RelativeLayout rel_add_to_card,rel_heart;

        public RelativeLayout relative;
        public ViewHolder(View v) {
            super(v);

            v.setOnClickListener(this);
            image =  v.findViewById(R.id.image);
            tv_name =  v.findViewById(R.id.tv_name);
            tv_price =  v.findViewById(R.id.tv_price);
            rateProduct =  v.findViewById(R.id.ratingBar2);
            rel_add_to_card =  v.findViewById(R.id.rel_add_to_card);
            rel_heart =  v.findViewById(R.id.rel_heart);

        }
        public void setData(MultipleProducts.Datum item) {
            this.item = item;

            tv_name.setText(item.name);
                Picasso.get()
                        .load(item.image)
                        .into(image);
            tv_price.setText("" + item.price);

        }
        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClick(item);
            }
        }
    }

    @Override
    public RecyclerProductAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.product_item, parent, false);

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
        void onItemClick(MultipleProducts.Datum item);
    }
}