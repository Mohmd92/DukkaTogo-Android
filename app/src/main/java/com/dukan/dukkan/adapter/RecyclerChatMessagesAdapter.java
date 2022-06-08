package com.dukan.dukkan.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dukan.dukkan.R;
import com.dukan.dukkan.activity.ChatActivity;
import com.dukan.dukkan.pojo.Chat;
import com.dukan.dukkan.pojo.ChatMessage;
import com.dukan.dukkan.util.SharedPreferenceManager;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class RecyclerChatMessagesAdapter extends RecyclerView.Adapter<RecyclerChatMessagesAdapter.ViewHolder> {
    List<ChatMessage.Datum> mValues;
    Context mContext;
    protected ItemListener mListener;
    private AdapterView.OnItemClickListener listener;
    public RecyclerChatMessagesAdapter(Context context, List<ChatMessage.Datum> values) {

        mValues = values;
        mContext = context;
//        mListener=itemListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ChatMessage.Datum item;

        RelativeLayout layout1_user,layout1;
        ImageView imageFriend,seen_image_user;
        TextView textContentUser_user,textContentFriend,tv_time_user,tv_time;

        public RelativeLayout relative;
        public ViewHolder(View v) {
            super(v);

            v.setOnClickListener(this);

            layout1_user = v.findViewById(R.id.layout1_user);
            textContentUser_user = v.findViewById(R.id.textContentUser_user);
            tv_time_user = v.findViewById(R.id.tv_time_user);
            seen_image_user = v.findViewById(R.id.seen_image_user);


            layout1 = v.findViewById(R.id.layout1);
            imageFriend = v.findViewById(R.id.imageView3);
            textContentFriend = v.findViewById(R.id.textContentFriend);
            tv_time = v.findViewById(R.id.tv_time);



        }
        public void setData(ChatMessage.Datum item) {
            this.item = item;
            if(item.user.name.equals(SharedPreferenceManager.getInstance(mContext).getUser_Name())) {
                layout1_user.setVisibility(View.VISIBLE);
                layout1.setVisibility(View.GONE);
                textContentUser_user.setText(item.message);
                tv_time_user.setText(item.stringCreated);
                if(item.status.equals("0"))
                    seen_image_user.setVisibility(View.VISIBLE);
            }else {
                layout1_user.setVisibility(View.GONE);
                layout1.setVisibility(View.VISIBLE);

                textContentFriend.setText(item.message);
                tv_time.setText(item.stringCreated);
                Picasso.get()
                        .load(item.user.image)
                        .into(imageFriend);
            }
        }
        @Override
        public void onClick(View view) {
//            Intent i2 = new Intent(mContext, ChatActivity.class);
//            i2.putExtra("username", name.getText().toString());
//            i2.putExtra("image", image_url);
//            i2.putExtra("status", item.message.status);
//            i2.putExtra("chat_id", item.id);
//            i2.addFlags(FLAG_ACTIVITY_NEW_TASK);
//            mContext.startActivity(i2);
            if (mListener != null) {
                mListener.onItemClick(item);
            }
        }
    }

    @Override
    public RecyclerChatMessagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.rc_item_message_friend, parent, false);

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
        void onItemClick(ChatMessage.Datum item);
    }
}