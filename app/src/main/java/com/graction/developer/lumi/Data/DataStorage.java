package com.graction.developer.lumi.Data;


import com.graction.developer.lumi.Model.Response.WeatherModel;
import com.graction.developer.lumi.Model.Xml.Weather;

import java.util.Map;

/**
 * Created by Hare on 2017-07-16.
 */

public class DataStorage {
//    public static double Location_Longitude, Location_Latitude;
//    public static FragmentEventListener S_FragmentEventListener;
    public static final int RequetCode = 200;

    public static boolean GpsPermissionOn = false;
    public static WeatherModel weatherModel;
    public static Map<Integer, Weather> weathers;

    public class OpenWeather {

        public static final String
                PARAM_KEY_APPID = "appid",
                PARAM_KEY_SEARCH = "q",
                PARAM_KEY_MDOE = "mode",
                PARAM_KEY_LAT = "lat",
                PARAM_KEY_LON = "lon";

        public static final String API_KEY = "5a165ea40110d89c6a33e762fb7501c6",
                API_URL = "http://api.openweathermap.org",
                RESOURCE_MODE = "json";
    }

    public class Path{
        public static final String PATH_ASSET = "file:///android_asset/"
                                , PATH_XML = "xml/"
                                , PATH_IMAGES= "images/"
                                , PATH_BACKGROUND= PATH_IMAGES+"background/"
                                ;
    }
}
