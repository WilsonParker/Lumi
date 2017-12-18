package com.graction.developer.lumi.Util.Image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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

    public Drawable drawableFromUrl(String url) throws IOException {
        Bitmap x;

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.connect();
        InputStream input = connection.getInputStream();

        x = BitmapFactory.decodeStream(input);
        return new BitmapDrawable(x);
    }
}