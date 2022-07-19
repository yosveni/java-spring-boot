create sequence if not exists seq_credit_information start 1 increment 1;

alter sequence seq_credit_information owner to postgres;

create sequence if not exists seq_earning start 1 increment 1;

alter sequence seq_earning owner to postgres;

create sequence if not exists seq_resume_operation start 1 increment 1;

alter sequence seq_resume_operation owner to postgres;

create table if not exists tab_credit_information
(
    id
    bigint
    not
    null
    constraint
    tab_credit_information_pkey
    primary
    key,
    assumed_obligation
    double
    precision,
    cnpj_if_requester
    varchar
(
    255
),
    count_institution integer,
    count_operation integer,
    count_operation_disagreement integer,
    count_operation_sub_judice integer,
    percent_document_processed double precision,
    percent_volume_processed double precision,
    responsibility_total_disagreement integer,
    responsibility_total_sub_judice integer,
    start_date_relationship timestamp,
    vendor_indirect_risk double precision
    );

alter table tab_credit_information owner to postgres;

create table if not exists tab_resume_operation
(
    id
    bigint
    not
    null
    constraint
    tab_resume_operation_pkey
    primary
    key,
    exchange_variation
    varchar
(
    255
),
    modality varchar
(
    255
),
    credit_information_id bigint
    constraint fk7kqg925rv5io7d3c5m6b6lybo
    references tab_credit_information
    );

alter table tab_resume_operation owner to postgres;

create table if not exists tab_earning
(
    id
    bigint
    not
    null
    constraint
    tab_earning_pkey
    primary
    key,
    code
    varchar
(
    255
),
    value double precision not null,
    resume_operation_id bigint
    constraint fkfw078cjynilssyn6u3ehhguvg
    references tab_resume_operation
    );

alter table tab_earning owner to postgres;

alter table if exists tab_company add column if not exists credit_information bigint constraint fkfvcb8cjyner44786u3eh0056y references tab_credit_information (id) on update cascade;
