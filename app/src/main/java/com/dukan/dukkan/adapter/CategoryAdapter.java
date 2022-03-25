package com.dukan.dukkan.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.dukan.dukkan.R;
import com.dukan.dukkan.pojo.Category;
import com.dukan.dukkan.pojo.CategoryProduct;
import com.dukan.dukkan.pojo.CategoryProduct;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflter;
    private List<CategoryProduct> mValues;
    private int chosenOne = -1;

    public CategoryAdapter(
            Context applicationContext,List<CategoryProduct> values
            ) {
        this.context = applicationContext;
        this.mValues = values;

        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getViewTypeCount() {
        return 1; // getCount();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return mValues.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint({"ViewHolder", "SetTextI18n"})
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (inflter == null)
            inflter = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = inflter.inflate(R.layout.categoty_item, null);
            viewHolder = new ViewHolder();
            viewHolder.category_name = (TextView) view.findViewById(R.id.category_name);
            viewHolder.relative = (RelativeLayout) view.findViewById(R.id.relative);

            view.setTag(viewHolder);


//            if(i==0){
//                viewHolder.relative.setBackground(ContextCompat.getDrawable(context,R.drawable.button55));
//                viewHolder.category_name.setTextColor(Color.WHITE);
//            }
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.category_name.setText("" + mValues.get(i).name);

        return view;
    }
    private class ViewHolder {
        TextView category_name;
        RelativeLayout relative;
    }


}
