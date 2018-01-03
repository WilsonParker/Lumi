package com.graction.developer.lumi.Fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.graction.developer.lumi.R;
import com.graction.developer.lumi.Util.Log.HLogger;
import com.graction.developer.lumi.databinding.FragmentAlarmBinding;

import static com.graction.developer.lumi.Data.DataStorage.Request.GOOGLE_PLACE_OK;
import static com.graction.developer.lumi.Data.DataStorage.Request.PLACE_PICKER_REQUEST;

public class AlarmFragment extends BaseFragment implements GoogleApiClient.OnConnectionFailedListener {
    private GoogleApiClient mGoogleApiClient;
    private FragmentAlarmBinding binding;
    private HLogger logger;

    private PlacePicker.IntentBuilder builder;

    public static Fragment getInstance() {
        Fragment fragment = new AlarmFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_alarm, null, false);
        return binding.getRoot();
    }

    @Override
    void init(View view) {
        logger = new HLogger(getClass());
        builder = new PlacePicker.IntentBuilder();
        mGoogleApiClient = new GoogleApiClient
                .Builder(getActivity())
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();
        try {
            startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PLACE_PICKER_REQUEST){
            logger.log(HLogger.LogType.INFO, "onActivityResult %d %d", requestCode, resultCode);
            if(resultCode == GOOGLE_PLACE_OK){
                Place place = PlacePicker.getPlace(data, getActivity());
                String toastMsg = String.format("Place : %s",place.getName());
                Toast.makeText(getContext(), toastMsg, Toast.LENGTH_LONG).show();
                logger.log(HLogger.LogType.INFO, "msg : "+toastMsg);
                logger.log(HLogger.LogType.INFO, "msg : "+place.getAddress());
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
}
