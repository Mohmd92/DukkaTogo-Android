package com.dukan.dukkan.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dukan.dukkan.APIClient;
import com.dukan.dukkan.APIInterface;
import com.dukan.dukkan.R;
import com.dukan.dukkan.pojo.Rate;
import com.dukan.dukkan.pojo.RateParameter;
import com.dukan.dukkan.pojo.RateStore;
import com.dukan.dukkan.pojo.RateStoreParameter;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewStoreSheetFragment extends BottomSheetDialogFragment {
    APIInterface apiInterface;
    EditText edit_review;
    int StoreID;
    RatingBar ratingBar;
    ProgressBar progressBar;
    public ReviewStoreSheetFragment() {
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
        return inflater.inflate(R.layout.bottom_sheet_add_review, container, false);
    }
    @SuppressLint("SetTextI18n")
    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edit_review = (EditText) view.findViewById(R.id.edit_review);
        progressBar = view.findViewById(R.id.progressBar);
        ratingBar = (RatingBar) view.findViewById(R.id.ratingBar2);
        Button confirm_button = (Button) view.findViewById(R.id.confirm_button);
        Bundle bundle = getArguments();
        if (bundle != null){
            StoreID = bundle.getInt("storeIds" );

        }



        apiInterface = APIClient.getClient(getContext()).create(APIInterface.class);

        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(edit_review.getText().toString()))
                    Toast.makeText(getContext(), getString(R.string.enter_review), Toast.LENGTH_SHORT).show();
                else
                    Rate();
            }
        });
    }
    private void Rate() {
        progressBar.setVisibility(View.VISIBLE);
        InputMethodManager inputMethodManager =
                (InputMethodManager) getContext().getSystemService(getContext().INPUT_METHOD_SERVICE);
        if (getActivity().getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(
                    getActivity().getCurrentFocus().getWindowToken(), 0);
        }
        @SuppressLint("HardwareIds") String ID = Settings.Secure.getString(getActivity().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        RateStoreParameter rateParameter = new RateStoreParameter(StoreID,edit_review.getText().toString(),ratingBar.getRating(),"android",ID);
        Call<RateStore> call1 = apiInterface.DoStoreRate(rateParameter);
        call1.enqueue(new Callback<RateStore>() {
            @Override
            public void onResponse(Call<RateStore> call, Response<RateStore> response) {
                RateStore Rate = response.body();
                    Toast.makeText(getContext(), ""+Rate.status, Toast.LENGTH_SHORT).show();
                    dismiss();
                progressBar.setVisibility(View.GONE);

            }
            @Override
            public void onFailure(Call<RateStore> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                call.cancel();
            }
        });
    }
}
