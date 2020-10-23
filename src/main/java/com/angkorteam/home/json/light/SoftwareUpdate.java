package com.angkorteam.home.json.light;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SoftwareUpdate {

    @Expose
    @SerializedName("state")
    private String state;

    @Expose
    @SerializedName("lastinstall")
    private String lastInstall;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLastInstall() {
        return lastInstall;
    }

    public void setLastInstall(String lastInstall) {
        this.lastInstall = lastInstall;
    }

}
