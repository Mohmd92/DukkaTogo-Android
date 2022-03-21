package com.yihsian.slider.library;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;


public class SliderItemView extends RelativeLayout {

    //@(#) PUBLIC INTERFACE
    /**
     * Slider View Interface
     */
    public interface SliderViewInterface {
        void onVideoCompletion();
        void onSliderViewClicked(int idx);
    }

    //@(#) STATIC ATTRIBUTES
    private static final String TAG = SliderItemView.class.getSimpleName();
    public static final int ITEM_LOCAL_IMAGE = 0;
    public static final int ITEM_LOCAL_VIDEO = 1;

    //@(#) PRIVATE ATTRIBUTES
    private TextView txt_title;
    private ImageView imageView;
    private LinearLayout imageEffectView;
    private String imagepath,videoPath,video_url;
    private SliderViewInterface sliderViewInterface;
    private File file;
    private int fileType;
    private int pageIdx;
    private ScaleType mScaleType = ScaleType.Fit;
    private LinearLayout lin_next;
    private RelativeLayout relll;

    //@(#) PUBLIC ENUM
    public enum ScaleType{
        CenterCrop, CenterInside, Fit
    }

    // @(#) PUBLIC CONSTRUCTORS

    /**
     * SliderItemView Constructor
     * @param context Context
     */
    public SliderItemView(Context context) {
        super(context);
        init();
    }

    /**
     * SliderItemView Constructor
     * @param context Context
     * @param attrs AttributeSet
     */
    public SliderItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * SliderItemView Constructor
     * @param context Context
     * @param attrs AttributeSet
     * @param defStyleAttr DefStyleAttr
     */
    public SliderItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.layout_slider_item, this);
        this.imageView = (ImageView) findViewById(R.id.itemImage);
        this.txt_title = (TextView) findViewById(R.id.txt_title);
        this.lin_next =  findViewById(R.id.lin_next);
        this.relll =  findViewById(R.id.relll);
        this.imageEffectView = (LinearLayout) findViewById(R.id.imageEffectView);
    }

    public void setItem2 (String str, String txt, final String url) {
        txt_title.setText(txt);
        imageView.setVisibility(View.VISIBLE);
        relll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("ssssddcccccccccccccccc "+url);
                String urls=url;
              if(!TextUtils.isEmpty(url)){
                  if (!url.startsWith("http://") && !url.startsWith("https://")) {
                       urls = "http://" + url;
                  }
                      Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urls));
                      getContext().startActivity(browserIntent);
              }
            }
        });

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
        rq.into(imageView);
    }

    /**
     * Set Scale Type
     * @param type
     * @return
     */
    public SliderItemView setScaleType(ScaleType type) {
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

        imageEffectView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                sliderViewInterface.onSliderViewClicked(pageIdx);
            }
        });
    }

    /**
     * Set Slider View Interface
     * @param viewInterface SliderViewInterface
     */
    protected void setSliderViewInterface(SliderViewInterface viewInterface) {
        this.sliderViewInterface = viewInterface;
    }

}
