package com.graction.developer.lumi.Fragment;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.graction.developer.lumi.Listener.AddressHandleListener;
import com.graction.developer.lumi.Model.ImageModel;
import com.graction.developer.lumi.Model.Response.IntegratedAirQualityModel;
import com.graction.developer.lumi.Model.Response.WeatherModel;
import com.graction.developer.lumi.Net.Net;
import com.graction.developer.lumi.R;
import com.graction.developer.lumi.Util.File.BaseActivityFileManager;
import com.graction.developer.lumi.Util.GPS.GoogleLocationManager;
import com.graction.developer.lumi.Util.GPS.GpsManager;
import com.graction.developer.lumi.Util.Image.GlideImageManager;
import com.graction.developer.lumi.Util.Log.HLogger;
import com.graction.developer.lumi.Util.Weather.WeatherManager;
import com.graction.developer.lumi.databinding.FragmentHomeBinding;

import java.io.IOException;
import java.io.InputStream;

import pl.droidsonroids.gif.AnimationListener;
import pl.droidsonroids.gif.GifDrawable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.graction.developer.lumi.Data.DataStorage.integratedAirQualityModel;
import static com.graction.developer.lumi.Data.DataStorage.weatherModel;

public class HomeFragment extends BaseFragment {
    private static final HomeFragment instance = new HomeFragment();
    private FragmentHomeBinding binding;
    private WeatherManager weatherManager;
    private GpsManager gpsManager;
    private GoogleLocationManager googleLocationManager;
    private BaseActivityFileManager baseActivityFileManager = BaseActivityFileManager.getInstance();
    private Call call;

    private String background_img_url = "", character_img_url = "", effect_img_url = "";
    private byte[] background_bytes, character_bytes;
    private Bitmap background_bitmap;

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
    protected void init(View view) {
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

        initUI();
        currentWeather();
    }

    private void initUI() {
        InputStream is = null;
        try {
            is = getActivity().getAssets().open("images/background/sunny.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap b = BitmapFactory.decodeStream(is);

        Glide.with(this).load(b).apply(new RequestOptions().centerCrop()).into(binding.fragmentHomeIVBackground);
    }

    @Override
    public void onResume() {
        super.onResume();
        // callIntegratedAirQuality();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (call.isExecuted())
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

    private void reloadWeatherInfo() {
//        gifImageView.startAnimation();
        if (gpsManager.isGetLocation()) {
//            googleLocationManager.getAddress(gpsManager.getLocation());
            if (weatherModel != null) {
                binding.setWeatherModel(weatherModel);
                try {
                    if (background_bitmap == null || !background_img_url.equals(weatherModel.getBackground_img_url())) {
                        logger.log(HLogger.LogType.INFO, "void reloadWeatherInfo()", "background img url : " + weatherModel.getBackground_img_url() + " / origin : " + background_img_url);
                        background_img_url = weatherModel.getBackground_img_url();
//                      Glide.with(this).load(background_img_url).apply(new RequestOptions().centerCrop()).into(binding.fragmentHomeIVBackground);
//                      Drawable d = BaseActivityFileManager.getInstance().getDrawableFromAssets(getActivity().getAssets(), "images/background/sunny.jpg");

                        ImageModel imageModel = weatherModel.getImageModel();
                        logger.log(HLogger.LogType.INFO, "reloadWeatherInfo()", "%s, %s, %s", imageModel.getBackground_img_path(), imageModel.getBackground_img_name(), imageModel.getBackground_img_url());
                        logger.log(HLogger.LogType.INFO, "reloadWeatherInfo()", "all path : "+baseActivityFileManager.getPath()+imageModel.getBackground_img_path()+ imageModel.getBackground_img_name());
                        GlideImageManager.getInstance().bindImage(getActivity(), binding.fragmentHomeIVBackground, new RequestOptions().centerCrop(), imageModel.getBackground_img_path(), imageModel.getBackground_img_name(), imageModel.getBackground_img_url());


                       /* String fName = "/images/background/sunny.jpg"
                                , url = "http://192.168.0.8:8101/lumiAssets/assets/images/background/sunny.jpg";
                        logger.log(HLogger.LogType.INFO, "void reloadWeatherInfo()", "path : " + baseActivityFileManager.getPath()+fName);
                        File save = new File(baseActivityFileManager.getPath()+fName);
                        logger.log(HLogger.LogType.INFO, "void reloadWeatherInfo()", "is exist : " + save.exists());
                        byte[] bytes = baseActivityFileManager.getByteFromURL(url);
                        FileOutputStream fos = new FileOutputStream(save);
                        Bitmap bitmap2 = baseActivityFileManager.convertDrawableToBitmap(d);
                        bitmap2.compress(Bitmap.CompressFormat.PNG, 100 ,fos);
                        logger.log(HLogger.LogType.INFO, "void reloadWeatherInfo()", "is exist : " + save.exists());
*/


//                        FileInputStream fis = getActivity().openFileInput(fName);
//                        Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fis.getFD());
//                        Glide.with(this).load(bitmap).apply(new RequestOptions().centerCrop()).into(binding.fragmentHomeIVBackground);

//                      background_bytes = BaseActivityFileManager.getInstance().getByteFromURL(background_img_url);
//                        background_bitmap = BaseActivityFileManager.getInstance().getBitmapFromURL(background_img_url);
                    } else if (background_bitmap != null) {
                        logger.log(HLogger.LogType.INFO, "void reloadWeatherInfo()", "background_bytes != null");
                        Glide.with(this).load(background_bitmap).apply(new RequestOptions().centerCrop()).into(binding.fragmentHomeIVBackground);
                    } else {
                        logger.log(HLogger.LogType.INFO, "void reloadWeatherInfo()", "background_bytes == null");
                        Glide.with(this).load(background_bitmap).apply(new RequestOptions().centerCrop()).into(binding.fragmentHomeIVBackground);
                    }
                    logger.log(HLogger.LogType.INFO, "void reloadWeatherInfo()", "background_bytes is null?" + (background_bitmap == null));

                    if ((!character_img_url.equals(weatherModel.getCharacter_img_url())) || character_img_url != null) {
                        logger.log(HLogger.LogType.INFO, "void reloadWeatherInfo()", "character img url : " + weatherModel.getCharacter_img_url());
                        character_img_url = weatherModel.getCharacter_img_url();

                        try {
                            GifDrawable gifDrawable = new GifDrawable(getResources().getAssets(), "images/background/test2.gif");

                            gifDrawable.addAnimationListener(new AnimationListener() {
                                @Override
                                public void onAnimationCompleted(int loopNumber) {
                                    gifDrawable.stop();
                                }
                            });

                            binding.fragmentHomeIVCharacter.setImageDrawable(gifDrawable);
                            binding.fragmentHomeIVCharacter.setOnClickListener((v) -> {
                                gifDrawable.start();
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
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
