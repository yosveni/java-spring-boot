create sequence if not exists seq_cri_cra_debenture start 1 increment 1;

create table if not exists tab_cri_cra_debenture
(
    id                          bigint not null,
    code                        varchar(255),
    debtor_issuer               varchar(255),
    insurance_sector            varchar(255),
    series_issue                varchar(255),
    issue_date                  timestamp,
    remuneration                varchar(255),
    due_date                    timestamp,
    indicative_value            double precision,
    pu_par_debenture            double precision,
    series_volume_on_issue_date double precision,
    type                        integer,
    company_id                  bigint
        constraint FKdsaf987ok345h345034593jpk references tab_company,
    primary key (id)
);
