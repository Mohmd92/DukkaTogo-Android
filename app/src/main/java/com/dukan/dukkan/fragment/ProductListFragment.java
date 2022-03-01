package com.dukan.dukkan.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.dukan.dukkan.APIClient;
import com.dukan.dukkan.APIInterface;
import com.dukan.dukkan.R;
import com.dukan.dukkan.activity.ProductsActivity;
import com.dukan.dukkan.activity.ShowStoresActivity;
import com.dukan.dukkan.adapter.BrandAdapter;
import com.dukan.dukkan.adapter.DeliveryAdapter;
import com.dukan.dukkan.adapter.ListProductAdapter;
import com.dukan.dukkan.adapter.MostWantedAdapter;
import com.dukan.dukkan.adapter.NewProductAdapter;
import com.dukan.dukkan.adapter.RecyclerProductAdapter;
import com.dukan.dukkan.adapter.StoreAdapter;
import com.dukan.dukkan.pojo.Brand;
import com.dukan.dukkan.pojo.CartMain;
import com.dukan.dukkan.pojo.CartParamenter;
import com.dukan.dukkan.pojo.CartRemoveParamenter;
import com.dukan.dukkan.pojo.Delivery;
import com.dukan.dukkan.pojo.FavoriteMain;
import com.dukan.dukkan.pojo.MostWanted;
import com.dukan.dukkan.pojo.MultipleProducts;
import com.dukan.dukkan.pojo.NewProduct;
import com.dukan.dukkan.pojo.Slider;
import com.dukan.dukkan.pojo.Store;
import com.dukan.dukkan.util.HorizontalListView;
import com.yihsian.slider.library.SliderItemView;
import com.yihsian.slider.library.SliderLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    APIInterface apiInterface;
    private HorizontalListView HorizontalListViewLastOffers,HorizontalListViewMost,HorizontalListViewNewProduct;
    private  SliderLayout sliderLayout;
    ImageView img_filter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    TextView store_name;
    String ID,StoreName="";
    int StoreId=0;

    @SuppressLint("HardwareIds")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.product_list, container, false);
        apiInterface = APIClient.getClient(getContext()).create(APIInterface.class);
        sliderLayout = root.findViewById(R.id.sliderLayout);
       ID = Settings.Secure.getString(getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            StoreId = bundle.getInt("StoreId", 0);
            StoreName = bundle.getString("StoreName", "");
        }
        TextView view_all_product = root.findViewById(R.id.view_all_product);
        TextView view_most_product = root.findViewById(R.id.view_most_product);
        TextView view_last_offers = root.findViewById(R.id.view_last_offers);
        store_name = root.findViewById(R.id.store_name);
        store_name.setText(StoreName);

        HorizontalListViewMost = root.findViewById(R.id.HorizontalListViewMost);
        HorizontalListViewNewProduct = root.findViewById(R.id.HorizontalListViewNewProduct);
        HorizontalListViewLastOffers = root.findViewById(R.id.HorizontalListViewLastOffers);
        mSwipeRefreshLayout = (SwipeRefreshLayout)  root.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        view_all_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), ProductsActivity.class);
                i.putExtra("title", getString(R.string.new_products));
                i.putExtra("store", StoreId);
                i.putExtra("new", 1);
                i.putExtra("most", 0);
                startActivity(i);
            }
        });
        view_most_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), ProductsActivity.class);
                i.putExtra("title", getString(R.string.most_wanted));
                i.putExtra("store", StoreId);
                i.putExtra("new", 0);
                i.putExtra("most", 1);
                startActivity(i);
            }
        });
        getNewProducts();
        getMostWanted();

        return root;
    }
    private void getNewProducts() {
        mSwipeRefreshLayout.setRefreshing(true);
      Call<MultipleProducts> callNew = apiInterface.doGetListProduct(ID,"android",StoreId,0,0,"",1,0);
        callNew.enqueue(new Callback<MultipleProducts>() {
            @Override
            public void onResponse(Call<MultipleProducts> callNew, Response<MultipleProducts> response) {
                Log.d("TAG111111",response.code()+"");
                MultipleProducts resource = response.body();
                String status = resource.status;
                List<MultipleProducts.Datum> newProduct = resource.data;
                ListProductAdapter listProductAdapter = new ListProductAdapter(getContext(),newProduct);
                HorizontalListViewNewProduct.setAdapter(listProductAdapter);
                listProductAdapter.notifyDataSetChanged();

                HorizontalListViewNewProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        onClikMostwanted(view,newProduct,i);
                    }
                });
                mSwipeRefreshLayout.setRefreshing(false);

            }
            @Override
            public void onFailure(Call<MultipleProducts> call, Throwable t) {
                Log.d("TAG111111","  e "+t.getMessage());
                mSwipeRefreshLayout.setRefreshing(false);

            }

        });
    }
    private void getMostWanted() {
        mSwipeRefreshLayout.setRefreshing(true);
        Call<MultipleProducts> callNew = apiInterface.doGetListProduct(ID,"android",StoreId,0,0,"",0,1);
        callNew.enqueue(new Callback<MultipleProducts>() {
            @Override
            public void onResponse(Call<MultipleProducts> callNew, Response<MultipleProducts> response) {
                Log.d("TAG111111",response.code()+"");
                MultipleProducts resource = response.body();
                String status = resource.status;
                List<MultipleProducts.Datum> newProduct = resource.data;
                ListProductAdapter listProductAdapter = new ListProductAdapter(getContext(),newProduct);
                HorizontalListViewMost.setAdapter(listProductAdapter);
                listProductAdapter.notifyDataSetChanged();

                HorizontalListViewMost.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        onClikMostwanted(view,newProduct,i);
                    }
                });
                mSwipeRefreshLayout.setRefreshing(false);
            }
            @Override
            public void onFailure(Call<MultipleProducts> call, Throwable t) {
                Log.d("TAG111111","  e "+t.getMessage());
                mSwipeRefreshLayout.setRefreshing(false);


            }

        });
    }

