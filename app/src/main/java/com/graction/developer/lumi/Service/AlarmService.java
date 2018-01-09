package com.graction.developer.lumi.Service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Graction06 on 2018-01-05.
 */

public class AlarmService extends Service{
    private final IBinder binder = new AlarmBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("AlarmService","Ring Ring Ring");
        Toast.makeText(this, "Ring Ring Ring", Toast.LENGTH_LONG).show();
        return START_NOT_STICKY;
    }

    public class AlarmBinder extends Binder{
        public AlarmService getService(){
            return AlarmService.this;
        }
    }
}
