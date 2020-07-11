
package com.abdallah.weathery.model.remote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Wind {

    @SerializedName("speed")
    @Expose
    private Object speed;
    @SerializedName("deg")
    @Expose
    private Object deg;

    public Object getSpeed() {
        return speed;
    }

    public void setSpeed(Object speed) {
        this.speed = speed;
    }

    public Object getDeg() {
        return deg;
    }

    public void setDeg(Object deg) {
        this.deg = deg;
    }

}
