package com.angkorteam.home.json.hue.sensor.motion.indoor.temperature;

import com.angkorteam.home.json.hue.SoftwareUpdate;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class State {

    @Expose
    @SerializedName("temperature")
    private int temperature;

    @Expose
    @SerializedName("lastupdated")
    private String lastUpdated;

}
