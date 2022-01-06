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
import android.widget.VideoView;

import androidx.annotation.NonNull;

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


/**
 * Created by Lee on 2018/2/27.
 */

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
        this.videoView = (VideoView)findViewById(R.id.itemVideo);
        this.imageEffectView = (LinearLayout) findViewById(R.id.imageEffectView);
    }

    // @(#) PUBLIC METHODS

    /**
     * Set Slider Item
     * @param filePath File path
     * @param type File type (image or video)
     */
    public void setItem(String filePath, int type) {
        setItemNew(filePath, type);
    }

    /**
     * Set Slider Item
     * @param type File type (image or video)
     */
    public void setItemNew (String str, int type) {
        getDetails(str,type);
        this.fileType = type;

    }
    public void setItemNew2 (String str, int type) {
        getDetails2(str,type);
        this.fileType = type;

    }
    public void setItemNew3 (String str, int type) {
        getDetails3(str,type);
        this.fileType = type;

    }
    public void setItemNew4 (String str) {
        setItem2(str);

    }
    public void setItem2 (String str) {

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

    /**
     * play video item
     */
    protected void playVideo() {
        Log.i(TAG, "playVideo:" + videoPath);
        if (videoView.getVisibility() == View.VISIBLE) {
            videoView.requestFocus();
//            videoView.setVideoURI(Uri.parse(videoPath));
            videoView.start();

            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
//                    try{
                    if (mediaPlayer != null) {
                        mediaPlayer.reset();
                        Uri uri = Uri.parse(video_url);
                        videoView.setVideoURI(uri);
                        videoView.setVideoPath(video_url);
//                        videoView.setVideoURI(Uri.parse(videoPath));
                        sliderViewInterface.onVideoCompletion();
                        Log.i(TAG, "Video Complete");
                    }
//                    }catch (NullPointerException e) {
//                    }
                }
            });

        }
    }

    /**
     * stop video item
     */
    protected void stopVideo() {
        if (videoPath != null) {
            Log.i(TAG, "stopVideo:" + videoPath);
            videoView.pause();
            videoView.stopPlayback();
            videoView.setVideoURI(Uri.parse(videoPath));
        }
    }

    /**
     * get fileType
     */
    protected int getFileType() {
        return this.fileType;
    }

