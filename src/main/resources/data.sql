-->Initial Load
---truncate table worktypes;
-->Worktype
INSERT INTO worktype (ID, longDesc, shortDesc) values (1,'Arbeitszeit', 'Arbeitszeit');
INSERT INTO worktype (ID, longDesc, shortDesc) values (2,'Urlaub', 'Urlaub');
INSERT INTO worktype (ID, longDesc, shortDesc) values (3,'Gleitzeit', 'Gleitzeit');
INSERT INTO worktype (ID, longDesc, shortDesc) values (4,'Krankheit', 'Krank');

--> Roles
INSERT INTO roles (name) VALUES ('USER');
INSERT INTO roles (name) VALUES ('CREATOR');
INSERT INTO roles (name) VALUES ('EDITOR');
INSERT INTO roles (name) VALUES ('ADMIN');

--> User
INSERT INTO usertbl (user_id, username, password, enabled) values (1, 'admin', '$2a$12$A7C/P3XXuwNoGHdy.K957.G75UzxDXtAUv9xIVDekI2K8bLFNNG8a', true);

--> User to Role
INSERT INTO users_roles (user_id, role_id) VALUES (1, 4);
