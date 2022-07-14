package com.dukan.dukkan.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.dukan.dukkan.APIClient;
import com.dukan.dukkan.APIInterface;
import com.dukan.dukkan.R;
import com.dukan.dukkan.activity.CartActivity;
import com.dukan.dukkan.activity.ProductsActivity;
import com.dukan.dukkan.pojo.CartMain;
import com.dukan.dukkan.pojo.CartRemoveParamenter;
import com.dukan.dukkan.pojo.CategoryProduct;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyclerCategoryAdapter extends RecyclerView.Adapter<RecyclerCategoryAdapter.ViewHolder> {
    List<CategoryProduct> mValues;
    Context mContext;
    private ItemClickListener clickListener;
    APIInterface apiInterface;

    public RecyclerCategoryAdapter(Context context, List<CategoryProduct> values) {

        mValues = values;
        mContext = context;
//        mListener=itemListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CategoryProduct item;
        TextView category_name;
        RelativeLayout relative;

        public ViewHolder(View v) {
            super(v);
            apiInterface = APIClient.getClient(mContext).create(APIInterface.class);

            category_name = v.findViewById(R.id.category_name);
            relative = v.findViewById(R.id.relative);

        }
        public void setData(CategoryProduct item) {
            this.item = item;
            category_name.setText(item.name);
            Log.d("TAG111111","item.name "+item.name);

            itemView.setOnClickListener(this); // bind the listener

        }

        @Override
        public void onClick(View view) {

                    Intent i = new Intent(mContext, ProductsActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("title", item.name);
                    i.putExtra("store", item.storeId);
                    i.putExtra("new", 0);
                    i.putExtra("most", 0);
                    i.putExtra("category", item.id);

            Toast.makeText(mContext, ""+item.name+"---"+item.categoryId, Toast.LENGTH_SHORT).show();
                    mContext.startActivity(i);
        }
    }

    @Override
    public RecyclerCategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.categoty_item2, parent, false);

        return new ViewHolder(view);
    }
    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    @Override
    public void onBindViewHolder(ViewHolder Vholder, int position) {
        Vholder.setData(mValues.get(position));

    }
  
    @Override
    public int getItemCount() {

        return mValues.size();
    }
    public interface ItemClickListener {
        void onClick(View view, int position);
    }
}