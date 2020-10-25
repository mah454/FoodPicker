-- Update table profile --
alter table if exists Profile add constraint UK_evw0xmgxtasppitmiw4t1swl0 unique (ssoId);
alter table if exists Profile add constraint UK_22mmjoes7q36gl8630mpekx7y unique (userId);
alter table if exists Profile add constraint UK_1wpe1j4lyc9m4yny8kjfv7y0s unique (username);

-- Add food category --
alter table if exists Food add column foodCategory_id int8;
create table FoodCategory (id int8 not null, name varchar(255), primary key (id));
alter table if exists FoodCategory drop constraint if exists UK_1f54an5prrjrccq4t6f3cx215;
alter table if exists FoodCategory add constraint UK_1f54an5prrjrccq4t6f3cx215 unique (name);
create sequence FoodCategory_seq start 1 increment 1;
alter table if exists Food add constraint FKq7bbiav9xufl5t9ygvtdpwep5 foreign key (foodCategory_id) references FoodCategory;