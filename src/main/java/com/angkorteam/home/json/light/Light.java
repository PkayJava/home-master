package com.angkorteam.home.json.light;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Light {

    @Expose
    @SerializedName("state")
    private State state;

    @Expose
    @SerializedName("swupdate")
    private SoftwareUpdate softwareUpdate;

    @Expose
    @SerializedName("type")
    private String type;

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("modelid")
    private String modelId;

    @Expose
    @SerializedName("manufacturername")
    private String manufacturerName;

    @Expose
    @SerializedName("productname")
    private String productName;

    @Expose
    @SerializedName("capabilities")
    private Capabilities capabilities;

    @Expose
    @SerializedName("config")
    private Config config;

    @Expose
    @SerializedName("uniqueid")
    private String uniqueId;

    @Expose
    @SerializedName("swversion")
    private String softwareVersion;

    @Expose
    @SerializedName("swconfigid")
    private String softwareConfigId;

    @Expose
    @SerializedName("productid")
    private String productId;

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public SoftwareUpdate getSoftwareUpdate() {
        return softwareUpdate;
    }

    public void setSoftwareUpdate(SoftwareUpdate softwareUpdate) {
        this.softwareUpdate = softwareUpdate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Capabilities getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(Capabilities capabilities) {
        this.capabilities = capabilities;
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getSoftwareVersion() {
        return softwareVersion;
    }

    public void setSoftwareVersion(String softwareVersion) {
        this.softwareVersion = softwareVersion;
    }

    public String getSoftwareConfigId() {
        return softwareConfigId;
    }

    public void setSoftwareConfigId(String softwareConfigId) {
        this.softwareConfigId = softwareConfigId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

}
