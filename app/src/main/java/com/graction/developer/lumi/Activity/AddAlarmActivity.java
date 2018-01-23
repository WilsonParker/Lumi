package com.graction.developer.lumi.Activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.graction.developer.lumi.Data.DataStorage;
import com.graction.developer.lumi.R;
import com.graction.developer.lumi.UI.Layout.CustomArrayView;
import com.graction.developer.lumi.Util.Log.HLogger;
import com.graction.developer.lumi.databinding.ActivityAddAlarmBinding;

import java.util.ArrayList;

import static com.graction.developer.lumi.Data.DataStorage.Request.GOOGLE_PLACE_OK;
import static com.graction.developer.lumi.Data.DataStorage.Request.PLACE_PICKER_REQUEST;

public class AddAlarmActivity extends BaseActivity implements GoogleApiClient.OnConnectionFailedListener {
    private GoogleApiClient mGoogleApiClient;
    private ActivityAddAlarmBinding binding;

    private PlacePicker.IntentBuilder builder;
    private int hourOfDay, minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_alarm);
    }

    @Override
    protected void init() {
        builder = new PlacePicker.IntentBuilder();
        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();


        binding.activityAddAlarmTimePicker.setOnTimeChangedListener((view, hourOfDay, minute) -> {
            logger.log(HLogger.LogType.INFO, "AlarmReceiver", "%d :%d", hourOfDay, minute);
            this.hourOfDay = hourOfDay;

            this.minute = minute;
            // 8: 15, 17 : 15
        });

        createArray();
        binding.setActivity(this);
        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createArray() {
        ArrayList<CustomArrayView.CustomItemViewModel> items = new ArrayList<>();
        int i = 0;
        for (String day : DataStorage.Date.DayOfTheWeek)
            items.add(binding.activityAddAlarmCustomArrayView.new CustomItemViewModel(day, i++));
        binding.activityAddAlarmCustomArrayView.bindView(this, items);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            logger.log(HLogger.LogType.INFO, "onActivityResult %d %d", requestCode, resultCode);
            if (resultCode == GOOGLE_PLACE_OK) {
                Place place = PlacePicker.getPlace(this, data);
                String toastMsg = String.format("Place : %s", place.getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
                logger.log(HLogger.LogType.INFO, "msg : " + toastMsg);
                logger.log(HLogger.LogType.INFO, "msg : " + place.getAddress());
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    // OnClick
    public void addAlarm(View view) {
        logger.log(HLogger.LogType.INFO, "addAlarm(view)");

    }
}
