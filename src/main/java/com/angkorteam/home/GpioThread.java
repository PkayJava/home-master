//package com.angkorteam.pi4.java;
//
//import com.pi4j.io.gpio.*;
//import com.pi4j.io.gpio.event.GpioPinListenerAnalog;
//import com.pi4j.io.gpio.event.GpioPinListenerDigital;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.jdbc.core.JdbcTemplate;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class GpioThread implements Runnable {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(GpioThread.class);
//
//    private final JdbcTemplate jdbcTemplate;
//
//    private final GpioController gpioBoard;
//
//    private final Map<Integer, GpioPin> gpioPorts;
//
//    private final GpioPinListenerDigital digitalListener;
//
//    private final GpioPinListenerAnalog analogListener;
//
//    public GpioThread(JdbcTemplate jdbcTemplate, GpioController gpioBoard, GpioPinListenerDigital digitalListener, GpioPinListenerAnalog analogListener) {
//        this.jdbcTemplate = jdbcTemplate;
//        this.gpioBoard = gpioBoard;
//        this.analogListener = analogListener;
//        this.digitalListener = digitalListener;
//        this.gpioPorts = new HashMap<>();
//    }
//
//    protected void firstRun() {
//        List<Map<String, Object>> pins = this.jdbcTemplate.queryForList("select * from gpio where ignored = 'NO' order by address asc");
//        List<String> names = new ArrayList<>();
//        for (Map<String, Object> pin : pins) {
//            Integer integer = (Integer) pin.get("address");
//            String name = (String) pin.get("name");
//            String type = (String) pin.get("type");
//            String value = (String) pin.get("value");
//            try {
//                Pin gpioPort = RaspiPin.getPinByAddress(integer);
//                if (PinMode.DIGITAL_OUTPUT.getName().equals(type)) {
//                    if (PinState.LOW.getName().equals(value)) {
//                        GpioPin gpio = this.gpioBoard.provisionPin(gpioPort, PinMode.DIGITAL_OUTPUT);
//                        ((GpioPinDigitalOutput) gpio).low();
//                        this.gpioPorts.put(gpioPort.getAddress(), gpio);
//                    } else if (PinState.HIGH.getName().equals(value)) {
//                        GpioPin gpio = this.gpioBoard.provisionPin(gpioPort, PinMode.DIGITAL_OUTPUT);
//                        ((GpioPinDigitalOutput) gpio).high();
//                        this.gpioPorts.put(gpioPort.getAddress(), gpio);
//                    }
//                } else if (PinMode.PWM_OUTPUT.getName().equals(type)) {
//                    if (PinState.LOW.getName().equals(value)) {
//                        GpioPin gpio = this.gpioBoard.provisionPin(gpioPort, PinMode.PWM_OUTPUT);
//                        this.gpioPorts.put(gpioPort.getAddress(), gpio);
//                    } else if (PinState.HIGH.getName().equals(value)) {
//                        GpioPin gpio = this.gpioBoard.provisionPin(gpioPort, PinMode.PWM_OUTPUT);
//                        this.gpioPorts.put(gpioPort.getAddress(), gpio);
//                    }
//                } else if (PinMode.SOFT_PWM_OUTPUT.getName().equals(type)) {
//                    if (PinState.LOW.getName().equals(value)) {
//                        GpioPin gpio = this.gpioBoard.provisionPin(gpioPort, PinMode.SOFT_PWM_OUTPUT);
//                        this.gpioPorts.put(gpioPort.getAddress(), gpio);
//                    } else if (PinState.HIGH.getName().equals(value)) {
//                        GpioPin gpio = this.gpioBoard.provisionPin(gpioPort, PinMode.SOFT_PWM_OUTPUT);
//                        this.gpioPorts.put(gpioPort.getAddress(), gpio);
//                    }
//                } else if (PinMode.DIGITAL_INPUT.getName().equals(type)) {
//                    GpioPin gpio = this.gpioBoard.provisionPin(gpioPort, PinMode.DIGITAL_INPUT);
//                    gpio.addListener((GpioPinListenerDigital) event -> {
//                        if (event.getState().isLow()) {
//                            this.jdbcTemplate.update("update gpio set value = '" + PinState.LOW.getName() + "' where name in (" + event.getPin().getName() + ")");
//                        } else if (event.getState().isHigh()) {
//                            this.jdbcTemplate.update("update gpio set value = '" + PinState.HIGH.getName() + "' where name in (" + event.getPin().getName() + ")");
//                        }
//                        this.digitalListener.handleGpioPinDigitalStateChangeEvent(event);
//                    });
//                    this.gpioPorts.put(gpioPort.getAddress(), gpio);
//                } else {
//                    throw new RuntimeException(type + " is not supported");
//                }
//                names.add("'" + name + "'");
//                LOGGER.info("PIN {} - {} is supported", name, type);
//            } catch (Throwable e) {
//                LOGGER.info("PIN {} - {} is not supported", name, type);
//            }
//        }
//    }
//
//    @Override
//    public void run() {
//        try {
//            firstRun();
//            while (true) {
//                Thread.sleep(1000);
//                List<Map<String, Object>> pins = this.jdbcTemplate.queryForList("select * from gpio where ignored = 'NO' order by address asc");
//                List<String> names = new ArrayList<>();
//                for (Map<String, Object> pin : pins) {
//                    Integer address = (Integer) pin.get("address");
//                    String name = (String) pin.get("name");
//                    String type = (String) pin.get("type");
//                    String value = (String) pin.get("value");
//                    if (this.gpioPorts.get(address) == null) {
//                        if (PinMode.DIGITAL_OUTPUT.getName().equals(type)) {
//                            if (PinState.LOW.getName().equals(value)) {
//                                GpioPinDigitalOutput gpio = this.gpioBoard.provisionDigitalOutputPin(RaspiPin.getPinByAddress(address), PinState.LOW);
//                                this.gpioPorts.put(gpio.getPin().getAddress(), gpio);
//                            } else if (PinState.HIGH.getName().equals(value)) {
//                                GpioPinDigitalOutput gpio = this.gpioBoard.provisionDigitalOutputPin(RaspiPin.getPinByAddress(address), PinState.HIGH);
//                                this.gpioPorts.put(gpio.getPin().getAddress(), gpio);
//                            }
//                        }
//                    } else {
//                        if (PinMode.DIGITAL_OUTPUT.getName().equals(type) && this.gpioPorts.get(address).getMode() == PinMode.DIGITAL_OUTPUT) {
//                            if (PinState.HIGH.getName().equals(value)) {
//                                if (((GpioPinDigitalOutput) this.gpioPorts.get(address)).getState() == PinState.LOW) {
//                                    ((GpioPinDigitalOutput) this.gpioPorts.get(address)).high();
//                                }
//                            } else if (PinState.LOW.getName().equals(value)) {
//                                if (((GpioPinDigitalOutput) this.gpioPorts.get(address)).getState() == PinState.HIGH) {
//                                    ((GpioPinDigitalOutput) this.gpioPorts.get(address)).low();
//                                }
//                            }
//                        }
//                    }
//                    names.add("'" + name + "'");
//                }
//            }
//        } catch (InterruptedException e) {
//        }
//    }
//
//}
