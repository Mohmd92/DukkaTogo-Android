package com.dukan.dukkan.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.dukan.dukkan.APIClient;
import com.dukan.dukkan.APIInterface;
import com.dukan.dukkan.R;
import com.dukan.dukkan.pojo.CartMain;
import com.dukan.dukkan.pojo.CartParamenter;
import com.dukan.dukkan.pojo.CartRemoveParamenter;
import com.dukan.dukkan.pojo.FavoriteMain;
import com.dukan.dukkan.pojo.MultipleProducts;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyclerProductMerchantAdapter extends RecyclerView.Adapter<RecyclerProductMerchantAdapter.ViewHolder> {
    List<MultipleProducts.Data.Product> mValues;
    Context mContext;
    private ItemClickListener clickListener;

    int row_index=-1;
    public RecyclerProductMerchantAdapter(Context context, List<MultipleProducts.Data.Product> values) {

        mValues = values;
        mContext = context;
//        mListener=itemListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        MultipleProducts.Data.Product item;
        TextView tv_status,tv_price,tv_name,text_add,tv_heart;
        RatingBar rateProduct;
        ImageView image,img_heart;
        RelativeLayout rel_add_to_card,rel_heart;
        APIInterface apiInterface;

        public RelativeLayout relative;
        public ViewHolder(View v) {
            super(v);

            v.setOnClickListener(this);
            image =  v.findViewById(R.id.image);
            img_heart =  v.findViewById(R.id.img_heart);
            tv_name =  v.findViewById(R.id.tv_name);
            tv_price =  v.findViewById(R.id.tv_price);
            tv_status =  v.findViewById(R.id.tv_status);
            tv_heart =  v.findViewById(R.id.tv_heart);
            text_add =  v.findViewById(R.id.text_add);
            rateProduct =  v.findViewById(R.id.ratingBar2);
            rel_add_to_card =  v.findViewById(R.id.rel_add_to_card);
            rel_heart =  v.findViewById(R.id.rel_heart);
            apiInterface = APIClient.getClient(mContext).create(APIInterface.class);

        }
        @SuppressLint("SetTextI18n")
        public void setData(MultipleProducts.Data.Product item) {
            this.item = item;
            if(item.name.length()>10)
                tv_name.setText(item.name.substring(0,10)+"...");
            else
                tv_name.setText(item.name);
                Picasso.get()
                        .load(item.image)
                        .into(image);
            tv_price.setText(""+item.price);
            rateProduct.setRating(item.rate);
            if(item.isCart!=null)
                text_add.setText(mContext.getString(R.string.remove_to_cart));
            else
                text_add.setText(mContext.getString(R.string.add_to_cart));

            if(item.isFavorite) {
                img_heart.setImageResource(R.drawable.ic_heart_on);
            }else{
                img_heart.setImageResource(R.drawable.ic_heart);
            }
            if(item.order_status.equals("0")) {
                tv_status.setText(mContext.getString(R.string.unavailable));
                tv_status.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
            }else {
                tv_status.setText(mContext.getString(R.string.available));
                tv_status.setTextColor(ContextCompat.getColor(mContext, R.color.green));
            }
            itemView.setOnClickListener(this); // bind the listener
        }

        @Override
        public void onClick(View view) {
            rel_heart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(tv_heart.getText().toString().equals("false")){
                        @SuppressLint("HardwareIds") String ID = Settings.Secure.getString(mContext.getContentResolver(),
                                Settings.Secure.ANDROID_ID);
                        CartParamenter cartParamenter = new CartParamenter(item.id, ID);
                        Call<FavoriteMain> call1 = apiInterface.favorite(cartParamenter);
                        call1.enqueue(new Callback<FavoriteMain>() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onResponse(Call<FavoriteMain> call, Response<FavoriteMain> response) {
                                FavoriteMain favorite = response.body();
                                if (favorite.status) {
                                    img_heart.setImageResource(R.drawable.ic_heart_on);
                                    tv_heart.setText("true");
                                }else
                                    Toast.makeText(mContext, favorite.message, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<FavoriteMain> call, Throwable t) {
//                            Toast.makeText(context, "onFailure", Toast.LENGTH_SHORT).show();
                                call.cancel();
                            }
                        });
                    }
                    else{
                        @SuppressLint("HardwareIds") String ID = Settings.Secure.getString(mContext.getContentResolver(),
                                Settings.Secure.ANDROID_ID);
                        CartRemoveParamenter cartRemoveParamenter = new CartRemoveParamenter(item.id, ID,1,"delete");
                        Call<FavoriteMain> call1 = apiInterface.favoriteRemove(cartRemoveParamenter);
                        call1.enqueue(new Callback<FavoriteMain>() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onResponse(Call<FavoriteMain> call, Response<FavoriteMain> response) {
                                FavoriteMain favorite = response.body();
                                if (favorite.status) {
                                    img_heart.setImageResource(R.drawable.ic_heart);
                                    tv_heart.setText("false");
                                }else
                                    Toast.makeText(mContext, favorite.message, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<FavoriteMain> call, Throwable t) {
//                            Toast.makeText(context, "onFailure", Toast.LENGTH_SHORT).show();
                                call.cancel();
                            }
                        });
                    }
                }
            });
            rel_add_to_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    @SuppressLint("HardwareIds") String ID = Settings.Secure.getString(mContext.getContentResolver(),
                            Settings.Secure.ANDROID_ID);

                    if (text_add.getText().equals(mContext.getString(R.string.add_to_cart))) {
                        CartParamenter cartParamenter = new CartParamenter(item.id, ID);
                        Call<CartMain> call1 = apiInterface.cart(ID,cartParamenter);
                        call1.enqueue(new Callback<CartMain>() {
                            @Override
                            public void onResponse(Call<CartMain> call, Response<CartMain> response) {
                                CartMain cart = response.body();
                                if (cart.status)
                                    text_add.setText(mContext.getString(R.string.remove_to_cart));
                                else
                                    Toast.makeText(mContext, cart.message, Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onFailure(Call<CartMain> call, Throwable t) {
                                System.out.println("7878788888888888 " + t.getMessage());
                                call.cancel();
                            }
                        });
                    } else {
                        CartRemoveParamenter cartRemoveParamenter = new CartRemoveParamenter(item.id, ID,1,"delete");
                        Call<CartMain> call1 = apiInterface.cartRemove(cartRemoveParamenter);
                        call1.enqueue(new Callback<CartMain>() {
                            @Override
                            public void onResponse(Call<CartMain> call, Response<CartMain> response) {
                                CartMain cart = response.body();
                                if (cart.status)
                                    text_add.setText(mContext.getString(R.string.add_to_cart));
                                else
                                    Toast.makeText(mContext, cart.message, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<CartMain> call, Throwable t) {
//                            Toast.makeText(context, "onFailure", Toast.LENGTH_SHORT).show();
                                call.cancel();
                            }
                        });
                    }
                }

            });
        }
        }

    @Override
    public RecyclerProductMerchantAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.product_merchant_item, parent, false);

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