package com.angkorteam.home.json.hue.sensor.motion.indoor.motion;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Config {

    @Expose
    @SerializedName("on")
    private boolean on;

    @Expose
    @SerializedName("battery")
    private int battery;

    @Expose
    @SerializedName("reachable")
    private boolean reachable;

    @Expose
    @SerializedName("alert")
    private String alert;

    @Expose
    @SerializedName("sensitivity")
    private int sensitivity;

    @Expose
    @SerializedName("sensitivitymax")
    private int sensitivityMax;

    @Expose
    @SerializedName("ledindication")
    private boolean ledIndication;

    @Expose
    @SerializedName("usertest")
    private boolean userTest;

    @Expose
    @SerializedName("pending")
    private Object[] pending;

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }

    public int getBattery() {
        return battery;
    }

    public void setBattery(int battery) {
        this.battery = battery;
    }

    public boolean isReachable() {
        return reachable;
    }

    public void setReachable(boolean reachable) {
        this.reachable = reachable;
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public int getSensitivity() {
        return sensitivity;
    }

    public void setSensitivity(int sensitivity) {
        this.sensitivity = sensitivity;
    }

    public int getSensitivityMax() {
        return sensitivityMax;
    }

    public void setSensitivityMax(int sensitivityMax) {
        this.sensitivityMax = sensitivityMax;
    }

    public boolean isLedIndication() {
        return ledIndication;
    }

    public void setLedIndication(boolean ledIndication) {
        this.ledIndication = ledIndication;
    }

    public boolean isUserTest() {
        return userTest;
    }

    public void setUserTest(boolean userTest) {
        this.userTest = userTest;
    }

    public Object[] getPending() {
        return pending;
    }

    public void setPending(Object[] pending) {
        this.pending = pending;
    }
}
