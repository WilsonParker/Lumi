package com.graction.developer.lumi.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.graction.developer.lumi.Activity.AddAlarmActivity;
import com.graction.developer.lumi.Adapter.AlarmListAdapter;
import com.graction.developer.lumi.Data.DataStorage;
import com.graction.developer.lumi.Model.Item.AlarmData;
import com.graction.developer.lumi.Util.File.PreferenceManager;
import com.graction.developer.lumi.Util.Log.HLogger;
import com.graction.developer.lumi.databinding.FragmentAlarmBinding;

public class AlarmFragment extends BaseFragment {
    private AlarmData alarmData;
    private PreferenceManager preferenceManager = new PreferenceManager(PreferenceManager.PREFERENCE_ALARM);
    private static final AlarmFragment instance = new AlarmFragment();
    private FragmentAlarmBinding binding;

    public static Fragment getInstance() {
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAlarmBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    protected void init(View view) {
        binding.setActivity(this);

//        testData();
        initData();
        binding.fragmentAlarmRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.fragmentAlarmRV.setAdapter(new AlarmListAdapter(alarmData.getItems()));
    }

    private void initData() {
        alarmData = new Gson().fromJson(preferenceManager.getValue(DataStorage.Preference.PREFERENCE_ALARM_DATA, "").toString() , AlarmData.class);
    }

    private void testData() {
        alarmData = new AlarmData();
        for (int i = 0; i < 10; i++) {
            int[] days = new int[7];
            days[i>6?i-(i/7*6):i] = 1;
            if(i%2 == 0)
                alarmData.getItems().add(alarmData.new AlarmItem("Address " + i, "Memo " + i, days, i, i));
            else
                alarmData.getItems().add(alarmData.new AlarmItem("Address " + i, "Memo " + i, days, i, i, 7/i));
        }
        preferenceManager.setValue(DataStorage.Preference.PREFERENCE_ALARM_DATA, new Gson().toJson(alarmData));
    }

    public void addAlarm(View view) {
        logger.log(HLogger.LogType.INFO, "addAlarm()", "addAlarm");
        startActivity(new Intent(getContext(), AddAlarmActivity.class));
    }
}
