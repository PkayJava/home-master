package com.angkorteam.home.json.hue.sensor.motion.indoor.light;

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
    @SerializedName("tholddark")
    private int tholdDark;

    @Expose
    @SerializedName("tholdoffset")
    private int tholdOffset;

    @Expose
    @SerializedName("ledindication")
    private boolean ledIndication;

    @Expose
    @SerializedName("usertest")
    private boolean userTest;

    @Expose
    @SerializedName("pending")
    private Object[] pending;
}
