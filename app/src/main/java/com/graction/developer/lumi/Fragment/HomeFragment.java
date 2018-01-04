package com.graction.developer.lumi.Fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.ViewTarget;
import com.graction.developer.lumi.Listener.AddressHandleListener;
import com.graction.developer.lumi.Model.Response.IntegratedAirQualityModel;
import com.graction.developer.lumi.Model.Response.WeatherModel;
import com.graction.developer.lumi.Net.Net;
import com.graction.developer.lumi.R;
import com.graction.developer.lumi.Util.File.BaseActivityFileManager;
import com.graction.developer.lumi.Util.GPS.GoogleLocationManager;
import com.graction.developer.lumi.Util.GPS.GpsManager;
import com.graction.developer.lumi.Util.Log.HLogger;
import com.graction.developer.lumi.Util.Weather.WeatherManager;
import com.graction.developer.lumi.databinding.FragmentHomeBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.graction.developer.lumi.Data.DataStorage.integratedAirQualityModel;
import static com.graction.developer.lumi.Data.DataStorage.weatherModel;

public class HomeFragment extends BaseFragment {
    private static final HomeFragment instance = new HomeFragment();
    private FragmentHomeBinding binding;
    private HLogger logger;
    private WeatherManager weatherManager;
    private GpsManager gpsManager;
    private GoogleLocationManager googleLocationManager;
    private Call call;

    private String background_img_url = "", character_img_url = "", effect_img_url = "";

