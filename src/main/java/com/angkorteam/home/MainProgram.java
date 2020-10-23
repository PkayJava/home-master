//package com.angkorteam.pi4.java;
//
//import com.mysql.cj.jdbc.Driver;
//import com.pi4j.io.gpio.Pin;
//import com.pi4j.io.gpio.PinMode;
//import com.pi4j.io.gpio.PinState;
//import com.pi4j.io.gpio.RaspiPin;
//import org.apache.commons.dbcp2.BasicDataSource;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
//
//import java.sql.SQLException;
//import java.util.HashMap;
//import java.util.Map;
//
//public class MainProgram {
//
//    public static void main(String[] args) throws SQLException {
//        // https://api.ipgeolocation.io/astronomy?apiKey=477599c7c931454abffc0c86eac6687e&location=Phnom%20Penh
//        BasicDataSource dataSource = new BasicDataSource();
//        dataSource.setDriverClassName(Driver.class.getName());
//        dataSource.setUsername("root");
//        dataSource.setPassword("password");
//        dataSource.setUrl("jdbc:mysql://192.168.1.6:3306/pi4");
//        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
//        jdbcTemplate.update("delete from gpio");
//        NamedParameterJdbcTemplate named = new NamedParameterJdbcTemplate(jdbcTemplate);
//        for (Pin pin : RaspiPin.allPins()) {
//            if (pin.getName().equals("GPIO 17")
//                    || pin.getName().equals("GPIO 18")
//                    || pin.getName().equals("GPIO 19")
//                    || pin.getName().equals("GPIO 20")) {
//                continue;
//            }
//            Map<String, Object> params = new HashMap<>();
//            params.put("address", pin.getAddress());
//            params.put("name", pin.getName());
//            params.put("type", PinMode.DIGITAL_OUTPUT.getName());
//            params.put("value", PinState.LOW.getName());
//            params.put("ignored", "YES");
//            named.update("INSERT INTO gpio(address, name, type, value, ignored) VALUES(:address, :name, :type, :value, :ignored)", params);
//        }
//
//        dataSource.close();
//    }
//
//}
