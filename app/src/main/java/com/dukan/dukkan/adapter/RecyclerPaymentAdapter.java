package com.dukan.dukkan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.dukan.dukkan.R;
import com.dukan.dukkan.pojo.PaymentGateway;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerPaymentAdapter extends RecyclerView.Adapter<RecyclerPaymentAdapter.ViewHolder> {
    List<PaymentGateway> mValues;
    Context mContext;
    protected ItemListener mListener;
    private AdapterView.OnItemClickListener listener;
    int row_index=-1;
    private int lastCheckedPosition = -1;
    private int initPosition = -1;
    public RecyclerPaymentAdapter(Context context, List<PaymentGateway> values) {

        mValues = values;
        mContext = context;
//        mListener=itemListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        PaymentGateway item;
        TextView payment_name;
        ImageView img_chk;
        ConstraintLayout constraintLayout;

        public RelativeLayout relative;
        public ViewHolder(View v) {
            super(v);

            v.setOnClickListener(this);
            img_chk = v.findViewById(R.id.img_chk);
            payment_name = v.findViewById(R.id.payment_name);
            constraintLayout = v.findViewById(R.id.constraintLayout);

        }
        public void setData(PaymentGateway item) {
            this.item = item;

            payment_name.setText(item.name);


        }
        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClick(item);
            }
        }
    }

    @Override
    public RecyclerPaymentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.payments_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder Vholder, int position) {
        Vholder.setData(mValues.get(position));
        if(Vholder.item.id==0){
            Picasso.get()
                    .load(R.drawable.ic_check)
                    .placeholder(R.drawable.ic_check)
                    .into(Vholder.img_chk);
        }
        if(lastCheckedPosition!=-1) {
            Picasso.get()
                    .load(R.drawable.ic_unchek)
                    .placeholder(R.drawable.ic_unchek)
                    .into(Vholder.img_chk);
            if (position == lastCheckedPosition) {
                Picasso.get()
                        .load(R.drawable.ic_check)
                        .placeholder(R.drawable.ic_check)
                        .into(Vholder.img_chk);
            } else {
                Picasso.get()
                        .load(R.drawable.ic_unchek)
                        .placeholder(R.drawable.ic_unchek)
                        .into(Vholder.img_chk);
            }
        }
        Vholder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastCheckedPosition >= 0)
                    notifyItemChanged(lastCheckedPosition);
                lastCheckedPosition = Vholder.getAdapterPosition();
                    notifyItemChanged(lastCheckedPosition);
                notifyDataSetChanged();
            }
        });
    }
    @Override
    public int getItemCount() {

        return mValues.size();
    }
     public interface ItemListener {
        void onItemClick(PaymentGateway item);
    }
}