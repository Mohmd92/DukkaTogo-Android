package com.dukan.dukkan.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dukan.dukkan.R;
import com.dukan.dukkan.activity.ShowStoresActivity;
import com.dukan.dukkan.pojo.Privacy;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class RecyclerPrivacyPolicyAdapter extends RecyclerView.Adapter<RecyclerPrivacyPolicyAdapter.ViewHolder> {
    List<Privacy.Datum> mValues;
    Context mContext;
    protected ItemListener mListener;
    private AdapterView.OnItemClickListener listener;
    int row_index=-1;
    public RecyclerPrivacyPolicyAdapter(Context context, List<Privacy.Datum> values) {

        mValues = values;
        mContext = context;
//        mListener=itemListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Privacy.Datum item;
        TextView name,tv_ref;
        ImageView img_ref;
        LinearLayout linear;

        public RelativeLayout relative;
        public ViewHolder(View v) {
            super(v);

            v.setOnClickListener(this);
            img_ref = v.findViewById(R.id.img_ref);
            name = v.findViewById(R.id.text1);
            tv_ref = v.findViewById(R.id.tv_ref);
            linear = v.findViewById(R.id.linear);

        }
        public void setData(Privacy.Datum item) {
            this.item = item;

            name.setText(item.title);
            tv_ref.setText(item.description);
            img_ref.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(linear.getVisibility()==View.VISIBLE)
                        linear.setVisibility(View.GONE);
                    else
                        linear.setVisibility(View.VISIBLE);
                }
            });
        }
        @Override
        public void onClick(View view) {

        }
    }

    @Override
    public RecyclerPrivacyPolicyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.privacy_policy_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder Vholder, int position) {
        Vholder.setData(mValues.get(position));

    }
    @Override
    public int getItemCount() {

        return mValues.size();
    }
     public interface ItemListener {
        void onItemClick(Privacy.Datum item);
    }
}