package com.graction.developer.lumi.Service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Graction06 on 2018-01-05.
 */

public class AlarmService extends Service{
    private IBinder binder = new AlarmBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    public class AlarmBinder extends Binder{
        public AlarmService getService(){
            return AlarmService.this;
        }
    }
}
