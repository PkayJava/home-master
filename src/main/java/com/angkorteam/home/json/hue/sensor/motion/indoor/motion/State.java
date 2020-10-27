package com.angkorteam.home.json.hue.sensor.motion.indoor.motion;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class State {

    @Expose
    @SerializedName("presence")
    private boolean presence;

    @Expose
    @SerializedName("lastupdated")
    private String lastUpdated;

    public boolean isPresence() {
        return presence;
    }

    public void setPresence(boolean presence) {
        this.presence = presence;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

}
