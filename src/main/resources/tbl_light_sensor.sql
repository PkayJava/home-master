DROP TABLE IF EXISTS tbl_light_sensor;
CREATE TABLE tbl_light_sensor (
    unique_id              VARCHAR(100)  NOT NULL,
    name                   VARCHAR(200)  NULL,
    model_id               VARCHAR(200)  NULL,
    manufacturer_name      VARCHAR(200)  NULL,
    product_name           VARCHAR(200)  NULL,
    software_version       VARCHAR(200)  NULL,
    resource_id            VARCHAR(5)    NOT NULL,

    state_light_level      INT(11)       NOT NULL,
    state_dark             TINYINT(1)    NOT NULL,
    state_daylight         TINYINT(1)    NOT NULL,
    state_last_updated     DATETIME      NOT NULL,

    config_on              TINYINT(1)    NOT NULL,
    config_battery         INT(11)       NOT NULL,
    config_reachable       TINYINT(1)    NOT NULL,
    config_alert           VARCHAR(50)   NOT NULL,
    config_led_indication  TINYINT(1)    NOT NULL,
    config_thold_dark      INT(11)       NOT NULL,
    config_thold_offset    INT(11)       NOT NULL,
    PRIMARY KEY (unique_id)
) ENGINE=InnoDB;

CREATE INDEX light_sensor_001 ON tbl_light_sensor(resource_id);
