package com.angkorteam.home.json.hue.sensor.motion.indoor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Capabilities {

    @Expose
    @SerializedName("certified")
    private boolean certified;

    @Expose
    @SerializedName("primary")
    private boolean primary;

}
