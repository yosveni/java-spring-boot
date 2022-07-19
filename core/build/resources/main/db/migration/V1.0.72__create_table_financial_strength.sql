create sequence if not exists seq_financial_strength start 1 increment 1;
create sequence if not exists seq_financial_strength_operation start 1 increment 1;

create table if not exists tab_financial_strength
(
    id    int8 not null,
    year  integer,
    total float8,
    primary key (id)
);

create table if not exists tab_financial_strength_operation
(
    id                    int8 not null,
    name                  varchar(255),
    value                 float8,
    financial_strength_id int8,
    primary key (id)
);

alter table if exists tab_sped
    add column if not exists financial_strength_id int8;

alter table if exists tab_sped
    add constraint FKsktskan2w4bsgxqymerl1qy7o foreign key (financial_strength_id) references tab_financial_strength;
alter table if exists tab_financial_strength_operation
    add constraint FKep5jrx047o2oc04vxj04bcer1 foreign key (financial_strength_id) references tab_financial_strength;
