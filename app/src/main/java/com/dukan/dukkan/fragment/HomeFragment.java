package com.dukan.dukkan.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.dukan.dukkan.APIClient;
import com.dukan.dukkan.APIInterface;
import com.dukan.dukkan.R;
import com.dukan.dukkan.activity.ProductsActivity;
import com.dukan.dukkan.activity.ShowProductActivity;
import com.dukan.dukkan.activity.ShowStoresActivity;
import com.dukan.dukkan.activity.StoresActivity;
import com.dukan.dukkan.adapter.BrandAdapter;
import com.dukan.dukkan.adapter.DeliveryAdapter;
import com.dukan.dukkan.adapter.MostWantedAdapter;
import com.dukan.dukkan.adapter.NewProductAdapter;
import com.dukan.dukkan.adapter.RecyclerMostwantedAdapter;
import com.dukan.dukkan.adapter.RecyclerNewProductAdapter;
import com.dukan.dukkan.adapter.RecyclerStoreAdapter;
import com.dukan.dukkan.adapter.StoreAdapter;
import com.dukan.dukkan.pojo.Advertisement;
import com.dukan.dukkan.pojo.Advertisement2;
import com.dukan.dukkan.pojo.Brand;
import com.dukan.dukkan.pojo.CartMain;
import com.dukan.dukkan.pojo.CartParamenter;
import com.dukan.dukkan.pojo.CartRemoveParamenter;
import com.dukan.dukkan.pojo.Delivery;
import com.dukan.dukkan.pojo.FavoriteMain;
import com.dukan.dukkan.pojo.Home;
import com.dukan.dukkan.pojo.MostWanted;
import com.dukan.dukkan.pojo.MultipleStore;
import com.dukan.dukkan.pojo.NewProduct;
import com.dukan.dukkan.pojo.Slider;
import com.dukan.dukkan.pojo.Store;
import com.dukan.dukkan.util.HorizontalListView;
import com.dukan.dukkan.util.SharedPreferenceManager;
import com.squareup.picasso.Picasso;
import com.yihsian.slider.library.SliderItemView;
import com.yihsian.slider.library.SliderLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,RecyclerMostwantedAdapter.ItemClickListener {
    APIInterface apiInterface;
    private HorizontalListView HorizontalListViewStore,HorizontalListViewMost,HorizontalListViewNewProduct,HorizontalListViewBrand,HorizontalListViewDelivery;
    private  SliderLayout sliderLayout;
    ProgressBar progressBar;
    ImageView img_filter,image_advertisement1,image_advertisement2;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView desc_advertisement1,title_advertisement1;
    private TextView desc_advertisement2,title_advertisement2;
    RecyclerView recyclerViewViewMost,recyclerViewNewProduct;
    View root;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.content_main_main, container, false);
        apiInterface = APIClient.getClient(getContext()).create(APIInterface.class);
        sliderLayout = root.findViewById(R.id.sliderLayout);
        img_filter = root.findViewById(R.id.img_filter);
        recyclerViewNewProduct = root.findViewById(R.id.recyclerViewNewProduct);
        recyclerViewViewMost = root.findViewById(R.id.recyclerViewViewMost);
        TextView view_all_product = root.findViewById(R.id.view_all_product);
        TextView view_stores = root.findViewById(R.id.view_stores);
        TextView view_most_product = root.findViewById(R.id.view_most_product);

        image_advertisement1 = root.findViewById(R.id.image_advertisement1);
        desc_advertisement1 = root.findViewById(R.id.desc_advertisement1);
        title_advertisement1 = root.findViewById(R.id.title_advertisement1);
        image_advertisement2 = root.findViewById(R.id.image_advertisement2);
        desc_advertisement2 = root.findViewById(R.id.desc_advertisement2);
        title_advertisement2 = root.findViewById(R.id.title_advertisement2);

        HorizontalListViewStore = root.findViewById(R.id.HorizontalListViewStore);
        HorizontalListViewMost = root.findViewById(R.id.HorizontalListViewMost);
        HorizontalListViewNewProduct = root.findViewById(R.id.HorizontalListViewNewProduct);
        HorizontalListViewBrand = root.findViewById(R.id.HorizontalListViewBrand);
        HorizontalListViewDelivery = root.findViewById(R.id.HorizontalListViewDelivery);
        mSwipeRefreshLayout = (SwipeRefreshLayout)  root.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        progressBar = root.findViewById(R.id.progressBar);
        img_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StoreFilterSheetFragment storeFilterSheetFragment = new StoreFilterSheetFragment();
                storeFilterSheetFragment.show(getParentFragmentManager()
                        , storeFilterSheetFragment.getTag());
            }
        });
        view_stores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), StoresActivity.class);
                startActivity(i);
            }
        });
        view_all_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), ProductsActivity.class);
                i.putExtra("title", getString(R.string.new_products));
                i.putExtra("store", 0);
                i.putExtra("new", 1);
                i.putExtra("most", 0);
                i.putExtra("category", 0);
                startActivity(i);
            }
        });
        view_most_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), ProductsActivity.class);
                i.putExtra("title", getString(R.string.most_wanted));
                i.putExtra("store", 0);
                i.putExtra("new", 0);
                i.putExtra("most", 1);
                i.putExtra("category", 0);
                startActivity(i);
            }
        });
        getHome();

        return root;
    }

    private void getHome() {
        mSwipeRefreshLayout.setRefreshing(true);
        @SuppressLint("HardwareIds") String ID = Settings.Secure.getString(getActivity().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        System.out.println("sssssssssss... "+ID);
        System.out.println("sssssssssss... "+ SharedPreferenceManager.getInstance(getContext()).get_api_token());

        sliderLayout.removeAllSliders();
        Call<Home> callNew = apiInterface.doGetListHome(ID,"android");
        callNew.enqueue(new Callback<Home>() {
            @Override
            public void onResponse(Call<Home> callNew, Response<Home> response) {
                Home resource = response.body();
                String message = resource.message;
                if(message.equals("success")){
                    List<Slider> slid = resource.data.sliders;
                    List<Store> stores = resource.data.stores;
                    List<MostWanted> mosted = (ArrayList<MostWanted>) resource.data.mostWanted;
                    List<NewProduct> newProduct = resource.data.newProducts;
                    List<Brand> brand = resource.data.brands;
                    List<Delivery> delivery = resource.data.deliveries;
                    Advertisement advertisement1 = resource.data.advertisement;

                    if(advertisement1!=null) {
                        title_advertisement1.setText(advertisement1.title);
                        desc_advertisement1.setText(advertisement1.description);
                        Picasso.get()
                                .load(advertisement1.image)
                                .into(image_advertisement1);
                    }else
                        root.findViewById(R.id.adv1).setVisibility(View.GONE);

                    Advertisement2 advertisement2 = resource.data.advertisement2;
                    if(advertisement2!=null) {
                        title_advertisement2.setText(advertisement2.title);
                        desc_advertisement2.setText(advertisement2.description);
                        Picasso.get()
                                .load(advertisement2.image)
                                .into(image_advertisement2);
                    }else
                        root.findViewById(R.id.adv2).setVisibility(View.GONE);



                    SliderItemView view01 = new SliderItemView(getContext());
                    for (Slider datum : slid) {
                        view01 = new SliderItemView(getContext());
                        view01.setItem2(datum.image,datum.name,datum.storeId,datum.productId);
                        sliderLayout.addSlider(view01);
                    }
                    if(slid.size()==0)
                        sliderLayout.setVisibility(View.GONE);

                    StoreAdapter customAdapter = new StoreAdapter(getContext(),stores);
                    HorizontalListViewStore.setAdapter(customAdapter);
                    customAdapter.notifyDataSetChanged();
                    System.out.println("ssssssssssssssxxxx "+stores.size());


                    HorizontalListViewStore.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent i2 = new Intent(getActivity(), ShowStoresActivity.class);
                            i2.putExtra("user", "");
                            i2.putExtra("StoreID", stores.get(i).id);
                            i2.putExtra("most", 0);
                            startActivity(i2);
                        }
                    });
//                    MostWantedAdapter mostAdapter = new MostWantedAdapter(getContext(),mosted);
//                    HorizontalListViewMost.setAdapter(mostAdapter);
//                    mostAdapter.notifyDataSetChanged();

                    LinearLayoutManager layoutManager= new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);
                    recyclerViewViewMost.setLayoutManager(layoutManager);
                    List<MostWanted> datumListMost = resource.data.mostWanted;
                    RecyclerMostwantedAdapter adapter = new RecyclerMostwantedAdapter(getContext(), datumListMost);
                    recyclerViewViewMost.setAdapter(adapter);

//                    HorizontalListViewMost.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                           onClikMostwanted(view,mosted,i);
//                        }
//                    });
                    /////////////////
                    LinearLayoutManager layoutManager2= new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);
                    recyclerViewNewProduct.setLayoutManager(layoutManager2);
                    List<NewProduct> datumListNew = resource.data.newProducts;
                    RecyclerNewProductAdapter adapterNew = new RecyclerNewProductAdapter(getContext(), datumListNew);
                    recyclerViewNewProduct.setAdapter(adapterNew);

//                    NewProductAdapter NewProductAdapter = new NewProductAdapter(getContext(),newProduct);
//                    HorizontalListViewNewProduct.setAdapter(NewProductAdapter);
//                    NewProductAdapter.notifyDataSetChanged();
//                    HorizontalListViewNewProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                            onClikMostwanted(view,mosted,i);
//                        }
//                    });

                    BrandAdapter brandAdapter = new BrandAdapter(getContext(),brand);
                    HorizontalListViewBrand.setAdapter(brandAdapter);
                    customAdapter.notifyDataSetChanged();
                    if(brand.size()==0){
                        HorizontalListViewBrand.setVisibility(View.GONE);

                    }

                    DeliveryAdapter deliveryAdapter = new DeliveryAdapter(getContext(),delivery);
                    HorizontalListViewDelivery.setAdapter(deliveryAdapter);
                    customAdapter.notifyDataSetChanged();

                    progressBar.setVisibility(View.GONE);

                }
                mSwipeRefreshLayout.setRefreshing(false);
            }
            @Override
            public void onFailure(Call<Home> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                mSwipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                System.out.println("88888888888 9999  "+t.getMessage());
            }

        });

    }

