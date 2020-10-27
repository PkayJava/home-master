package com.angkorteam.home.json.hue.sensor.motion.indoor.temperature;

import com.angkorteam.home.json.hue.sensor.motion.indoor.Capabilities;
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
    @SerializedName("ledindication")
    private boolean ledIndication;

    @Expose
    @SerializedName("usertest")
    private boolean userTest;

    @Expose
    @SerializedName("pending")
    private Object[] pending;

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("type")
    private String type;

    @Expose
    @SerializedName("modelid")
    private String modelId;

    @Expose
    @SerializedName("manufacturername")
    private String manufacturerName;

    @Expose
    @SerializedName("productname")
    private String productName;

    @Expose
    @SerializedName("swversion")
    private String softwareVersion;

    @Expose
    @SerializedName("uniqueid")
    private String uniqueId;

    @Expose
    @SerializedName("capabilities")
    private Capabilities capabilities;
    

}
