package com.angkorteam.home.thread;

import org.apache.commons.io.FileUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class PhilipsHueTask implements Runnable {

    public static final String NAME = "philips-hue.json";

    private static final Logger LOGGER = LoggerFactory.getLogger(PhilipsHueTask.class);

    private final CloseableHttpClient client;

    private final String hub;

    private final String username;

    public PhilipsHueTask(CloseableHttpClient client, String hub, String username) {
        this.client = client;
        this.hub = hub;
        this.username = username;
    }

    @Override
    public void run() {
        File tempWorkspace = FileUtils.getTempDirectory();
        queryLight(this.client, this.hub, this.username, tempWorkspace);
    }

    public static void queryLight(CloseableHttpClient client, String hub, String username, File outputFolder) {
        File hueFile = new File(outputFolder, PhilipsHueTask.NAME);

        RequestBuilder requestBuilder = RequestBuilder.create("GET");
        requestBuilder.setUri("http://" + hub + "/api/" + username);

        try (CloseableHttpResponse response = client.execute(requestBuilder.build())) {
            FileUtils.writeStringToFile(hueFile, EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8), StandardCharsets.UTF_8);
        } catch (IOException e) {
            LOGGER.info(e.getMessage());
        }
    }

}
