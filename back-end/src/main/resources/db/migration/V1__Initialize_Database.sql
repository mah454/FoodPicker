-- Generate Schema --
create sequence beverage_seq start 1 increment 1;
create sequence food_seq start 1 increment 1;
create sequence profile_seq start 1 increment 1;
create sequence role_seq start 1 increment 1;
create table Beverage (id int8 not null, name varchar(255), primary key (id));
create table Food (id int8 not null, name varchar(255), primary key (id));
create table Profile (id int8 not null, firstName varchar(255), lastName varchar(255), nickName varchar(255), ssoId varchar(255), userId int8 not null, username varchar(255), primary key (id));
create table Profile_Role (Profile_id int8 not null, roles_id int8 not null);
create table Role (id int8 not null, roleType varchar(255), primary key (id));
alter table if exists Beverage add constraint UK_ok66oq3xulylt396y8lcjsn1e unique (name);
alter table if exists Food add constraint UK_s10dojj6e5yrg48pisohs94k6 unique (name);
alter table if exists Profile_Role add constraint FKkk2wg94c41cwblkoay451510j foreign key (roles_id) references Role;
alter table if exists Profile_Role add constraint FK7oj9gwnre9hsvi2todsm0wtqn foreign key (Profile_id) references Profile;
insert into role values (nextval('role_seq'),'PROFILE_MANAGER');
insert into role values (nextval('role_seq'),'FOOD_PICKER');
insert into role values (nextval('role_seq'),'STATISTIC');
insert into role values (nextval('role_seq'),'FOOD_MANAGER');
insert into role values (nextval('role_seq'),'CALENDAR');
