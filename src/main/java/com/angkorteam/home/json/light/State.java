package com.angkorteam.home.json.light;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class State {

    @Expose
    @SerializedName("on")
    private boolean on;

    @Expose
    @SerializedName("bri")
    private int bri;

    @Expose
    @SerializedName("hue")
    private int hue;

    @Expose
    @SerializedName("sat")
    private int sat;

    @Expose
    @SerializedName("effect")
    private String effect;

    @Expose
    @SerializedName("xy")
    private double[] xy;

    @Expose
    @SerializedName("ct")
    private int ct;

    @Expose
    @SerializedName("alert")
    private String alert;

    @Expose
    @SerializedName("colormode")
    private String colorMode;

    @Expose
    @SerializedName("mode")
    private String mode;

    @Expose
    @SerializedName("reachable")
    private boolean reachable;

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }

    public int getBri() {
        return bri;
    }

    public void setBri(int bri) {
        this.bri = bri;
    }

    public int getHue() {
        return hue;
    }

    public void setHue(int hue) {
        this.hue = hue;
    }

    public int getSat() {
        return sat;
    }

    public void setSat(int sat) {
        this.sat = sat;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public double[] getXy() {
        return xy;
    }

    public void setXy(double[] xy) {
        this.xy = xy;
    }

    public int getCt() {
        return ct;
    }

    public void setCt(int ct) {
        this.ct = ct;
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public String getColorMode() {
        return colorMode;
    }

    public void setColorMode(String colorMode) {
        this.colorMode = colorMode;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public boolean isReachable() {
        return reachable;
    }

    public void setReachable(boolean reachable) {
        this.reachable = reachable;
    }

}
