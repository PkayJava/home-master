package com.angkorteam.home.thread;

import com.angkorteam.home.query.DeleteQuery;
import com.angkorteam.home.query.InsertQuery;
import com.angkorteam.home.query.SelectQuery;
import com.angkorteam.home.query.UpdateQuery;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class PhilipsHueTask implements Runnable {

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
            Map<String, Object> philipsHueObject = gson.fromJson(EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8), objectType);

            Map<String, Object> lightsObject = (Map<String, Object>) philipsHueObject.get("lights");
            Map<String, Object> sensorsObject = (Map<String, Object>) philipsHueObject.get("sensors");

            for (Map.Entry<String, Object> lightEntry : lightsObject.entrySet()) {
                Map<String, Object> lightObject = (Map<String, Object>) lightEntry.getValue();
                String type = (String) lightObject.get("type");
                if ("Extended color light".equals(type)) {
                    String resource_id = lightEntry.getKey();
                    String unique_id = (String) lightObject.get("uniqueid");
                    String software_version = (String) lightObject.get("swversion");
                    String software_config_id = (String) lightObject.get("swconfigid");
                    String product_id = (String) lightObject.get("productid");
                    String name = (String) lightObject.get("name");
                    String model_id = (String) lightObject.get("modelid");
                    String manufacturer_name = (String) lightObject.get("manufacturername");
                    String product_name = (String) lightObject.get("productname");
                    boolean state_on = (boolean) ((Map<String, Object>) lightObject.get("state")).get("on");
                    int state_bri = (int) (double) ((Map<String, Object>) lightObject.get("state")).get("bri");
                    int state_hue = (int) (double) ((Map<String, Object>) lightObject.get("state")).get("hue");
                    int state_sat = (int) (double) ((Map<String, Object>) lightObject.get("state")).get("sat");
                    String state_effect = (String) ((Map<String, Object>) lightObject.get("state")).get("effect");
                    double state_xy_x = ((List<Double>) ((Map<String, Object>) lightObject.get("state")).get("xy")).get(0);
                    double state_xy_y = ((List<Double>) ((Map<String, Object>) lightObject.get("state")).get("xy")).get(1);
                    int state_ct = (int) (double) ((Map<String, Object>) lightObject.get("state")).get("ct");
                    String state_alert = (String) ((Map<String, Object>) lightObject.get("state")).get("alert");
                    String state_color_mode = (String) ((Map<String, Object>) lightObject.get("state")).get("colormode");
                    String state_mode = (String) ((Map<String, Object>) lightObject.get("state")).get("mode");
                    boolean state_reachable = (boolean) ((Map<String, Object>) lightObject.get("state")).get("reachable");

                    SelectQuery selectQuery = new SelectQuery("tbl_extended_color_light");
                    selectQuery.addField("COUNT(unique_id)");
                    selectQuery.addWhere("unique_id = :unique_id", unique_id);

                    Boolean has = named.queryForObject(selectQuery.toSQL(), selectQuery.toParam(), Boolean.class);
                    if (has == null || !has) {
                        // insert
                        DeleteQuery deleteQuery = new DeleteQuery("tbl_extended_color_light");
                        deleteQuery.addWhere("resource_id = :resource_id", resource_id);
                        named.update(deleteQuery.toSQL(), deleteQuery.toParam());

                        InsertQuery insertQuery = new InsertQuery("tbl_extended_color_light");
                        insertQuery.addValue("unique_id = :unique_id", unique_id);
                        insertQuery.addValue("resource_id = :resource_id", resource_id);
                        insertQuery.addValue("software_version = :software_version", software_version);
                        insertQuery.addValue("software_config_id = :software_config_id", software_config_id);
                        insertQuery.addValue("product_id = :product_id", product_id);
                        insertQuery.addValue("name = :name", name);
                        insertQuery.addValue("model_id = :model_id", model_id);
                        insertQuery.addValue("manufacturer_name = :manufacturer_name", manufacturer_name);
                        insertQuery.addValue("product_name = :product_name", product_name);
                        insertQuery.addValue("state_on = :state_on", state_on);
                        insertQuery.addValue("state_bri = :state_bri", state_bri);
                        insertQuery.addValue("state_hue = :state_hue", state_hue);
                        insertQuery.addValue("state_sat = :state_sat", state_sat);
                        insertQuery.addValue("state_effect = :state_effect", state_effect);
                        insertQuery.addValue("state_xy_x = :state_xy_x", state_xy_x);
                        insertQuery.addValue("state_xy_y = :state_xy_y", state_xy_y);
                        insertQuery.addValue("state_ct = :state_ct", state_ct);
                        insertQuery.addValue("state_alert = :state_alert", state_alert);
                        insertQuery.addValue("state_color_mode = :state_color_mode", state_color_mode);
                        insertQuery.addValue("state_mode = :state_mode", state_mode);
                        insertQuery.addValue("state_reachable = :state_reachable", state_reachable);
                        named.update(insertQuery.toSQL(), insertQuery.toParam());
                    } else {
                        // update
                        DeleteQuery deleteQuery = new DeleteQuery("tbl_extended_color_light");
                        deleteQuery.addWhere("resource_id = :resource_id", resource_id);
                        deleteQuery.addWhere("unique_id != :unique_id", unique_id);
                        named.update(deleteQuery.toSQL(), deleteQuery.toParam());

                        UpdateQuery updateQuery = new UpdateQuery("tbl_extended_color_light");
                        updateQuery.addWhere("unique_id = :unique_id", unique_id);
                        updateQuery.addValue("resource_id = :resource_id", resource_id);
                        updateQuery.addValue("software_version = :software_version", software_version);
                        updateQuery.addValue("software_config_id = :software_config_id", software_config_id);
                        updateQuery.addValue("product_id = :product_id", product_id);
                        updateQuery.addValue("name = :name", name);
                        updateQuery.addValue("model_id = :model_id", model_id);
                        updateQuery.addValue("manufacturer_name = :manufacturer_name", manufacturer_name);
                        updateQuery.addValue("product_name = :product_name", product_name);
                        updateQuery.addValue("state_on = :state_on", state_on);
                        updateQuery.addValue("state_bri = :state_bri", state_bri);
                        updateQuery.addValue("state_hue = :state_hue", state_hue);
                        updateQuery.addValue("state_sat = :state_sat", state_sat);
                        updateQuery.addValue("state_effect = :state_effect", state_effect);
                        updateQuery.addValue("state_xy_x = :state_xy_x", state_xy_x);
                        updateQuery.addValue("state_xy_y = :state_xy_y", state_xy_y);
                        updateQuery.addValue("state_ct = :state_ct", state_ct);
                        updateQuery.addValue("state_alert = :state_alert", state_alert);
                        updateQuery.addValue("state_color_mode = :state_color_mode", state_color_mode);
                        updateQuery.addValue("state_mode = :state_mode", state_mode);
                        updateQuery.addValue("state_reachable = :state_reachable", state_reachable);
                        named.update(updateQuery.toSQL(), updateQuery.toParam());
                    }
                }
            }
        } catch (Throwable e) {
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        }
    }

}
