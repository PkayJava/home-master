package com.angkorteam.home.json.hue.sensor.motion.indoor.light;

import com.angkorteam.home.json.hue.SoftwareUpdate;
import com.angkorteam.home.json.hue.sensor.motion.indoor.Capabilities;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IndoorLightSensor {

    @Expose
    @SerializedName("state")
    private State state;

    @Expose
    @SerializedName("swupdate")
    private SoftwareUpdate softwareUpdate;

    @Expose
    @SerializedName("config")
    private Config config;

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
