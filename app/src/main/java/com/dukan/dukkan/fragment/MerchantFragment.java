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


public class MerchantFragment extends Fragment {
    APIInterface apiInterface;
    ProgressBar progressBar;
    TextView header_tv_user_name,tv_desc,tv_day,tv_working_hours,tv_view_all;
    RecyclerView recyclerView;
    ImageView image_Merchant;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.content_main_merchant, container, false);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        image_Merchant = root.findViewById(R.id.imag);
        header_tv_user_name = root.findViewById(R.id.header_tv_user_name);
        tv_desc = root.findViewById(R.id.tv_desc);
        tv_day = root.findViewById(R.id.tv_day);
        tv_view_all = root.findViewById(R.id.tv_view_all);
        tv_working_hours = root.findViewById(R.id.tv_working_hours);
        recyclerView = root.findViewById(R.id.recyclerView);
        progressBar = root.findViewById(R.id.progressBar);

        RelativeLayout rel_profile = root.findViewById(R.id.rellll);
        image_Merchant.setClipToOutline(true);
        rel_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(getActivity(), DriverProfileActivity.class));
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