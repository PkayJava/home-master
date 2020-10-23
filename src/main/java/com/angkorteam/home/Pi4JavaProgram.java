//package com.angkorteam.pi4.java;
//
//import com.mysql.cj.jdbc.Driver;
//import com.pi4j.io.gpio.GpioController;
//import com.pi4j.io.gpio.GpioFactory;
//import com.pi4j.io.gpio.event.GpioPinAnalogValueChangeEvent;
//import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
//import com.pi4j.io.gpio.event.GpioPinListenerAnalog;
//import com.pi4j.io.gpio.event.GpioPinListenerDigital;
//import org.apache.catalina.LifecycleEvent;
//import org.apache.catalina.LifecycleListener;
//import org.apache.catalina.LifecycleState;
//import org.apache.catalina.core.StandardServer;
//import org.apache.commons.dbcp2.BasicDataSource;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.jdbc.core.JdbcTemplate;
//
//import java.sql.SQLException;
//
//public class Pi4JavaProgram implements LifecycleListener, GpioPinListenerDigital, GpioPinListenerAnalog {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(Pi4JavaProgram.class);
//
//    private GpioController gpio;
//
//    private Thread gpioThread;
//
//    private JdbcTemplate jdbcTemplate;
//
//    private BasicDataSource dataSource;
//
//    @Override
//    public void lifecycleEvent(LifecycleEvent event) {
//        if (event.getSource() != null) {
//            if (event.getSource() instanceof StandardServer) {
//                if (event.getLifecycle().getState() == LifecycleState.INITIALIZING) {
//                    this.gpio = GpioFactory.getInstance();
//                    this.dataSource = new BasicDataSource();
//                    this.dataSource.setDriverClassName(Driver.class.getName());
//                    this.dataSource.setUsername("root");
//                    this.dataSource.setPassword("password");
//                    this.dataSource.setUrl("jdbc:mysql://192.168.1.6:3306/pi4");
//                    this.jdbcTemplate = new JdbcTemplate(this.dataSource);
//                    this.gpioThread = new Thread(new GpioThread(this.jdbcTemplate, this.gpio, this, this));
//                    LOGGER.info("INITIALIZED");
//                } else if (event.getLifecycle().getState() == LifecycleState.STARTING) {
//                    this.gpioThread.start();
//                    LOGGER.info("STARTED");
//                } else if (event.getLifecycle().getState() == LifecycleState.STOPPING) {
//                    this.gpioThread.interrupt();
//                    LOGGER.info("STOPPED");
//                } else if (event.getLifecycle().getState() == LifecycleState.DESTROYING) {
//                    this.gpio.shutdown();
//                    try {
//                        this.dataSource.close();
//                    } catch (SQLException e) {
//                    }
//                    LOGGER.info("DESTROYED");
//                }
//            }
//        }
//    }
//
//    @Override
//    public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
//        LOGGER.info("digital event");
//        LOGGER.info("event [name] {} [is low] {} [is high] {} [edge] {} [event type] {}", event.getState().getName(), event.getState().isLow(), event.getState().isHigh(), event.getEdge().getName(), event.getEventType().name());
//    }
//
//    @Override
//    public void handleGpioPinAnalogValueChangeEvent(GpioPinAnalogValueChangeEvent event) {
//        LOGGER.info("analog event");
//    }
//}
