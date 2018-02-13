package com.graction.developer.zoocaster.image;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.graction.developer.zoocaster.Util.File.BaseActivityFileManager;
import com.graction.developer.zoocaster.Util.Log.HLogger;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by Graction06 on 2018-01-08.
 */

@RunWith(AndroidJUnit4.class)
public class ImageFileTest {
    private static final String TAG = "DataTest";

    /**
     * Instrumentation currentWeather, which will execute on an Android device.
     *
     * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
     */

    @Test
    public void useAppContext() {
        // Context of the app under currentWeather.
        Context appContext = InstrumentationRegistry.getTargetContext();
        final String TAG = getClass().getCanonicalName();
        HLogger logger = new HLogger(getClass());
        BaseActivityFileManager bafm = BaseActivityFileManager.getInstance();

        try {
            logger.log(HLogger.LogType.DEBUG, " " + Environment.getExternalStorageDirectory());
            logger.log(HLogger.LogType.DEBUG, " " + Environment.getExternalStorageDirectory().getAbsolutePath());
            logger.log(HLogger.LogType.DEBUG, " " + appContext.getFilesDir());
            logger.log(HLogger.LogType.DEBUG, " " + appContext.getDataDir());
            logger.log(HLogger.LogType.DEBUG, " " + appContext.getCacheDir());
            logger.log(HLogger.LogType.DEBUG, " " + appContext.getCacheDir());

            String fName = "sunny.jpg"
                    , url = "http://192.168.0.8:8101/zoocasterAssets/assets/images/background/sunny.jpg";
            Bitmap b = bafm.getBitmapFromURL(url);
            byte[] bytes = bafm.getByteFromURL(url);
            FileOutputStream fos = appContext.openFileOutput(fName, Context.MODE_PRIVATE);
            fos.write(bytes);
            FileInputStream fis = appContext.openFileInput(fName);
            Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fis.getFD());

            fos.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
