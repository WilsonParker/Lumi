package com.graction.developer.lumi.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.graction.developer.lumi.Activity.AddAlarmActivity;
import com.graction.developer.lumi.Adapter.AlarmListAdapter;
import com.graction.developer.lumi.Model.Item.AlarmItem;
import com.graction.developer.lumi.Util.Log.HLogger;
import com.graction.developer.lumi.databinding.FragmentAlarmBinding;

import java.util.ArrayList;

public class AlarmFragment extends BaseFragment {
    public static ArrayList<AlarmItem> alarmItems;
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

        testData();
        binding.fragmentAlarmRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.fragmentAlarmRV.setAdapter(new AlarmListAdapter(alarmItems));
    }

    private void testData() {
        alarmItems = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int[] days = new int[7];
            days[i>6?i-(i/7*6):i] = 1;
            alarmItems.add(new AlarmItem("Address " + i, "Memo " + i, days, i, i));
            logger.log(HLogger.LogType.INFO,"testData()","add "+i);
        }
    }

    public void addAlarm(View view) {
        logger.log(HLogger.LogType.INFO, "addAlarm()", "addAlarm");
        startActivity(new Intent(getContext(), AddAlarmActivity.class));
    }
}
