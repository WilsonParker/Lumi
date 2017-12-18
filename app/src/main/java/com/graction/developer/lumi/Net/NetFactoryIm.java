package com.graction.developer.lumi.Net;

import com.graction.developer.lumi.Model.Response.DailyForecast;
import com.graction.developer.lumi.Model.Response.WeatherModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NetFactoryIm {

	// 16 day weather forecast
	@GET("app/dailyforecast")
	Call<DailyForecast> selectForecastDaily(@Query("lat") double lat, @Query("lon") double lon);

	// Current weather
	@GET("app/current_weather")
	Call<WeatherModel> selectWeather(@Query("lat") double lat, @Query("lon") double lon);
}