package com.graction.developer.lumi.Activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.databinding.DataBindingUtil;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.WindowManager;
import android.widget.TimePicker;

import com.graction.developer.lumi.R;
import com.graction.developer.lumi.Util.Log.HLogger;
import com.graction.developer.lumi.databinding.ActivityAlarmBinding;

public class AlarmActivity extends BaseActivity {
    private ActivityAlarmBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_alarm);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_alarm);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
    }

    protected void init() {
        logger.log(HLogger.LogType.INFO, "AlarmReceiver", "AlarmActivity init"); // com.graction.developer.lumi.ALARM_START
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

        binding.activityAlarmTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                logger.log(HLogger.LogType.INFO, "AlarmReceiver", "%d :%d", hourOfDay, minute);
            }
        });
        noti();
    }

    private void noti() {
        logger.log(HLogger.LogType.INFO, "AlarmReceiver", "AlarmActivity noti");

       /* Notification notification = new Notification.Builder(getBaseContext())
                // Show controls on lock screen even when user hides sensitive content.
//                .setVisibility(Notification.VISIBILITY_PUBLIC)
                .setSmallIcon(R.drawable.ic_stat_player)
                // Add media control buttons that invoke intents in your media service
                .addAction(R.drawable.ic_prev, "Previous", prevPendingIntent) // #0
                // Apply the media style template
                .setstyle(new Notification.MediaStyle()
                        .setShowActionsInCompactView(1 *//* #1: pause button *//*)
                        .setMediaSession(mMediaSession.getSessionToken())
                        .setContentTitle("Wonderful music")
                        .setContentText("My Awesome Band")
                        .setLargeIcon(albumArtBitmap)
                        .build();*/


        Notification notification =
                new NotificationCompat.Builder(this, "Alarm ID")
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .setContentTitle("Alarm Title")
                        .setContentText("text")
                        .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);

    }

}
