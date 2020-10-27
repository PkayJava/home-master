package com.angkorteam.home.json.hue.light.extended.color;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Capabilities {

    @Expose
    @SerializedName("certified")
    private boolean certified;

    @Expose
    @SerializedName("control")
    private Control control;

    @Expose
    @SerializedName("streaming")
    private Streaming streaming;

    public boolean isCertified() {
        return certified;
    }

    public void setCertified(boolean certified) {
        this.certified = certified;
    }

    public Control getControl() {
        return control;
    }

    public void setControl(Control control) {
        this.control = control;
    }

    public Streaming getStreaming() {
        return streaming;
    }

    public void setStreaming(Streaming streaming) {
        this.streaming = streaming;
    }

}
