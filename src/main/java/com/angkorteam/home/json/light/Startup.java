package com.angkorteam.home.json.light;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Startup {

    @Expose
    @SerializedName("mode")
    private String mode;

    @Expose
    @SerializedName("configured")
    private boolean configured;

    @Expose
    @SerializedName("customsettings")
    private CustomSettings customSettings;

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public boolean isConfigured() {
        return configured;
    }

    public void setConfigured(boolean configured) {
        this.configured = configured;
    }

    public CustomSettings getCustomSettings() {
        return customSettings;
    }

    public void setCustomSettings(CustomSettings customSettings) {
        this.customSettings = customSettings;
    }

}
