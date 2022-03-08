package com.yihsian.slider.library;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
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
    private VideoView videoView;
    private LinearLayout imageEffectView;
    private String imagepath,videoPath,video_url;
    private SliderViewInterface sliderViewInterface;
    private File file;
    private int fileType;
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
        this.videoView = (VideoView)findViewById(R.id.itemVideo);
        this.imageEffectView = (LinearLayout) findViewById(R.id.imageEffectView);
    }

    public void setItem2 (String str,String txt) {
        txt_title.setText(txt);
        imageView.setVisibility(View.VISIBLE);
        videoView.setVisibility(View.INVISIBLE);

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
    public void setItem3 (String str,String txt) {
        txt_title.setText(txt);
        imageView.setVisibility(View.VISIBLE);
        videoView.setVisibility(View.INVISIBLE);
        txt_title.setVisibility(View.GONE);

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
    public void setItem (File file, int type) {
        this.file = file;
        this.fileType = type;

        switch (type) {
            case ITEM_LOCAL_IMAGE:
                imageView.setVisibility(View.VISIBLE);
                videoView.setVisibility(View.INVISIBLE);

                if(file.exists()){
                    RequestCreator rq = Picasso.get().load(file);
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
                break;
            case ITEM_LOCAL_VIDEO:
                imageView.setVisibility(View.INVISIBLE);
                videoView.setVisibility(View.VISIBLE);
                videoPath = file.getAbsolutePath();
                videoView.setVideoURI(Uri.parse(videoPath));
                break;
        }
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
