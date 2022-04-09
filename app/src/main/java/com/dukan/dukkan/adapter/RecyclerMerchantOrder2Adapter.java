package com.dukan.dukkan.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dukan.dukkan.R;
import com.dukan.dukkan.activity.MerchantOrderDetailsActivity;
import com.dukan.dukkan.activity.MerchantOrdersDeliveredActivity;
import com.dukan.dukkan.activity.ShowStoresActivity;
import com.dukan.dukkan.pojo.Order;
import com.dukan.dukkan.pojo.Order.Datum;
import com.dukan.dukkan.util.SharedPreferenceManager;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class RecyclerMerchantOrder2Adapter extends RecyclerView.Adapter<RecyclerMerchantOrder2Adapter.ViewHolder> {
    List<Order.Datum> mValues;
    Context mContext;
    protected ItemListener mListener;
    public RecyclerMerchantOrder2Adapter(Context context, List<Order.Datum> values) {

        mValues = values;
        mContext = context;
//        mListener=itemListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Order.Datum item;
        TextView tv_from,tv_to;


        public RelativeLayout relative;
        public ViewHolder(View v) {
            super(v);

            v.setOnClickListener(this);
            tv_from = v.findViewById(R.id.tv_from);
            tv_to = v.findViewById(R.id.tv_to);

        }
        public void setData(Order.Datum item) {
            this.item = item;

            tv_from.setText(SharedPreferenceManager.getInstance(mContext).getAddress());
            if(item.address!=null)
                tv_to.setText(item.address.name);

        }
        @Override
        public void onClick(View view) {
            Intent i2 = new Intent(mContext, MerchantOrderDetailsActivity.class);
            i2.putExtra("OrderId", item.id);
            i2.addFlags(FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(i2);
            if (mListener != null) {
                mListener.onItemClick(item);
            }
        }
    }

    @Override
    public RecyclerMerchantOrder2Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.requests_item, parent, false);

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
        void onItemClick(Order.Datum item);
    }
}