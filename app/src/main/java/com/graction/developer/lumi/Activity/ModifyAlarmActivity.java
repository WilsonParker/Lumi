package com.graction.developer.lumi.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TimePicker;

import com.graction.developer.lumi.Data.DataStorage;
import com.graction.developer.lumi.DataBase.DataBaseParserManager;
import com.graction.developer.lumi.DataBase.DataBaseStorage;
import com.graction.developer.lumi.Model.Address.PostcodifyModel;
import com.graction.developer.lumi.Model.DataBase.AlarmTable;
import com.graction.developer.lumi.Model.Item.AlarmItem;
import com.graction.developer.lumi.R;
import com.graction.developer.lumi.UI.Layout.CustomArrayView;
import com.graction.developer.lumi.Util.Alarm.AlarmManager;
import com.graction.developer.lumi.Util.Date.DateManager;
import com.graction.developer.lumi.Util.Log.HLogger;
import com.graction.developer.lumi.Util.NullChecker;
import com.graction.developer.lumi.Util.StringUtil;
import com.graction.developer.lumi.databinding.ActivityModifyAlarmBinding;

import java.util.ArrayList;

public class ModifyAlarmActivity extends BaseActivity {
    private ActivityModifyAlarmBinding binding;
    private AlarmItem item;
    private int hourOfDay, minute, index;
    private int[] selectedWeek = new int[8];
    private boolean isSpeaker = true;
    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        item = (AlarmItem) getIntent().getSerializableExtra(DataStorage.Key.KEY_ALARM_ITEM);
        index = getIntent().getIntExtra(DataStorage.Key.KEY_ALARM_INDEX, -1);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_modify_alarm);
    }

    @Override
    protected void init() {
        createArray();
        binding.setActivity(this);
        selectAlarmType(isSpeaker);
        binding.activityAddAlarmSBVolume.setOnTouchListener((v, event) -> !isSpeaker);
        binding.activityAddAlarmSBVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                                                                        @Override
                                                                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                                                            progress = progress > 0 ? progress : 1;
                                                                            seekBar.setProgress(progress);
                                                                        }

                                                                        @Override
                                                                        public void onStartTrackingTouch(SeekBar seekBar) {

                                                                        }

                                                                        @Override
                                                                        public void onStopTrackingTouch(SeekBar seekBar) {

                                                                        }
                                                                    }
        );

        initView();

        logger.log(HLogger.LogType.INFO, "onCreate(Bundle savedInstanceState)", "index : " + index);

    }

    private void initView() {
        binding.activityAddAlarmETMemo.setText(item.getMemo());
        binding.activityAddAlarmSBVolume.setProgress(item.getVolume());
        binding.activityAddAlarmTVAddress.setText(item.getAddress());

        binding.activityAddAlarmTimePicker.setCurrentHour(item.getHour());
        binding.activityAddAlarmTimePicker.setCurrentMinute(item.getMinute());
        selectAlarmType(item.getIsSpeaker());
    }

    private void createArray() {
        ArrayList<CustomArrayView.CustomItemViewModel> items = new ArrayList<>();
        int[] iArr = item.getDays();
        for (int i = 1; i < iArr.length; i++)
            items.add(binding.activityAddAlarmCustomArrayView.new CustomItemViewModel(DataStorage.Date.DayOfTheWeek[i], i, iArr[i] == CustomArrayView.WeekClickListener.click));
        binding.activityAddAlarmCustomArrayView.setWeekClickListener((idx, value) -> selectedWeek[idx] = value);
        binding.activityAddAlarmCustomArrayView.bindView(this, items);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    // onTimeChange
    public void onTimeChange(TimePicker view, int hourOfDay, int minute) {
        logger.log(HLogger.LogType.INFO, "AlarmReceiver", "%d : %d", hourOfDay, minute);
        this.hourOfDay = hourOfDay;
        this.minute = minute;
        // 8: 15, 17 : 15
    }

    // OnClick
    public void modifyAlarm(View view) {
//        AlarmData.AlarmItem item = alarmData.new AlarmItem(place_name, place_address, memo, selectedWeek, hourOfDay, minute);
        address = binding.activityAddAlarmTVAddress.getText() + "";
        if (!validCheck())
            return;
        AlarmItem item = new AlarmItem(new AlarmTable(hourOfDay, minute, address, binding.activityAddAlarmETMemo.getText() + "", StringUtil.arrayToString(selectedWeek), AlarmTable.ENABLED, binding.activityAddAlarmSBVolume.getProgress(), isSpeaker ? AlarmTable.ENABLED : AlarmTable.DISABLED));
        ContentValues values = null;
        try {
            values = DataBaseParserManager.getInstance().bindContentValues(item);
        } catch (Exception e) {
            logger.log(HLogger.LogType.ERROR, "modifyAlarm(View view)", "bindContentValues - Error", e);
        }
        String whereClause = DataBaseStorage.Column.COLUMN_ALARM_INDEX + " = ?";
        String[] args = {item.getIndex() + ""};
        DataBaseStorage.alarmDataBaseHelper.update(DataBaseStorage.Table.TABLE_ALARM, values, whereClause, args);
        AlarmManager.getInstance().setAlarmService(item);
        DataBaseStorage.alarmList.remove(item.getIndex());
        DataBaseStorage.alarmList.add(item.getIndex(), item);
        onBackPressed();
    }

    private boolean validCheck() {
        if (NullChecker.getInstance().isNull(address))
            return false;
        if (hourOfDay == 0)
            hourOfDay = Integer.parseInt(DateManager.getInstance().getNow(DateManager.DateType.HourOfDay));
        if (minute == 0)
            minute = Integer.parseInt(DateManager.getInstance().getNow(DateManager.DateType.Minute));
        return true;
    }

    // OnClick
    public void selectAlarmType(boolean enabled) {
        isSpeaker = enabled;
        if (enabled)
            binding.activityAddAlarmSBVolume.setProgressDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.seekbar_on2, null));
        else
            binding.activityAddAlarmSBVolume.setProgressDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.seekbar_off2, null));
    }

    // OnClick
    public void searchEvent(View view) {
        startActivityForResult(new Intent(this, SearchAddressActivity.class), DataStorage.Request.SEARCH_ADDRESS_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DataStorage.Request.SEARCH_ADDRESS_REQUEST && resultCode == DataStorage.Request.SEARCH_ADDRESS_OK) {
            PostcodifyModel.ItemModel item = (PostcodifyModel.ItemModel) data.getSerializableExtra(DataStorage.Key.KEY_ADDRESS_ITEM);
            binding.activityAddAlarmTVAddress.setText((address = item.getAddress()));
        }
    }
}
