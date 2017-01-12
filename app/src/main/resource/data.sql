insert into language (name) values ('Srpski'); 
insert into language (name) values ('Engleski');
insert into language (name) values ('Nemacki');

insert into category (name) values ('Programiranje');
insert into category (name) values ('Drama');
insert into category (name) values ('Naucna fantastika');
insert into category (name) values ('Akcija');

insert into usertype(name) values ('administrator');
insert into usertype(name) values ('preplatnik');
insert into usertype(name) values ('posetilac');

insert into bookusers(firstname, lastname, password, type, username, category) values ('Petar', 'Petrovic', 'pera', 1, 'pera', null);
insert into bookusers(firstname, lastname, password, type, username, category) values ('Marko', 'Markovic', 'marko', 2, 'marko', 1);












