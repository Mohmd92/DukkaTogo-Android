package com.dukan.dukkan.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
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

import com.dukan.dukkan.APIClient;
import com.dukan.dukkan.APIInterface;
import com.dukan.dukkan.R;
import com.dukan.dukkan.activity.ProductsActivity;
import com.dukan.dukkan.adapter.BrandAdapter;
import com.dukan.dukkan.adapter.DeliveryAdapter;
import com.dukan.dukkan.adapter.MostWantedAdapter;
import com.dukan.dukkan.adapter.NewProductAdapter;
import com.dukan.dukkan.adapter.StoreAdapter;
import com.dukan.dukkan.pojo.Brand;
import com.dukan.dukkan.pojo.CartMain;
import com.dukan.dukkan.pojo.CartParamenter;
import com.dukan.dukkan.pojo.CartRemoveParamenter;
import com.dukan.dukkan.pojo.Home;
import com.dukan.dukkan.pojo.MostWanted;
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


public class HomeFragment extends Fragment {
    APIInterface apiInterface;
    private HorizontalListView HorizontalListViewStore,HorizontalListViewMost,HorizontalListViewNewProduct,HorizontalListViewBrand,HorizontalListViewDelivery;
    private  SliderLayout sliderLayout;
    ProgressBar progressBar;
    ImageView img_filter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.content_main_main, container, false);
        apiInterface = APIClient.getClient(getContext()).create(APIInterface.class);
        sliderLayout = root.findViewById(R.id.sliderLayout);
        img_filter = root.findViewById(R.id.img_filter);
        TextView view_all_product = root.findViewById(R.id.view_all_product);
        HorizontalListViewStore = root.findViewById(R.id.HorizontalListViewStore);
        HorizontalListViewMost = root.findViewById(R.id.HorizontalListViewMost);
        HorizontalListViewNewProduct = root.findViewById(R.id.HorizontalListViewNewProduct);
        HorizontalListViewBrand = root.findViewById(R.id.HorizontalListViewBrand);
        HorizontalListViewDelivery = root.findViewById(R.id.HorizontalListViewDelivery);
        progressBar = root.findViewById(R.id.progressBar);
        img_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StoreFilterSheetFragment storeFilterSheetFragment = new StoreFilterSheetFragment();
                storeFilterSheetFragment.show(getParentFragmentManager()
                        , storeFilterSheetFragment.getTag());
            }
        });
        view_all_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ProductsActivity.class));

            }
        });
        getHome();

        return root;
    }
    private void getHome() {
        @SuppressLint("HardwareIds") String ID = Settings.Secure.getString(getActivity().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        progressBar.setVisibility(View.VISIBLE);
        sliderLayout.removeAllSliders();
        Call<Home> callNew = apiInterface.doGetListHome(ID);
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
                    List<Brand> delivery = resource.data.brands;
                    SliderItemView view01 = new SliderItemView(getContext());
                    for (Slider datum : slid) {
                        view01 = new SliderItemView(getContext());
                        view01.setItem2(datum.image,datum.name);
                        sliderLayout.addSlider(view01);
                    }
                    StoreAdapter customAdapter = new StoreAdapter(getContext(),stores);
                    HorizontalListViewStore.setAdapter(customAdapter);
                    customAdapter.notifyDataSetChanged();

                    MostWantedAdapter mostAdapter = new MostWantedAdapter(getContext(),mosted);
                    HorizontalListViewMost.setAdapter(mostAdapter);
                    customAdapter.notifyDataSetChanged();
                    HorizontalListViewMost.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            onClikMostwanted(view,mosted,i);
                        }
                    });
                    /////////////////

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

void  onClikMostwanted(View view, List<MostWanted> mosted,int i){
    TextView text_add = (TextView) view.findViewById(R.id.text_add);
    RelativeLayout rel_add_to_card = (RelativeLayout) view.findViewById(R.id.rel_add_to_card);
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
                            Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
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
}
}