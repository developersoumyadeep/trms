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
        substation_office_id VARCHAR(10) NOT NULL,
        voltage_level INT NOT NULL,
        feeder_type VARCHAR(20) NOT NULL,
        incomer_11kV_feeder_id VARCHAR(10),
        feeding_ptr_id VARCHAR(10),
        feeding_33kV_feeder_id VARCHAR(10),
        is_charged boolean not null,
        is_loaded boolean not null,
        ictr DOUBLE,
        iptr DOUBLE,
        PRIMARY KEY (feeder_id),
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
    first_name VARCHAR(128) NOT NULL,
    last_name VARCHAR(128) NOT NULL,
    mobile_number VARCHAR(10) NOT NULL,
    authentication_string VARCHAR(256) NOT NULL,
    sap_id VARCHAR(8),
    is_departmental boolean,
    is_contractual boolean,
    is_reengaged boolean,
    is_retired boolean,
    reengagement_contract_expired boolean,
    is_terminated boolean,
    is_suspended boolean,
    is_regular boolean,
    is_vendor boolean,
    company_name VARCHAR(256),
    joining_date_at_current_office DATE,
    release_date_from_previous_office DATE,
    date_of_retirement DATE,
    date_of_expiry_of_reengagement_contract DATE,
    date_of_birth DATE,
    account_not_expired boolean,
    account_not_locked boolean,
    credentials_not_expired boolean,
    enabled boolean,
    PRIMARY KEY (user_id),
    UNIQUE KEY (mobile_number)
)  ENGINE=INNODB;

drop table if exists ss_log_user_role;
CREATE TABLE ss_log_user_role (
    user_id VARCHAR(45) NOT NULL,
    role VARCHAR(45) NOT NULL,
    CONSTRAINT role_pk PRIMARY KEY (user_id , role)
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
    feeder_loading_type VARCHAR(10) NOT NULL,
    remarks VARCHAR(255),
    PRIMARY KEY (load_record_id),
    foreign key (feeder_id) references ss_log_feeder_m_view (feeder_id),
	foreign key (substation_office_id) references ss_log_office_m_view (office_id)
)  ENGINE=INNODB;

drop table if exists ss_log_energy_meter_master;
CREATE TABLE ss_log_energy_meter_master (
    energy_meter_no VARCHAR(15) NOT NULL,
    meter_ct_ratio DOUBLE NOT NULL,
    meter_pt_ratio DOUBLE NOT NULL,
    energy_unit VARCHAR(3) NOT NULL,
    PRIMARY KEY (energy_meter_no)
) ENGINE = INNODB;
        
drop table if exists ss_log_energy_meter_reading_master;
CREATE TABLE ss_log_energy_meter_reading_master (
    meter_reading_id VARCHAR(36) NOT NULL,
    feeder_id VARCHAR(10) NOT NULL,
    substation_office_id VARCHAR(10) NOT NULL,
    record_date DATE NOT NULL,
    record_time TIME NOT NULL,
    energy_meter_no VARCHAR(15) NOT NULL,
    energy_unit VARCHAR(3) NOT NULL,
    meter_reading DOUBLE NOT NULL,
    recorded_by VARCHAR(10) NOT NULL,
    multiplying_factor DOUBLE,
    PRIMARY KEY (meter_reading_id),
    foreign key (feeder_id) references ss_log_feeder_m_view (feeder_id),
	foreign key (substation_office_id) references ss_log_office_m_view (office_id),
	foreign key (energy_meter_no) references ss_log_energy_meter_master (energy_meter_no)
)  ENGINE=INNODB;

drop table if exists ss_log_energy_meter_feeder_association;
CREATE TABLE ss_log_energy_meter_feeder_association (
    association_id VARCHAR(36) NOT NULL,
    energy_meter_no VARCHAR(15) NOT NULL,
    feeder_id VARCHAR(10) NOT NULL,
    association_start_date DATE NOT NULL,
    association_start_time TIME NOT NULL,
    association_end_date DATE,
    association_end_time TIME,
    PRIMARY KEY (association_id),
    foreign key (energy_meter_no) references ss_log_energy_meter_master (energy_meter_no),
    foreign key (feeder_id) references ss_log_feeder_m_view (feeder_id)
) ENGINE=INNODB;

