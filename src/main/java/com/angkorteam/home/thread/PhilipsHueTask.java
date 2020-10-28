package com.angkorteam.home.thread;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

public class PhilipsHueTask implements Runnable {

    public static final String NAME = "philips-hue.json";

    private static final Logger LOGGER = LoggerFactory.getLogger(PhilipsHueTask.class);

    private final CloseableHttpClient client;

    private final String ip;

    private final String username;

    private final Gson gson;

    private final NamedParameterJdbcTemplate named;

    public PhilipsHueTask(NamedParameterJdbcTemplate named, Gson gson, CloseableHttpClient client, String ip, String username) {
        this.named = named;
        this.gson = gson;
        this.client = client;
        this.ip = ip;
        this.username = username;
    }

    @Override
    public void run() {
        queryLight(this.named, this.gson, this.client, this.ip, this.username);
    }

    public static void queryLight(NamedParameterJdbcTemplate named, Gson gson, CloseableHttpClient client, String hub, String username) {
        RequestBuilder requestBuilder = RequestBuilder.create("GET");
        requestBuilder.setUri("http://" + hub + "/api/" + username);

        try (CloseableHttpResponse response = client.execute(requestBuilder.build())) {
            Type objectType = new TypeToken<Map<String, Object>>() {
            }.getType();
            Map<String, Object> philipsHueObject = gson.fromJson(EntityUtils.toString(response.getEntity()), objectType);

            Map<String, Object> lightsObject = (Map<String, Object>) philipsHueObject.get("lights");
            Map<String, Object> sensorsObject = (Map<String, Object>) philipsHueObject.get("sensors");

            for (Map.Entry<String, Object> lightEntry : lightsObject.entrySet()) {
                Map<String, Object> lightObject = (Map<String, Object>) lightEntry.getValue();
                String resource_id = (String) lightEntry.getKey();
                String type = (String) lightObject.get("type");
                String unique_id = (String) lightObject.get("uniqueid");
                String software_version = (String) lightObject.get("swversion");
                String software_config_id = (String) lightObject.get("swconfigid");
                String product_id = (String) lightObject.get("productid");
                String name = (String) lightObject.get("name");
                String model_id = (String) lightObject.get("modelid");
                String manufacturer_name = (String) lightObject.get("manufacturername");
                String product_name = (String) lightObject.get("productname");
                boolean state_on = (boolean) ((Map<String, Object>) lightObject.get("state")).get("on");
                int state_bri = (int) ((Map<String, Object>) lightObject.get("state")).get("bri");
                int state_hue = (int) ((Map<String, Object>) lightObject.get("state")).get("hue");
                int state_sat = (int) ((Map<String, Object>) lightObject.get("state")).get("sat");
                String state_effect = (String) ((Map<String, Object>) lightObject.get("state")).get("effect");
                double state_xy_x = ((float[]) ((Map<String, Object>) lightObject.get("state")).get("xy"))[0];
                double state_xy_y = ((float[]) ((Map<String, Object>) lightObject.get("state")).get("xy"))[1];
                int state_ct = (int) ((Map<String, Object>) lightObject.get("state")).get("ct");
                String state_alert = (String) ((Map<String, Object>) lightObject.get("state")).get("alert");
                String state_color_mode = (String) ((Map<String, Object>) lightObject.get("state")).get("colormode");
                String mode = (String) ((Map<String, Object>) lightObject.get("state")).get("mode");
                String state_reachable = (String) ((Map<String, Object>) lightObject.get("state")).get("reachable");
            }
        } catch (IOException e) {
            LOGGER.info(e.getMessage());
        }
    }

}
