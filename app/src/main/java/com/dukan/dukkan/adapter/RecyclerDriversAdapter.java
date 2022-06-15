package com.dukan.dukkan.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
import com.dukan.dukkan.activity.DriverProfileActivity;
import com.dukan.dukkan.activity.DriverProfileForMerchentActivity;
import com.dukan.dukkan.activity.DriverProfileLicenceForMerchantActivity;
import com.dukan.dukkan.activity.ShowStoresActivity;
import com.dukan.dukkan.pojo.MultipleStore;
import com.dukan.dukkan.pojo.UserOrder;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class RecyclerDriversAdapter extends RecyclerView.Adapter<RecyclerDriversAdapter.ViewHolder> {
    List<UserOrder> mValues;
    Context mContext;
    protected ItemListener mListener;
    private AdapterView.OnItemClickListener listener;
    int row_index=-1;
    public RecyclerDriversAdapter(Context context, List<UserOrder> values) {

        mValues = values;
        mContext = context;
//        mListener=itemListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        UserOrder item;
        TextView name,tv_join_date;
        ImageView image_driver;

        public RelativeLayout relative;
        public ViewHolder(View v) {
            super(v);

            v.setOnClickListener(this);
            image_driver = v.findViewById(R.id.image_driver);
            name = v.findViewById(R.id.tv_name);
            tv_join_date = v.findViewById(R.id.tv_join_date);

        }
        public void setData(UserOrder item) {
            this.item = item;

            name.setText(item.name);
            tv_join_date.setText(item.createdAt.split("T")[0]);
                Picasso.get()
                        .load(item.image)
                        .into(image_driver);

        }
        @Override
        public void onClick(View view) {
            String UserProfileString=item.name+"&&"+item.image+"&&"+item.licenseNumber+"&&"+"null"+"&&"+item.licensePicture+"&&"+item.vehiclePicture+"&&"+item.id;

            Intent i = new Intent(mContext, DriverProfileLicenceForMerchantActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("join","true");
            i.putExtra("UserProfile", UserProfileString);
            mContext.startActivity(i);
            if (mListener != null) {
                mListener.onItemClick(item);
            }
        }
    }

    @Override
    public RecyclerDriversAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.drivers_item, parent, false);

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
        void onItemClick(UserOrder item);
    }
}