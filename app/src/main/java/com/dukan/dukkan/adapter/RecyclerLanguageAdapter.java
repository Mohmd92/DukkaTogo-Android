package com.dukan.dukkan.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.dukan.dukkan.R;
import com.dukan.dukkan.model.DataModeLanguage;
import com.dukan.dukkan.pojo.MultipleStore;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerLanguageAdapter extends RecyclerView.Adapter<RecyclerLanguageAdapter.ViewHolder> {
    List<DataModeLanguage> mValues;
    Context mContext;
    protected ItemListener mListener;
    private AdapterView.OnItemClickListener listener;
    int row_index=-1;
    private int lastCheckedPosition = -1;
    private int initPosition = -1;
    public RecyclerLanguageAdapter(Context context, List<DataModeLanguage> values) {

        mValues = values;
        mContext = context;
//        mListener=itemListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        DataModeLanguage item;
        TextView name;
        ImageView select_item_image,img_chk;
        ConstraintLayout constraintLayout;

        public RelativeLayout relative;
        public ViewHolder(View v) {
            super(v);

            v.setOnClickListener(this);
            select_item_image = v.findViewById(R.id.image);
            img_chk = v.findViewById(R.id.img_chk);
            name = v.findViewById(R.id.tv_name);
            constraintLayout = v.findViewById(R.id.constraintLayout);

        }
        public void setData(DataModeLanguage item) {
            this.item = item;

            name.setText(item.name);
                Picasso.get()
                        .load(item.image)
                        .into(select_item_image);
//                if(item.status){
//                    initPosition=getAdapterPosition();
//                    Picasso.get()
//                            .load(R.drawable.ic_check)
//                            .placeholder(R.drawable.ic_check)
//                            .into(img_chk);
//                }

        }
        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClick(item);
            }
        }
    }

    @Override
    public RecyclerLanguageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.language_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder Vholder, int position) {
        Vholder.setData(mValues.get(position));
        if(Vholder.item.status){
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
        void onItemClick(DataModeLanguage item);
    }
}