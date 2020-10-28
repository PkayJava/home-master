package com.angkorteam.home;

import com.angkorteam.home.query.SelectQuery;
import com.angkorteam.home.thread.AstronomyTask;
import com.angkorteam.home.thread.PhilipsHueTask;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.core.StandardServer;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BootstrapProgram implements LifecycleListener {

    private static final DateTimeFormatter FORMATTER = DateTimeFormat.forPattern("yyyy/MM/dd");

    private static final Logger LOGGER = LoggerFactory.getLogger(BootstrapProgram.class);

    private String astronomyApiKey;
    private String astronomyLocation;

    private String hueHubIp;
    private String hueHubUsername;

    private String jdbcUrl;
    private String jdbcUsername;
    private String jdbcPassword;
    private String jdbcDriverClassName;

    private ScheduledExecutorService executor;
    private CloseableHttpClient client;
    private BasicDataSource dataSource;
    private NamedParameterJdbcTemplate named;
    private Gson gson;

    @Override
    public void lifecycleEvent(LifecycleEvent event) {
        if (event.getSource() != null) {
            if (event.getSource() instanceof StandardServer) {
                if (event.getLifecycle().getState() == LifecycleState.INITIALIZING) {
                    this.gson = new GsonBuilder().setPrettyPrinting().create();
                    this.dataSource = new BasicDataSource();
                    this.dataSource.setUrl(this.jdbcUrl);
                    this.dataSource.setUsername(this.jdbcUsername);
                    this.dataSource.setPassword(this.jdbcPassword);
                    this.dataSource.setDriverClassName(this.jdbcDriverClassName);
                    this.named = new NamedParameterJdbcTemplate(this.dataSource);

                    HttpClientBuilder clientBuilder = HttpClientBuilder.create();
                    this.client = clientBuilder.build();

                    this.executor = Executors.newScheduledThreadPool(2);

                    LocalDate today = LocalDate.now();

                    SelectQuery selectQuery = new SelectQuery("tbl_astronomy");
                    selectQuery.addField("COUNT(astronomy_date)");
                    selectQuery.addWhere("astronomy_date = :astronomy_date", AstronomyTask.FORMATTER.print(today));

                    Boolean todayValue = this.named.queryForObject(selectQuery.toSQL(), selectQuery.toParam(), Boolean.class);
                    if (todayValue == null || !todayValue) {
                        AstronomyTask.queryData(this.gson, this.named, this.client, this.astronomyApiKey, this.astronomyLocation, today);
                    }
                    this.executor.scheduleWithFixedDelay(new AstronomyTask(this.gson, this.named, this.client, this.astronomyApiKey, this.astronomyLocation), 1, 1, TimeUnit.HOURS);

                    this.executor.scheduleWithFixedDelay(new PhilipsHueTask(this.named, this.gson, this.client, this.hueHubIp, this.hueHubUsername), 1, 1, TimeUnit.SECONDS);

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
                    try {
                        this.dataSource.close();
                    } catch (SQLException e) {
                        LOGGER.info(e.getMessage());
                    }
                    LOGGER.info("STOPPED");
                } else if (event.getLifecycle().getState() == LifecycleState.DESTROYING) {
                    LOGGER.info("DESTROYED");
                }
            }
        }
    }

    public String getAstronomyApiKey() {
        return astronomyApiKey;
    }

    public void setAstronomyApiKey(String astronomyApiKey) {
        this.astronomyApiKey = astronomyApiKey;
    }

    public String getAstronomyLocation() {
        return astronomyLocation;
    }

    public void setAstronomyLocation(String astronomyLocation) {
        this.astronomyLocation = astronomyLocation;
    }

    public String getHueHubIp() {
        return hueHubIp;
    }

    public void setHueHubIp(String hueHubIp) {
        this.hueHubIp = hueHubIp;
    }

    public String getHueHubUsername() {
        return hueHubUsername;
    }

    public void setHueHubUsername(String hueHubUsername) {
        this.hueHubUsername = hueHubUsername;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getJdbcUsername() {
        return jdbcUsername;
    }

    public void setJdbcUsername(String jdbcUsername) {
        this.jdbcUsername = jdbcUsername;
    }

    public String getJdbcPassword() {
        return jdbcPassword;
    }

    public void setJdbcPassword(String jdbcPassword) {
        this.jdbcPassword = jdbcPassword;
    }

    public String getJdbcDriverClassName() {
        return jdbcDriverClassName;
    }

    public void setJdbcDriverClassName(String jdbcDriverClassName) {
        this.jdbcDriverClassName = jdbcDriverClassName;
    }

}
