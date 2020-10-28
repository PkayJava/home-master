package com.angkorteam.home.thread;

import com.angkorteam.home.json.astronomy.Astronomy;
import com.google.gson.Gson;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class AstronomyTask implements Runnable {

    public static final DateTimeFormatter FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd");

    private static final Logger LOGGER = LoggerFactory.getLogger(AstronomyTask.class);

    private final CloseableHttpClient client;

    private final String apiKey;

    private final String location;

    private final Gson gson;

    private final NamedParameterJdbcTemplate named;

    private final JdbcTemplate jdbcTemplate;

    public AstronomyTask(Gson gson, JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate named, CloseableHttpClient client, String apiKey, String location) {
        this.gson = gson;
        this.jdbcTemplate = jdbcTemplate;
        this.named = named;
        this.client = client;
        this.apiKey = apiKey;
        this.location = location;
    }

    @Override
    public void run() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);

        Boolean tomorrowValue = this.jdbcTemplate.queryForObject("SELECT COUNT(astronomy_date) FROM tbl_astronomy WHERE astronomy_date = ?", Boolean.class, FORMATTER.print(tomorrow));
        if (tomorrowValue == null || !tomorrowValue) {
            queryData(this.gson, this.jdbcTemplate, this.named, this.client, this.apiKey, this.location, tomorrow);
        }
        queryData(this.gson, this.jdbcTemplate, this.named, this.client, this.apiKey, this.location, today);
    }

    public static void queryData(Gson gson, JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate named, CloseableHttpClient client, String apiKey, String location, LocalDate date) {
        RequestBuilder requestBuilder = RequestBuilder.create("GET");
        requestBuilder.setUri("https://api.ipgeolocation.io/astronomy");
        requestBuilder.addParameter("apiKey", apiKey);
        requestBuilder.addParameter("location", location);
        requestBuilder.addParameter("date", FORMATTER.print(date));
        try (CloseableHttpResponse response = client.execute(requestBuilder.build())) {
            Astronomy astronomy = gson.fromJson(EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8), Astronomy.class);
            Boolean dateValue = jdbcTemplate.queryForObject("SELECT COUNT(astronomy_date) FROM tbl_astronomy WHERE astronomy_date = ?", Boolean.class, FORMATTER.print(date));
            if (dateValue == null || !dateValue) {
                Map<String, Object> params = new HashMap<>();
                params.put("astronomy_date", FORMATTER.print(date));
                if (astronomy.getSunrise() == null || ":".equals(astronomy.getSunrise())) {
                    params.put("sunrise", null);
                } else {
                    params.put("sunrise", astronomy.getSunrise() + ":00");
                }
                if (astronomy.getSunset() == null || ":".equals(astronomy.getSunset())) {
                    params.put("sunset", null);
                } else {
                    params.put("sunset", astronomy.getSunset() + ":00");
                }

                params.put("sun_status", astronomy.getSunStatus());
                if (astronomy.getMoonrise() == null || ":".equals(astronomy.getMoonrise())) {
                    params.put("moonrise", null);
                } else {
                    params.put("moonrise", astronomy.getMoonrise() + ":00");
                }
                if (astronomy.getMoonset() == null || ":".equals(astronomy.getMoonset())) {
                    params.put("moonset", null);
                } else {
                    params.put("moonset", astronomy.getMoonset() + ":00");
                }
                params.put("moon_status", astronomy.getMoonStatus());
                params.put("location", astronomy.getLocation().getLocation());
                named.update("INSERT INTO tbl_astronomy(astronomy_date, sunrise, sunset, sun_status, moonrise, moonset, moon_status, location) VALUES(:astronomy_date, :sunrise, :sunset, :sun_status, :moonrise, :moonset, :moon_status, :location)", params);
            } else {
                Map<String, Object> params = new HashMap<>();
                params.put("astronomy_date", FORMATTER.print(date));
                if (astronomy.getSunrise() == null || ":".equals(astronomy.getSunrise())) {
                    params.put("sunrise", null);
                } else {
                    params.put("sunrise", astronomy.getSunrise() + ":00");
                }
                if (astronomy.getSunset() == null || ":".equals(astronomy.getSunset())) {
                    params.put("sunset", null);
                } else {
                    params.put("sunset", astronomy.getSunset() + ":00");
                }

                params.put("sun_status", astronomy.getSunStatus());
                if (astronomy.getMoonrise() == null || ":".equals(astronomy.getMoonrise())) {
                    params.put("moonrise", null);
                } else {
                    params.put("moonrise", astronomy.getMoonrise() + ":00");
                }
                if (astronomy.getMoonset() == null || ":".equals(astronomy.getMoonset())) {
                    params.put("moonset", null);
                } else {
                    params.put("moonset", astronomy.getMoonset() + ":00");
                }
                params.put("moon_status", astronomy.getMoonStatus());
                params.put("location", astronomy.getLocation().getLocation());
                named.update("UPDATE tbl_astronomy SET sunrise = :sunrise, sunset = :sunset, sun_status = :sun_status, moonrise = :moonrise, moonset = :moonset, moon_status = :moon_status, location = :location WHERE astronomy_date = :astronomy_date", params);
            }
        } catch (IOException e) {
            LOGGER.info(e.getMessage());
        }
    }
}
