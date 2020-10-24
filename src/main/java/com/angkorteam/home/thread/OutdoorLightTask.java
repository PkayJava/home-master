package com.angkorteam.home.thread;

import com.angkorteam.home.json.app.Home;
import com.angkorteam.home.json.astronomy.Astronomy;
import com.angkorteam.home.json.light.Light;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class OutdoorLightTask implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(OutdoorLightTask.class);

    private static final DateTimeFormatter FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd");

    private final Gson gson;

    private final CloseableHttpClient client;

    private final String hub;

    private final String username;

    public OutdoorLightTask(CloseableHttpClient client, String hub, String username) {
        this.gson = new GsonBuilder().create();
        this.client = client;
        this.hub = hub;
        this.username = username;
    }

    @Override
    public void run() {
        File tempWorkspace = FileUtils.getTempDirectory();
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);

        File todayFile = new File(tempWorkspace, FORMATTER.print(today) + ".json");
        File yesterdayFile = new File(tempWorkspace, FORMATTER.print(yesterday) + ".json");
        File homeFile = new File("conf/home.json");
        File hueFile = new File(tempWorkspace, "hue.json");

        FileUtils.deleteQuietly(yesterdayFile);

        if (hueFile.exists() && homeFile.exists() && todayFile.exists()) {
            try {
                Home home = gson.fromJson(FileUtils.readFileToString(homeFile, StandardCharsets.UTF_8), Home.class);

                Type type = new TypeToken<Map<String, Light>>() {
                }.getType();
                Map<String, Light> lights = gson.fromJson(FileUtils.readFileToString(hueFile, StandardCharsets.UTF_8), type);

                LocalTime now = LocalTime.now();
                Astronomy response = gson.fromJson(FileUtils.readFileToString(todayFile, StandardCharsets.UTF_8), Astronomy.class);
                int currentTime = now.getHourOfDay() * 60 + now.getMinuteOfHour();
                String sunrise = response.getSunrise();
                int sunriseTime = 0;
                boolean hasSunrise;
                if (sunrise != null && !"".equals(sunrise) & !"-:-".equals(sunrise)) {
                    String[] time = StringUtils.split(sunrise, ':');
                    sunriseTime = (Integer.parseInt(time[0]) * 60) + Integer.parseInt(time[1]);
                    hasSunrise = true;
                }

                String sunset = response.getSunset();
                int sunsetTime = 0;
                boolean hasSunset;
                if (sunset != null && !"".equals(sunset) & !"-:-".equals(sunset)) {
                    String[] time = StringUtils.split(sunset, ':');
                    sunsetTime = (Integer.parseInt(time[0]) * 60) + Integer.parseInt(time[1]);
                    hasSunset = true;
                }

                String moonrise = response.getMoonrise();
                int moonriseTime = 0;
                boolean hasMoonrise;
                if (moonrise != null && !"".equals(moonrise) & !"-:-".equals(moonrise)) {
                    String[] time = StringUtils.split(moonrise, ':');
                    moonriseTime = (Integer.parseInt(time[0]) * 60) + Integer.parseInt(time[1]);
                    hasMoonrise = true;
                }

                String moonset = response.getMoonset();
                int moonsetTime = 0;
                boolean hasMoonset;
                if (moonset != null && !"".equals(moonset) & !"-:-".equals(moonset)) {
                    String[] time = StringUtils.split(moonset, ':');
                    moonsetTime = (Integer.parseInt(time[0]) * 60) + Integer.parseInt(time[1]);
                    hasMoonset = true;
                }

                for (Map.Entry<String, Light> linker : lights.entrySet()) {
                    String id = linker.getKey();
                    Light light = linker.getValue();
                    for (String uniqueId : home.getOutdoor()) {
                        if (uniqueId.equals(light.getUniqueId())) {
                            if (light.getState().isReachable()) {
                                if (currentTime > sunriseTime && currentTime < sunsetTime) {
                                    turnOff(this.gson, this.client, this.hub, this.username, id);
                                } else {
                                    int bri = 0;
                                    if (currentTime <= sunriseTime) {
                                        bri = Math.min((sunriseTime - currentTime) * 3, 254);
                                    }
                                    if (currentTime >= sunriseTime) {
                                        bri = Math.min((currentTime - sunriseTime) * 3, 254);
                                    }
                                    Map<String, Object> params = new HashMap<>();
                                    params.put("on", true);
                                    params.put("bri", bri);
                                    params.put("xy", xy(1.0f, 1.0f, 1.0f));
                                    turnOn(this.gson, this.client, this.hub, this.username, id, params);
                                }
                            }
                            break;
                        }
                    }
                }
            } catch (IOException e) {
                LOGGER.info(e.getMessage());
            }
        }
    }

    public static float[] xy(float red, float green, float blue) {
        float X = red * 0.649926f + green * 0.103455f + blue * 0.197109f;
        float Y = red * 0.234327f + green * 0.743075f + blue * 0.022598f;
        float Z = red * 0.0000000f + green * 0.053077f + blue * 1.035763f;
        float x = X / (X + Y + Z);
        float y = Y / (X + Y + Z);
        return new float[]{x, y};
    }

    public static void turnOn(Gson gson, CloseableHttpClient client, String hub, String username, String light, Map<String, Object> params) {
        EntityBuilder entityBuilder = EntityBuilder.create();
        entityBuilder.setContentEncoding(StandardCharsets.UTF_8.name());
        entityBuilder.setContentType(ContentType.APPLICATION_JSON);
        entityBuilder.setText(gson.toJson(params));

        RequestBuilder requestBuilder = RequestBuilder.create("PUT");
        requestBuilder.setUri("http://" + hub + "/api/" + username + "/lights/" + light + "/state");
        requestBuilder.setCharset(StandardCharsets.UTF_8);
        requestBuilder.setHeader("content-type", "application/json");
        requestBuilder.setEntity(entityBuilder.build());

        try (CloseableHttpResponse response = client.execute(requestBuilder.build())) {
            EntityUtils.consume(response.getEntity());
        } catch (IOException e) {
            LOGGER.info(e.getMessage());
        }
    }

    public static void turnOff(Gson gson, CloseableHttpClient client, String hub, String username, String light) {
        Map<String, Object> entity = new HashMap<>();
        entity.put("on", false);

        EntityBuilder entityBuilder = EntityBuilder.create();
        entityBuilder.setContentEncoding(StandardCharsets.UTF_8.name());
        entityBuilder.setContentType(ContentType.APPLICATION_JSON);
        entityBuilder.setText(gson.toJson(entity));

        RequestBuilder requestBuilder = RequestBuilder.create("PUT");
        requestBuilder.setUri("http://" + hub + "/api/" + username + "/lights/" + light + "/state");
        requestBuilder.setCharset(StandardCharsets.UTF_8);
        requestBuilder.setHeader("content-type", "application/json");
        requestBuilder.setEntity(entityBuilder.build());

        try (CloseableHttpResponse response = client.execute(requestBuilder.build())) {
            EntityUtils.consume(response.getEntity());
        } catch (IOException e) {
            LOGGER.info(e.getMessage());
        }
    }
}
