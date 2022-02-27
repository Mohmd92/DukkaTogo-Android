package com.dukan.dukkan.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dukan.dukkan.APIClient;
import com.dukan.dukkan.APIInterface;
import com.dukan.dukkan.R;
import com.dukan.dukkan.activity.LoginActivity;
import com.dukan.dukkan.activity.MainActivity;
import com.dukan.dukkan.pojo.CartMain;
import com.dukan.dukkan.pojo.CartParamenter;
import com.dukan.dukkan.pojo.CartRemoveParamenter;
import com.dukan.dukkan.pojo.Login;
import com.dukan.dukkan.pojo.MostWanted;
import com.dukan.dukkan.pojo.Role;
import com.dukan.dukkan.pojo.Store;
import com.dukan.dukkan.pojo.User;
import com.dukan.dukkan.util.SharedPreferenceManager;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MostWantedAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflter;
    private List<MostWanted> mValues;

    public MostWantedAdapter(
            Context applicationContext,List<MostWanted> values
            ) {
        this.context = applicationContext;
        this.mValues = values;

        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getViewTypeCount() {
        return 1; // getCount();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return mValues.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint({"ViewHolder", "SetTextI18n"})
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        final APIInterface apiInterface = APIClient.getClient(context).create(APIInterface.class);
        if (inflter == null)
            inflter = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = inflter.inflate(R.layout.wanted_item, null);
            viewHolder = new ViewHolder();
            viewHolder.progressBar =  view.findViewById(R.id.progressBar);
            viewHolder.image =  view.findViewById(R.id.image);
            viewHolder.tv_name =  view.findViewById(R.id.tv_name);
            viewHolder.tv_price =  view.findViewById(R.id.tv_price);
            viewHolder.rateProduct =  view.findViewById(R.id.ratingBar2);
            viewHolder.rel_add_to_card =  view.findViewById(R.id.rel_add_to_card);
            viewHolder.text_add =  view.findViewById(R.id.text_add);
            viewHolder.rel_heart =  view.findViewById(R.id.rel_heart);
//            viewHolder.rel_add_to_card.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    viewHolder.progressBar.setVisibility(View.VISIBLE);
//                    if(viewHolder.text_add.getText().equals(context.getString(R.string.add_to_cart))) {
//                        @SuppressLint("HardwareIds") String ID = Settings.Secure.getString(context.getContentResolver(),
//                                Settings.Secure.ANDROID_ID);
//                        CartParamenter cartParamenter = new CartParamenter(mValues.get(i).id, ID);
//                        Call<CartMain> call1 = apiInterface.cart(cartParamenter);
//                        call1.enqueue(new Callback<CartMain>() {
//                            @Override
//                            public void onResponse(Call<CartMain> call, Response<CartMain> response) {
//                                CartMain cart = response.body();
//                                Toast.makeText(context, ""+cart.status, Toast.LENGTH_SHORT).show();
//                                if (cart.status)
//                                    viewHolder.text_add.setText(context.getString(R.string.remove_to_cart));
//                                else
//                                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
//                                viewHolder.progressBar.setVisibility(View.GONE);
//                                notifyDataSetChanged();
//                            }
//
//                            @Override
//                            public void onFailure(Call<CartMain> call, Throwable t) {
////                            Toast.makeText(context, "onFailure", Toast.LENGTH_SHORT).show();
//                                viewHolder.progressBar.setVisibility(View.GONE);
//                                call.cancel();
//                            }
//                        });
//                    }
//                    else{
//                        @SuppressLint("HardwareIds") String ID = Settings.Secure.getString(context.getContentResolver(),
//                                Settings.Secure.ANDROID_ID);
//                        CartRemoveParamenter cartRemoveParamenter = new CartRemoveParamenter(mValues.get(i).id, ID,1,"delete");
//                        Call<CartMain> call1 = apiInterface.cartRemove(cartRemoveParamenter);
//                        call1.enqueue(new Callback<CartMain>() {
//                            @Override
//                            public void onResponse(Call<CartMain> call, Response<CartMain> response) {
//                                CartMain cart = response.body();
//                                if (cart.status)
//                                    viewHolder.text_add.setText(context.getString(R.string.add_to_cart));
//                                else
//                                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
//                                viewHolder.progressBar.setVisibility(View.GONE);
//                                notifyDataSetChanged();
//                            }
//
//                            @Override
//                            public void onFailure(Call<CartMain> call, Throwable t) {
////                            Toast.makeText(context, "onFailure", Toast.LENGTH_SHORT).show();
//                                viewHolder.progressBar.setVisibility(View.GONE);
//                                call.cancel();
//                            }
//                        });
//                    }
//
//                    notifyDataSetChanged();
//                }
//            });
            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tv_name.setText("" + mValues.get(i).name);
        viewHolder.tv_price.setText("" + mValues.get(i).price);
        Picasso.get()
                .load(mValues.get(i).image)
                .into(viewHolder.image);
        return view;
    }
    private class ViewHolder {
        TextView text_add,tv_price,tv_name;
        RatingBar rateProduct;
        ImageView image;
        ProgressBar progressBar;
        RelativeLayout rel_add_to_card,rel_heart;
    }


}
