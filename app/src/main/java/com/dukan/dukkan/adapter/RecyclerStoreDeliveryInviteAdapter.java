package com.dukan.dukkan.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dukan.dukkan.APIClient;
import com.dukan.dukkan.APIInterface;
import com.dukan.dukkan.R;
import com.dukan.dukkan.activity.AddAddressActivity;
import com.dukan.dukkan.activity.ShowStoresActivity;
import com.dukan.dukkan.pojo.Address;
import com.dukan.dukkan.pojo.AddressParameter;
import com.dukan.dukkan.pojo.Request;
import com.dukan.dukkan.pojo.RequestMerchant;
import com.dukan.dukkan.pojo.RequestStatus;
import com.dukan.dukkan.pojo.Store;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.sql.Timestamp;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class RecyclerStoreDeliveryInviteAdapter extends RecyclerView.Adapter<RecyclerStoreDeliveryInviteAdapter.ViewHolder> {
    List<RequestMerchant.Datum> mValues;
    Context mContext;
    Activity Aactivity;
    List<Store> stores;

    protected ItemListener mListener;
    private AdapterView.OnItemClickListener listener;
    int row_index = -1;

    public void setStores(List<Store> stores) {
        this.stores = stores;
        notifyDataSetChanged();
    }

    public RecyclerStoreDeliveryInviteAdapter(Activity activity, Context context, List<RequestMerchant.Datum> values) {

        mValues = values;
        mContext = context;
        Aactivity = activity;

//        mListener=itemListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        RequestMerchant.Datum item;
        TextView name;
        ImageView img_store;
        Button join_button;
        ProgressBar progressBar;
        APIInterface apiInterface;
        public RelativeLayout relative;

        public ViewHolder(View v) {
            super(v);

            v.setOnClickListener(this);
            img_store = v.findViewById(R.id.img_store);
            name = v.findViewById(R.id.tv_name);
            join_button = v.findViewById(R.id.join_button);

        }

        public void setData(RequestMerchant.Datum item) {
            this.item = item;

            name.setText(item.store.name);
            Picasso.get()
                    .load(item.store.image)
                    .into(img_store);
            // Glide.with(mContext).load(item.store.image).into(img_store);

            join_button.setVisibility(View.GONE);
            System.out.println("IIIIIIII88888 " + item.store.image);
//            Log.e("TAG", "setData: ", );
        }


        @Override
        public void onClick(View view) {

        }
    }

    @Override
    public RecyclerStoreDeliveryInviteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.store_invite_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder Vholder, int position) {
        Store store = stores.get(position);
        final int pos = position;
        Vholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.app.Dialog EndDialog = new Dialog(Aactivity, android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
                EndDialog.setContentView(R.layout.dialog_invite);
                EndDialog.setCancelable(false);
                Vholder.progressBar = EndDialog.findViewById(R.id.progressBar);
                ImageView img_close = EndDialog.findViewById(R.id.img_close);
                TextView dialog_ok = EndDialog.findViewById(R.id.yes_button);
                TextView dialog_cancel = EndDialog.findViewById(R.id.no_button);
                img_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EndDialog.dismiss();
                    }
                });
                dialog_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Doee(EndDialog, Vholder, "1", mValues.get(pos).id);
                     //   Log.e("TAG", "onClick: " + mValues.get(position).id);
                    }
                });
                dialog_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Doee(EndDialog, Vholder, "2", mValues.get(pos).id);
                    }
                });
                EndDialog.show();
                if (mListener != null) {
                    //    mListener.onItemClick(item);
                }
            }
        });
        Vholder.name.setText(store.name);
        Picasso.get()
                .load(store.image)
                .into(Vholder.img_store);
        // Glide.with(mContext).load(item.store.image).into(img_store);

        Vholder.join_button.setVisibility(View.GONE);
        // System.out.println("IIIIIIII88888 " + item.store.image);
        RequestMerchant.Datum datum = mValues.get(pos);
        if (datum.store != null) {
            Vholder.setData(datum);
        }


//        RequestMerchant.Datum datum= mValues.get(position);
//        Log.e("TAG", "onBindViewHolder: "+datum.store );
    }

    public void Doee(android.app.Dialog EndDialog, ViewHolder viewHolder, String statts, int id) {
        APIInterface apiInterface = APIClient.getClient(Aactivity).create(APIInterface.class);
        viewHolder.progressBar.setVisibility(View.VISIBLE);
        Call<RequestStatus> call1 = apiInterface.RequestDeliveryStatus(id, statts);
        call1.enqueue(new Callback<RequestStatus>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<RequestStatus> call, Response<RequestStatus> response) {
                RequestStatus requestStatus = response.body();
                if (requestStatus.status) {
                    Toast.makeText(mContext, requestStatus.message, Toast.LENGTH_SHORT).show();
                    EndDialog.dismiss();
                } else
                    Toast.makeText(mContext, requestStatus.message, Toast.LENGTH_SHORT).show();
                viewHolder.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<RequestStatus> call, Throwable t) {
                viewHolder.progressBar.setVisibility(View.GONE);
                call.cancel();
            }
        });
    }


    @Override
    public int getItemCount() {

        return stores.size();
    }

    public interface ItemListener {
        void onItemClick(RequestMerchant.Datum item);
    }
}