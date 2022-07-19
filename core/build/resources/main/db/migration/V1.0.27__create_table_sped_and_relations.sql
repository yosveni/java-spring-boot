create sequence if not exists seq_sped start 1 increment 1;
create sequence if not exists seq_sped_pattern start 1 increment 1;
create sequence if not exists seq_sped_directory start 1 increment 1;

create table if not exists tab_sped
(
    id                      int8 not null,
    demonstrative_end_date  timestamp,
    demonstrative_init_date timestamp,
    primary key (id)
);

create table if not exists tab_sped_balance
(
    id                   int8      not null,
    code                 varchar(255),
    code_description     varchar(255),
    code_level           int4 default 0,
    code_nature          varchar(255),
    code_synthetic       varchar(255),
    code_type            varchar(255),
    created              timestamp not null,
    end_value            float8,
    end_value_situation  varchar(255),
    modified             timestamp not null,
    credit_value         float8,
    debit_value          float8,
    init_value           float8,
    init_value_situation varchar(255),
    init_date            timestamp,
    end_date             timestamp,
    primary key (id)
);

create table if not exists tab_sped_demonstration
(
    id                  int8      not null,
    code                varchar(255),
    code_description    varchar(255),
    code_level          int4 default 0,
    code_nature         varchar(255),
    code_synthetic      varchar(255),
    code_type           varchar(255),
    created             timestamp not null,
    end_value           float8,
    end_value_situation varchar(255),
    modified            timestamp not null,
    primary key (id)
);

create table if not exists tab_sped_directory
(
    id   int8 not null,
    year int4 default 0,
    primary key (id)
);

alter table if exists tab_sped add column if not exists company_id int8 constraint FK1ecyc3rjhfcpgx3yj6vsq2viw references tab_company(id) on update cascade;
alter table if exists tab_sped_balance add column if not exists sped_id int8 constraint FKrpid48y7sb9oafr93qan53txx references tab_sped(id) on update cascade;
alter table if exists tab_sped_demonstration add column if not exists sped_id int8 constraint FK58www4pv1ypkrcyvupjn27g88 references tab_sped(id) on update cascade;
alter table if exists tab_sped_directory add column if not exists company_id int8 constraint FKmedj17qcwt52fvw0s35avi6qu references tab_company(id) on update cascade;
alter table if exists tab_directory add column if not exists sped_directory_id int8 constraint FK6hbn49e0h17rn1odc2yfe9lf8 references tab_sped_directory(id) on update cascade;
