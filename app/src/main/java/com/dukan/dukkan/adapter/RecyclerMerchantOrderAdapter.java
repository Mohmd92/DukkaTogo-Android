package com.dukan.dukkan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dukan.dukkan.R;
import com.dukan.dukkan.pojo.OrderItem;

import java.util.List;

public class RecyclerMerchantOrderAdapter extends RecyclerView.Adapter<RecyclerMerchantOrderAdapter.ViewHolder> {
    List<OrderItem> mValues;
    Context mContext;
    protected ItemListener mListener;
    private AdapterView.OnItemClickListener listener;
    int row_index=-1;
    public RecyclerMerchantOrderAdapter(Context context, List<OrderItem> values) {

        mValues = values;
        mContext = context;
//        mListener=itemListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        OrderItem item;
        TextView tv_product_name,tv_product_qty,tv_price;
        ImageView image;

        public RelativeLayout relative;
        public ViewHolder(View v) {
            super(v);

            v.setOnClickListener(this);
            image = v.findViewById(R.id.image);
            tv_product_name = v.findViewById(R.id.tv_product_name);
            tv_product_qty = v.findViewById(R.id.tv_product_qty);
            tv_price = v.findViewById(R.id.tv_price);

        }
        public void setData(OrderItem item) {
            this.item = item;

//            tv_product_name.setText(item.);
//            tv_product_qty.setText(item.qty);
//            tv_price.setText(item.price);

        }
        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClick(item);
            }
        }
    }

    @Override
    public RecyclerMerchantOrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.order_list_item, parent, false);

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
        void onItemClick(OrderItem item);
    }
}