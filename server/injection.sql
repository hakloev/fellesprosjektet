insert into ansatt values ('hakloev', 'hakloev', 'Håkon Løvdal');
insert into ansatt values ('havard', 'havard', 'Håvard Snarby');
insert into ansatt values ('kvolden', 'kvolden', 'Kristian Volden');
insert into ansatt values ('tarald', 'tarald', 'Tarald Gåsbakk');
insert into ansatt values ('torgeirws', 'torgeir', 'Torgeir Skogen');
insert into ansatt values ('trulsmp', 'trulsmp', 'Truls Mørk Pettersen');
insert into ansatt values ('link', 'link', 'Link from Zelda');

insert into moterom values ('R1', 478);
insert into moterom values ('F1', 500);
insert into moterom values ('E304', 10);
insert into moterom values ('B183', 6);
insert into moterom values ('B1-126', 6);
insert into moterom values ('311', 6);
insert into moterom values ('B0-110', 10);
insert into moterom values ('411-Bober', 50);
insert into moterom values ('E404', 10);

insert into gruppe values ('Gruppe7');
insert into gruppe values ('fagKom');
insert into gruppe values ('Ledergruppa');

insert into gruppemedlem values ('Gruppe7', 'hakloev');
insert into gruppemedlem values ('Gruppe7', 'havard');
insert into gruppemedlem values ('Gruppe7', 'kvolden');
insert into gruppemedlem values ('Gruppe7', 'tarald');
insert into gruppemedlem values ('Gruppe7', 'torgeirws');
insert into gruppemedlem values ('Gruppe7', 'trulsmp');

insert into gruppemedlem values ('fagKom', 'hakloev');
insert into gruppemedlem values ('fagKom', 'trulsmp');

insert into gruppemedlem values ('Ledergruppa', 'kvolden');
insert into gruppemedlem values ('Ledergruppa', 'tarald');

INSERT INTO avtale VALUES (1, 'kvolden', '2014-03-17 10:00:00', '2014-03-17 11:00:00', 'Kjapt møte', 'R1', 'R1');
INSERT INTO avtale VALUES (2, 'hakloev', '2014-03-18 09:00:00', '2014-03-18 11:00:00', 'Kjapt møte', 'B126', 'B1-126');
INSERT INTO avtale VALUES (3, 'torgeirws', '2014-03-17 10:00:00', '2014-03-17 11:00:00', 'Kjapt møte', 'F1', 'F1');
INSERT INTO avtale VALUES (4, 'havard', '2014-03-17 08:00:00', '2014-03-17 10:00:00', 'Kjapt møte', 'E304', 'E304');

INSERT INTO deltager VALUES ('hakloev', 1, 'deltar', null, 1);
INSERT INTO deltager VALUES ('torgeirws', 1, 'deltar', null, 1);
INSERT INTO deltager VALUES ('kvolden', 1, 'deltar_ikke', null, 1);
INSERT INTO deltager VALUES ('tarald', 2, 'deltar', null, 0);
INSERT INTO deltager VALUES ('havard', 2, 'deltar', null, 0);
INSERT INTO deltager VALUES ('hakloev', 2, 'deltar', null, 0);
INSERT INTO deltager VALUES ('torgeirws', 3, 'deltar', null, 1);
INSERT INTO deltager VALUES ('trulsmp', 3, 'deltar', null, 1);
INSERT INTO deltager VALUES ('link', 4, 'deltar', null, 1);
INSERT INTO deltager VALUES ('havard', 4, 'deltar', null, 1);

INSERT INTO email VALUES ('haakloev@gmail.com', 2);
INSERT INTO email VALUES ('derp@derp.com', 4);


