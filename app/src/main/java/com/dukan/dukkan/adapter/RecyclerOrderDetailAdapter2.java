package com.dukan.dukkan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dukan.dukkan.R;
import com.dukan.dukkan.pojo.OrderDetail;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerOrderDetailAdapter2 extends RecyclerView.Adapter<RecyclerOrderDetailAdapter2.ViewHolder> {

    List<OrderDetail> mValues;
    Context mContext;
    protected ItemListener mListener;
    private AdapterView.OnItemClickListener listener;
    int row_index = -1;

    public RecyclerOrderDetailAdapter2(Context context, List<OrderDetail> values, ItemListener itemListener) {

        mValues = values;
        mContext = context;
        mListener = itemListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        OrderDetail item;
        TextView tv_product_name, tv_product_qty, tv_price;
        ImageView image;
        Button btnCancel;

        public RelativeLayout relative;

        public ViewHolder(View v) {
            super(v);

            v.setOnClickListener(this);
            image = v.findViewById(R.id.image);
            tv_product_name = v.findViewById(R.id.tv_product_name);
            tv_product_qty = v.findViewById(R.id.tv_product_qty);
            tv_price = v.findViewById(R.id.tv_price);
            btnCancel = v.findViewById(R.id.cancel_button);

        }

        public void setData(OrderDetail item) {
            this.item = item;

            tv_product_name.setText(item.productName);
            tv_product_qty.setText(item.qty);
            tv_price.setText("" + item.price);
            Picasso.get()
                    .load(item.product.image)
                    .into(image);


        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClick(item);
            }
        }
    }

    @Override
    public RecyclerOrderDetailAdapter2.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.order_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder Vholder, int position) {
        OrderDetail orderDetail = mValues.get(position);
        Vholder.setData(mValues.get(position));

//        Vholder.btnCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                mListener.onItemClick(orderDetail);
//            }
//        });

    }

    @Override
    public int getItemCount() {

        return mValues.size();
    }

    public interface ItemListener {
        void onItemClick(OrderDetail item);
    }

}