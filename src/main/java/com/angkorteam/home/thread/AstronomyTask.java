package com.angkorteam.home.thread;

import com.angkorteam.home.json.astronomy.Astronomy;
import com.angkorteam.home.query.InsertQuery;
import com.angkorteam.home.query.SelectQuery;
import com.angkorteam.home.query.UpdateQuery;
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
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class AstronomyTask implements Runnable {

    public static final DateTimeFormatter FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd");

    private static final Logger LOGGER = LoggerFactory.getLogger(AstronomyTask.class);

    private final CloseableHttpClient client;

    private final String apiKey;

    private final String location;

    private final Gson gson;

    private final NamedParameterJdbcTemplate named;

    public AstronomyTask(Gson gson, NamedParameterJdbcTemplate named, CloseableHttpClient client, String apiKey, String location) {
        this.gson = gson;
        this.named = named;
        this.client = client;
        this.apiKey = apiKey;
        this.location = location;
    }

    @Override
    public void run() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);

        SelectQuery selectQuery = new SelectQuery("tbl_astronomy");
        selectQuery.addField("COUNT(astronomy_date)");
        selectQuery.addWhere("astronomy_date = :astronomy_date", FORMATTER.print(tomorrow));

        Boolean tomorrowValue = this.named.queryForObject(selectQuery.toSQL(), selectQuery.toParam(), Boolean.class);
        if (tomorrowValue == null || !tomorrowValue) {
            queryData(this.gson, this.named, this.client, this.apiKey, this.location, tomorrow);
        }
        queryData(this.gson, this.named, this.client, this.apiKey, this.location, today);
    }

    public static void queryData(Gson gson, NamedParameterJdbcTemplate named, CloseableHttpClient client, String apiKey, String location, LocalDate date) {
        RequestBuilder requestBuilder = RequestBuilder.create("GET");
        requestBuilder.setUri("https://api.ipgeolocation.io/astronomy");
        requestBuilder.addParameter("apiKey", apiKey);
        requestBuilder.addParameter("location", location);
        requestBuilder.addParameter("date", FORMATTER.print(date));
        try (CloseableHttpResponse response = client.execute(requestBuilder.build())) {
            SelectQuery selectQuery = new SelectQuery("tbl_astronomy");
            selectQuery.addField("COUNT(astronomy_date)");
            selectQuery.addWhere("astronomy_date = :astronomy_date", FORMATTER.print(date));
            Astronomy astronomy = gson.fromJson(EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8), Astronomy.class);
            Boolean dateValue = named.queryForObject(selectQuery.toSQL(), selectQuery.toParam(), Boolean.class);
            if (dateValue == null || !dateValue) {
                InsertQuery insertQuery = new InsertQuery("tbl_astronomy");
                insertQuery.addValue("astronomy_date = :astronomy_date", FORMATTER.print(date));
                if (astronomy.getSunrise() != null && !":".equals(astronomy.getSunrise())) {
                    insertQuery.addValue("sunrise = :sunrise", astronomy.getSunrise() + ":00");
                }
                if (astronomy.getSunset() != null && !":".equals(astronomy.getSunset())) {
                    insertQuery.addValue("sunset = :sunset", astronomy.getSunset() + ":00");
                }
                insertQuery.addValue("sun_status = :sun_status", astronomy.getSunStatus());
                if (astronomy.getMoonrise() != null && !":".equals(astronomy.getMoonrise())) {
                    insertQuery.addValue("moonrise = :moonrise", astronomy.getMoonrise() + ":00");
                }
                if (astronomy.getMoonset() != null && !":".equals(astronomy.getMoonset())) {
                    insertQuery.addValue("moonset = :moonset", astronomy.getMoonset() + ":00");
                }
                insertQuery.addValue("moon_status = :moon_status", astronomy.getMoonStatus());
                insertQuery.addValue("location = :location", astronomy.getLocation().getLocation());
                named.update(insertQuery.toSQL(), insertQuery.toParam());
            } else {
                UpdateQuery updateQuery = new UpdateQuery("tbl_astronomy");
                updateQuery.addWhere("astronomy_date = :astronomy_date", FORMATTER.print(date));
                if (astronomy.getSunrise() == null || ":".equals(astronomy.getSunrise())) {
                    updateQuery.addValue("sunrise = NULL");
                } else {
                    updateQuery.addValue("sunrise = :sunrise", astronomy.getSunrise() + ":00");
                }
                if (astronomy.getSunset() == null || ":".equals(astronomy.getSunset())) {
                    updateQuery.addValue("sunset = NULL");
                } else {
                    updateQuery.addValue("sunset = :sunset", astronomy.getSunset() + ":00");
                }

                updateQuery.addValue("sun_status = :sun_status", astronomy.getSunStatus());
                if (astronomy.getMoonrise() == null || ":".equals(astronomy.getMoonrise())) {
                    updateQuery.addValue("moonrise = NULL");
                } else {
                    updateQuery.addValue("moonrise = :moonrise", astronomy.getMoonrise() + ":00");
                }
                if (astronomy.getMoonset() == null || ":".equals(astronomy.getMoonset())) {
                    updateQuery.addValue("moonset = NULL");
                } else {
                    updateQuery.addValue("moonset = :moonset", astronomy.getMoonset() + ":00");
                }
                updateQuery.addValue("moon_status = :moon_status", astronomy.getMoonStatus());
                updateQuery.addValue("location = :location", astronomy.getLocation().getLocation());
                named.update(updateQuery.toSQL(), updateQuery.toParam());
            }
        } catch (IOException e) {
            LOGGER.info(e.getMessage());
        }
    }
}