void  onClikMostwanted(View view, List<MostWanted> mosted,int i){
    TextView text_add = (TextView) view.findViewById(R.id.text_add);
    TextView tv_heart = (TextView) view.findViewById(R.id.tv_heart);
    ImageView img_heart = (ImageView) view.findViewById(R.id.img_heart);
    LinearLayout linear_main = (LinearLayout) view.findViewById(R.id.linear_main);
    RelativeLayout rel_add_to_card = (RelativeLayout) view.findViewById(R.id.rel_add_to_card);
    RelativeLayout rel_heart = (RelativeLayout) view.findViewById(R.id.rel_heart);
    ProgressBar progressBar2 = (ProgressBar) view.findViewById(R.id.progressBar);
    linear_main.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i2 = new Intent(getActivity(), ShowProductActivity.class);
            i2.putExtra("productID", mosted.get(i).id);
            startActivity(i2);
 
        }
    });
    //////////////////
    rel_add_to_card.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            progressBar2.setVisibility(View.VISIBLE);
            if(text_add.getText().equals(getString(R.string.add_to_cart))) {
                @SuppressLint("HardwareIds") String ID = Settings.Secure.getString(getActivity().getContentResolver(),
                        Settings.Secure.ANDROID_ID);
                CartParamenter cartParamenter = new CartParamenter(mosted.get(i).id, ID);
                Call<CartMain> call1 = apiInterface.cart(ID,cartParamenter);
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
        getHome();
    }

    @Override
    public void onClick(View view, int position) {
        Toast.makeText(getContext(), "pppppppppppp", Toast.LENGTH_SHORT).show();
    }
}