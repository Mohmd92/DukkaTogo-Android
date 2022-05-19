package com.dukan.dukkan.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.dukan.dukkan.APIClient;
import com.dukan.dukkan.APIInterface;
import com.dukan.dukkan.R;
import com.dukan.dukkan.activity.ShowProductActivity;
import com.dukan.dukkan.activity.ShowStoresActivity;
import com.dukan.dukkan.pojo.CartParamenter;
import com.dukan.dukkan.pojo.JoinStore;
import com.dukan.dukkan.pojo.JoinStore;
import com.dukan.dukkan.pojo.MultipleStore;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class RecyclerStoreNeedAdapter extends RecyclerView.Adapter<RecyclerStoreNeedAdapter.ViewHolder> {
    List<MultipleStore.Datum> mValues;
    Context mContext;
    protected ItemListener mListener;
    private AdapterView.OnItemClickListener listener;
    APIInterface apiInterface;
    public RecyclerStoreNeedAdapter(Context context, List<MultipleStore.Datum> values) {

        mValues = values;
        mContext = context;
//        mListener=itemListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        MultipleStore.Datum item;
        TextView name;
        ImageView img_store;
        Button join_button;
        ProgressBar progressBar;

        public RelativeLayout relative;
        public ViewHolder(View v) {
            super(v);
            apiInterface = APIClient.getClient(mContext).create(APIInterface.class);
            v.setOnClickListener(this);
            img_store = v.findViewById(R.id.img_store);
            name = v.findViewById(R.id.tv_name);
            join_button = v.findViewById(R.id.join_button);
            progressBar = v.findViewById(R.id.progressBar);

        }
        public void setData(MultipleStore.Datum item) {
            this.item = item;

            name.setText(item.name);
                Picasso.get()
                        .load(item.image)
                        .into(img_store);
            join_button.setVisibility(View.GONE);
            join_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Call<JoinStore> call1 = apiInterface.JoinStores(item.id);
                    call1.enqueue(new Callback<JoinStore>() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onResponse(Call<JoinStore> call, Response<JoinStore> response) {
                            JoinStore joinstore = response.body();
                            if(joinstore.status){
                                join_button.setVisibility(View.GONE);
                            }
                            Toast.makeText(mContext, ""+joinstore.message, Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onFailure(Call<JoinStore> call, Throwable t) {
//                            Toast.makeText(context, "onFailure", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            call.cancel();
                        }
                    });
                }
            });
        }
        @Override
        public void onClick(View view) {
//            Intent i2 = new Intent(mContext, ShowStoresActivity.class);
//            i2.putExtra("user", "delivery");
//            i2.putExtra("StoreID", item.id);
//            i2.putExtra("most", 0);
//            i2.addFlags(FLAG_ACTIVITY_NEW_TASK);
//            mContext.startActivity(i2);
//            if (mListener != null) {
//                mListener.onItemClick(item);
//            }
        }
    }

    @Override
    public RecyclerStoreNeedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.store_need_item, parent, false);

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
        void onItemClick(MultipleStore.Datum item);
    }
}