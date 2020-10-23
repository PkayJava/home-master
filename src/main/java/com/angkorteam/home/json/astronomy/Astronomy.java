package com.angkorteam.home.json.astronomy;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Astronomy {

    @Expose
    @SerializedName("location")
    private Location location;

    @Expose
    @SerializedName("date")
    private String date;

    @Expose
    @SerializedName("current_time")
    private String currentTime;

    @Expose
    @SerializedName("sunrise")
    private String sunrise;

    @Expose
    @SerializedName("sunset")
    private String sunset;

    @Expose
    @SerializedName("sun_status")
    private String sunStatus;

    @Expose
    @SerializedName("solar_noon")
    private String solarNoon;

    @Expose
    @SerializedName("day_length")
    private String dayLength;

    @Expose
    @SerializedName("sun_altitude")
    private Double sunAltitude;

    @Expose
    @SerializedName("sun_distance")
    private Double sunDistance;

    @Expose
    @SerializedName("sun_azimuth")
    private Double sunAzimuth;

    @Expose
    @SerializedName("moonrise")
    private String moonrise;

    @Expose
    @SerializedName("moonset")
    private String moonset;

    @Expose
    @SerializedName("moon_status")
    private String moonStatus;

    @Expose
    @SerializedName("moon_altitude")
    private Double moonAltitude;

    @Expose
    @SerializedName("moon_distance")
    private Double moonDistance;

    @Expose
    @SerializedName("moon_azimuth")
    private Double moonAzimuth;

    @Expose
    @SerializedName("moon_parallactic_angle")
    private Double moonParallacticAngle;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public String getSunStatus() {
        return sunStatus;
    }

    public void setSunStatus(String sunStatus) {
        this.sunStatus = sunStatus;
    }

    public String getSolarNoon() {
        return solarNoon;
    }

    public void setSolarNoon(String solarNoon) {
        this.solarNoon = solarNoon;
    }

    public String getDayLength() {
        return dayLength;
    }

    public void setDayLength(String dayLength) {
        this.dayLength = dayLength;
    }

    public Double getSunAltitude() {
        return sunAltitude;
    }

    public void setSunAltitude(Double sunAltitude) {
        this.sunAltitude = sunAltitude;
    }

    public Double getSunDistance() {
        return sunDistance;
    }

    public void setSunDistance(Double sunDistance) {
        this.sunDistance = sunDistance;
    }

    public Double getSunAzimuth() {
        return sunAzimuth;
    }

    public void setSunAzimuth(Double sunAzimuth) {
        this.sunAzimuth = sunAzimuth;
    }

    public String getMoonrise() {
        return moonrise;
    }

    public void setMoonrise(String moonrise) {
        this.moonrise = moonrise;
    }

    public String getMoonset() {
        return moonset;
    }

    public void setMoonset(String moonset) {
        this.moonset = moonset;
    }

    public String getMoonStatus() {
        return moonStatus;
    }

    public void setMoonStatus(String moonStatus) {
        this.moonStatus = moonStatus;
    }

    public Double getMoonAltitude() {
        return moonAltitude;
    }

    public void setMoonAltitude(Double moonAltitude) {
        this.moonAltitude = moonAltitude;
    }

    public Double getMoonDistance() {
        return moonDistance;
    }

    public void setMoonDistance(Double moonDistance) {
        this.moonDistance = moonDistance;
    }

    public Double getMoonAzimuth() {
        return moonAzimuth;
    }

    public void setMoonAzimuth(Double moonAzimuth) {
        this.moonAzimuth = moonAzimuth;
    }

    public Double getMoonParallacticAngle() {
        return moonParallacticAngle;
    }

    public void setMoonParallacticAngle(Double moonParallacticAngle) {
        this.moonParallacticAngle = moonParallacticAngle;
    }

}
