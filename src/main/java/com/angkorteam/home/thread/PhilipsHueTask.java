package com.angkorteam.home.thread;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.FileUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class PhilipsHueTask implements Runnable {

    public static final String NAME = "philips-hue.json";

    private static final Logger LOGGER = LoggerFactory.getLogger(PhilipsHueTask.class);

    private final CloseableHttpClient client;

    private final String hub;

    private final String username;

    private final Gson gson;

    public PhilipsHueTask(Gson gson, CloseableHttpClient client, String hub, String username) {
        this.gson = gson;
        this.client = client;
        this.hub = hub;
        this.username = username;
    }

    @Override
    public void run() {
        File tempWorkspace = FileUtils.getTempDirectory();
        queryLight(this.gson, this.client, this.hub, this.username, tempWorkspace);
    }

    public static void queryLight(Gson gson, CloseableHttpClient client, String hub, String username, File outputFolder) {
        File hueFile = new File(outputFolder, PhilipsHueTask.NAME);
        File hueReachableStateFile = new File(outputFolder, "philips-hue-reachable-state.json");
        File hueStateFile = new File(outputFolder, "philips-hue-state.json");

        RequestBuilder requestBuilder = RequestBuilder.create("GET");
        requestBuilder.setUri("http://" + hub + "/api/" + username);

        try (CloseableHttpResponse response = client.execute(requestBuilder.build())) {
            Type type = new TypeToken<Map<String, Object>>() {
            }.getType();
            Map<String, Object> philipsHueObject = gson.fromJson(EntityUtils.toString(response.getEntity()), type);
            FileUtils.writeStringToFile(hueFile, gson.toJson(philipsHueObject), StandardCharsets.UTF_8);

            Map<String, Object> lightsObject = (Map<String, Object>) philipsHueObject.get("lights");
            Map<String, Object> sensorsObject = (Map<String, Object>) philipsHueObject.get("sensors");

            {
                Map<String, Object> newPhilipsHueObject = new HashMap<>();

                Map<String, Object> newLightsObject = new HashMap<>();
                newPhilipsHueObject.put("lights", newLightsObject);
                for (Map.Entry<String, Object> lightObject : lightsObject.entrySet()) {
                    Map<String, Object> lightObjectValue = (Map<String, Object>) lightObject.getValue();
                    Map<String, Object> newLightObjectValue = new HashMap<>();
                    newLightObjectValue.put("state", lightObjectValue.get("state"));
                    newLightsObject.put(lightObject.getKey(), newLightObjectValue);
                }

                Map<String, Object> newSensorsObject = new HashMap<>();
                newPhilipsHueObject.put("sensors", newSensorsObject);
                for (Map.Entry<String, Object> sensorObject : sensorsObject.entrySet()) {
                    Map<String, Object> sensorObjectValue = (Map<String, Object>) sensorObject.getValue();
                    Map<String, Object> newSensorObjectValue = new HashMap<>();
                    newSensorObjectValue.put("state", sensorObjectValue.get("state"));
                    newSensorsObject.put(sensorObject.getKey(), newSensorObjectValue);
                }
                FileUtils.writeStringToFile(hueStateFile, gson.toJson(newPhilipsHueObject), StandardCharsets.UTF_8);
            }

            {
                Map<String, Object> newPhilipsHueObject = new HashMap<>();

                Map<String, Object> newLightsObject = new HashMap<>();
                newPhilipsHueObject.put("lights", newLightsObject);
                for (Map.Entry<String, Object> lightObject : lightsObject.entrySet()) {
                    Map<String, Object> lightObjectValue = (Map<String, Object>) lightObject.getValue();
                    Map<String, Object> stateMap = (Map<String, Object>) lightObjectValue.get("state");
                    boolean reachable = (boolean) stateMap.get("reachable");
                    if (reachable) {
                        Map<String, Object> newLightObjectValue = new HashMap<>();
                        newLightObjectValue.put("state", lightObjectValue.get("state"));
                        newLightsObject.put(lightObject.getKey(), newLightObjectValue);
                    }
                }

                Map<String, Object> newSensorsObject = new HashMap<>();
                newPhilipsHueObject.put("sensors", newSensorsObject);
                for (Map.Entry<String, Object> sensorObject : sensorsObject.entrySet()) {
                    Map<String, Object> sensorObjectValue = (Map<String, Object>) sensorObject.getValue();
                    Map<String, Object> configMap = (Map<String, Object>) sensorObjectValue.get("config");
                    boolean reachable = configMap.get("reachable") != null && (boolean) configMap.get("reachable");
                    if (reachable) {
                        Map<String, Object> newSensorObjectValue = new HashMap<>();
                        newSensorObjectValue.put("state", sensorObjectValue.get("state"));
                        newSensorsObject.put(sensorObject.getKey(), newSensorObjectValue);
                    }
                }
                FileUtils.writeStringToFile(hueReachableStateFile, gson.toJson(newPhilipsHueObject), StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            LOGGER.info(e.getMessage());
        }
    }

}
