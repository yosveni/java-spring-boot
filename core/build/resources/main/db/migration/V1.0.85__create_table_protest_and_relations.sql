create sequence if not exists seq_protest_information start 1 increment 1;
create sequence if not exists seq_protest_registry start 1 increment 1;
create sequence if not exists seq_protest start 1 increment 1;

create table if not exists tab_protest_information
(
    id          int8         not null,
    analysis    jsonb,
    created     timestamp    not null,
    document    varchar(255) not null,
    modified    timestamp    not null,
    total       int4         not null,
    total_error int4         not null,
    primary key (id)
);

create table if not exists tab_protest_registry
(
    id                     int8 not null,
    address                varchar(255),
    amount                 int4 not null,
    city                   varchar(255),
    city_code              varchar(255),
    city_code_ibge         varchar(255),
    code                   int4 not null,
    municipality           varchar(255),
    name                   varchar(255),
    neighborhood           varchar(255),
    phone                  varchar(255),
    research_period        varchar(255),
    uf                     varchar(255),
    update_date            varchar(255),
    protest_information_id int8
        constraint FK44vmw3gmn6il2hrd2vtb854qe references tab_protest_information,
    primary key (id)
);

create table if not exists tab_protest
(
    id                  int8    not null,
    area                integer not null,
    assignor_name       varchar(255),
    consult_date        timestamp,
    due_date            timestamp,
    has_consent         boolean,
    key                 varchar(255),
    presenter_name      varchar(255),
    value               float8  not null,
    protest_registry_id int8
        constraint FK7nxh65vwd4ll1s8ie2wt6xc4g references tab_protest_registry,
    primary key (id)
);

alter table if exists tab_company
    add column if not exists protest_information_id int8
        constraint FK3beaw3gmn6il2hrsf5ub854em references tab_protest_information;
