package com.dukan.dukkan.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.dukan.dukkan.APIClient;
import com.dukan.dukkan.APIInterface;
import com.dukan.dukkan.R;
import com.dukan.dukkan.activity.LoginActivity;
import com.dukan.dukkan.adapter.RecyclerStoreAdapter;
import com.dukan.dukkan.pojo.Category;
import com.dukan.dukkan.pojo.Category;
import com.dukan.dukkan.util.SharedPreferenceManager;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterSheetFragment extends BottomSheetDialogFragment {
    APIInterface apiInterface;
    Spinner spinner_country;
    public FilterSheetFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.bottom_sheet_filter, container, false);
    }
    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CrystalRangeSeekbar rangeSeekbar = (CrystalRangeSeekbar) view.findViewById(R.id.rangeSeekbar1);
        TextView tvMin = (TextView) view.findViewById(R.id.textMin1);
        TextView tvMax = (TextView) view.findViewById(R.id.textMax1);
        spinner_country = view.findViewById(R.id.spinner_country);
        rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                tvMin.setText(String.valueOf(minValue)+" $");
                tvMax.setText(String.valueOf(maxValue)+" $");
            }
        });
        apiInterface = APIClient.getClient(getContext()).create(APIInterface.class);
        getCategories();
// set final value listener
        rangeSeekbar.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
            @Override
            public void finalValue(Number minValue, Number maxValue) {
                Log.d("CRS=>", String.valueOf(minValue) + " : " + String.valueOf(maxValue));
            }
        });
//        Button yes_button =  view.findViewById(R.id.yes_button);
//        Button no_button =  view.findViewById(R.id.no_button);
//        yes_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SharedPreferenceManager.getInstance(getContext()).set_api_token("");
//                SharedPreferenceManager.getInstance(getContext()).setUser_Name("");
//                SharedPreferenceManager.getInstance(getContext()).set_email("");
//                    SharedPreferenceManager.getInstance(getContext()).setPassword("");
//                startActivity(new Intent(getActivity(), LoginActivity.class));
//                getActivity().finish();
//                dismiss();
//            }
//        });
//        no_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dismiss();
//            }
//        });
    }
    private void getCategories() {
        Call<Category> callNew = apiInterface.doGetListCategory();
        callNew.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> callNew, Response<Category> response) {
                Log.d("TAG111111",response.code()+"");
                Category resource = response.body();
                Boolean status = resource.status;
                if(status) {
                    List<Category.Datum> datumList = resource.data;
                    String[] categ=new String[datumList.size()+1];
                    categ[0]=getString(R.string.select);
                    int i=1;
                  for (Category.Datum datum : datumList) {
                      categ[i]=datum.name;
                              i++;
                  }
//                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(),R.layout.brand_dropdown,categ);

                    final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                            getActivity(),R.layout.brand_dropdown,categ){
                        @Override
                        public boolean isEnabled(int position){
                            if(position == 0)
                            {
                                // Disable the first item from Spinner
                                // First item will be use for hint
                                return false;
                            }
                            else
                            {
                                return true;
                            }
                        }
                        @Override
                        public View getDropDownView(int position, View convertView,
                                                    ViewGroup parent) {
                            View view = super.getDropDownView(position, convertView, parent);
                            TextView tv = (TextView) view;
                            if(position == 0){
                                // Set the hint text color gray
                                tv.setTextColor(Color.GRAY);
                            }
                            else {
                                tv.setTextColor(Color.BLACK);
                            }
                            return view;
                        }
                    };
                    spinnerArrayAdapter.setDropDownViewResource(R.layout.brand_dropdown);
                    spinner_country.setPrompt(getString(R.string.select));
                    spinner_country.setAdapter(spinnerArrayAdapter);
                }
            }
            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                Log.d("TAG111111","  e "+t.getMessage());
            }
        });
}
}
