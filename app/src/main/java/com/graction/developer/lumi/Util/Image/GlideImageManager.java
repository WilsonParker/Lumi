package com.graction.developer.lumi.Util.Image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.graction.developer.lumi.Net.Net;
import com.graction.developer.lumi.Util.File.BaseActivityFileManager;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by Hare on 2017-07-19.
 */

/*
    compile 'com.squareup.picasso:picasso:2.5.2'
 */
public class GlideImageManager {
    private static GlideImageManager imageManager = new GlideImageManager();
//    public static final int BASIC_TYPE = 0x0000, FIT_TYPE = 0x0001, PICTURE_TYPE = 0x0010, THUMBNAIL_TYPE = 0x0011, ICON_TYPE = 0x0100;
    public enum Type {
        BASIC_TYPE, FIT_TYPE, PICTURE_TYPE, THUMBNAIL_TYPE, ICON_TYPE
    }

    public static GlideImageManager getInstance() {
        return imageManager;
    }

    private void init(Context context, ImageView img){
        Glide.with(context).load("").into(img);
    }

   /* private RequestCreator basicSetting(RequestCreator requestCreator) {
        GlideApp.with
        return requestCreator
//                .error(R.drawable.noimage)
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE);
    }

    private RequestCreator requestCreatorSetCase(RequestCreator requestCreator, Type type) {
        requestCreator = basicSetting(requestCreator);
        switch (type) {
            case BASIC_TYPE:
                break;
            case FIT_TYPE:
                requestCreator.fit();
                break;
            case PICTURE_TYPE:
                requestCreator.resize(1280, 720);
                break;
            case THUMBNAIL_TYPE:
                requestCreator.resize(640, 360);
                break;
            case ICON_TYPE:
                requestCreator.resize(320, 180);
                break;
        }
        return requestCreator;
    }*/

    public Bitmap resizeImage(Bitmap bitmap, int newSize){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        int newWidth = 0;
        int newHeight = 0;

        if(width > height){
            newWidth = newSize;
            newHeight = (newSize * height)/width;
        } else if(width < height){
            newHeight = newSize;
            newWidth = (newSize * width)/height;
        } else if (width == height){
            newHeight = newSize;
            newWidth = newSize;
        }

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                width, height, matrix, true);

        return resizedBitmap;
    }


    public void bindImage(Context context, ImageView imageView, RequestOptions requestOptions, String path, String name, String url) throws FileNotFoundException {
        BaseActivityFileManager baseActivityFileManager = BaseActivityFileManager.getInstance();
        String file = path + name;
        if(baseActivityFileManager.isExists(file)){
            Glide.with(context).load(new File(file)).apply(new RequestOptions().centerCrop()).into(imageView);
        }else{
            baseActivityFileManager.makeDirectory(path);
            Glide.with(context).load(Net.BASE_URL+url).apply(new RequestOptions().centerCrop()).into(imageView);
            baseActivityFileManager.saveDrawable(imageView.getDrawable(), path+name);
        }

    }
}


/* private void initGlideGifImageView() {

        showProgressDialog("Loading image...");
        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(imgSafetyGif, 1);
        Glide
                .with(this)
                .load(GIF_SOURCE_URL)
                .placeholder(R.drawable.img_placeholder_1)
                .error(R.drawable.img_error_1_280_text)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<String, GlideDrawable>() {

                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        hideProgressDialog();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        hideProgressDialog();

                        GifDrawable gifDrawable = null;
                        Handler handler = new Handler();
                        if (resource instanceof GifDrawable) {
                            gifDrawable = (GifDrawable) resource;

                            int duration = 0;
                            GifDecoder decoder = gifDrawable.getDecoder();
                            for (int i = 0; i < gifDrawable.getFrameCount(); i++) {
                                duration += decoder.getDelay(i);
                            }

                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    SplashScreenActivity.this.finish();
                                }
                            }, (duration + 3000));

                        }

                        return false;
                    }

                })
                .into(imageViewTarget);
    }*/