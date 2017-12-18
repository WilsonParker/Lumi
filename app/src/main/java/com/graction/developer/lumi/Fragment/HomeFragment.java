package com.graction.developer.lumi.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.felipecsl.gifimageview.library.GifImageView;
import com.graction.developer.lumi.Listener.AddressHandleListener;
import com.graction.developer.lumi.Model.Response.DailyForecast;
import com.graction.developer.lumi.Model.Response.WeatherModel;
import com.graction.developer.lumi.Net.Net;
import com.graction.developer.lumi.R;
import com.graction.developer.lumi.UI.UIFactory;
import com.graction.developer.lumi.Util.File.BaseActivityFileManager;
import com.graction.developer.lumi.Util.GPS.GoogleLocationManager;
import com.graction.developer.lumi.Util.GPS.GpsManager;
import com.graction.developer.lumi.Util.GPS.LocationManager;
import com.graction.developer.lumi.Util.Image.GlideImageManager;
import com.graction.developer.lumi.Util.Log.HLogger;
import com.graction.developer.lumi.Util.Weather.WeatherManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.graction.developer.lumi.Data.DataStorage.weatherModel;

public class HomeFragment extends BaseFragment {
    private HLogger logger;
    private UIFactory uiFactory;
    private GlideImageManager imageManager;
    private WeatherManager weatherManager;
    private GpsManager gpsManager;
    private GoogleLocationManager googleLocationManager;
    private LocationManager locationManager;

    private FrameLayout root;
    private ImageView IV_background, IV_character;
    private TextView TV_address;
    private GifImageView gifImageView;

    private String background_img_url ="", character_img_url ="", effect_img_url ="";