    public static Fragment getInstance() {
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, null, false);
        return binding.getRoot();
//        return inflater.inflate(R.layout.fragment_home, null);
    }

    @Override
    void init(View view) {
        logger = new HLogger(getClass());
        weatherManager = WeatherManager.getInstance();
        gpsManager = new GpsManager(getActivity());

        googleLocationManager = new GoogleLocationManager(new AddressHandleListener() {
            @Override
            public void setAddress(String address) {
                logger.log(HLogger.LogType.INFO, "address : " + address);
//                TV_address.setText(address);
                binding.fragmentHomeTVAddress.setText(address);
            }
        });

//        root = uiFactory.createView(R.id.fragment_home_root);
//        TV_address = uiFactory.createView(R.id.fragment_home_TV_address);
//        IV_background = uiFactory.createView(R.id.fragment_home_IV_background);
//        IV_character = uiFactory.createView(R.id.fragment_home_IV_character);

//        gifImageView.gotoFrame(2);
        /*gifImageView.setOnFrameAvailable(new GifImageView.OnFrameAvailable() {
            @Override public Bitmap onFrameAvailable(Bitmap bitmap) {
                return blurFilter.blur(bitmap);
            }
        });*/

        currentWeather();
    }

    @Override
    public void onResume() {
        super.onResume();
        // callIntegratedAirQuality();
    }

    @Override
    public void onPause() {
        super.onPause();
        if(call.isExecuted())
            call.cancel();
    }

    private void currentWeather() {
        call = Net.getInstance().getFactoryIm().selectWeather(gpsManager.getLatitude(), gpsManager.getLongitude());
        call.enqueue(new Callback<WeatherModel>() {
            @Override
            public void onResponse(Call<WeatherModel> call, Response<WeatherModel> response) {
                if (response.isSuccessful()) {
                    logger.log(HLogger.LogType.INFO, "onResponse(Call<WeatherModel> call, Response<WeatherModel> response)", "isSuccessful");
                    logger.log(HLogger.LogType.INFO, "onResponse(Call<WeatherModel> call, Response<WeatherModel> response)", "response : " + response.body());
                    weatherModel = response.body();
                    reloadWeatherInfo();
                    callIntegratedAirQuality();
                } else {
                    logger.log(HLogger.LogType.WARN, "onResponse(Call<WeatherModel> call, Response<WeatherModel> response)", "is not Successful");
                    logger.log(HLogger.LogType.INFO, "onResponse(Call<WeatherModel> call, Response<WeatherModel> response)", "response : " + response.body());
                }
            }

            @Override
            public void onFailure(Call<WeatherModel> call, Throwable t) {
                logger.log(HLogger.LogType.ERROR, "onFailure(Call<WeatherModel> call, Throwable t)", "onFailure", t);
                t.printStackTrace();
            }
        });
    }

    ViewTarget character;
    private void reloadWeatherInfo() {
//        gifImageView.startAnimation();
        if (gpsManager.isGetLocation()) {
//            googleLocationManager.getAddress(gpsManager.getLocation());
            if (weatherModel != null) {
                binding.setWeatherModel(weatherModel);
                try {
                    if (!background_img_url.equals(weatherModel.getBackground_img_url())) {
                        logger.log(HLogger.LogType.INFO, "void reloadWeatherInfo()", "background img url : " + weatherModel.getBackground_img_url() + " / origin : " + background_img_url);
                        background_img_url = weatherModel.getBackground_img_url();
                        Glide.with(this).load(background_img_url).apply(new RequestOptions().centerCrop()).into(binding.fragmentHomeIVBackground);
                    }

                    if (!character_img_url.equals(weatherModel.getCharacter_img_url())) {
                        logger.log(HLogger.LogType.INFO, "void reloadWeatherInfo()", "character img url : " + weatherModel.getCharacter_img_url());
                        character_img_url = weatherModel.getCharacter_img_url();
                        // Glide.with(this).load(BaseActivityFileManager.getInstance().getAssetFileToByte(getResources().getAssets(), "images/background/test4.gif")).into(binding.fragmentHomeIVCharacter);
                        character = Glide.with(this).load(BaseActivityFileManager.getInstance().getAssetFileToByte(getResources().getAssets(), "images/background/test4.gif")).into(binding.fragmentHomeIVCharacter);
                    }
                    logger.log(HLogger.LogType.INFO, "void reloadWeatherInfo()", "background img url : " + weatherModel.getBackground_img_url());
                    logger.log(HLogger.LogType.INFO, "void reloadWeatherInfo()", "weatherModel : " + weatherModel);
                } catch (Exception e) {
                    logger.log(HLogger.LogType.ERROR, "reloadWeatherInfo()", "reloadWeatherInfo Error", e);
                }
            }
        } else {
            gpsManager.showSettingsAlert();
        }

        byte[] img = BaseActivityFileManager.getInstance().getAssetFileToByte(getResources().getAssets(), "images/background/test2.gif");
        binding.fragmentHomeIVCharacter2.setBytes(img);
        binding.fragmentHomeIVCharacter2.startAnimation();

//        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.animtest);
//        binding.fragmentHomeIVCharacter2.startAnimation(animation);
        long duration = binding.fragmentHomeIVCharacter2.getFramesDisplayDuration();
        logger.log(HLogger.LogType.INFO, "void reloadWeatherInfo()", "duration : "+duration);
        binding.fragmentHomeIVCharacter2.setFramesDisplayDuration(duration);

        binding.fragmentHomeIVCharacter2.setOnClickListener((view)->{
            if(binding.fragmentHomeIVCharacter2.isAnimating())
                binding.fragmentHomeIVCharacter2.stopAnimation();
            else
                binding.fragmentHomeIVCharacter2.startAnimation();
        });
        // callIntegratedAirQuality();
    }

    private void callIntegratedAirQuality() {
        call = Net.getInstance().getFactoryIm().selectIntegratedAirQuality(gpsManager.getLatitude(), gpsManager.getLongitude());
        call.enqueue(new Callback<IntegratedAirQualityModel>() {
            @Override
            public void onResponse(Call<IntegratedAirQualityModel> call, Response<IntegratedAirQualityModel> response) {
                if (response.isSuccessful()) {
                    integratedAirQualityModel = response.body();
                    if (integratedAirQualityModel != null) {
                        binding.setIntegratedAirQualityModel(integratedAirQualityModel);
                        binding.setIntegratedAirQualityModelItem(integratedAirQualityModel.getFirstItem());
                        logger.log(HLogger.LogType.INFO, "void callIntegratedAirQuality()", "response body: " + integratedAirQualityModel);
                    }
                }
            }

            @Override
            public void onFailure(Call<IntegratedAirQualityModel> call, Throwable t) {
                logger.log(HLogger.LogType.ERROR, "callIntegratedAirQuality()", "callIntegratedAirQuality onFailure", t);
            }
        });
    }
}
