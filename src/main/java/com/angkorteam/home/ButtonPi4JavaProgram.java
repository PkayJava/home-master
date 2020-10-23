//package com.angkorteam.pi4.java;
//
//import com.pi4j.io.gpio.*;
//import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
//import com.pi4j.io.gpio.event.GpioPinListenerDigital;
//import org.apache.catalina.LifecycleEvent;
//import org.apache.catalina.LifecycleListener;
//import org.apache.catalina.LifecycleState;
//import org.apache.catalina.core.StandardServer;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//public class ButtonPi4JavaProgram implements LifecycleListener, GpioPinListenerDigital {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(ButtonPi4JavaProgram.class);
//
//    private GpioController gpio;
//
//    private GpioPinDigitalOutput gpio01;
//
//    private boolean toggle;
//
//    @Override
//    public void lifecycleEvent(LifecycleEvent event) {
//        if (event.getSource() != null) {
//            if (event.getSource() instanceof StandardServer) {
//                if (event.getLifecycle().getState() == LifecycleState.INITIALIZING) {
//                    this.gpio = GpioFactory.getInstance();
//                    GpioPinDigitalInput gpio00 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_00, PinPullResistance.PULL_DOWN);
//                    gpio00.addListener(this);
//                    LOGGER.info("INITIALIZED");
//                } else if (event.getLifecycle().getState() == LifecycleState.STARTING) {
//                    LOGGER.info("STARTED");
//                } else if (event.getLifecycle().getState() == LifecycleState.STOPPING) {
//                    this.gpio01.low();
//                    LOGGER.info("STOPPED");
//                } else if (event.getLifecycle().getState() == LifecycleState.DESTROYING) {
//                    this.gpio.shutdown();
//                    LOGGER.info("DESTROYED");
//                }
//            }
//        }
//    }
//
//    @Override
//    public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent e) {
//        LOGGER.info(" --> GPIO PIN STATE CHANGE: {} = {}", e.getPin(), e.getState());
//        if (e.getState() == PinState.HIGH) {
//            LOGGER.info(" --> Clicked : {} = {}", e.getPin(), e.getState());
//            if (this.toggle) {
//                this.gpio01.high();
//            } else {
//                this.gpio01.low();
//            }
//            this.toggle = !this.toggle;
//        }
//    }
//}
