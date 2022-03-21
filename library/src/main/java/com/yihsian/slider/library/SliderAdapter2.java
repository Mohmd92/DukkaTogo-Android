package com.yihsian.slider.library;


import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lee on 2018/2/27.
 */

public class SliderAdapter2 extends PagerAdapter {

    private List<SliderItemView2> itemViewList;

    public SliderAdapter2() {
        this.itemViewList = new ArrayList<>();
    }

    public SliderAdapter2(List<SliderItemView2> list) {
        this.itemViewList = list;
    }

    public int addView (SliderItemView2 view) {
        return addView (view, itemViewList.size());
    }

    public int addView (SliderItemView2 view, int position) {
        itemViewList.add (position, view);
        return position;
    }

    public void removeAllSliders() {
        itemViewList.clear();
        notifyDataSetChanged();
    }


    public View getView(int position) {
        return itemViewList.get(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        SliderItemView2 itemView = itemViewList.get(position);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public int getCount() {
        return itemViewList.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)   {
        container.removeView(itemViewList.get(position % itemViewList.size()));
    }

}
