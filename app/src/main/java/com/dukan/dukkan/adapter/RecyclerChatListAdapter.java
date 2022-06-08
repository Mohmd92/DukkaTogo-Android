package com.dukan.dukkan.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.dukan.dukkan.activity.ChatActivity;
import com.dukan.dukkan.activity.ShowStoresActivity;
import com.dukan.dukkan.pojo.Chat;
import com.dukan.dukkan.util.SharedPreferenceManager;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class RecyclerChatListAdapter extends RecyclerView.Adapter<RecyclerChatListAdapter.ViewHolder> {
    List<Chat.Datum> mValues;
    Context mContext;
    protected ItemListener mListener;
    private AdapterView.OnItemClickListener listener;
    public RecyclerChatListAdapter(Context context, List<Chat.Datum> values) {

        mValues = values;
        mContext = context;
//        mListener=itemListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Chat.Datum item;
        TextView name,tv_time,tv_msg;
        ImageView img_user,img_status;
        String image_url;

        public RelativeLayout relative;
        public ViewHolder(View v) {
            super(v);

            v.setOnClickListener(this);
            img_status = v.findViewById(R.id.img_status);
            img_user = v.findViewById(R.id.img_user);
            name = v.findViewById(R.id.tv_name);
            tv_msg = v.findViewById(R.id.tv_msg);
            tv_time = v.findViewById(R.id.tv_time);

        }
        public void setData(Chat.Datum item) {
            this.item = item;
            int i=0;
            if(!item.participants.get(1).name.equals(SharedPreferenceManager.getInstance(mContext).getUser_Name()))
                i=1;
            name.setText(item.participants.get(i).name);
                Picasso.get()
                        .load(item.participants.get(i).image)
                        .into(img_user);

            image_url=item.participants.get(i).image;
            tv_msg.setText(item.message.message);
            tv_time.setText(item.message.stringCreated);
            if(item.message.status.equals("0"))
                img_status.setVisibility(View.GONE);
        }
        @Override
        public void onClick(View view) {
            Intent i2 = new Intent(mContext, ChatActivity.class);
            i2.putExtra("username", name.getText().toString());
            i2.putExtra("image", image_url);
            i2.putExtra("status", item.message.status);
            i2.putExtra("chat_id", item.id);
            i2.addFlags(FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(i2);
            if (mListener != null) {
                mListener.onItemClick(item);
            }
        }
    }

    @Override
    public RecyclerChatListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item, parent, false);

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
        void onItemClick(Chat.Datum item);
    }
}