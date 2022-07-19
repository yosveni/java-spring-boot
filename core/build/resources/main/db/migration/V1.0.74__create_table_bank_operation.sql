create sequence if not exists gen_bank_operation start 1 increment 1;

create table if not exists tab_bank_operation
(
    id                  bigint           not null,
    amortization_period integer          not null,
    contracted_date     timestamp,
    contracted_value    double precision not null,
    created             timestamp        not null,
    financial_agent     varchar(255),
    financial_cost      varchar(255),
    grace_period        integer          not null,
    modified            timestamp        not null,
    product             varchar(255),
    status              integer          not null,
    tax                 double precision not null,
    type                varchar(255),
    uf                  varchar(255),
    company_id          bigint,
    primary key (id)
);

alter table if exists tab_bank_operation
    add constraint FKhvjts1k8urmjdvh2g6ig2i8pk foreign key (company_id) references tab_company;