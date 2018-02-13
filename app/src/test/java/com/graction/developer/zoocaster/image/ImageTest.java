package com.graction.developer.zoocaster.image;/*
package com.graction.developer.zoocaster.image;

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
import com.graction.developer.zoocaster.Fragment.BaseFragment;
import com.graction.developer.zoocaster.Listener.AddressHandleListener;
import com.graction.developer.zoocaster.R;
import com.graction.developer.zoocaster.Util.File.BaseActivityFileManager;
import com.graction.developer.zoocaster.Util.GPS.GoogleLocationManager;
import com.graction.developer.zoocaster.Util.GPS.GpsManager;
import com.graction.developer.zoocaster.Util.Log.HLogger;
import com.graction.developer.zoocaster.Util.Weather.WeatherManager;
import com.graction.developer.zoocaster.databinding.FragmentHomeBinding;

import java.io.IOException;

import pl.droidsonroids.gif.AnimationListener;
import pl.droidsonroids.gif.GifDrawable;
import retrofit2.Call;

import static com.graction.developer.zoocaster.Data.DataStorage.weatherModel;

public class ImageTest extends BaseFragment{
    private static final ImageTest instance = new ImageTest();
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
    }

    @Override
    protected void init(View view) {
        logger = new HLogger(getClass());
        weatherManager = WeatherManager.getInstance();
        gpsManager = new GpsManager(getActivity());

        googleLocationManager = new GoogleLocationManager(new AddressHandleListener() {
            @Override
            public void setAlarm_address(String address) {
                logger.log(HLogger.LogType.INFO, "address : " + address);
                binding.fragmentHomeTVAddress.setText(address);
            }
        });
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

    ViewTarget character;
    private void reloadWeatherInfo() {
//        gifImageView.startAnimation();
        if (gpsManager.isGetLocation()) {
//            googleLocationManager.getAlarm_address(gpsManager.getLocation());
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

        gifTest();
    }

    private void gifTest(){
        byte[] img = BaseActivityFileManager.getInstance().getAssetFileToByte(getResources().getAssets(), "images/background/test2.gif");
        binding.fragmentHomeIVCharacter2.setBytes(img);
        binding.fragmentHomeIVCharacter2.startAnimation();

//        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.animtest);
//        binding.fragmentHomeIVCharacter2.startAnimation(animation);
        long duration = binding.fragmentHomeIVCharacter2.getFramesDisplayDuration();
        binding.fragmentHomeIVCharacter2.setFramesDisplayDuration(duration);

        logger.log(HLogger.LogType.INFO, "void reloadWeatherInfo()", "frameCount : "+binding.fragmentHomeIVCharacter2.getFrameCount());
        logger.log(HLogger.LogType.INFO, "void reloadWeatherInfo()", "duration : "+binding.fragmentHomeIVCharacter2.getFramesDisplayDuration());
        // logger.log(HLogger.LogType.INFO, "void reloadWeatherInfo()", "duration : "+binding.fragmentHomeIVCharacter2.setOnAn);

        binding.fragmentHomeIVCharacter2.setOnClickListener((view)->{
            if(binding.fragmentHomeIVCharacter2.isAnimating())
                binding.fragmentHomeIVCharacter2.stopAnimation();
            else
                binding.fragmentHomeIVCharacter2.startAnimation();
        });

        try {
            GifDrawable gifDrawable = new GifDrawable(getResources().getAssets(), "images/background/test2.gif");
            logger.log(HLogger.LogType.INFO, "void reloadWeatherInfo()", "Duration : "+gifDrawable.getDuration());
            logger.log(HLogger.LogType.INFO, "void reloadWeatherInfo()", "FrameByteCount : "+gifDrawable.getFrameByteCount());
            logger.log(HLogger.LogType.INFO, "void reloadWeatherInfo()", "LoopCount : "+gifDrawable.getLoopCount());
            logger.log(HLogger.LogType.INFO, "void reloadWeatherInfo()", "Frames : "+gifDrawable.getNumberOfFrames());

            gifDrawable.addAnimationListener(new AnimationListener() {
                @Override
                public void onAnimationCompleted(int loopNumber) {
                    logger.log(HLogger.LogType.INFO, "void reloadWeatherInfo()", "end!!!!");
                    gifDrawable.stop();
                }
            });

            binding.fragmentHomeIVCharacter3.setImageDrawable(gifDrawable);
            binding.fragmentHomeIVCharacter3.setOnClickListener((v)->{
                gifDrawable.run();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
*/
