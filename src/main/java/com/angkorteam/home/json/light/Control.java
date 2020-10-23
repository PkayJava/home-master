package com.angkorteam.home.json.light;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Control {

    @Expose
    @SerializedName("mindimlevel")
    private int minDimLevel;

    @Expose
    @SerializedName("maxlumen")
    private int maxLumen;

    @Expose
    @SerializedName("colorgamuttype")
    private String colorGamutType;

    @Expose
    @SerializedName("colorgamut")
    private List<double[]> colorGamut;

    @Expose
    @SerializedName("ct")
    private Ct ct;

    public int getMinDimLevel() {
        return minDimLevel;
    }

    public void setMinDimLevel(int minDimLevel) {
        this.minDimLevel = minDimLevel;
    }

    public int getMaxLumen() {
        return maxLumen;
    }

    public void setMaxLumen(int maxLumen) {
        this.maxLumen = maxLumen;
    }

    public String getColorGamutType() {
        return colorGamutType;
    }

    public void setColorGamutType(String colorGamutType) {
        this.colorGamutType = colorGamutType;
    }

    public List<double[]> getColorGamut() {
        return colorGamut;
    }

    public void setColorGamut(List<double[]> colorGamut) {
        this.colorGamut = colorGamut;
    }

    public Ct getCt() {
        return ct;
    }

    public void setCt(Ct ct) {
        this.ct = ct;
    }

}
