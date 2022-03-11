package com.dukan.dukkan.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.dukan.dukkan.APIClient;
import com.dukan.dukkan.APIInterface;
import com.dukan.dukkan.R;
import com.dukan.dukkan.pojo.Rate;
import com.dukan.dukkan.pojo.RateParameter;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewSheetFragment extends BottomSheetDialogFragment {
    APIInterface apiInterface;
    EditText edit_review;
    int productID;
    RatingBar ratingBar;
    public ReviewSheetFragment() {
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
        ratingBar = (RatingBar) view.findViewById(R.id.ratingBar2);
        Button confirm_button = (Button) view.findViewById(R.id.confirm_button);
        Bundle bundle = this.getArguments();
        if (bundle != null)
            productID = bundle.getInt("productID",0 );
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
        InputMethodManager inputMethodManager =
                (InputMethodManager) getContext().getSystemService(getContext().INPUT_METHOD_SERVICE);
        if (getActivity().getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(
                    getActivity().getCurrentFocus().getWindowToken(), 0);
        }
        @SuppressLint("HardwareIds") String ID = Settings.Secure.getString(getActivity().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        RateParameter rateParameter = new RateParameter(productID,edit_review.getText().toString(),ratingBar.getRating(),"android",ID);
        Call<Rate> call1 = apiInterface.DoRate(rateParameter);
        call1.enqueue(new Callback<Rate>() {
            @Override
            public void onResponse(Call<Rate> call, Response<Rate> response) {
                Rate Rate = response.body();
                    Toast.makeText(getContext(), Rate.message, Toast.LENGTH_SHORT).show();
                    dismiss();
            }

            @Override
            public void onFailure(Call<Rate> call, Throwable t) {
                call.cancel();
            }
        });
    }
}
