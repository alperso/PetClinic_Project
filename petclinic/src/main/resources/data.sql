INSERT INTO t_owner (id,first_name,last_name) VALUES (1, 'Ziya', 'Ferit');
INSERT INTO t_owner (id,first_name,last_name) VALUES (2, 'Beşir', 'Dal');
INSERT INTO t_owner (id,first_name,last_name) VALUES (3, 'Eda', 'Rize');
INSERT INTO t_owner (id,first_name,last_name) VALUES (4, 'Hadi', 'Duru');
INSERT INTO t_owner (id,first_name,last_name) VALUES (5, 'Pınar', 'Mus');
INSERT INTO t_owner (id,first_name,last_name) VALUES (6, 'Çiğdem', 'Su');
INSERT INTO t_owner (id,first_name,last_name) VALUES (7, 'Aslı', 'Zor');
INSERT INTO t_owner (id,first_name,last_name) VALUES (8, 'Murat', 'Eski');
INSERT INTO t_owner (id,first_name,last_name) VALUES (9, 'Davut', 'Saz');
INSERT INTO t_owner (id,first_name,last_name) VALUES (10, 'Kadir', 'Mutlu');

INSERT INTO t_pet (id,name,birth_date,owner_id) VALUES (1, 'Maviş', '2005-09-07', 1);
INSERT INTO t_pet (id,name,birth_date,owner_id) VALUES (2, 'Donetello', '2008-08-06', 1);
INSERT INTO t_pet (id,name,birth_date,owner_id) VALUES (3, 'Karabaş', '2001-04-17', 1);
INSERT INTO t_pet (id,name,birth_date,owner_id) VALUES (4, 'Joe', '2009-03-07', 2);
INSERT INTO t_pet (id,name,birth_date,owner_id) VALUES (5, 'Canavar', '2010-11-30', 2);
INSERT INTO t_pet (id,name,birth_date,owner_id) VALUES (6, 'Tatlım', '2010-01-20', 3);
INSERT INTO t_pet (id,name,birth_date,owner_id) VALUES (7, 'Samanta', '2008-09-04', 5);
INSERT INTO t_pet (id,name,birth_date,owner_id) VALUES (8, 'Boncuk', '2008-09-04', 5);
INSERT INTO t_pet (id,name,birth_date,owner_id) VALUES (9, 'Şanslı', '2007-08-06', 5);
INSERT INTO t_pet (id,name,birth_date,owner_id) VALUES (10, 'Karaburun', '2009-02-24', 6);
INSERT INTO t_pet (id,name,birth_date,owner_id) VALUES (11, 'Twetty', '2000-03-09', 7);
INSERT INTO t_pet (id,name,birth_date,owner_id) VALUES (12, 'Tarçın', '2000-06-24', 8);
INSERT INTO t_pet (id,name,birth_date,owner_id) VALUES (13, 'Sarı', '2002-06-08', 9);

--Sifrelenmemis hali
--INSERT INTO USERS VALUES('user1','{noop}user1',TRUE);
--INSERT INTO USERS VALUES('user2','{noop}user2',TRUE);
--INSERT INTO USERS VALUES('user3','{noop}user3',TRUE);

INSERT INTO USERS VALUES('user1','{bcrypt}$2a$10$v2skZFsOcSl591Tt6nyK5eQNZukaI3d7qA8Tc9IFAga.uPgRRJDp.',TRUE);
INSERT INTO USERS VALUES('user2','{bcrypt}$2a$10$moi.ThdISUji4BC9qaVrHep5cFaxF0AexnA4XoRPoGSbcpTY3BiOy',TRUE);
INSERT INTO USERS VALUES('user3','{bcrypt}$2a$10$VK1pPwEkjtz7Sv4swsmdfuNmq76UhOCmMShJys.n3LmqSvdWDA7bm',TRUE);
INSERT INTO USERS VALUES('user4','{bcrypt}$2a$10$j33iOLfzdT63o8Qsu5Qxy.YDb5Xg5ejBvpGYLHnfhZP0Yv02KQZem',TRUE);


INSERT INTO AUTHORITIES VALUES('user1','ROLE_USER');
INSERT INTO AUTHORITIES VALUES('user2','ROLE_USER');
INSERT INTO AUTHORITIES VALUES('user2','ROLE_EDITOR');
INSERT INTO AUTHORITIES VALUES('user3','ROLE_USER');
INSERT INTO AUTHORITIES VALUES('user4','ROLE_SECRETER');


INSERT INTO t_vet (id,first_name,last_name) VALUES (14, 'Ali', 'Güç');
INSERT INTO t_vet (id,first_name,last_name) VALUES (15, 'Bekir', 'Sağ');
INSERT INTO t_vet (id,first_name,last_name) VALUES (16, 'Esma', 'Kul');
 