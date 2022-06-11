package com.dukan.dukkan.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dukan.dukkan.APIClient;
import com.dukan.dukkan.APIInterface;
import com.dukan.dukkan.R;
import com.dukan.dukkan.pojo.Coupon;
import com.dukan.dukkan.pojo.CouponList;
import com.dukan.dukkan.pojo.Order;
import com.dukan.dukkan.pojo.UpdateCouponStatus;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        SwitchCompat swCustom3;
        APIInterface apiInterface;
        ProgressBar progressBar;
        int sw3=0;
        public RelativeLayout relative;
        public ViewHolder(View v) {
            super(v);

            v.setOnClickListener(this);
            tv_discount_id = v.findViewById(R.id.tv_discount_id);
            tv_expiry_date = v.findViewById(R.id.tv_expiry_date);
            tv_desc = v.findViewById(R.id.tv_desc);
            swCustom3 = v.findViewById(R.id.swCustom3);
            progressBar = v.findViewById(R.id.progressBar);
            apiInterface = APIClient.getClient(mContext).create(APIInterface.class);


        }
        @SuppressLint("SetTextI18n")
        public void setData(Coupon item) {
            this.item = item;
            tv_discount_id.setText(""+item.code);
            tv_expiry_date.setText(item.expiration);
            System.out.println("HgggggggFFFF "+item.id);
            if( item.store!=null)
                tv_desc.setText("For Store");
            else  if( item.category!=null)
                tv_desc.setText("For "+item.category.name);
            if( item.product!=null)
                tv_desc.setText("For "+item.product.name);

            if(item.status.equals("1"))
                swCustom3.setChecked(true);
            else
                swCustom3.setChecked(false);
            swCustom3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                {
                    progressBar.setVisibility(View.VISIBLE);
                    if(isChecked)
                        sw3=1;
                    else
                        sw3=0;
                    Call<UpdateCouponStatus> call1 = apiInterface.UpdateStatusCoupon(item.id,sw3,"put");
                    call1.enqueue(new Callback<UpdateCouponStatus>() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onResponse(Call<UpdateCouponStatus> call, Response<UpdateCouponStatus> response) {
                            UpdateCouponStatus UpdateCouponStatus = response.body();
                            Toast.makeText(mContext, UpdateCouponStatus.message, Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onFailure(Call<UpdateCouponStatus> call, Throwable t) {
                            System.out.println("XXXXXXXXXaa "+item.id+"  "+t.getMessage());
                            progressBar.setVisibility(View.GONE);
                            call.cancel();
                        }
                    });

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