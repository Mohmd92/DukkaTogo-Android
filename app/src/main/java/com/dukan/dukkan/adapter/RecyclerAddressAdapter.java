package com.dukan.dukkan.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.dukan.dukkan.R;
import com.dukan.dukkan.activity.CheckOut;
import com.dukan.dukkan.activity.EditAddressActivity;
import com.dukan.dukkan.activity.ProductsActivity;
import com.dukan.dukkan.pojo.AllAddress;
import com.dukan.dukkan.pojo.AllAddress.AddressData;
import com.dukan.dukkan.pojo.CartMain;
import com.dukan.dukkan.pojo.CartParamenter;
import com.dukan.dukkan.pojo.CartRemoveParamenter;
import com.dukan.dukkan.pojo.Rate;
import com.dukan.dukkan.util.SharedPreferenceManager;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyclerAddressAdapter extends RecyclerView.Adapter<RecyclerAddressAdapter.ViewHolder> {
    List<AllAddress.AddressData> mValues;
    Context mContext;
    protected ItemListener mListener;
    private AdapterView.OnItemClickListener listener;
    int row_index=-1;
    public RecyclerAddressAdapter(Context context, List<AllAddress.AddressData> values) {

        mValues = values;
        mContext = context;
//        mListener=itemListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        AllAddress.AddressData item;
        TextView tv_mobile,tv_address,tv_name,tv_change,tv_address_name;
        ImageView image;

        public RelativeLayout relative;
        public ViewHolder(View v) {
            super(v);

            v.setOnClickListener(this);
            image = v.findViewById(R.id.image);
            tv_mobile = v.findViewById(R.id.tv_mobile);
            tv_address = v.findViewById(R.id.tv_address);
            tv_name = v.findViewById(R.id.tv_name);
            tv_change = v.findViewById(R.id.tv_change);
            tv_address_name = v.findViewById(R.id.tv_address_name);

        }
        public void setData(AllAddress.AddressData item) {
            this.item = item;
            int ii=getAdapterPosition()+1;
            tv_address_name.setText("Home "+ii);
            tv_name.setText(item.name);
            tv_address.setText(item.location);
            tv_mobile.setText(item.mobile);
            tv_change.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(mContext, EditAddressActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("allData", item.id+"&&"+item.name+"&&"+item.location+"&&"+item.country+"&&"+item.city);
                    mContext.startActivity(i);
                }
            });
        }
        @Override
        public void onClick(View view) {
            SharedPreferenceManager.getInstance(mContext).setSelectedAddress(item.id+"&"+item.name+"&"+item.location+"&"+item.mobile+"&"+tv_address_name.getText().toString());
            Intent i = new Intent(mContext, CheckOut.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(i);
            if (mListener != null) {
                mListener.onItemClick(item);
            }
        }
    }

    @Override
    public RecyclerAddressAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.address_item, parent, false);

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
        void onItemClick(AllAddress.AddressData item);
    }
}