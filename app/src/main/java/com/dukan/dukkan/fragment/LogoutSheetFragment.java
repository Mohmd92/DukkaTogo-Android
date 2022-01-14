package com.dukan.dukkan.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dukan.dukkan.R;
import com.dukan.dukkan.activity.LoginActivity;
import com.dukan.dukkan.activity.MainActivity;
import com.dukan.dukkan.activity.RegisterActivity;
import com.dukan.dukkan.util.SharedPreferenceManager;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class LogoutSheetFragment extends BottomSheetDialogFragment {
    public LogoutSheetFragment() {
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
        return inflater.inflate(R.layout.bottom_sheet_logout, container, false);
    }
    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button yes_button =  view.findViewById(R.id.yes_button);
        Button no_button =  view.findViewById(R.id.no_button);
        yes_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferenceManager.getInstance(getContext()).set_api_token("");
                SharedPreferenceManager.getInstance(getContext()).setUser_Name("");
                SharedPreferenceManager.getInstance(getContext()).set_email("");
                    SharedPreferenceManager.getInstance(getContext()).setPassword("");
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                dismiss();
            }
        });
        no_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}
