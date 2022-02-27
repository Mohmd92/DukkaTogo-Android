package com.dukan.dukkan.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.dukan.dukkan.APIClient;
import com.dukan.dukkan.APIInterface;
import com.dukan.dukkan.R;
import com.dukan.dukkan.activity.DriverProfileActivity;
import com.dukan.dukkan.activity.LoginActivity;
import com.dukan.dukkan.activity.ProductsActivity;
import com.dukan.dukkan.adapter.BrandAdapter;
import com.dukan.dukkan.adapter.DeliveryAdapter;
import com.dukan.dukkan.adapter.MostWantedAdapter;
import com.dukan.dukkan.adapter.NewProductAdapter;
import com.dukan.dukkan.adapter.StoreAdapter;
import com.dukan.dukkan.pojo.Brand;
import com.dukan.dukkan.pojo.Home;
import com.dukan.dukkan.pojo.MostWanted;
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


public class DriveFragment extends Fragment {
    APIInterface apiInterface;
    ProgressBar progressBar;
    TextView header_tv_user_name;
    TextView tv_last_seen,tv_time_req;
    RecyclerView recyclerView;
    ImageView image_Derive;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.content_main_driver, container, false);
        apiInterface = APIClient.getClient(getContext()).create(APIInterface.class);
        image_Derive = root.findViewById(R.id.imag);
        header_tv_user_name = root.findViewById(R.id.header_tv_user_name);
        tv_last_seen = root.findViewById(R.id.tv_last_seen);
        tv_time_req = root.findViewById(R.id.tv_time_req);
        recyclerView = root.findViewById(R.id.recyclerView);
        progressBar = root.findViewById(R.id.progressBar);
        RelativeLayout rel_profile = root.findViewById(R.id.rellll);
        image_Derive.setClipToOutline(true);
        rel_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), DriverProfileActivity.class));
            }
        });
        return root;
    }
//    private void getHome() {
//        progressBar.setVisibility(View.VISIBLE);
//        Call<Home> callNew = apiInterface.doGetListHome();
//        callNew.enqueue(new Callback<Home>() {
//            @Override
//            public void onResponse(Call<Home> callNew, Response<Home> response) {
//                Home resource = response.body();
//                String message = resource.message;
//                if(message.equals("success")){
//                    List<Slider> slid = resource.data.sliders;
//                    List<Store> stores = resource.data.stores;
//                    List<MostWanted> mosted = resource.data.mostWanted;
//                    List<NewProduct> newProduct = resource.data.newProducts;
//                    List<Brand> brand = resource.data.brands;
//                    List<Brand> delivery = resource.data.brands;
//                    SliderItemView view01 = new SliderItemView(getContext());
//
//                    progressBar.setVisibility(View.GONE);
//                }
//
//            }
//            @Override
//            public void onFailure(Call<Home> call, Throwable t) {
//                progressBar.setVisibility(View.GONE);
//            }
//
//        });
//    }
}