package com.angkorteam.home.json.hue.light.extended.color;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomSettings {

    @Expose
    @SerializedName("bri")
    private int bri;

    @Expose
    @SerializedName("xy")
    private double[] xy;

    public int getBri() {
        return bri;
    }

    public void setBri(int bri) {
        this.bri = bri;
    }

    public double[] getXy() {
        return xy;
    }

    public void setXy(double[] xy) {
        this.xy = xy;
    }

}
