package com.dukan.dukkan.fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.dukan.dukkan.APIClient;
import com.dukan.dukkan.APIInterface;
import com.dukan.dukkan.R;
import com.dukan.dukkan.activity.DriverStatisticsActivity;
import com.dukan.dukkan.activity.LoginActivity;
import com.dukan.dukkan.activity.ProductsActivity;
import com.dukan.dukkan.adapter.RecyclerStoreAdapter;
import com.dukan.dukkan.pojo.Category;
import com.dukan.dukkan.pojo.Category;
import com.dukan.dukkan.pojo.CategoryProduct;
import com.dukan.dukkan.util.SharedPreferenceManager;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterSheetFragment extends BottomSheetDialogFragment {
    APIInterface apiInterface;
    Spinner spinner_country;
    String title;
    TextView filter_title;
    int currentItem = 0,categId=-1;
    Number minValuess=0;
    Number maxValuess=1000;

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
    @SuppressLint("SetTextI18n")
    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CrystalRangeSeekbar rangeSeekbar = (CrystalRangeSeekbar) view.findViewById(R.id.rangeSeekbar1);
        TextView tvMin = (TextView) view.findViewById(R.id.textMin1);
        TextView tvMax = (TextView) view.findViewById(R.id.textMax1);
        Button confirm_button = view.findViewById(R.id.confirm_button);
        spinner_country = view.findViewById(R.id.spinner_country);
        filter_title = view.findViewById(R.id.textView0);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            title = bundle.getString("title", "");
            filter_title.setText(getContext().getString(R.string.filter)+" "+title);
        }
        rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                tvMin.setText(String.valueOf(minValue)+" $");
                tvMax.setText(String.valueOf(maxValue)+" $");
                minValuess=minValue;
                maxValuess=maxValue;
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
        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sendData=categId+"&"+minValuess+"&"+maxValuess;
                if(categId!=-1){
                    SharedPreferenceManager.getInstance(getContext()).setFilterDates(sendData);
                    dismiss();
                }
            }
        });

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
                    List<CategoryProduct> datumList = resource.data;
                    String[] categ=new String[datumList.size()+1];
                    Integer[] categssID=new Integer[datumList.size()+1];
                    categ[0]=getString(R.string.select);
                    categssID[0]=0;
                    int i=1;
                  for (CategoryProduct datum : datumList) {
                      categ[i]=datum.name;
                      categssID[i]=datum.id;
                              i++;
                  }
                    spinner_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if(currentItem == position){
                                return; //do nothing
                            }
                            else {
                                categId=categssID[position];
                            }
                            currentItem = position;
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
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
    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
        Intent i = new Intent(getActivity(), ProductsActivity.class);
//        i.putExtra("title", getString(R.string.most_wanted));
//        i.putExtra("store", 0);
//        i.putExtra("new", 0);
//        i.putExtra("most", 1);
//        i.putExtra("category", 0);
        startActivity(i);
    }
}
