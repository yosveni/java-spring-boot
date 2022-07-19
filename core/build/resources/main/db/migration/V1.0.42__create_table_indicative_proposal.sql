create sequence seq_indicative_proposal start 1 increment 1;
create sequence seq_partner_bank start 1 increment 1;

create table tab_indicative_proposal
(
    proposal_type         int4      not null,
    id                    int8      not null,
    area                  int4      not null,
    created               timestamp not null,
    description           TEXT,
    discount              float8    not null,
    installments          float8    not null,
    iof                   float8    not null,
    modified              timestamp not null,
    month_cet             int4,
    next_step_description TEXT,
    pai_by_installment    float8,
    proposal_state        int4      not null,
    registration_fee      float8,
    rejected              boolean   not null,
    rejected_reason       TEXT,
    response_time         timestamp,
    tax_percent           float8,
    tax_value             float8,
    total                 float8    not null,
    volume                float8    not null,
    year_cet              int4,
    image_id              int8,
    partner_bank_id       int8,
    offer_id              int8,
    primary key (id)
);

create table tab_partner_bank
(
    id       int8         not null,
    created  timestamp    not null,
    days     int4         not null,
    modified timestamp    not null,
    name     varchar(255) not null,
    primary key (id)
);

alter table if exists tab_indicative_proposal
    add constraint FK6nug7qtb3yrndv751vt9yb1u8 foreign key (image_id) references tab_directory;
alter table if exists tab_indicative_proposal
    add constraint FKrto5qbowfioggooj0er4fuqb1 foreign key (partner_bank_id) references tab_partner_bank;
alter table if exists tab_indicative_proposal
    add constraint FK9hi1t7gduc5muulkh2cgv0kj9 foreign key (offer_id) references tab_offer;