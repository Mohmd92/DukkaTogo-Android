package com.dukan.dukkan.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.dukan.dukkan.adapter.RecyclerDriverOrderAdapter;
import com.dukan.dukkan.adapter.RecyclerMerchantOrder2Adapter;
import com.dukan.dukkan.adapter.StoreAdapter;
import com.dukan.dukkan.pojo.Brand;
import com.dukan.dukkan.pojo.Home;
import com.dukan.dukkan.pojo.MostWanted;
import com.dukan.dukkan.pojo.NewProduct;
import com.dukan.dukkan.pojo.Order;
import com.dukan.dukkan.pojo.Slider;
import com.dukan.dukkan.pojo.Store;
import com.dukan.dukkan.util.HorizontalListView;
import com.dukan.dukkan.util.SharedPreferenceManager;
import com.squareup.picasso.Picasso;
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
        getProfile2();
        getOrders();
        return root;
    }
    private void getProfile2() {
        header_tv_user_name.setText(SharedPreferenceManager.getInstance(getContext()).getUser_Name());
        Picasso.get()
                .load(SharedPreferenceManager.getInstance(getContext()).getUserImage())
                .into(image_Derive);
    }
    private void getOrders() {
        @SuppressLint("HardwareIds") String ID = Settings.Secure.getString(getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        progressBar.setVisibility(View.VISIBLE);
        System.out.println("TAG111111 ssssss "+ID);
        Call<Order> callNew = apiInterface.GetAllOrders(ID,"android","1","","","","","");
        callNew.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> callNew, Response<Order> response) {
                Log.d("TAG111111",response.code()+"");
                Order resource = response.body();
                Log.d("TAG111111","111111111111111111111111111111111 resource "+resource.status);
                if(resource.status){
                    Log.d("TAG111111","111111111111111111111111111111111ww");
                    List<Order.Datum> datumList = resource.data;
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    RecyclerDriverOrderAdapter adapter = new RecyclerDriverOrderAdapter(getActivity(), datumList);
                    recyclerView.setAdapter(adapter);
                }
                progressBar.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                Log.d("TAG111111","  e "+t.getMessage());
                progressBar.setVisibility(View.GONE);

            }

        });
    }
}