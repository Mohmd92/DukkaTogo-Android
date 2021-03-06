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
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dukan.dukkan.APIClient;
import com.dukan.dukkan.APIInterface;
import com.dukan.dukkan.R;
import com.dukan.dukkan.fragment.ChooseDriverSheetFragment;
import com.dukan.dukkan.pojo.Order;
import com.dukan.dukkan.pojo.OrderDetail;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyclerOrderAdapter2 extends RecyclerView.Adapter<RecyclerOrderAdapter2.ViewHolder> {
    List<Order.Datum> mValues;
    Context mContext;
    protected ItemListener mListener;
    private AdapterView.OnItemClickListener listener;
    int row_index = -1;
    private int lastCheckedPosition = -1;
    private int initPosition = -1;
    Activity Aactivity;
    APIInterface apiInterface;

    public RecyclerOrderAdapter2(Context context, List<Order.Datum> values, Activity activity, ItemListener Listener) {

        mValues = values;
        mContext = context;
        Aactivity = activity;
        mListener = Listener;
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
            Toast.makeText(mContext, item.status, Toast.LENGTH_SHORT).show();
            linnnar.setVisibility(View.GONE);
//            if(item.status.equals("0")) {
            linnnar.setVisibility(View.VISIBLE);
            tv_date.setText(item.createdAt.split("T")[0]);
            tv_num_products.setText("" + item.orderDetails.size());
            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
            RecyclerOrderDetailAdapter2 adapter = new RecyclerOrderDetailAdapter2(mContext, item.orderDetails, new RecyclerOrderDetailAdapter2.ItemListener() {
                @Override
                public void onItemClick(OrderDetail item) {

                }
            });
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(layoutManager);
            img_barcode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();

                    final BottomSheetDialog bt = new BottomSheetDialog(Aactivity, R.style.AppBottomSheetDialogTheme);
                    View views = LayoutInflater.from(mContext).inflate(R.layout.bottom_sheet_choose_driver, null);
                    ImageView imageView = views.findViewById(R.id.img_qrcode);
                    Glide.with(mContext).load(item.qrCode).into(imageView);
                    bt.setContentView(views);
                    bt.show();
                }
            });
//            }

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
        apiInterface = APIClient.getClient(mContext).create(APIInterface.class);
        View view = LayoutInflater.from(mContext).inflate(R.layout.order_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder Vholder, int position) {
        Order.Datum datum = mValues.get(position);
        if (!datum.status.equals("4")) {
            Vholder.setData(mValues.get(position));
        }
        Vholder.btnCancel.setOnClickListener(view -> {
            mListener.onItemClick(datum);
            cancelOrders(datum);
            notifyDataSetChanged();

        });
    }

    @Override
    public int getItemCount() {
        return mValues != null ? mValues.size() : 0;
    }

    public interface ItemListener {
        void onItemClick(Order.Datum item);
    }
    private void cancelOrders(Order.Datum item) {

        apiInterface.cancelOrders(item.id, 4).enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {

                Log.e("TAG", "onResponse: " + response.message());
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                Log.e("TAG", "onResponse: " + t.getMessage());
            }
        });

    }
}