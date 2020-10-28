DROP TABLE IF EXISTS tbl_astronomy;
CREATE TABLE tbl_astronomy (
    astronomy_date  DATE         NOT NULL,
    sunrise         TIME         NULL,
    sunset          TIME         NULL,
    sun_status      VARCHAR(200) NULL,
    moonrise        TIME         NULL,
    moonset         TIME         NULL,
    moon_status     VARCHAR(200) NULL,
    location        VARCHAR(200) NOT NULL,
    PRIMARY KEY (astronomy_date)
) ENGINE=InnoDB;
