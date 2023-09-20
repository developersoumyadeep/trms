drop schema if exists feeder_management_schema;
create schema feeder_management_schema;

use feeder_management_schema;

drop table if exists feeder_management_office_m_view;
CREATE TABLE feeder_management_office_m_view (
    office_id VARCHAR(10) NOT NULL,
    office_text VARCHAR(255) NOT NULL,
    PRIMARY KEY (office_id)
)  ENGINE=INNODB;

drop table if exists feeder_master;
create table feeder_master (
	feeder_id varchar(10) not null,
    feeder_text varchar(20) not null,
    voltage_level double not null,
    source_substation_office_id varchar(10) not null,
    terminal_substation_office_id varchar(10),
    energy_meter_no_at_source varchar(20) not null,
    energy_meter_no_at_terminal varchar(20),
    gis_length double not null,
    ct_ratio_at_source double not null,
    ct_ratio_at_terminal double,
    multiplying_factor double,
    feeder_type varchar(25) not null,
    ptr_id varchar(12),
    incomer_ct_ratio double,
    switchgear_id varchar(13),
    is_dedicated_bulk_feeder boolean,
    incomer_11kV_feeder_id varchar(10),
    feeding_PTR_id varchar(10),
    feeding_33kV_feeder_id varchar(10),
    primary key (feeder_id),
    foreign key (source_substation_office_id) references feeder_management_office_m_view(office_id),
    foreign key (terminal_substation_office_id) references feeder_management_office_m_view(office_id)
    )engine = InnoDB;
    
    
    