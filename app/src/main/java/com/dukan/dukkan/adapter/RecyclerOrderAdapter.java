package com.dukan.dukkan.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dukan.dukkan.R;
import com.dukan.dukkan.fragment.ChooseDriverSheetFragment;
import com.dukan.dukkan.pojo.Order;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RecyclerOrderAdapter extends RecyclerView.Adapter<RecyclerOrderAdapter.ViewHolder> {
    List<Order.Datum> mValues;
    Context mContext;
    Activity Aactivity;
    protected ItemListener mListener;
    private AdapterView.OnItemClickListener listener;
    int row_index = -1;
    private int lastCheckedPosition = -1;
    private int initPosition = -1;

    public RecyclerOrderAdapter(Context context, List<Order.Datum> values, Activity activity) {

        mValues = values;
        mContext = context;
        Aactivity = activity;

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Order.Datum item;
        TextView tv_day, tv_date, tv_num_products;
        ImageView img_barcode;
        RecyclerView recyclerView;
        ConstraintLayout linnnar;
        Button btnCancel;

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
            btnCancel = v.findViewById(R.id.cancel_button);

        }

        @SuppressLint("SetTextI18n")
        public void setData(Order.Datum item) {
            this.item = item;
            btnCancel.setVisibility(View.GONE);
            linnnar.setVisibility(View.GONE);
            linnnar.setVisibility(View.VISIBLE);
            tv_date.setText(item.createdAt.split("T")[0]);

            tv_num_products.setText("" + item.orderDetails.size());
            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
            RecyclerOrderDetailAdapter adapter = new RecyclerOrderDetailAdapter(mContext, item.orderDetails);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(layoutManager);
            img_barcode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final BottomSheetDialog bt = new BottomSheetDialog(Aactivity, R.style.AppBottomSheetDialogTheme);
                    View views = LayoutInflater.from(mContext).inflate(R.layout.bottom_sheet_choose_driver, null);
                    ImageView imageView = views.findViewById(R.id.img_qrcode);
                    Glide.with(mContext).load(item.qrCode).into(imageView);
                    bt.setContentView(views);
                    bt.show();
                }
            });

        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClick(item);
            }
        }
    }

    @Override
    public RecyclerOrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.order_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder Vholder, int position) {
        Order.Datum datum = mValues.get(position);
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