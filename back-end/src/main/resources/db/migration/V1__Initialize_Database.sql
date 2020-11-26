-- Generate Schema --
create table Food (id int8 not null, active boolean not null, foodType int4, name varchar(255), foodCategory_id int8, primary key (id));
create table FoodCategory (id int8 not null, name varchar(255), primary key (id));
create table Profile (id int8 not null, firstName varchar(255), lastName varchar(255), nickName varchar(255), ssoId varchar(255), userId int8, username varchar(255), primary key (id));
create table Profile_Role (Profile_id int8 not null, roles_id int8 not null, primary key (Profile_id, roles_id));
create table Role (id int8 not null, roleType varchar(255), primary key (id));
alter table if exists Food drop constraint if exists UK_s10dojj6e5yrg48pisohs94k6;
alter table if exists Food add constraint UK_s10dojj6e5yrg48pisohs94k6 unique (name);
alter table if exists FoodCategory drop constraint if exists UK_1f54an5prrjrccq4t6f3cx215;
alter table if exists FoodCategory add constraint UK_1f54an5prrjrccq4t6f3cx215 unique (name);
alter table if exists Profile drop constraint if exists UK_evw0xmgxtasppitmiw4t1swl0;
alter table if exists Profile add constraint UK_evw0xmgxtasppitmiw4t1swl0 unique (ssoId);
alter table if exists Profile drop constraint if exists UK_22mmjoes7q36gl8630mpekx7y;
alter table if exists Profile add constraint UK_22mmjoes7q36gl8630mpekx7y unique (userId);
alter table if exists Profile drop constraint if exists UK_1wpe1j4lyc9m4yny8kjfv7y0s;
alter table if exists Profile add constraint UK_1wpe1j4lyc9m4yny8kjfv7y0s unique (username);
create sequence food_seq start 1 increment 1;
create sequence FoodCategory_seq start 1 increment 1;
create sequence profile_seq start 1 increment 1;
create sequence role_seq start 1 increment 1;
alter table if exists Food add constraint FKq7bbiav9xufl5t9ygvtdpwep5 foreign key (foodCategory_id) references FoodCategory;
alter table if exists Profile_Role add constraint FKkk2wg94c41cwblkoay451510j foreign key (roles_id) references Role;
alter table if exists Profile_Role add constraint FK7oj9gwnre9hsvi2todsm0wtqn foreign key (Profile_id) references Profile;

-- Initialize Basic Roles --
insert into role values (nextval('role_seq'),'PROFILE_MANAGER');
insert into role values (nextval('role_seq'),'FOOD_PICKER');
insert into role values (nextval('role_seq'),'STATISTIC');
insert into role values (nextval('role_seq'),'FOOD_MANAGER');
insert into role values (nextval('role_seq'),'CALENDAR');
