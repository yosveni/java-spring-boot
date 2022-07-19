create sequence if not exists seq_sintegra_inscription start 1 increment 1;
create sequence if not exists seq_simple_national start 1 increment 1;
create sequence if not exists seq_suframa start 1 increment 1;

create table if not exists tab_simple_national
(
    id               int8 not null,
    simei            boolean default false,
    simei_date       timestamp,
    simple           boolean default false,
    simple_date      timestamp,
    simple_irregular boolean default false,
    primary key (id)
);

create table if not exists tab_suframa
(
    id                     int8 not null,
    expiration_date        timestamp,
    icms                   varchar(255),
    ipi                    varchar(255),
    pis_cofins             varchar(255),
    registration_number    varchar(255),
    registration_situation varchar(255),
    primary key (id)
);

create table if not exists tab_sintegra_inscription
(
    id                          int8 not null,
    email                       varchar(255),
    phone                       varchar(255),
    regime                      varchar(255),
    registration_number         varchar(255),
    registration_situation      varchar(255),
    registration_situation_date timestamp,
    uf                          varchar(255),
    company_id                  int8,
    primary key (id)
);

alter table if exists tab_sintegra_inscription add column if not exists company_id int8 constraint FKj2xwrtpggyrn84ytd0m0k6y0x references tab_company;
alter table if exists tab_company add column if not exists simple_national int8 constraint FKl394wa9xjjs7ih87td4bkol48 references tab_simple_national;
alter table if exists tab_company add column if not exists suframa int8 constraint FKgwnla1fgp442f2vqegwx9guhi references tab_suframa;

alter table if exists tab_company add column if not exists passive_iiss boolean default false;
