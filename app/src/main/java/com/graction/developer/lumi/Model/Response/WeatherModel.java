package com.graction.developer.lumi.Model.Response;


import com.graction.developer.lumi.Model.weather.Clouds;
import com.graction.developer.lumi.Model.weather.Coord;
import com.graction.developer.lumi.Model.weather.Main;
import com.graction.developer.lumi.Model.weather.Sys;
import com.graction.developer.lumi.Model.weather.Weather;
import com.graction.developer.lumi.Model.weather.Wind;

import java.util.ArrayList;

/*
 * Current weather model
 */
public class WeatherModel {
	private static final String RESOURCE_URL = "http://192.168.0.8:8101/lumiAssets";
	private Coord coord;
	private ArrayList<Weather> weather;
	private Main main;
	private Wind wind;
	private Clouds clouds;
	private Sys sys;
	private String base		// Internal parameter
					, name	//	City Mane
					, address	// Address obtained with lat & lng
					, address_do
					, address_si
					, address_gu
					, background_img_path
					, background_img_name
		//			, background_img_url
					, character_img_path
					, character_img_name
		//			, character_img_url
					, effect_img_path
					, effect_img_name
		//			, effect_img_url
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

	public String getAddress_do() {
		return address_do;
	}

	public void setAddress_do(String address_do) {
		this.address_do = address_do;
	}

	public String getAddress_si() {
		return address_si;
	}

	public void setAddress_si(String address_si) {
		this.address_si = address_si;
	}

	public String getAddress_gu() {
		return address_gu;
	}

	public void setAddress_gu(String address_gu) {
		this.address_gu = address_gu;
	}

	public String getBackground_img_path() {
		return background_img_path;
	}

	public void setBackground_img_path(String background_img_path) {
		this.background_img_path = background_img_path;
	}

	public String getBackground_img_name() {
		return background_img_name;
	}

	public void setBackground_img_name(String background_img_name) {
		this.background_img_name = background_img_name;
	}

	public String getCharacter_img_path() {
		return character_img_path;
	}

	public void setCharacter_img_path(String character_img_path) {
		this.character_img_path = character_img_path;
	}

	public String getCharacter_img_name() {
		return character_img_name;
	}

	public void setCharacter_img_name(String character_img_name) {
		this.character_img_name = character_img_name;
	}

	public String getEffect_img_path() {
		return effect_img_path;
	}

	public void setEffect_img_path(String effect_img_path) {
		this.effect_img_path = effect_img_path;
	}

	public String getEffect_img_name() {
		return effect_img_name;
	}

	public void setEffect_img_name(String effect_img_name) {
		this.effect_img_name = effect_img_name;
	}

	public String getBackground_img_url() {
		return RESOURCE_URL+getBackground_img_path()+getBackground_img_name();
	}

	public String getCharacter_img_url() {
		return RESOURCE_URL+getCharacter_img_path()+getCharacter_img_name();
	}

	public String getEffect_img_url() {
		return RESOURCE_URL+getEffect_img_path()+getEffect_img_name();
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
				", address_do='" + address_do + '\'' +
				", address_si='" + address_si + '\'' +
				", address_gu='" + address_gu + '\'' +
				", background_img_url='" + getBackground_img_url() + '\'' +
				", character_img_url='" + getCharacter_img_url() + '\'' +
				", effect_img_url='" + getEffect_img_url() + '\'' +
				", dt=" + dt +
				", id=" + id +
				", cod=" + cod +
				'}';
	}
}
