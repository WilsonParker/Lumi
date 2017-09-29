package com.graction.developer.lumi.Model.Response;


import com.graction.developer.lumi.Model.weather.Clouds;
import com.graction.developer.lumi.Model.weather.Coord;
import com.graction.developer.lumi.Model.weather.Main;
import com.graction.developer.lumi.Model.weather.Sys;
import com.graction.developer.lumi.Model.weather.Weather;
import com.graction.developer.lumi.Model.weather.Wind;

import java.util.ArrayList;

public class WeatherModel {
	private Coord coord;
	private ArrayList<Weather> weather;
	private Main main;
	private Wind wind;
	private Clouds clouds;
	private Sys sys;
	private String base		// Internal parameter
					, name	//	City Mane
					, address	// Address obtained with lat & lng
					;

	private long dt			// Time of data calculation, unix, UTC
				, id;		// City ID
	private int cod;		// Internal parameter

	public Coord getCoord() {
		return coord;
	}

	public void setCoord(Coord coord) {
		this.coord = coord;
	}

	public ArrayList<Weather> getWeather() {
		return weather;
	}

	public Weather getFirstWeather() {
		return weather.get(0);
	}

	public void setWeather(ArrayList<Weather> weather) {
		this.weather = weather;
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Main getMain() {
		return main;
	}

	public void setMain(Main main) {
		this.main = main;
	}

	public Wind getWind() {
		return wind;
	}

	public void setWind(Wind wind) {
		this.wind = wind;
	}

	public Clouds getClouds() {
		return clouds;
	}

	public void setClouds(Clouds clouds) {
		this.clouds = clouds;
	}

	public Sys getSys() {
		return sys;
	}

	public void setSys(Sys sys) {
		this.sys = sys;
	}

	public long getDt() {
		return dt;
	}

	public void setDt(long dt) {
		this.dt = dt;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getCod() {
		return cod;
	}

	public void setCod(int cod) {
		this.cod = cod;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "WeatherModel{" +
				"coord=" + coord +
				", weather=" + weather +
				", main=" + main +
				", wind=" + wind +
				", clouds=" + clouds +
				", sys=" + sys +
				", base='" + base + '\'' +
				", name='" + name + '\'' +
				", address='" + address + '\'' +
				", dt=" + dt +
				", id=" + id +
				", cod=" + cod +
				'}';
	}
}
