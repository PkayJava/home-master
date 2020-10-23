//package com.angkorteam.pi4.java;
//
//import com.pi4j.io.gpio.GpioPinDigitalOutput;
//import org.apache.commons.lang3.RandomUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//public class RelayThread implements Runnable {
//    private static final Logger LOGGER = LoggerFactory.getLogger(RelayThread.class);
//
//    public RelayThread(GpioPinDigitalOutput pin) {
//        this.pin = pin;
//    }
//
//    private final GpioPinDigitalOutput pin;
//
//    @Override
//    public void run() {
//        while (true) {
//            try {
//                if (pin.isLow()) {
//                    pin.high();
//                } else {
//                    pin.low();
//                }
//                Thread.sleep(RandomUtils.nextInt(200, 1000));
//            } catch (InterruptedException e) {
//                LOGGER.info(this.pin.getName() + " " + e.getMessage());
//                this.pin.high();
//                return;
//            }
//        }
//    }
//}
