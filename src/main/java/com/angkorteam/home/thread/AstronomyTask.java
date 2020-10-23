package com.angkorteam.home.thread;

import org.apache.commons.io.FileUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class AstronomyTask implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(AstronomyTask.class);

    private static final DateTimeFormatter FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd");

    private final CloseableHttpClient client;

    private final String apiKey;

    private final String location;

    public AstronomyTask(CloseableHttpClient client, String apiKey, String location) {
        this.client = client;
        this.apiKey = apiKey;
        this.location = location;
    }

    @Override
    public void run() {
        File tempWorkspace = FileUtils.getTempDirectory();

        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);

        File tomorrowFile = new File(tempWorkspace, FORMATTER.print(tomorrow) + ".json");
        if (!tomorrowFile.exists()) {
            queryData(this.client, this.apiKey, this.location, tomorrow, tempWorkspace);
        }
    }

    public static void queryData(CloseableHttpClient client, String apiKey, String location, LocalDate date, File outputFolder) {
        File mockFile = new File("conf/astronomy-mock.json");
        File dateFile = new File(outputFolder, FORMATTER.print(date) + ".json");
        if (mockFile.exists()) {
            try {
                FileUtils.copyFile(mockFile, dateFile);
            } catch (IOException e) {
                LOGGER.info(e.getMessage());
            }
        } else {
            RequestBuilder requestBuilder = RequestBuilder.create("GET");
            requestBuilder.setUri("https://api.ipgeolocation.io/astronomy");
            requestBuilder.addParameter("apiKey", apiKey);
            requestBuilder.addParameter("location", location);
            requestBuilder.addParameter("date", FORMATTER.print(date));
            try (CloseableHttpResponse response = client.execute(requestBuilder.build())) {
                FileUtils.writeStringToFile(dateFile, EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8), StandardCharsets.UTF_8);
            } catch (IOException e) {
                LOGGER.info(e.getMessage());
            }
        }
    }
}
