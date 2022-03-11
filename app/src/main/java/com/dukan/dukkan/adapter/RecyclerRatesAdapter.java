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

import androidx.recyclerview.widget.RecyclerView;

import com.dukan.dukkan.R;
import com.dukan.dukkan.pojo.Rate;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RecyclerRatesAdapter extends RecyclerView.Adapter<RecyclerRatesAdapter.ViewHolder> {
    List<Rate.Datum> mValues;
    Context mContext;
    protected ItemListener mListener;
    private AdapterView.OnItemClickListener listener;
    int row_index=-1;
    public RecyclerRatesAdapter(Context context, List<Rate.Datum> values) {

        mValues = values;
        mContext = context;
//        mListener=itemListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Rate.Datum item;
        TextView tv_user_name,tv_date,tv_comment;
        ImageView image;
        RatingBar ratebar;

        public RelativeLayout relative;
        public ViewHolder(View v) {
            super(v);

            v.setOnClickListener(this);
            image = v.findViewById(R.id.image);
            tv_user_name = v.findViewById(R.id.tv_user_name);
            tv_date = v.findViewById(R.id.tv_date);
            tv_comment = v.findViewById(R.id.tv_comment);
            ratebar = v.findViewById(R.id.ratingBar2);

        }
        public void setData(Rate.Datum item) {
            this.item = item;

            tv_user_name.setText(item.user.name);
            SimpleDateFormat formatterOut = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");

            try {
                Date date = formatterOut.parse(item.createdAt);
                System.out.println(date);
                System.out.println(formatterOut.format(date));
                tv_date.setText(formatterOut.format(date));

            } catch (ParseException e) {
                e.printStackTrace();
            }


            tv_comment.setText(item.comment);
            ratebar.setRating(Float.parseFloat(item.rate));
                Picasso.get()
                        .load(item.user.image)
                        .into(image);

        }
        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClick(item);
            }
        }
    }

    @Override
    public RecyclerRatesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.customer_review_item, parent, false);

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
        void onItemClick(Rate.Datum item);
    }
}