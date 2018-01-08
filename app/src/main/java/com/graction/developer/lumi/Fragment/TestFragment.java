package com.graction.developer.lumi.Fragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.graction.developer.lumi.R;
import com.graction.developer.lumi.Service.AlarmService;
import com.graction.developer.lumi.Util.Log.HLogger;
import com.graction.developer.lumi.databinding.FragmentTestBinding;

import java.util.Calendar;
import java.util.Date;

public class TestFragment extends BaseFragment {
    private FragmentTestBinding binding;
    private AlarmService alarmService;
    private boolean mBound = false; // 서비스 연결 여부

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            AlarmService.AlarmBinder alarmBinder = (AlarmService.AlarmBinder) service;
            alarmService = alarmBinder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
        }
    };

    public static Fragment getInstance() {
        Fragment fragment = new TestFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_test, null, false);
        return binding.getRoot();
    }

    @Override
    protected void init(View view) {
        logger.log(HLogger.LogType.INFO, "init");
        binding.setActivity(this);
        initService();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    // http://ccdev.tistory.com/28 -> 작성 중
    // c
    // http://developer88.tistory.com/83
    // http://developer88.tistory.com/36
    private void initService() {
        Intent serviceIntent = new Intent(getActivity(), AlarmService.class);
        getActivity().bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    // onClick
    public void alarmTest(View view){

        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(binding.fragmentTestETHour.getText()+""));
        mCalendar.set(Calendar.MINUTE, Integer.parseInt(binding.fragmentTestETMinute.getText()+""));
        mCalendar.set(Calendar.SECOND, Integer.parseInt(binding.fragmentTestETSecond.getText()+""));

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getContext(),
                0,
                new Intent(""),
                PendingIntent.FLAG_ONE_SHOT
        );

        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(
                AlarmManager.RTC_WAKEUP,
                mCalendar.getTimeInMillis(),
                pendingIntent
        );

    }

}
