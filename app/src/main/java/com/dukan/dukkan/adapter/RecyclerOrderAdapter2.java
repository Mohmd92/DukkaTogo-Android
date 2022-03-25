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
import com.dukan.dukkan.pojo.Order;

import java.util.List;

public class RecyclerOrderAdapter2 extends RecyclerView.Adapter<RecyclerOrderAdapter2.ViewHolder> {
    List<Order.Datum> mValues;
    Context mContext;
    protected ItemListener mListener;
    private AdapterView.OnItemClickListener listener;
    int row_index=-1;
    private int lastCheckedPosition = -1;
    private int initPosition = -1;
    public RecyclerOrderAdapter2(Context context, List<Order.Datum> values) {

        mValues = values;
        mContext = context;
//        mListener=itemListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Order.Datum item;
        TextView tv_day,tv_date,tv_num_products;
        ImageView img_barcode;
        RecyclerView recyclerView;
        ConstraintLayout linnnar;

        public RelativeLayout relative;
        public ViewHolder(View v) {
            super(v);

            v.setOnClickListener(this);
            tv_day = v.findViewById(R.id.tv_day);
            tv_date = v.findViewById(R.id.tv_date);
            tv_num_products = v.findViewById(R.id.tv_num_products);
            img_barcode = v.findViewById(R.id.img_barcode);
            recyclerView = v.findViewById(R.id.recyclerView);
            linnnar = v.findViewById(R.id.linnnar);

        }
        @SuppressLint("SetTextI18n")
        public void setData(Order.Datum item) {
            this.item = item;
            linnnar.setVisibility(View.GONE);
            if(item.status.equals("0")) {
                linnnar.setVisibility(View.VISIBLE);
                tv_date.setText(item.createdAt.split("T")[0]);
                tv_num_products.setText("" + item.orderDetails.size());
                LinearLayoutManager layoutManager
                        = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                RecyclerOrderDetailAdapter2 adapter = new RecyclerOrderDetailAdapter2(mContext, item.orderDetails);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(layoutManager);
            }

        }
        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClick(item);
            }
        }
    }

    @Override
    public RecyclerOrderAdapter2.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.order_item, parent, false);

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