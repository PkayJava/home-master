package com.angkorteam.home;

import com.angkorteam.home.thread.AstronomyTask;
import com.angkorteam.home.thread.PhilipsHueTask;
import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.core.StandardServer;
import org.apache.commons.io.FileUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BootstrapProgram implements LifecycleListener {

    private static final DateTimeFormatter FORMATTER = DateTimeFormat.forPattern("yyyy/MM/dd");

    private static final Logger LOGGER = LoggerFactory.getLogger(BootstrapProgram.class);

    private String apiKey;

    private String location;

    private String hub;

    private String username;

    private ScheduledExecutorService executor;

    private CloseableHttpClient client;

    @Override
    public void lifecycleEvent(LifecycleEvent event) {
        if (event.getSource() != null) {
            if (event.getSource() instanceof StandardServer) {
                if (event.getLifecycle().getState() == LifecycleState.INITIALIZING) {
                    HttpClientBuilder clientBuilder = HttpClientBuilder.create();
                    this.client = clientBuilder.build();

                    this.executor = Executors.newScheduledThreadPool(2);

                    File tempWorkspace = FileUtils.getTempDirectory();

                    LocalDate today = LocalDate.now();
                    File todayFile = new File(tempWorkspace, FORMATTER.print(today) + ".json");
                    if (!todayFile.exists()) {
                        AstronomyTask.queryData(this.client, this.apiKey, this.location, today, tempWorkspace);
                    }
                    this.executor.scheduleWithFixedDelay(new AstronomyTask(this.client, this.apiKey, this.location), 1, 1, TimeUnit.HOURS);

                    File hueFile = new File(tempWorkspace, "hue.json");
                    if (!hueFile.exists()) {
                        PhilipsHueTask.queryLight(this.client, this.hub, this.username, tempWorkspace);
                    }
                    this.executor.scheduleWithFixedDelay(new PhilipsHueTask(this.client, this.hub, this.username), 1, 1, TimeUnit.SECONDS);

                    // this.executor.scheduleWithFixedDelay(new OutdoorLightTask(this.client, this.hub, this.username), 10, 10, TimeUnit.SECONDS);

                    LOGGER.info("INITIALIZED");
                } else if (event.getLifecycle().getState() == LifecycleState.STARTING) {
                    LOGGER.info("STARTED");
                } else if (event.getLifecycle().getState() == LifecycleState.STOPPING) {
                    this.executor.shutdown();
                    try {
                        this.client.close();
                    } catch (IOException e) {
                        LOGGER.info(e.getMessage());
                    }
                    LOGGER.info("STOPPED");
                } else if (event.getLifecycle().getState() == LifecycleState.DESTROYING) {
                    LOGGER.info("DESTROYED");
                }
            }
        }
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getHub() {
        return hub;
    }

    public void setHub(String hub) {
        this.hub = hub;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
