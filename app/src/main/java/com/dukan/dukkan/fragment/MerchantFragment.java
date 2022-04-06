package com.dukan.dukkan.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
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
import com.dukan.dukkan.activity.MerchantProfileActivity;
import com.dukan.dukkan.pojo.Profile;
import com.dukan.dukkan.pojo.StoreTimeWork;
import com.dukan.dukkan.util.SharedPreferenceManager;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MerchantFragment extends Fragment {
    APIInterface apiInterface;
    ProgressBar progressBar;
    TextView tv_time_from_work,header_tv_user_name,tv_desc,tv_day,tv_working_hours,tv_view_all;
    RecyclerView recyclerView;
    ImageView image_Merchant,img_store;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.content_main_merchant, container, false);
        apiInterface = APIClient.getClient(getContext()).create(APIInterface.class);
        image_Merchant = root.findViewById(R.id.imag);
        tv_time_from_work = root.findViewById(R.id.tv_time_from_work);
        tv_desc = root.findViewById(R.id.tv_desc);
        header_tv_user_name = root.findViewById(R.id.header_tv_user_name);
        tv_day = root.findViewById(R.id.tv_day);
        tv_view_all = root.findViewById(R.id.tv_view_all);
        tv_working_hours = root.findViewById(R.id.tv_working_hours);
        recyclerView = root.findViewById(R.id.recyclerView);
        progressBar = root.findViewById(R.id.progressBar);
        img_store = root.findViewById(R.id.img_store);

        RelativeLayout rel_profile = root.findViewById(R.id.rellll);
        image_Merchant.setClipToOutline(true);
        rel_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MerchantProfileActivity.class));
            }
        });
        getProfile();
        return root;
    }
    private void getProfile2() {
        header_tv_user_name.setText(SharedPreferenceManager.getInstance(getContext()).getUser_Name());
        Picasso.get()
                .load(SharedPreferenceManager.getInstance(getContext()).getUserImage())
                .into(image_Merchant);
    }
    private void getProfile() {
        @SuppressLint("HardwareIds") String ID = Settings.Secure.getString(getActivity().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        Calendar calendar = Calendar.getInstance();
//        int cuarentDay = calendar.get(Calendar.DAY_OF_WEEK);
        String cuarentDay = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(System.currentTimeMillis());
        System.out.println("IDIDIDIDIDIDID "+ID);
        progressBar.setVisibility(View.VISIBLE);
        Call<Profile> callNew = apiInterface.UserProfile();
        callNew.enqueue(new Callback<Profile>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<Profile> callNew, Response<Profile> response) {
                Profile resource = response.body();
                Boolean status = resource.status;
                if(status) {
                    header_tv_user_name.setText(resource.data.name);
                    Picasso.get()
                            .load(resource.data.image)
                            .into(image_Merchant);
                    Picasso.get()
                            .load(resource.data.store.image)
                            .into(img_store);
                    List<StoreTimeWork> timeWork = resource.data.store.storeTimeWorks;
                    for (StoreTimeWork datum : timeWork) {
                        if(datum.day.equals(cuarentDay)){
                            tv_time_from_work.setVisibility(View.VISIBLE);
                            tv_day.setText(cuarentDay);
                            tv_working_hours.setText(datum.from+"-"+datum.to);
                            if(datum.from.equals("00:00:00") && datum.to.equals("00:00:00")){
                                tv_day.setText(R.string.offline);
                                tv_time_from_work.setVisibility(View.GONE);
                                tv_working_hours.setVisibility(View.GONE);
                            }
                            break;
                        }

                    }
                }
                progressBar.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
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