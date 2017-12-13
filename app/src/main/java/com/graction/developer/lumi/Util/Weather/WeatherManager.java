package com.graction.developer.lumi.Util.Weather;

import android.content.Context;
import android.widget.ImageView;

import com.graction.developer.lumi.Data.DataStorage;
import com.graction.developer.lumi.Model.weather.Weather;
import com.graction.developer.lumi.Util.Image.ImageManager;
import com.graction.developer.lumi.Util.Log.HLogger;

/**
 * Created by graction03 on 2017-09-28.
 */

public class WeatherManager {
    private HLogger logger;
    private static final WeatherManager instance = new WeatherManager();
    private ImageManager imageManager = ImageManager.getInstance();

    public WeatherManager() {
        logger = new HLogger(getClass());
    }

    public static WeatherManager getInstance() {
        return instance;
    }

    public void setWeatherBackground(Context context, ImageView imageView, Weather weather) {
        imageManager.loadImage(imageManager.createRequestCreator(context, DataStorage.Path.PATH_ASSET + DataStorage.Path.PATH_BACKGROUND + DataStorage.weathers.get(weather.getId()).getImage(), ImageManager.Type.FIT_TYPE).centerCrop(), imageView);
    }

}
