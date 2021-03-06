DROP TABLE IF EXISTS tbl_extended_color_light;
CREATE TABLE tbl_extended_color_light (
    unique_id           VARCHAR(100)  NOT NULL,
    name                VARCHAR(200)  NULL,
    model_id            VARCHAR(200)  NULL,
    manufacturer_name   VARCHAR(200)  NULL,
    product_name        VARCHAR(200)  NULL,
    software_version    VARCHAR(200)  NULL,
    software_config_id  VARCHAR(200)  NULL,
    product_id          VARCHAR(200)  NULL,
    resource_id         VARCHAR(5)    NOT NULL,
    state_on            TINYINT(1)    NOT NULL,
    state_bri           INT(11)       NOT NULL,
    state_hue           INT(11)       NOT NULL,
    state_sat           INT(11)       NOT NULL,
    state_effect        VARCHAR(50)   NOT NULL,
    state_xy_x          DECIMAL(10,7) NOT NULL,
    state_xy_y          DECIMAL(10,7) NOT NULL,
    state_ct            INT(11)       NOT NULL,
    state_alert         VARCHAR(50)   NOT NULL,
    state_color_mode    VARCHAR(50)   NOT NULL,
    state_mode          VARCHAR(50)   NOT NULL,
    state_reachable     TINYINT(1)    NOT NULL,
    PRIMARY KEY (unique_id)
) ENGINE=InnoDB;

CREATE INDEX extended_color_light_001 ON tbl_extended_color_light(resource_id);
