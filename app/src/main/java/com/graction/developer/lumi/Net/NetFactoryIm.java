package com.graction.developer.lumi.Net;

import com.graction.developer.lumi.Model.Response.DailyForecast;
import com.graction.developer.lumi.Model.Response.WeatherModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NetFactoryIm {

	@GET("app/dailyforecast")
	Call<DailyForecast> selectForecastDaily(@Query("lat") double lat, @Query("lon") double lon);

	@GET("app/weather")
	Call<WeatherModel> selectWeather(@Query("lat") double lat, @Query("lon") double lon);
}