    public static Fragment getInstance() {
        Fragment fragment = new HomeFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, null);
    }

    @Override
    void init(View view) {
        logger = new HLogger(getClass());
        uiFactory = UIFactory.getInstance(view);
        imageManager = GlideImageManager.getInstance();
        weatherManager = WeatherManager.getInstance();
        gpsManager = new GpsManager(getActivity());

        googleLocationManager = new GoogleLocationManager(new AddressHandleListener() {
            @Override
            public void setAddress(String address) {
                logger.log(HLogger.LogType.INFO, "address : " + address);
                TV_address.setText(address);
            }
        });
        locationManager = new LocationManager(getContext(), new AddressHandleListener() {
            @Override
            public void setAddress(String address) {
            }
        });

        root = uiFactory.createView(R.id.fragment_home_root);
        TV_address = uiFactory.createView(R.id.fragment_home_TV_address);
        IV_background = uiFactory.createView(R.id.fragment_home_IV_background);
        IV_character = uiFactory.createView(R.id.fragment_home_IV_character);
        gifImageView = uiFactory.createView(R.id.fragment_home_GV_background);

//        gifImageView.gotoFrame(2);
        /*gifImageView.setOnFrameAvailable(new GifImageView.OnFrameAvailable() {
            @Override public Bitmap onFrameAvailable(Bitmap bitmap) {
                return blurFilter.blur(bitmap);
            }
        });*/
    }

    @Override
    public void onResume() {
        super.onResume();
        test2();
//        test();
        reloadWeatherInfo();
    }

    private void reloadWeatherInfo() {
//        gifImageView.startAnimation();
        if (gpsManager.isGetLocation()) {
//            googleLocationManager.getAddress(gpsManager.getLocation());
            if (weatherModel != null) {
                TV_address.setText(weatherModel.getAddress());
                try {
//                    weatherManager.setWeatherBackground(getContext(), IV_background, DataStorage.weatherModel.getFirstWeather());
                    // imageManager.loadImage(getContext(), weatherModel.getBackground_img_url(), IV_background, PicassoImageManager.Type.FIT_TYPE);
                    if(!background_img_url.equals(weatherModel.getBackground_img_url())){
                        logger.log(HLogger.LogType.INFO, "void reloadWeatherInfo()", "background img url : "+weatherModel.getBackground_img_url()+" / origin : "+background_img_url);
                        // RequestCreator rq = imageManager.createRequestCreator(getContext(),weatherModel.getBackground_img_url(), PicassoImageManager.Type.FIT_TYPE).noFade().placeholder(BaseActivityFileManager.getInstance().getDrawableFromAssets(getResources().getAssets(),"images/background/sunny.jpg"));
//                        RequestCreator rq = imageManager.createRequestCreator(getContext(),weatherModel.getBackground_img_url(), PicassoImageManager.Type.FIT_TYPE);
                        background_img_url = weatherModel.getBackground_img_url();
                        // imageManager.loadImage(getContext(), background_img_url, IV_background, PicassoImageManager.Type.FIT_TYPE);
//                        imageManager.loadImage(rq, IV_background);
                        Glide.with(this).load(background_img_url).into(IV_background);
                    }

                    if(!character_img_url.equals(weatherModel.getCharacter_img_url())){
                        logger.log(HLogger.LogType.INFO, "void reloadWeatherInfo()", "character img url : "+weatherModel.getCharacter_img_url());
                        character_img_url = weatherModel.getCharacter_img_url();
                        /*gifImageView.setBytes(BaseActivityFileManager.getInstance().getAssetFileToByte(getResources().getAssets(), "images/background/test4.gif"));
                        gifImageView.startAnimation();*/
                        Glide.with(this).load(BaseActivityFileManager.getInstance().getAssetFileToByte(getResources().getAssets(), "images/background/test4.gif")).into(IV_character);
                    }
//                    imageManager.loadImage(rq, IV_background);
//                    File file = BaseActivityFileManager.getInstance().getFile("images/background/sunny.jpg");
//                    imageManager.loadImage(getContext(), BaseActivityFileManager.getInstance().getAssetFileToByte("images/background/sunny.jpg"), IV_background, PicassoImageManager.Type.FIT_TYPE);
                    logger.log(HLogger.LogType.INFO, "void reloadWeatherInfo()", "background img url : "+weatherModel.getBackground_img_url());
                } catch (Exception e) {
                    logger.log(HLogger.LogType.ERROR, "reloadWeatherInfo()", "reloadWeatherInfo Error", e);
                }
            }
        } else {
            gpsManager.showSettingsAlert();
        }
    }

    public void test() {
        Net.getInstance().getFactoryIm().selectForecastDaily(gpsManager.getLatitude(), gpsManager.getLongitude()).enqueue(new Callback<DailyForecast>() {
            @Override
            public void onResponse(Call<DailyForecast> call, Response<DailyForecast> response) {
                if (response.isSuccessful()) {
                    logger.log(HLogger.LogType.INFO, "onResponse(Call<DailyForecast> call, Response<DailyForecast> response)", "isSuccessful");
                    logger.log(HLogger.LogType.INFO, "onResponse(Call<DailyForecast> call, Response<DailyForecast> response)", "response : " + response.body());
                } else {
                    logger.log(HLogger.LogType.WARN, "onResponse(Call<DailyForecast> call, Response<DailyForecast> response)", "is not Successful");
                }
            }

            @Override
            public void onFailure(Call<DailyForecast> call, Throwable t) {
                logger.log(HLogger.LogType.ERROR, "onFailure(Call<DailyForecast> call, Throwable t)", "onFailure", t);
            }
        });
    }

    public void test2() {
        Net.getInstance().getFactoryIm().selectWeather(gpsManager.getLatitude(), gpsManager.getLongitude()).enqueue(new Callback<WeatherModel>() {
            @Override
            public void onResponse(Call<WeatherModel> call, Response<WeatherModel> response) {
                if (response.isSuccessful()) {
                    logger.log(HLogger.LogType.INFO, "onResponse(Call<WeatherModel> call, Response<WeatherModel> response)", "isSuccessful");
                    logger.log(HLogger.LogType.INFO, "onResponse(Call<WeatherModel> call, Response<WeatherModel> response)", "response : " + response.body());
                    weatherModel = response.body();
                    reloadWeatherInfo();
                } else {
                    logger.log(HLogger.LogType.WARN, "onResponse(Call<WeatherModel> call, Response<WeatherModel> response)", "is not Successful");
                }
            }

            @Override
            public void onFailure(Call<WeatherModel> call, Throwable t) {
                logger.log(HLogger.LogType.ERROR, "onFailure(Call<WeatherModel> call, Throwable t)", "onFailure", t);
                t.printStackTrace();
            }
        });
    }
}
