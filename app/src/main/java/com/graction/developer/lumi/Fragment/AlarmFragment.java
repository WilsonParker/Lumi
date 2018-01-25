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
import com.graction.developer.lumi.Model.Item.AlarmData;
import com.graction.developer.lumi.Util.Alarm.AlarmManager;
import com.graction.developer.lumi.Util.File.PreferenceManager;
import com.graction.developer.lumi.Util.Log.HLogger;
import com.graction.developer.lumi.databinding.FragmentAlarmBinding;

import java.util.List;

import static com.graction.developer.lumi.DataBase.DataBaseStorage.alarmData;

public class AlarmFragment extends BaseFragment {
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
        binding.fragmentAlarmRV.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void initData() {
//        alarmData = new Gson().fromJson(preferenceManager.getValue(DataStorage.Preference.PREFERENCE_ALARM_DATA, "").toString(), AlarmData.class);
        alarmData = new AlarmData();
        testData();
    }

    private void testData() {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext(), DataBaseStorage.DATABASE_NAME, null, DataBaseStorage.Version.TABLE_ALARM_VERSION);
        List<AlarmTable> tableList = dataBaseHelper.<AlarmTable>selectList("SELECT * FROM "+DataBaseStorage.Table.TABLE_ALARM,AlarmTable.class);
        for(AlarmTable table : tableList){
            DataBaseStorage.alarmData.addItem(new AlarmData.AlarmItem(table));
        }
    }

    public void addAlarm(View view) {
        logger.log(HLogger.LogType.INFO, "addAlarm()", "addAlarm");
        startActivity(new Intent(getContext(), AddAlarmActivity.class));
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
        binding.fragmentAlarmRV.setAdapter(new AlarmListAdapter(alarmData.getItems()));
    }

    @Override
    public void onPause() {
        super.onPause();
        AlarmManager.getInstance().saveAlarm();
        logger.log(HLogger.LogType.INFO, "onPause()", "onPause");
    }
}
