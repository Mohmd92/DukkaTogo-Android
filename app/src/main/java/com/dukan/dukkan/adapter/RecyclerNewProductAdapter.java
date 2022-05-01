package com.dukan.dukkan.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.dukan.dukkan.activity.ShowProductActivity;
import com.dukan.dukkan.pojo.CartMain;
import com.dukan.dukkan.pojo.CartParamenter;
import com.dukan.dukkan.pojo.CartRemoveParamenter;
import com.dukan.dukkan.pojo.FavoriteMain;
import com.dukan.dukkan.pojo.IsCart;
import com.dukan.dukkan.pojo.NewProduct;
import com.dukan.dukkan.util.SharedPreferenceManager;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyclerNewProductAdapter extends RecyclerView.Adapter<RecyclerNewProductAdapter.ViewHolder> {
    List<NewProduct> mValues;
    Context mContext;
    private ItemClickListener clickListener;
    APIInterface apiInterface;

    public RecyclerNewProductAdapter(Context context, List<NewProduct> values) {

        mValues = values;
        mContext = context;
//        mListener=itemListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        NewProduct item;
        TextView text_add,tv_price,tv_name,tv_heart;
        RatingBar rateProduct;
        ImageView image,img_heart;
        ProgressBar progressBar;
        RelativeLayout rel_add_to_card,rel_heart;
        LinearLayout linear_main;
        public ViewHolder(View v) {
            super(v);
            apiInterface = APIClient.getClient(mContext).create(APIInterface.class);
            progressBar =  v.findViewById(R.id.progressBar);
            image =  v.findViewById(R.id.image);
            img_heart =  v.findViewById(R.id.img_heart);
            tv_name =  v.findViewById(R.id.tv_name);
            tv_price =  v.findViewById(R.id.tv_price);
            tv_heart =  v.findViewById(R.id.tv_heart);
            rateProduct =  v.findViewById(R.id.ratingBar2);
            rel_add_to_card =  v.findViewById(R.id.rel_add_to_card);
            text_add =  v.findViewById(R.id.text_add);
            rel_heart =  v.findViewById(R.id.rel_heart);
            linear_main =  v.findViewById(R.id.linear_main);

        }
        public void setData(NewProduct item) {
            this.item = item;
//            category_name.setText(item.name);
            Log.d("TAG111111","item.name "+item.name);
            tv_name.setText("" + item.name);
            tv_price.setText("" + item.price);
            Picasso.get()
                    .load(item.image)
                    .into(image);
            if(item.isCart!=null)
                text_add.setText(mContext.getString(R.string.remove_to_cart));
            else
                text_add.setText(mContext.getString(R.string.add_to_cart));

            if(item.isFavorite) {
                img_heart.setImageResource(R.drawable.ic_heart_on);
                tv_heart.setText("true");
            }else{
                img_heart.setImageResource(R.drawable.ic_heart);
                tv_heart.setText("false");
            }
            itemView.setOnClickListener(this); // bind the listener
            linear_main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i2 = new Intent(mContext, ShowProductActivity.class);
                    i2.putExtra("productID", item.id);
                    mContext.startActivity(i2);

                }
            });
            rel_heart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressBar.setVisibility(View.VISIBLE);
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
                                progressBar.setVisibility(View.GONE);
                            }

                            @Override
                            public void onFailure(Call<FavoriteMain> call, Throwable t) {
//                            Toast.makeText(context, "onFailure", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
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
                                progressBar.setVisibility(View.GONE);
                            }

                            @Override
                            public void onFailure(Call<FavoriteMain> call, Throwable t) {
//                            Toast.makeText(context, "onFailure", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                call.cancel();
                            }
                        });
                    }
                }
            });
            rel_add_to_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressBar.setVisibility(View.VISIBLE);
                    if(text_add.getText().equals(mContext.getString(R.string.add_to_cart))) {
                        @SuppressLint("HardwareIds") String ID = Settings.Secure.getString(mContext.getContentResolver(),
                                Settings.Secure.ANDROID_ID);
                        CartParamenter cartParamenter = new CartParamenter(item.id, ID);
                        Call<CartMain> call1 = apiInterface.cart(ID,cartParamenter);
                        call1.enqueue(new Callback<CartMain>() {
                            @Override
                            public void onResponse(Call<CartMain> call, Response<CartMain> response) {
                                CartMain cart = response.body();
//                                Toast.makeText(mContext, ""+cart.status, Toast.LENGTH_SHORT).show();
                                if (cart.status) {
                                    text_add.setText(mContext.getString(R.string.remove_to_cart));
                                    item.isCart =new IsCart();
                                    SharedPreferenceManager.getInstance(mContext).setCartCount(SharedPreferenceManager.getInstance(mContext).getCartCount()+1);

                                }
                                else
                                    Toast.makeText(mContext, "Error", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                notifyDataSetChanged();

                            }

                            @Override
                            public void onFailure(Call<CartMain> call, Throwable t) {
//                            Toast.makeText(context, "onFailure", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                call.cancel();
                            }
                        });
                    }
                    else{
                        @SuppressLint("HardwareIds") String ID = Settings.Secure.getString(mContext.getContentResolver(),
                                Settings.Secure.ANDROID_ID);
                        CartRemoveParamenter cartRemoveParamenter = new CartRemoveParamenter(item.id, ID,1,"delete");
                        Call<CartMain> call1 = apiInterface.cartRemove(cartRemoveParamenter);
                        call1.enqueue(new Callback<CartMain>() {
                            @Override
                            public void onResponse(Call<CartMain> call, Response<CartMain> response) {
                                CartMain cart = response.body();
                                if (cart.status) {
                                    text_add.setText(mContext.getString(R.string.add_to_cart));
                                    item.isCart = null;
                                    SharedPreferenceManager.getInstance(mContext).setCartCount(SharedPreferenceManager.getInstance(mContext).getCartCount()-1);

                                }
                                else
                                    Toast.makeText(mContext, "Error", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                notifyDataSetChanged();
                            }

                            @Override
                            public void onFailure(Call<CartMain> call, Throwable t) {
//                            Toast.makeText(context, "onFailure", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                call.cancel();
                            }
                        });
                    }

                    notifyDataSetChanged();
                }
            });
        }

        @Override
        public void onClick(View view) {

        }
    }

    @Override
    public RecyclerNewProductAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.wanted_item, parent, false);

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