void  onClikMostwanted(View view, List<MultipleProducts.Datum> mosted,int i){
    TextView text_add = (TextView) view.findViewById(R.id.text_add);
    TextView tv_heart = (TextView) view.findViewById(R.id.tv_heart);
    ImageView img_heart = (ImageView) view.findViewById(R.id.img_heart);
    RelativeLayout rel_add_to_card = (RelativeLayout) view.findViewById(R.id.rel_add_to_card);
    RelativeLayout rel_heart = (RelativeLayout) view.findViewById(R.id.rel_heart);
    ProgressBar progressBar2 = (ProgressBar) view.findViewById(R.id.progressBar);

    rel_add_to_card.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            progressBar2.setVisibility(View.VISIBLE);
            if(text_add.getText().equals(getString(R.string.add_to_cart))) {
                @SuppressLint("HardwareIds") String ID = Settings.Secure.getString(getActivity().getContentResolver(),
                        Settings.Secure.ANDROID_ID);
                CartParamenter cartParamenter = new CartParamenter(mosted.get(i).id, ID);
                Call<CartMain> call1 = apiInterface.cart(cartParamenter);
                call1.enqueue(new Callback<CartMain>() {
                    @Override
                    public void onResponse(Call<CartMain> call, Response<CartMain> response) {
                        CartMain cart = response.body();
                        if (cart.status)
                            text_add.setText(getActivity().getString(R.string.remove_to_cart));
                        else
                            Toast.makeText(getActivity(), cart.message, Toast.LENGTH_SHORT).show();
                        progressBar2.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onFailure(Call<CartMain> call, Throwable t) {
//                            Toast.makeText(context, "onFailure", Toast.LENGTH_SHORT).show();
                        progressBar2.setVisibility(View.VISIBLE);
                        call.cancel();
                    }
                });
            }
            else{
                @SuppressLint("HardwareIds") String ID = Settings.Secure.getString(getActivity().getContentResolver(),
                        Settings.Secure.ANDROID_ID);
                CartRemoveParamenter cartRemoveParamenter = new CartRemoveParamenter(mosted.get(i).id, ID,1,"delete");
                Call<CartMain> call1 = apiInterface.cartRemove(cartRemoveParamenter);
                call1.enqueue(new Callback<CartMain>() {
                    @Override
                    public void onResponse(Call<CartMain> call, Response<CartMain> response) {
                        CartMain cart = response.body();
                        if (cart.status)
                            text_add.setText(getString(R.string.add_to_cart));
                        else
                            Toast.makeText(getActivity(), cart.message, Toast.LENGTH_SHORT).show();
                        progressBar2.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<CartMain> call, Throwable t) {
//                            Toast.makeText(context, "onFailure", Toast.LENGTH_SHORT).show();
                        progressBar2.setVisibility(View.GONE);
                        call.cancel();
                    }
                });
            }
        }
    });
    //////////////////
    rel_heart.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            progressBar2.setVisibility(View.VISIBLE);
            if(tv_heart.getText().toString().equals("false")){
                @SuppressLint("HardwareIds") String ID = Settings.Secure.getString(getActivity().getContentResolver(),
                        Settings.Secure.ANDROID_ID);
                CartParamenter cartParamenter = new CartParamenter(mosted.get(i).id, ID);
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
                            Toast.makeText(getActivity(), favorite.message, Toast.LENGTH_SHORT).show();
                        progressBar2.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onFailure(Call<FavoriteMain> call, Throwable t) {
//                            Toast.makeText(context, "onFailure", Toast.LENGTH_SHORT).show();
                        progressBar2.setVisibility(View.VISIBLE);
                        call.cancel();
                    }
                });
            }
            else{
                @SuppressLint("HardwareIds") String ID = Settings.Secure.getString(getActivity().getContentResolver(),
                        Settings.Secure.ANDROID_ID);
                CartRemoveParamenter cartRemoveParamenter = new CartRemoveParamenter(mosted.get(i).id, ID,1,"delete");
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
                            Toast.makeText(getActivity(), favorite.message, Toast.LENGTH_SHORT).show();
                        progressBar2.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<FavoriteMain> call, Throwable t) {
//                            Toast.makeText(context, "onFailure", Toast.LENGTH_SHORT).show();
                        progressBar2.setVisibility(View.GONE);
                        call.cancel();
                    }
                });
            }
        }
    });
}

    @Override
    public void onRefresh() {
        getNewProducts();
        getMostWanted();
    }
}