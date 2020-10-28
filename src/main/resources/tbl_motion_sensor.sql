DROP TABLE IF EXISTS tbl_motion_sensor;
CREATE TABLE tbl_motion_sensor (
    unique_id              VARCHAR(100)  NOT NULL,
    name                   VARCHAR(200)  NULL,
    model_id               VARCHAR(200)  NULL,
    manufacturer_name      VARCHAR(200)  NULL,
    product_name           VARCHAR(200)  NULL,
    software_version       VARCHAR(200)  NULL,
    resource_id            VARCHAR(5)    NOT NULL,

    state_presence         TINYINT(1)    NULL,
    state_last_updated     DATETIME      NULL,

    config_on              TINYINT(1)    NOT NULL,
    config_battery         INT(11)       NOT NULL,
    config_reachable       TINYINT(1)    NOT NULL,
    config_alert           VARCHAR(50)   NOT NULL,
    config_sensitivity     INT(11)       NOT NULL,
    config_sensitivity_max INT(11)       NOT NULL,
    config_led_indication  TINYINT(1)    NOT NULL,
    PRIMARY KEY (unique_id)
) ENGINE=InnoDB;

CREATE INDEX motion_sensor_001 ON tbl_motion_sensor(resource_id);
