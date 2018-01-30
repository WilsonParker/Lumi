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
import com.graction.developer.lumi.DataBase.DataBaseHelper;
import com.graction.developer.lumi.DataBase.DataBaseStorage;
import com.graction.developer.lumi.Model.DataBase.AlarmTable;
import com.graction.developer.lumi.Model.Item.AlarmItem;
import com.graction.developer.lumi.Util.Alarm.AlarmManager;
import com.graction.developer.lumi.Util.Log.HLogger;
import com.graction.developer.lumi.databinding.FragmentAlarmBinding;

import java.util.ArrayList;
import java.util.List;

import static com.graction.developer.lumi.DataBase.DataBaseStorage.DATABASE_NAME;

public class AlarmFragment extends BaseFragment {
    private static final AlarmFragment instance = new AlarmFragment();
    private AlarmListAdapter alarmListAdapter;
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
        binding.fragmentAlarmRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        initData();
    }

    private void initData() {
        if(DataBaseStorage.alarmDataBaseHelper == null)
            DataBaseStorage.alarmDataBaseHelper = new DataBaseHelper(getContext(), DATABASE_NAME, null, DataBaseStorage.Version.TABLE_ALARM_VERSION);
        List<AlarmTable> tableList = DataBaseStorage.alarmDataBaseHelper.selectList("SELECT * FROM " + DataBaseStorage.Table.TABLE_ALARM, AlarmTable.class);
        ArrayList<AlarmItem> alarmList = new ArrayList<>();
        for (AlarmTable table : tableList) {
            AlarmItem item = new AlarmItem(table);
            AlarmManager.getInstance().setAlarm(getContext(), item);
            alarmList.add(item);
        }
        DataBaseStorage.alarmList = alarmList;
        alarmListAdapter = new AlarmListAdapter(alarmList);
        binding.fragmentAlarmRV.setAdapter(alarmListAdapter);
    }

    public void addAlarm(View view) {
        logger.log(HLogger.LogType.INFO, "addAlarm()", "addAlarm");
        startActivity(new Intent(getContext(), AddAlarmActivity.class));
    }

    @Override
    public void onResume() {
        super.onResume();
        logger.log(HLogger.LogType.INFO, "onResume()", "onResume");
        alarmListAdapter.notifyDataSetChanged();
    }

}