//    public void onDestroy() {
//        Picasso.with(getContext()).load(null).into(imageView);
//    }
private void getDetails(String id,final int type){
    Firebase ref = new Firebase("https://plantdiseases-64256.firebaseio.com/ads/"+id);
    ref.addListenerForSingleValueEvent(
            new ValueEventListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (snapshot.getValue() != null) {

                        try {
                            switch (type) {
                                case ITEM_LOCAL_IMAGE:
                                    imageView.setVisibility(View.VISIBLE);
                                    videoView.setVisibility(View.INVISIBLE);
                                    imagepath = snapshot.child("image").getValue().toString();
                                    RequestCreator rq = Picasso.get().load(imagepath);
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
                                    break;
                                case ITEM_LOCAL_VIDEO:
                                    video_url = snapshot.child("video").getValue().toString();
                                    imageView.setVisibility(View.INVISIBLE);
                                    videoView.setVisibility(View.VISIBLE);
                                    // videoPath = file.getAbsolutePath();
//                                    videoView.setVideoURI(Uri.parse(videoPath));




                                    String url = video_url;
//                                    videoView.setVideoURI(Uri.parse(video_url));
//                                    videoView.setVideoPath(video_url);
//                                    videoView.requestFocus();
                                    StorageReference ref = FirebaseStorage.getInstance().getReferenceFromUrl(url);

                                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            videoView.setVideoURI(Uri.parse(video_url));
                                            videoView.setVideoPath(video_url);
                                            videoView.requestFocus();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                            if (exception instanceof StorageException &&
                                                    ((StorageException) exception).getErrorCode() == StorageException.ERROR_OBJECT_NOT_FOUND) {
                                                Log.d("XXXXXXXXX*1 ", "File not exist");
                                            }
                                        }
                                    });
                                    break;
                            }

//                            if(type==1) {
////                                video_url = snapshot.child("video").getValue().toString();
////                                Uri uri = Uri.parse(video_url);
////                                videoView.setVideoURI(uri);
////                                videoView.setVideoPath(video_url);
////                                videoView.requestFocus();
//                            }else{
////                                imagepath = snapshot.child("image").getValue().toString();
//                            }
                        } catch (Exception e) {
                            // TODO: handle exception
                        }
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }

            });
}
    private void getDetails3(String id,final int type){
                            try {
                                switch (type) {
                                    case ITEM_LOCAL_IMAGE:
                                        imageView.setVisibility(View.VISIBLE);
                                        videoView.setVisibility(View.INVISIBLE);
                                        RequestCreator rq = Picasso.get().load(id);
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
                                        break;
                                    case ITEM_LOCAL_VIDEO:
                                        imageView.setVisibility(View.INVISIBLE);
                                        videoView.setVisibility(View.VISIBLE);
                                        Uri uri = Uri.parse(id);
                                        videoView.setVideoURI(uri);
                                        videoView.setVideoPath(video_url);
                                        videoView.requestFocus();
                                        break;
                                }

                            } catch (Exception e) {
                                // TODO: handle exception
                            }

    }
    private void getDetails2(String id,final int type){

                            try {
                                switch (type) {
                                    case ITEM_LOCAL_IMAGE:
                                        imageView.setVisibility(View.VISIBLE);
                                        videoView.setVisibility(View.INVISIBLE);
                                        imagepath = id;
                                        Uri uri = Uri.parse(imagepath);
//                                        RequestCreator rq =
//                                                Picasso.get().load(uri)
//                                                .into(imageView);;


                                        Picasso.get()
                                                .load(imagepath)
                                                .into(imageView, new Callback() {
                                                    @Override
                                                    public void onSuccess() {
                                                        System.out.println("deeeeeeeeeeeeeet onSuccess "+ imagepath);
                                                    }

                                                    @Override
                                                    public void onError(Exception e) {
                                                        System.out.println("deeeeeeeeeeeeeet onError "+ imagepath);
                                                    }
                                                });
//                                        switch (mScaleType){
//                                            case Fit:
//                                                rq.fit();
//                                                break;
//                                            case CenterCrop:
//                                                rq.fit().centerCrop();
//                                                break;
//                                            case CenterInside:
//                                                rq.fit().centerInside();
//                                                break;
//                                        }
//                                        rq.into(imageView);
                                        break;
                                    case 4:
                                        imageView.setVisibility(View.VISIBLE);
                                        videoView.setVisibility(View.INVISIBLE);


                                        String filePath=Environment.getExternalStorageDirectory()+"/test.png";
                                        File imgFile = new  File(filePath);
                                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                                        imageView.setImageBitmap(RotateBitmap(myBitmap,90));
//                                        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//
//                                        String filename = "test.png";
//                                        File file = new File(Environment.getExternalStorageDirectory(), filename); //or any other format supported
//                                        RequestCreator rq2 = Picasso.get().load(file);
//                                        imageView.setVisibility(View.INVISIBLE);
//                                        videoView.setVisibility(View.VISIBLE);
//                                        switch (mScaleType){
//                                            case Fit:
//                                                rq2.fit();
//                                                break;
//                                            case CenterCrop:
//                                                rq2.fit().centerCrop();
//                                                break;
//                                            case CenterInside:
//                                                rq2.fit().centerInside();
//                                                break;
//                                        }
//                                        rq2.into(imageView);
                                        break;
                                }
                            } catch (Exception e) {
                                // TODO: handle exception
                            }
                        }
    public static Bitmap RotateBitmap(Bitmap source, float angle)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }
}
