package com.yihsian.slider.library;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.io.File;


public class SliderItemView2 extends RelativeLayout {

    //@(#) PUBLIC INTERFACE
    /**
     * Slider View Interface
     */
    public interface SliderViewInterface {
        void onVideoCompletion();
        void onSliderViewClicked(int idx);
    }

    //@(#) STATIC ATTRIBUTES
    private static final String TAG = SliderItemView2.class.getSimpleName();
    public static final int ITEM_LOCAL_IMAGE = 0;
    public static final int ITEM_LOCAL_VIDEO = 1;

    //@(#) PRIVATE ATTRIBUTES
    private TextView title_advertisement1,desc_advertisement1;
    private ImageView image_advertisement1;
    private SliderViewInterface sliderViewInterface;
    private int pageIdx;
    private ScaleType mScaleType = ScaleType.Fit;


    //@(#) PUBLIC ENUM
    public enum ScaleType{
        CenterCrop, CenterInside, Fit
    }

    // @(#) PUBLIC CONSTRUCTORS

    /**
     * SliderItemView Constructor
     * @param context Context
     */
    public SliderItemView2(Context context) {
        super(context);
        init();
    }

    /**
     * SliderItemView Constructor
     * @param context Context
     * @param attrs AttributeSet
     */
    public SliderItemView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * SliderItemView Constructor
     * @param context Context
     * @param attrs AttributeSet
     * @param defStyleAttr DefStyleAttr
     */
    public SliderItemView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.layout_slider_item2, this);
        this.image_advertisement1 = (ImageView) findViewById(R.id.image_advertisement1);
        this.title_advertisement1 = (TextView) findViewById(R.id.title_advertisement1);
        this.desc_advertisement1 =  findViewById(R.id.desc_advertisement1);
    }

    public void setItem2 (String str, String txt, final String desc) {
        title_advertisement1.setText(txt);
        desc_advertisement1.setText(desc);
        System.out.println("SWQQAAAAdddd 22  "+str);

        RequestCreator rq = Picasso.get().load(str);
        switch (mScaleType){
            case Fit:
                rq.fit();
                break;
            case CenterCrop:
                rq.fit().centerCrop();
                break;
            case CenterInside:
                rq.fit().centerInside();
                break;
        }
        rq.into(image_advertisement1);

        Picasso.get()
                .load(str)
                .into(image_advertisement1);
    }


    /**
     * Set Scale Type
     * @param type
     * @return
     */
    public SliderItemView2 setScaleType(ScaleType type) {
        mScaleType = type;
        return this;
    }

    // @(#) PROTECTED METHODS
    /**
     * SET PAGE INDEX
     * @param idx index of SliderItemView
     */
    protected void setPageIdx (int idx) {
        this.pageIdx = idx;


    }

    /**
     * Set Slider View Interface
     * @param viewInterface SliderViewInterface
     */
    protected void setSliderViewInterface(SliderViewInterface viewInterface) {
        this.sliderViewInterface = viewInterface;
    }

}
