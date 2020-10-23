package com.angkorteam.home.json.light;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ct {

    @Expose
    @SerializedName("min")
    private int min;

    @Expose
    @SerializedName("max")
    private int max;

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

}
