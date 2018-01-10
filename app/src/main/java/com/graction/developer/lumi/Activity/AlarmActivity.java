package com.graction.developer.lumi.Activity;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.graction.developer.lumi.R;
import com.graction.developer.lumi.Util.Log.HLogger;

public class AlarmActivity extends AppCompatActivity {
    private HLogger logger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        init();
    }

    private void init() {
        logger = new HLogger(getClass());
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
//            mediaPlayer = MediaPlayer.create(this, R.raw.kt);
//            AssetFileDescriptor assetFileDescriptor = getResources().openRawResourceFd(R.raw.kt);
            AssetFileDescriptor assetFileDescriptor = getAssets().openFd("kt.mp3");

            mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
            assetFileDescriptor.close();

            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
