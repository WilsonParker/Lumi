package com.graction.developer.lumi.Util.Image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.widget.ImageView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.io.File;

/**
 * Created by Hare on 2017-07-19.
 */

/*
    compile 'com.squareup.picasso:picasso:2.5.2'
 */
public class ImageManager {
    private static ImageManager imageManager = new ImageManager();
//    public static final int BASIC_TYPE = 0x0000, FIT_TYPE = 0x0001, PICTURE_TYPE = 0x0010, THUMBNAIL_TYPE = 0x0011, ICON_TYPE = 0x0100;
    public enum TYPE {
        BASIC_TYPE, FIT_TYPE, PICTURE_TYPE, THUMBNAIL_TYPE, ICON_TYPE
    }

    public static ImageManager getInstance() {
        return imageManager;
    }

    public void loadImage(Context context, String downloadURI, ImageView imageView, TYPE type) {
        createRequestCreator(context, downloadURI, type).into(imageView);
    }

    public void loadImage(Context context, int id, ImageView imageView, TYPE type) {
        createRequestCreator(context, id, type).into(imageView);
    }

    public void loadImage(Context context, File file, ImageView imageVIew, TYPE type) {
        createRequestCreator(context, file, type).into(imageVIew);
    }

    public void loadImage(RequestCreator requestCreator, ImageView imageView) {
        requestCreator.into(imageView);
    }

    public RequestCreator createRequestCreator(Context context, int id, TYPE type) {
        return requestCreatorSetCase(Picasso.with(context).load(id), type);
    }

    public RequestCreator createRequestCreator(Context context, String url, TYPE type) {
        return requestCreatorSetCase(Picasso.with(context).load(url), type);
    }

    public RequestCreator createRequestCreator(Context context, File file, TYPE type) {
        return requestCreatorSetCase(Picasso.with(context).load(file), type);
    }

    public RequestCreator createRequestCreator(Context context, Uri uri, TYPE type) {
        return requestCreatorSetCase(Picasso.with(context).load(uri), type);
    }

    private RequestCreator basicSetting(RequestCreator requestCreator) {
        return requestCreator
//                .error(R.drawable.noimage)
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE);
    }

    private RequestCreator requestCreatorSetCase(RequestCreator requestCreator, TYPE type) {
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
    }

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
}