package com.graction.developer.lumi.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.graction.developer.lumi.Service.AlarmService;

/**
 * Created by Graction06 on 2018-01-05.
 */

public class AlarmReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("AlarmReceiver","intent.getAction() : "+intent.getAction()); // com.graction.developer.lumi.ALARM_START
        Log.i("AlarmReceiver","Ring Ring Ring");
        intent.setClass(context, AlarmService.class);
        context.startService(intent);
    }
}
