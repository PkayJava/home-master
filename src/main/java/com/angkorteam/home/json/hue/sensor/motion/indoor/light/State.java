package com.angkorteam.home.json.hue.sensor.motion.indoor.light;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class State {

    @Expose
    @SerializedName("lightlevel")
    private int lightLevel;

    @Expose
    @SerializedName("dark")
    private boolean dark;

    @Expose
    @SerializedName("daylight")
    private boolean daylight;

    @Expose
    @SerializedName("lastupdated")
    private String lastUpdated;

}
