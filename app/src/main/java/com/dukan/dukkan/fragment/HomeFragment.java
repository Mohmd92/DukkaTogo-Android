package com.dukan.dukkan.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dukan.dukkan.APIClient;
import com.dukan.dukkan.APIInterface;
import com.dukan.dukkan.R;
import com.dukan.dukkan.adapter.BrandAdapter;
import com.dukan.dukkan.adapter.DeliveryAdapter;
import com.dukan.dukkan.adapter.MostWantedAdapter;
import com.dukan.dukkan.adapter.NewProductAdapter;
import com.dukan.dukkan.adapter.RecyclerStoreAdapter;
import com.dukan.dukkan.adapter.StoreAdapter;
import com.dukan.dukkan.pojo.Brand;
import com.dukan.dukkan.pojo.Data;
import com.dukan.dukkan.pojo.Home;
import com.dukan.dukkan.pojo.MostWanted;
import com.dukan.dukkan.pojo.MultipleStore;
import com.dukan.dukkan.pojo.NewProduct;
import com.dukan.dukkan.pojo.Slider;
import com.dukan.dukkan.pojo.Store;
import com.dukan.dukkan.util.HorizontalListView;
import com.yihsian.slider.library.SliderItemView;
import com.yihsian.slider.library.SliderLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {
    APIInterface apiInterface;
    private HorizontalListView HorizontalListViewStore,HorizontalListViewMost,HorizontalListViewNewProduct,HorizontalListViewBrand,HorizontalListViewDelivery;
    private  SliderLayout sliderLayout;
    ProgressBar progressBar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.content_main_main, container, false);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        sliderLayout = root.findViewById(R.id.sliderLayout);
        HorizontalListViewStore = root.findViewById(R.id.HorizontalListViewStore);
        HorizontalListViewMost = root.findViewById(R.id.HorizontalListViewMost);
        HorizontalListViewNewProduct = root.findViewById(R.id.HorizontalListViewNewProduct);
        HorizontalListViewBrand = root.findViewById(R.id.HorizontalListViewBrand);
        HorizontalListViewDelivery = root.findViewById(R.id.HorizontalListViewDelivery);
        progressBar = root.findViewById(R.id.progressBar);

        getHome();
        return root;
    }
    private void getHome() {
        progressBar.setVisibility(View.VISIBLE);
        sliderLayout.removeAllSliders();
        Call<Home> callNew = apiInterface.doGetListHome();
        callNew.enqueue(new Callback<Home>() {
            @Override
            public void onResponse(Call<Home> callNew, Response<Home> response) {
                Home resource = response.body();
                String message = resource.message;
                if(message.equals("success")){
                    List<Slider> slid = resource.data.sliders;
                    List<Store> stores = resource.data.stores;
                    List<MostWanted> mosted = resource.data.mostWanted;
                    List<NewProduct> newProduct = resource.data.newProducts;
                    List<Brand> brand = resource.data.brands;
                    List<Brand> delivery = resource.data.brands;
                    SliderItemView view01 = new SliderItemView(getContext());
                    for (Slider datum : slid) {
                        view01.setItem2(datum.image,datum.name);
                        sliderLayout.addSlider(view01);
                    }
                    StoreAdapter customAdapter = new StoreAdapter(getContext(),stores);
                    HorizontalListViewStore.setAdapter(customAdapter);
                    customAdapter.notifyDataSetChanged();

                    MostWantedAdapter mostAdapter = new MostWantedAdapter(getContext(),mosted);
                    HorizontalListViewMost.setAdapter(mostAdapter);
                    customAdapter.notifyDataSetChanged();

                    NewProductAdapter NewProductAdapter = new NewProductAdapter(getContext(),newProduct);
                    HorizontalListViewNewProduct.setAdapter(NewProductAdapter);
                    customAdapter.notifyDataSetChanged();

                    BrandAdapter brandAdapter = new BrandAdapter(getContext(),brand);
                    HorizontalListViewBrand.setAdapter(brandAdapter);
                    customAdapter.notifyDataSetChanged();

                    DeliveryAdapter deliveryAdapter = new DeliveryAdapter(getContext(),delivery);
                    HorizontalListViewDelivery.setAdapter(deliveryAdapter);
                    customAdapter.notifyDataSetChanged();

                    progressBar.setVisibility(View.GONE);
                }

            }
            @Override
            public void onFailure(Call<Home> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }

        });
    }


}