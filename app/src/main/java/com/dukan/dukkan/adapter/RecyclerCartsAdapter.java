package com.dukan.dukkan.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.dukan.dukkan.APIClient;
import com.dukan.dukkan.APIInterface;
import com.dukan.dukkan.R;
import com.dukan.dukkan.activity.CartActivity;
import com.dukan.dukkan.pojo.Cart;
import com.dukan.dukkan.pojo.CartMain;
import com.dukan.dukkan.pojo.CartParamenter;
import com.dukan.dukkan.pojo.CartRemoveParamenter;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyclerCartsAdapter extends RecyclerView.Adapter<RecyclerCartsAdapter.ViewHolder> {
    List<Cart> mValues;
    Context mContext;
    private ItemClickListener clickListener;
    private onProgressChangeState changeState;
    APIInterface apiInterface;

    public RecyclerCartsAdapter(Context context, List<Cart> values, onProgressChangeState changeState) {

        mValues = values;
        mContext = context;
//        mListener=itemListener;
        this.changeState = changeState;
    }

    public interface onProgressChangeState {
        void onProgress(boolean result);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Cart item;
        TextView product_name, product_count, product_price;
        ImageView image, product_color;
        RelativeLayout relative_plus, relative_minus, relative_delete;

        public RelativeLayout relative;

        public ViewHolder(View v) {
            super(v);
            apiInterface = APIClient.getClient(mContext).create(APIInterface.class);

            image = v.findViewById(R.id.image);
            product_color = v.findViewById(R.id.product_color);
            product_name = v.findViewById(R.id.product_name);
            product_count = v.findViewById(R.id.product_count);
            product_price = v.findViewById(R.id.product_price);
            relative_plus = v.findViewById(R.id.relative_plus);
            relative_minus = v.findViewById(R.id.relative_minus);
            relative_delete = v.findViewById(R.id.relative_delete);

        }

        public void setData(Cart item) {
            this.item = item;
            if (item.product != null) {
                if (item.product.name.length() > 24)
                    product_name.setText(item.product.name.substring(0, 24) + "...");
                else
                    product_name.setText(item.product.name);

                product_price.setText("" + item.product.price);
                product_count.setText(Integer.toString(item.qty));
                Picasso.get()
                        .load(item.product.image)
                        .into(image);
            }
            itemView.setOnClickListener(this); // bind the listener


            relative_plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    @SuppressLint("HardwareIds") String ID = Settings.Secure.getString(mContext.getContentResolver(),
                            Settings.Secure.ANDROID_ID);
                    CartParamenter cartParamenter = new CartParamenter(item.product.id, ID);
                    Call<CartMain> call1 = apiInterface.cart(ID, cartParamenter);
                    startProgress();
                    call1.enqueue(new Callback<CartMain>() {
                        @Override
                        public void onResponse(Call<CartMain> call, Response<CartMain> response) {
                            CartMain cart = response.body();
                            System.out.println("7878788888888888 " + cart.status);
                            if (cart.status) {
                                int pCount = Integer.parseInt(product_count.getText().toString());
                                pCount++;
                                product_count.setText("" + pCount);
                                TextView tv_total_price = (TextView) CartActivity.tv_total_price;
                                float pTotal = Float.parseFloat(tv_total_price.getText().toString());
                                pTotal = pTotal + item.product.price;
                                tv_total_price.setText("" + pTotal);
                                if (call1.isExecuted()) {
                                    stopProgress();
                                }
                            } else
                                Toast.makeText(mContext, "" + cart.message, Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onFailure(Call<CartMain> call, Throwable t) {
                            System.out.println("7878788888888888 " + t.getMessage());
                            call.cancel();
                        }
                    });
                }

            });
            relative_minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Integer.parseInt(product_count.getText().toString()) > 1) {
                        @SuppressLint("HardwareIds") String ID = Settings.Secure.getString(mContext.getContentResolver(),
                                Settings.Secure.ANDROID_ID);
                        CartRemoveParamenter cartRemoveParamenter = new CartRemoveParamenter(item.product.id, ID, 1, "delete");
                        Call<CartMain> call1 = apiInterface.cartRemove(cartRemoveParamenter);
                        startProgress();
                        call1.enqueue(new Callback<CartMain>() {
                            @Override
                            public void onResponse(Call<CartMain> call, Response<CartMain> response) {
                                CartMain cart = response.body();
                                if (cart.status) {
                                    int pCount = Integer.parseInt(product_count.getText().toString());
                                    pCount--;

                                    product_count.setText("" + pCount);
                                    TextView tv_total_price = (TextView) CartActivity.tv_total_price;
                                    float pTotal = Float.parseFloat(tv_total_price.getText().toString());
                                    pTotal = pTotal - item.product.price;
                                    tv_total_price.setText("" + pTotal);
                                    if (call.isExecuted()){
                                        relative_plus.setEnabled(true);
                                        relative_minus.setEnabled(true);
                                        changeState.onProgress(false);
                                    }
                                } else
                                    Toast.makeText(mContext, "" + cart.message, Toast.LENGTH_SHORT).show();
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
            relative_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeState.onProgress(true);
                    if (Integer.parseInt(product_count.getText().toString()) > 1) {
                        @SuppressLint("HardwareIds") String ID = Settings.Secure.getString(mContext.getContentResolver(),
                                Settings.Secure.ANDROID_ID);
                        CartRemoveParamenter cartRemoveParamenter = new CartRemoveParamenter(item.product.id, ID, item.qty, "delete");
                        Call<CartMain> call1 = apiInterface.cartRemove(cartRemoveParamenter);
                        call1.enqueue(new Callback<CartMain>() {
                            @Override
                            public void onResponse(Call<CartMain> call, Response<CartMain> response) {
                                CartMain cart = response.body();
                                System.out.println("resssssssssssss " + response.body().message);
                                if (cart.status) {
                                    TextView tv_total_price = (TextView) CartActivity.tv_total_price;

                                    tv_total_price.setText("" + cart.data.cartTotal);

                                    TextView tv_num_products = (TextView) CartActivity.tv_num_products;
                                    int pCount = Integer.parseInt(tv_num_products.getText().toString());
                                    pCount--;
                                    tv_num_products.setText("" + pCount);

                                    removeAt(getAdapterPosition());
                                    if (call1.isExecuted()){
                                        changeState.onProgress(false);
                                    }
                                } else
                                    Toast.makeText(mContext, "" + cart.message, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<CartMain> call, Throwable t) {
                                Toast.makeText(mContext, "onFailure", Toast.LENGTH_SHORT).show();
                                call.cancel();
                            }
                        });
//
//                        Toast.makeText(mContext, "pwwwwwppp "+product_count.getText().toString(), Toast.LENGTH_SHORT).show();
//
//                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//
//                        builder.setTitle(mContext.getString(R.string.delete));
//                        builder.setMessage(mContext.getString(R.string.are_you_sure));
//
//                        builder.setPositiveButton(mContext.getString(R.string.yes), new DialogInterface.OnClickListener() {
//
//                            public void onClick(DialogInterface dialog, int which) {
//                                Toast.makeText(mContext, "pppp", Toast.LENGTH_SHORT).show();
//                                // Do nothing but close the dialog
//
//                                @SuppressLint("HardwareIds") String ID = Settings.Secure.getString(mContext.getContentResolver(),
//                                        Settings.Secure.ANDROID_ID);
//                                CartRemoveParamenter cartRemoveParamenter = new CartRemoveParamenter(item.product.id, ID,item.qty,"delete");
//                                Call<CartMain> call1 = apiInterface.cartRemove(cartRemoveParamenter);
//                                call1.enqueue(new Callback<CartMain>() {
//                                    @Override
//                                    public void onResponse(Call<CartMain> call, Response<CartMain> response) {
//                                        CartMain cart = response.body();
//                                        System.out.println("resssssssssssss "+response.body().message);
//                                        if (cart.status){
//                                            TextView tv_total_price = (TextView) CartActivity.tv_total_price;
//
//                                            tv_total_price.setText(""+cart.data.cartTotal);
//
//                                            TextView tv_num_products = (TextView) CartActivity.tv_num_products;
//                                            int pCount= Integer.parseInt(tv_num_products.getText().toString());
//                                            pCount--;
//                                            tv_num_products.setText(""+pCount);
//
//                                            removeAt(getAdapterPosition());
//                                        }else
//                                            Toast.makeText(mContext, ""+cart.message, Toast.LENGTH_SHORT).show();
//                                    }
//
//                                    @Override
//                                    public void onFailure(Call<CartMain> call, Throwable t) {
//                            Toast.makeText(mContext, "onFailure", Toast.LENGTH_SHORT).show();
//                                        call.cancel();
//                                    }
//                                });
//                                dialog.dismiss();
//                            }
//                        });
//
//                        builder.setNegativeButton(mContext.getString(R.string.no), new DialogInterface.OnClickListener() {
//
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                                // Do nothing
//                                dialog.dismiss();
//                            }
//                        });
//                        AlertDialog alert = builder.create();
//                        alert.show();

                    }
                }

            });
        }

        private void stopProgress() {
            relative_plus.setEnabled(true);
            relative_minus.setEnabled(true);
            changeState.onProgress(false);
        }

        private void startProgress() {
            relative_plus.setEnabled(false);
            relative_minus.setEnabled(false);
            changeState.onProgress(true);
        }

        @Override
        public void onClick(View view) {
        }
    }

    @Override
    public RecyclerCartsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.cart_item, parent, false);

        return new ViewHolder(view);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    @Override
    public void onBindViewHolder(ViewHolder Vholder, int position) {
        Vholder.setData(mValues.get(position));

    }

    public void removeAt(int position) {
        mValues.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mValues.size());
    }

    @Override
    public int getItemCount() {

        return mValues.size();
    }

    public interface ItemClickListener {
        void onClick(View view, int position);
    }


}