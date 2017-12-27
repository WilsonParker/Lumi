package com.graction.developer.lumi.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.graction.developer.lumi.Listener.AddressHandleListener;
import com.graction.developer.lumi.Model.Response.Forecast5DayModel;
import com.graction.developer.lumi.Net.Net;
import com.graction.developer.lumi.R;
import com.graction.developer.lumi.Util.GPS.GoogleLocationManager;
import com.graction.developer.lumi.Util.GPS.GpsManager;
import com.graction.developer.lumi.Util.Log.HLogger;
import com.graction.developer.lumi.databinding.FragmentForecast5dayBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Forecast5DayFragment extends BaseFragment {
    private FragmentForecast5dayBinding binding;
    private HLogger logger;
    private GpsManager gpsManager;
    private GoogleLocationManager googleLocationManager;
    private Forecast5DayModel model;

    public static Fragment getInstance() {
        Fragment fragment = new Forecast5DayFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_forecast5day, null);
    }

    @Override
    void init(View view) {
        logger = new HLogger(getClass());
        gpsManager = new GpsManager(getActivity());

        googleLocationManager = new GoogleLocationManager(new AddressHandleListener() {
            @Override
            public void setAddress(String address) {
                logger.log(HLogger.LogType.INFO, "address : " + address);
                // binding.fragmentHomeTVAddress.setText(address);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        forecast5day();
    }

    public void forecast5day() {
        Net.getInstance().getFactoryIm().selectForecast5Day(gpsManager.getLatitude(), gpsManager.getLongitude()).enqueue(new Callback<Forecast5DayModel>() {
            @Override
            public void onResponse(Call<Forecast5DayModel> call, Response<Forecast5DayModel> response) {
                if (response.isSuccessful()) {
                    logger.log(HLogger.LogType.INFO, "void forecast5day() - onResponse(Call<Forecast5DayModel> call, Response<Forecast5DayModel> response)", "isSuccessful");
                    model = response.body();
                    reloadWeatherInfo();
                } else {
                    logger.log(HLogger.LogType.WARN, "void forecast5day() - onResponse(Call<Forecast5DayModel> call, Response<Forecast5DayModel> response)", "is not Successful");
                }
            }

            @Override
            public void onFailure(Call<Forecast5DayModel> call, Throwable t) {
                logger.log(HLogger.LogType.ERROR, "void forecast5day() - onFailure(Call<Forecast5DayModel> call, Throwable t)", "onFailure", t);
            }
        });
    }

    private void reloadWeatherInfo() {
        if (gpsManager.isGetLocation()) {

        } else {
            gpsManager.showSettingsAlert();
        }
    }
}