package com.dukan.dukkan.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dukan.dukkan.R;
import com.dukan.dukkan.pojo.Coupon;
import com.dukan.dukkan.pojo.CouponList;
import com.dukan.dukkan.pojo.Order;

import java.util.List;

public class RecyclerdiscountAdapter extends RecyclerView.Adapter<RecyclerdiscountAdapter.ViewHolder> {
    List<Coupon> mValues;
    Context mContext;
    protected ItemListener mListener;
    private AdapterView.OnItemClickListener listener;
    int row_index=-1;
    private int lastCheckedPosition = -1;
    private int initPosition = -1;
    public RecyclerdiscountAdapter(Context context, List<Coupon> values) {

        mValues = values;
        mContext = context;
//        mListener=itemListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Coupon item;
        TextView tv_discount_id,tv_expiry_date,tv_desc;

        public RelativeLayout relative;
        public ViewHolder(View v) {
            super(v);

            v.setOnClickListener(this);
            tv_discount_id = v.findViewById(R.id.tv_discount_id);
            tv_expiry_date = v.findViewById(R.id.tv_expiry_date);
            tv_desc = v.findViewById(R.id.tv_desc);

        }
        @SuppressLint("SetTextI18n")
        public void setData(Coupon item) {
            this.item = item;
            tv_discount_id.setText(""+item.code);
            tv_expiry_date.setText(item.expiration);
            if( item.store!=null)
                tv_desc.setText("For Store");
            else  if( item.category!=null)
                tv_desc.setText("For "+item.category.name);
            if( item.product!=null)
                tv_desc.setText("For "+item.product.name);

        }
        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClick(item);
            }
        }
    }

    @Override
    public RecyclerdiscountAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.discount_item, parent, false);

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
        void onItemClick(Coupon item);
    }
}