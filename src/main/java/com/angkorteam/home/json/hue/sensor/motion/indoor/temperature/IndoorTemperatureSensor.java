package com.angkorteam.home.json.hue.sensor.motion.indoor.temperature;

import com.angkorteam.home.json.hue.SoftwareUpdate;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IndoorTemperatureSensor {

    @Expose
    @SerializedName("state")
    private State state;

    @Expose
    @SerializedName("swupdate")
    private SoftwareUpdate softwareUpdate;



}
