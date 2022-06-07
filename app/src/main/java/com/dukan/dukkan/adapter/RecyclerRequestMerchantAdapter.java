package com.dukan.dukkan.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dukan.dukkan.R;
import com.dukan.dukkan.activity.DriverProfileForMerchentActivity;
import com.dukan.dukkan.pojo.RequestMerchant;
import com.dukan.dukkan.pojo.RequestMerchant.Datum;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerRequestMerchantAdapter extends RecyclerView.Adapter<RecyclerRequestMerchantAdapter.ViewHolder> {
    List<RequestMerchant.Datum> mValues;
    Context mContext;
    protected ItemListener mListener;
    private AdapterView.OnItemClickListener listener;
    int row_index=-1;
    public RecyclerRequestMerchantAdapter(Context context, List<RequestMerchant.Datum> values) {

        mValues = values;
        mContext = context;
//        mListener=itemListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        RequestMerchant.Datum item;
        TextView name,tv_join_date,tv_status;
        ImageView image_driver;

        public RelativeLayout relative;
        public ViewHolder(View v) {
            super(v);

            v.setOnClickListener(this);
            image_driver = v.findViewById(R.id.image_driver);
            name = v.findViewById(R.id.tv_name);
            tv_status = v.findViewById(R.id.tv_status);
            tv_join_date = v.findViewById(R.id.tv_join_date);

        }
        public void setData(RequestMerchant.Datum item) {
            this.item = item;

            name.setText(item.delivery.name);
            tv_status.setText(item.stringStatus);
            tv_join_date.setText(item.createdAt.split("T")[0]);
                Picasso.get()
                        .load(item.delivery.image)
                        .into(image_driver);

        }
        @Override
        public void onClick(View view) {
            Intent i = new Intent(mContext, DriverProfileForMerchentActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("join","false");
            i.putExtra("allData", item.id+"&&"+item.delivery.name+"&&"+item.delivery.address+"&&"+item.delivery.licenseNumber+"&&"+item.delivery.image+"&&"+item.status);
            mContext.startActivity(i);
            if (mListener != null) {
                mListener.onItemClick(item);
            }
        }
    }

    @Override
    public RecyclerRequestMerchantAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.drivers_item2, parent, false);

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
        void onItemClick(RequestMerchant.Datum item);
    }
}