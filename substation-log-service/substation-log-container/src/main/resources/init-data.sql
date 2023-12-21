insert into ss_log_user_m_view values
("90014842", "3412000"),
("XXXXXXXX", "XXXXXXX");

insert into ss_log_office_m_view values
("3412000", "Siliguri Town Division", null),
("3412100", "Siliguri 33/11kV Substation", "3412000"),
("3412200", "Rabindranagar 33/11kV Substation", "3412000"),
("XXXXXXX", "Dummy Office", "3412000");

insert into ss_log_ss_m_view values
("3412100", "8900795060"),
("3412200", "8900795055"),
("XXXXXXX", "XXXXXXXXXX");

insert into ss_log_feeder_m_view values
("392A", "Siliguri Ckt 2", "WEB18104", "3412100", 33, "INCOMING_33kV", null,null,null,true,true),
("392B", "Siliguri Ckt 1", "WEB18105", "3412100", 33, "INCOMING_33kV",null,null,null,true,false),
("392C", "Gangadhar 33kV", "WEB18310", "3412100", 33, "INCOMING_33kV",null,null,null,true,false),
("392X", "PTR1", null, "3412100", 33, "PTR_BAY",null,null,"392A",true,true),
("392W", "PTR2", null, "3412100", 33, "PTR_BAY",null,null,"392A",true,true),
("392Y", "PTR3", null, "3412100", 33, "PTR_BAY",null,null,"392A",true,true),
("392Z", "PTR4", null, "3412100", 33, "PTR_BAY",null,null,"392A",true,true),
("392D", "Incomer 1", "WEB19558", "3412100", 11, "INCOMING_11kV",null,"392X","392A",true,true),
("392E", "Incomer 2", "WEB19559", "3412100", 11, "INCOMING_11kV",null,"392W","392A",true,true),
("392F", "Incomer 3", "WEB19560", "3412100", 11, "INCOMING_11kV",null,"392Y","392A",true,true),
("392G", "Incomer 4", "WEB19561", "3412100", 11, "INCOMING_11kV",null,"392Z","392A",true,true),
("3921", "Feeder No 1", "WEB18163","3412100", 11, "OUTGOING_11kV","392D","392X","392A",true,true),
("3922", "Feeder No 2", "WEB18100", "3412100", 11, "OUTGOING_11kV","392D","392X","392A",true,true),
("3923", "Feeder No 3", "WEB19140", "3412100", 11, "OUTGOING_11kV","392E","392W","392A",true,true),
("3924", "North City", "WEB19304", "3412100", 11, "OUTGOING_11kV","392F","392Y","392A",true,true),
("3925", "Colony", "WEB19305", "3412100", 11, "OUTGOING_11kV","392G","392Z","392A",true,true),
("381A", "Siliguri Rabindranagar", "WEB11200","3412200", "33", "INCOMING_33kV", null,null,null,true,true),
("381B", "NJP Rabindranagar", "WEB11201","3412200", "33", "INCOMING_33kV", null,null,null,true,true),
("381C", "Siliguri Deshbandhupara Tap", "WEB11202","3412200", "33", "INCOMING_33kV", null,null,null,true,false),
("381X", "PTR1", null, "3412200", 33, "PTR_BAY",null,null,"381B",true,true),
("381W", "PTR2", null, "3412200", 33, "PTR_BAY",null,null,"381B",true,true),
("381Y", "PTR3", null, "3412200", 33, "PTR_BAY",null,null,"381A",true,true),
("381Z", "PTR4", null, "3412200", 33, "PTR_BAY",null,null,"381A",true,true),
("381D", "Incomer 1", "WEB11203", "3412200", 11, "INCOMING_11kV",null,"381X","381B",true,true),
("381E", "Incomer 2", "WEB11204", "3412200", 11, "INCOMING_11kV",null,"381W","381B",true,true),
("381F", "Incomer 3", "WEB11205", "3412200", 11, "INCOMING_11kV",null,"381Y","381A",true,true),
("381G", "Incomer 4", "WEB11206", "3412200", 11, "INCOMING_11kV",null,"381Z","381A",true,true),
("3811", "Adarshapally", "WFSB0774", "3412200", 11, "OUTGOING_11kV", "381D","381X","381B",true,true),
("3812", "Baneswar", "WFSB0797", "3412200", 11, "OUTGOING_11kV", "381D","381X","381B",true,true),
("3813", "Rathkhola", "WFSB0775", "3412200", 11, "OUTGOING_11kV", "381D","381X","381B",true,true),
("3814", "Fafri", "WFSB0710", "3412200", 11, "OUTGOING_11kV", "381E","381W","381B",true,true),
("3815", "Hospital", "WFSB0706", "3412200", 11, "OUTGOING_11kV", "381E","381W","381B",true,true),
("3816", "Subhaspally", "WFSB0674", "3412200", 11, "OUTGOING_11kV", "381E","381W","381B",true,true),
("3817", "Hatiayadanga", "WFSB0673", "3412200", 11, "OUTGOING_11kV", "381F","381Y","381A",true,true),
("3818", "Pakurtala", "WFSB0795", "3412200", 11, "OUTGOING_11kV", "381F","381Y","381A",true,true),
("381M", "Hyderpara", "WFSB0746", "3412200", 11, "OUTGOING_11kV", "381G","381Z","381A",true,true),
("381N", "Sanghati", "WFSB0750", "3412200", 11, "OUTGOING_11kV", "381G","381Z","381A",true,true),
("381O", "Santinagar", "WFSB0749", "3412200", 11, "OUTGOING_11kV", "381G","381Z","381A",true,true),
("381P", "Vivekananda Pally", "WFSB0748", "3412200", 11, "OUTGOING_11kV", "381G","381Z","381A",true,true),
("381Q", "VIP Road", "WFSB4376", "3412200", 11, "OUTGOING_11kV", "381G","381Z","381A",true,true);

insert into substation_incoming_source_mapping values
("3412100", "392A"),
("3412100", "392B"),
("3412100", "392C"),
("3412200", "381A"),
("3412200", "381B"),
("3412200", "381C");

insert into feeder_loading_history values
(1, "392A", "2023-08-12", "09:50:00", null, null),
(2, "381A", "2023-08-12", "09:50:00", null, null),
(3, "381B", "2023-08-12", "09:50:00", null, null);


