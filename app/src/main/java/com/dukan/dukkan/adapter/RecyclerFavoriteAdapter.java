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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.dukan.dukkan.APIClient;
import com.dukan.dukkan.APIInterface;
import com.dukan.dukkan.R;
import com.dukan.dukkan.activity.CartActivity;
import com.dukan.dukkan.activity.ShowProductActivity;
import com.dukan.dukkan.pojo.CartMain;
import com.dukan.dukkan.pojo.CartParamenter;
import com.dukan.dukkan.pojo.CartRemoveParamenter;
import com.dukan.dukkan.pojo.FavoriteMain;
import com.dukan.dukkan.pojo.IsCart;
import com.dukan.dukkan.util.SharedPreferenceManager;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyclerFavoriteAdapter extends RecyclerView.Adapter<RecyclerFavoriteAdapter.ViewHolder> {
    List<FavoriteMain.Datum> mValues;
    Context mContext;
    private ItemClickListener clickListener;
    int row_index=-1;
    public RecyclerFavoriteAdapter(Context context, List<FavoriteMain.Datum> values) {

        mValues = values;
        mContext = context;
//        mListener=itemListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        FavoriteMain.Datum item;
        TextView tv_price,tv_name,text_add;
        RatingBar rateProduct;
        ImageView image,img_heart;
        RelativeLayout rel_add_to_card,rel_heart;
        APIInterface apiInterface;
        LinearLayout linear_main;
        ProgressBar progressBar;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            image =  v.findViewById(R.id.image);
            img_heart =  v.findViewById(R.id.img_heart);
            tv_name =  v.findViewById(R.id.tv_name);
            text_add =  v.findViewById(R.id.text_add);
            tv_price =  v.findViewById(R.id.tv_price);
            rateProduct =  v.findViewById(R.id.ratingBar2);
            rel_add_to_card =  v.findViewById(R.id.rel_add_to_card);
            rel_heart =  v.findViewById(R.id.rel_heart);
            linear_main =  v.findViewById(R.id.linear_main);
            progressBar =  v.findViewById(R.id.progressBar);

            apiInterface = APIClient.getClient(mContext).create(APIInterface.class);



        }
        public void setData(FavoriteMain.Datum item) {
            this.item = item;
            if(item.product!=null) {
                if (item.product.name.length() > 10)
                    tv_name.setText(item.product.name.substring(0, 10) + "...");
                else
                    tv_name.setText(item.product.name);
                Picasso.get()
                        .load(item.product.image)
                        .into(image);

                tv_price.setText(String.valueOf(item.product.price));
                rateProduct.setRating(item.product.rate);
                if (item.product.isCart != null)
                    text_add.setText(mContext.getString(R.string.remove_to_cart));
                else
                    text_add.setText(mContext.getString(R.string.add_to_cart));

                if (item.product.isFavorite) {
                    img_heart.setImageResource(R.drawable.ic_heart_on);
                } else {
                    img_heart.setImageResource(R.drawable.ic_heart);
                }
                itemView.setOnClickListener(this); // bind the listener
            }

            rel_heart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressBar.setVisibility(View.VISIBLE);

                        @SuppressLint("HardwareIds") String ID = Settings.Secure.getString(mContext.getContentResolver(),
                                Settings.Secure.ANDROID_ID);
                        CartRemoveParamenter cartRemoveParamenter = new CartRemoveParamenter(item.product.id, ID,1,"delete");
                        Call<FavoriteMain> call1 = apiInterface.favoriteRemove(cartRemoveParamenter);
                        call1.enqueue(new Callback<FavoriteMain>() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onResponse(Call<FavoriteMain> call, Response<FavoriteMain> response) {
                                FavoriteMain favorite = response.body();
                                if (favorite.status) {
                                    removeAt(getAdapterPosition());
                                }else
                                    Toast.makeText(mContext, favorite.message, Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }

                            @Override
                            public void onFailure(Call<FavoriteMain> call, Throwable t) {
                                progressBar.setVisibility(View.GONE);
                                call.cancel();
                            }
                        });
                }
            });
            linear_main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i2 = new Intent(mContext, ShowProductActivity.class);
                    i2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i2.putExtra("productID", item.productId);
                    mContext.startActivity(i2);

                }
            });
            rel_add_to_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressBar.setVisibility(View.VISIBLE);

                    @SuppressLint("HardwareIds") String ID = Settings.Secure.getString(mContext.getContentResolver(),
                            Settings.Secure.ANDROID_ID);

                    if (text_add.getText().equals(mContext.getString(R.string.add_to_cart))) {
                        CartParamenter cartParamenter = new CartParamenter(item.product.id, ID);
                        Call<CartMain> call1 = apiInterface.cart(ID,cartParamenter);
                        call1.enqueue(new Callback<CartMain>() {
                            @Override
                            public void onResponse(Call<CartMain> call, Response<CartMain> response) {
                                CartMain cart = response.body();
                                if (cart.status) {
                                    text_add.setText(mContext.getString(R.string.remove_to_cart));
                                    item.product.isCart = new IsCart();
                                    SharedPreferenceManager.getInstance(mContext).setCartCount(SharedPreferenceManager.getInstance(mContext).getCartCount()+1);

                                }
                                else
                                    Toast.makeText(mContext, cart.message, Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);

                            }

                            @Override
                            public void onFailure(Call<CartMain> call, Throwable t) {
                                progressBar.setVisibility(View.GONE);
                                call.cancel();
                            }
                        });
                    } else {
                        CartRemoveParamenter cartRemoveParamenter = new CartRemoveParamenter(item.product.id, ID,1,"delete");
                        Call<CartMain> call1 = apiInterface.cartRemove(cartRemoveParamenter);
                        call1.enqueue(new Callback<CartMain>() {
                            @Override
                            public void onResponse(Call<CartMain> call, Response<CartMain> response) {
                                CartMain cart = response.body();
                                if (cart.status){
                                    text_add.setText(mContext.getString(R.string.add_to_cart));
                                    item.product.isCart = null;
                                    SharedPreferenceManager.getInstance(mContext).setCartCount(SharedPreferenceManager.getInstance(mContext).getCartCount()-1);

                                }

                                else
                                    Toast.makeText(mContext, cart.message, Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);

                            }

                            @Override
                            public void onFailure(Call<CartMain> call, Throwable t) {
                                progressBar.setVisibility(View.GONE);
                                call.cancel();
                            }
                        });
                    }
                }

            });
        }
        @Override
        public void onClick(View view) {
        }
    }
    public void removeAt(int position) {
        mValues.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mValues.size());
    }
    @Override
    public RecyclerFavoriteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.product_item, parent, false);

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