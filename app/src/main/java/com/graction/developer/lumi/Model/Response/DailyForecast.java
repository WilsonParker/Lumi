package com.graction.developer.lumi.Model.Response;

import com.graction.developer.lumi.Model.weather.Coord;
import com.graction.developer.lumi.Model.weather.Weather;

import java.util.ArrayList;


public class DailyForecast {
    private int cod // Internal parameter
            , cnt; // Number of lines returned by this API call
    private String message; // Internal parameter
    private ArrayList<DailyForecastItem> list;
    private City city;

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<DailyForecastItem> getList() {
        return list;
    }

    public void setList(ArrayList<DailyForecastItem> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("DailyForecast [\ncod=" + cod + "\n, cnt=" + cnt + "\n, message=" + message + "\n, list=");
        if (list!= null)
            for (DailyForecastItem item : list)
            builder.append("\n" + item);
        builder.append("\n, city=" + city + "\n]");
        return builder.toString();
    }

    class City {
        private long id // City ID
                , populationo; //
        private String name // City name
                , country; // City code (GB, JP etc)
        private Coord coord;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public long getPopulationo() {
            return populationo;
        }

        public void setPopulationo(long populationo) {
            this.populationo = populationo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public Coord getCoord() {
            return coord;
        }

        public void setCoord(Coord coord) {
            this.coord = coord;
        }

        @Override
        public String toString() {
            return "City [id=" + id + ", populationo=" + populationo + ", name=" + name + ", country=" + country
                    + ", coord=" + coord + "]";
        }

    }

    class DailyForecastItem {
        private int humidity // Humidity, %
                , deg // Wind direction, degrees (meteorological)
                , clouds; // Cloudiness, %
        private long dt; // Time of data forecasted
        private double pressure // Atmospheric pressure on the sea level, hPa
                , speed; // Wind speed, Unit Default: meter/sec,
        // Metric:meter/sec, Imperial: miles/hour
        private ArrayList<Weather> weather;

        public int getHumidity() {
            return humidity;
        }

        public void setHumidity(int humidity) {
            this.humidity = humidity;
        }

        public int getDeg() {
            return deg;
        }

        public void setDeg(int deg) {
            this.deg = deg;
        }

        public int getClouds() {
            return clouds;
        }

        public void setClouds(int clouds) {
            this.clouds = clouds;
        }

        public long getDt() {
            return dt;
        }

        public void setDt(long dt) {
            this.dt = dt;
        }

        public double getPressure() {
            return pressure;
        }

        public void setPressure(double pressure) {
            this.pressure = pressure;
        }

        public double getSpeed() {
            return speed;
        }

        public void setSpeed(double speed) {
            this.speed = speed;
        }

        public ArrayList<Weather> getWeather() {
            return weather;
        }

        public void setWeather(ArrayList<Weather> weather) {
            this.weather = weather;
        }

        public Weather getFirstWeather() {
            return weather.get(0);
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("DailyForecastItem [humidity=" + humidity + ", deg=" + deg + ", clouds=" + clouds + ", dt="
                    + dt + ", pressure=" + pressure + ", speed=" + speed + ", weather=");
            if (weather != null)
                for (Weather w : weather)
                    builder.append("\n" + w);
            builder.append("]");
            return builder.toString();
        }

    }

}
