package com.angkorteam.home.json.app;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Home {

    @Expose
    @SerializedName("outdoor")
    private String[] outdoor;

    public String[] getOutdoor() {
        return outdoor;
    }

    public void setOutdoor(String[] outdoor) {
        this.outdoor = outdoor;
    }

}
