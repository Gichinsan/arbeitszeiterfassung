-->Initial Load
truncate table worktypes;
-->Worktype
INSERT INTO worktype (ID, longDesc, shortDesc) values (1,'Arbeitszeit', 'Arbeitszeit');
INSERT INTO worktype (ID, longDesc, shortDesc) values (2,'Urlaub', 'Urlaub');
INSERT INTO worktype (ID, longDesc, shortDesc) values (3,'Gleitzeit', 'Gleitzeit');
INSERT INTO worktype (ID, longDesc, shortDesc) values (4,'Krankheit', 'Krank');
--> User
INSERT INTO usertbl (ID, username, password) values (1, 'admin', 'admin');