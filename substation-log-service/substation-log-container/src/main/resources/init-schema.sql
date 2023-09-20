drop schema if exists substation_log_schema;
create schema substation_log_schema;

use substation_log_schema;


drop table if exists ss_log_office_m_view;
CREATE TABLE ss_log_office_m_view (
        office_id VARCHAR(10) NOT NULL,
        office_text VARCHAR(100) NOT NULL,
        parent_office_id VARCHAR(10),
        PRIMARY KEY (office_id),
        FOREIGN KEY (parent_office_id)
            REFERENCES ss_log_office_m_view (office_id)
)  ENGINE=INNODB;

drop table if exists ss_log_ss_m_view;
CREATE TABLE ss_log_ss_m_view (
       office_id VARCHAR(10) NOT NULL,
       substation_contact_number VARCHAR(10) NOT NULL,
       PRIMARY KEY (office_id),
       FOREIGN KEY (office_id)
           REFERENCES ss_log_office_m_view (office_id)
) ENGINE=INNODB;

drop table if exists ss_log_feeder_m_view;
CREATE TABLE ss_log_feeder_m_view (
        feeder_id VARCHAR(10) NOT NULL,
        feeder_name VARCHAR(50) NOT NULL,
        energy_meter_no VARCHAR(15),
        substation_office_id VARCHAR(10) NOT NULL,
        voltage_level INT NOT NULL,
        feeder_type VARCHAR(20) NOT NULL,
        incomer_11kV_feeder_id VARCHAR(10),
        feeding_ptr_id VARCHAR(10),
        feeding_33kV_feeder_id VARCHAR(10),
        is_charged boolean not null,
        is_loaded boolean not null,
        PRIMARY KEY (feeder_id),
        UNIQUE KEY (energy_meter_no),
        FOREIGN KEY (substation_office_id)
            REFERENCES ss_log_office_m_view (office_id),
        FOREIGN KEY (incomer_11kV_feeder_id)
            REFERENCES ss_log_feeder_m_view (feeder_id),
        FOREIGN KEY (feeding_ptr_id)
            REFERENCES ss_log_feeder_m_view (feeder_id),
        FOREIGN KEY (feeding_33kV_feeder_id)
            REFERENCES ss_log_feeder_m_view (feeder_id)
)  ENGINE=INNODB;

drop table if exists feeder_loading_history;
CREATE TABLE feeder_loading_history (
    history_id int not null auto_increment,
    feeder_id VARCHAR(10) NOT NULL,
    loaded_from_date DATE,
    loaded_from_time TIME,
    loaded_to_date DATE,
    loaded_to_time TIME,
    PRIMARY KEY (history_id),
    FOREIGN KEY (feeder_id)
        REFERENCES ss_log_feeder_m_view (feeder_id)
)  ENGINE=INNODB;

drop table if exists substation_incoming_source_mapping;
CREATE TABLE substation_incoming_source_mapping (
    substation_office_id VARCHAR(10) NOT NULL,
    incoming_source_feeder_id VARCHAR(10) NOT NULL,
    PRIMARY KEY (substation_office_id , incoming_source_feeder_id),
    FOREIGN KEY (substation_office_id)
        REFERENCES ss_log_office_m_view (office_id),
    FOREIGN KEY (incoming_source_feeder_id)
        REFERENCES ss_log_feeder_m_view (feeder_id)
)  ENGINE=INNODB;


drop table if exists ss_log_user_m_view;
CREATE TABLE ss_log_user_m_view (
    user_id VARCHAR(10) NOT NULL,
    office_id VARCHAR(10) NOT NULL,
    PRIMARY KEY (user_id)
)  ENGINE=INNODB;

drop table if exists interruption_master;
CREATE TABLE interruption_master (
    interruption_id VARCHAR(36) NOT NULL,
    faulty_feeder_id VARCHAR(10) NOT NULL,
    substation_office_id VARCHAR(10) NOT NULL,
    interruption_type VARCHAR(20) NOT NULL,
    fault_nature VARCHAR(20),
    start_date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_date DATE,
    end_time TIME,
    created_by VARCHAR(10) NOT NULL,
    restored_by VARCHAR(10) NOT NULL,
    creation_time_stamp TIMESTAMP NOT NULL,
    restoration_time_stamp TIMESTAMP,
    interruption_status  VARCHAR(20),
    cause VARCHAR(255),
    PRIMARY KEY (interruption_id),
    foreign key (faulty_feeder_id) references ss_log_feeder_m_view (feeder_id),
	foreign key (substation_office_id) references ss_log_office_m_view (office_id)
)  ENGINE=INNODB;
    
drop table if exists load_record_master;
CREATE TABLE load_record_master (
    load_record_id VARCHAR(36) NOT NULL,
    feeder_id VARCHAR(10) NOT NULL,
    substation_office_id VARCHAR(10) NOT NULL,
    record_date DATE NOT NULL,
    record_time TIME NOT NULL,
    recorded_load DOUBLE NOT NULL,
    recorded_by VARCHAR(10) NOT NULL,
    PRIMARY KEY (load_record_id),
    foreign key (feeder_id) references ss_log_feeder_m_view (feeder_id),
	foreign key (substation_office_id) references ss_log_office_m_view (office_id)
)  ENGINE=INNODB;
        
drop table if exists energy_consumption_master;
CREATE TABLE energy_consumption_master (
    consumption_id VARCHAR(36) NOT NULL,
    feeder_id VARCHAR(10) NOT NULL,
    substation_office_id VARCHAR(10) NOT NULL,
    record_date DATE NOT NULL,
    record_time TIME NOT NULL,
    energy_meter_no VARCHAR(15) NOT NULL,
    energy_unit VARCHAR(3) NOT NULL,
    consumption DOUBLE NOT NULL,
    multiplying_factor INT NOT NULL,
    recorded_by VARCHAR(10) NOT NULL,
    PRIMARY KEY (consumption_id),
    foreign key (feeder_id) references ss_log_feeder_m_view (feeder_id),
	foreign key (substation_office_id) references ss_log_office_m_view (office_id)
)  ENGINE=INNODB;

