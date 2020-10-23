package com.angkorteam.home.thread;

import com.angkorteam.home.json.astronomy.Astronomy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class OutdoorLightTask implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(OutdoorLightTask.class);

    private static final DateTimeFormatter FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd");

    private final Gson gson;

    private final String username;

    public OutdoorLightTask(String username) {
        this.gson = new GsonBuilder().create();
        this.username = username;
    }

    @Override
    public void run() {
        File tempWorkspace = FileUtils.getTempDirectory();
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);

        File todayFile = new File(tempWorkspace, FORMATTER.print(today) + ".json");
        File yesterdayFile = new File(tempWorkspace, FORMATTER.print(yesterday) + ".json");

        FileUtils.deleteQuietly(yesterdayFile);

        if (todayFile.exists()) {
            try {
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

                if (currentTime > sunriseTime && currentTime < sunsetTime) {
                    // offlight
                } else {
                    // onlight

                }


            } catch (IOException e) {
                LOGGER.info(e.getMessage());
            }
        }
    }

//    public static void turnOn(CloseableHttpClient client, String username, String light){
//
//        http://192.168.1.101/api/HxQiTOofDwRL-FmLX-VIOuJsdJfkS5QE7kpCRZ6g/lights
//    }
//
//    public static void turnOff(CloseableHttpClient client, String username, String light){
//        http://192.168.1.101/api/HxQiTOofDwRL-FmLX-VIOuJsdJfkS5QE7kpCRZ6g/lights
//    }

}
