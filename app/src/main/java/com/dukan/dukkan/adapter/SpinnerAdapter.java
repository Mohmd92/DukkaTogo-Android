package com.dukan.dukkan.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dukan.dukkan.R;
import com.dukan.dukkan.util.Utils;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;
import com.squareup.picasso.Picasso;

public class SpinnerAdapter extends ArrayAdapter<String> {

    private Context ctx;
    private String[] contentArray;
    private String[] imageArray;

    public SpinnerAdapter(Context context, int resource, String[] objects,
                          String[] imageArray) {
        super(context,  R.layout.country_item, R.id.spinnerTextView, objects);
        this.ctx = context;
        this.contentArray = objects;
        this.imageArray = imageArray;
    }

    @Override
    public View getDropDownView(int position, View convertView,ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.country_item, parent, false);

        TextView textView = (TextView) row.findViewById(R.id.spinnerTextView);
        textView.setText(contentArray[position]);
        ImageView imageView = (ImageView)row.findViewById(R.id.spinnerImages);
        if(!imageArray[position].equals("null")) {
            if (!imageArray[position].equals("city"))
                GlideToVectorYou.init().with(getContext()).load(Uri.parse(imageArray[position]), imageView);
            else
                imageView.setVisibility(View.GONE);
        }
        return row;
    }
}