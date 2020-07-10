
package com.abdallah.weathery.model.weather_info;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sys {

    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("country")
    @Expose
    private String country;


    @SerializedName("sunrise")
    @Expose
    private Object sunrise;


    @SerializedName("sunset")
    @Expose
    private Object sunset;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Object getSunrise() {
        return sunrise;
    }

    public void setSunrise(Object sunrise) {
        this.sunrise = sunrise;
    }

    public Object getSunset() {
        return sunset;
    }

    public void setSunset(Object sunset) {
        this.sunset = sunset;
    }


}
