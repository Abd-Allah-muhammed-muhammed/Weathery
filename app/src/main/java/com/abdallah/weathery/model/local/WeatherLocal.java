package com.abdallah.weathery.model.local;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class WeatherLocal {

    public WeatherLocal() {
    }


    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "name")
    public String areaName;

    @ColumnInfo(name = "main")
    public String main;


     @ColumnInfo(name = "description")
    public String description;


     @ColumnInfo(name = "temp")
    public Double temp;


       @ColumnInfo(name = "pressure")
    public Integer pressure;


       @ColumnInfo(name = "humidity")
    public Integer humidity;

       @ColumnInfo(name = "speed")
    public Double speed;


       @ColumnInfo(name = "sunrise")
    public String sunrise;


         @ColumnInfo(name = "sunset")
    public String sunset;


         @ColumnInfo(name = "timezone")
    public String timezone;

         @ColumnInfo(name = "feelsLike")
    public Double feelsLike;


    public int getId( ) {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAreaName( ) {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getDescription( ) {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getTemp( ) {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Integer getPressure( ) {
        return pressure;
    }

    public void setPressure(Integer pressure) {
        this.pressure = pressure;
    }

    public Integer getHumidity( ) {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Double getSpeed( ) {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public String getSunrise( ) {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset( ) {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public String getTimezone( ) {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public Double getFeelsLike( ) {
        return feelsLike;
    }

    public void setFeelsLike(Double feelsLike) {
        this.feelsLike = feelsLike;
    }

    public String getMain( ) {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }
}
