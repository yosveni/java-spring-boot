create sequence if not exists seq_company_related start 1 increment 1;
create sequence if not exists seq_property_guarantee start 1 increment 1;
create sequence if not exists seq_corporate_participation start 1 increment 1;
create sequence if not exists seq_company_beneficiary start 1 increment 1;

create table if not exists tab_company_cnae
(
    company_id int8 not null,
    cnae_id    int8 not null,
    primary key (company_id, cnae_id)
);

create table if not exists tab_company_related
(
    id               int8      not null,
    cnpj             varchar(18),
    created          timestamp not null,
    description_cnae TEXT,
    modified         timestamp not null,
    municipality     varchar(255),
    opening_date     timestamp,
    social_reason    TEXT,
    uf               varchar(255),
    company_id       int8,
    primary key (id)
);

create table if not exists tab_corporate_participation
(
    id                              int8   not null,
    branch_activity_cnae            varchar(255),
    cnpj                            varchar(255),
    description_cnae                TEXT,
    estimated_billing               varchar(255),
    estimated_billing_group         varchar(255),
    level_preparation               varchar(255),
    level_preparation_rf            varchar(255),
    municipality                    varchar(255),
    opening_date                    timestamp,
    participation                   float8 not null,
    participation_rf                float8,
    participation_social_capital    float8,
    participation_social_capital_rf float8,
    qualification                   varchar(255),
    qualification_rf                varchar(255),
    situation                       varchar(255),
    social_capital                  varchar(255),
    social_reason                   TEXT,
    uf                              varchar(255),
    person_id                       int8,
    primary key (id)
);

create table if not exists tab_company_beneficiary
(
    id                int8    not null,
    dead              boolean not null,
    document          varchar(255),
    grade             int4    not null,
    grade_qsa         int4,
    name              varchar(255),
    participation     float8  not null,
    participation_qsa float8,
    company_id        int8,
    primary key (id)
);

alter table if exists tab_cnae
    drop column if exists company_id;

alter table if exists tab_company_cnae
    add constraint FKm1cl25du13gpv9lx2lpyvohw9 foreign key (cnae_id) references tab_cnae,
    add constraint FKcjtypd727s16jpt3i5bfnlg4n foreign key (company_id) references tab_company;

alter table if exists tab_company_related
    add constraint FKh80dxxn4iacva2n703maoqh3f foreign key (company_id) references tab_company;

alter table if exists tab_corporate_participation
    add constraint FKltiof3f7ouydbrrwe06we2ihe foreign key (person_id) references tab_person;

alter table if exists tab_company_beneficiary
    add constraint FK8kbd5o50svgcbfk998w0yxtw foreign key (company_id) references tab_company;

drop table if exists tab_company_companies_related;
drop table if exists tab_company_beneficiaries;

alter sequence seq_company_partners restart with 1;
alter sequence seq_company_beneficiaries restart with 1;
alter sequence seq_cnae restart with 1;

-- Add new colums
alter table if exists tab_property
    alter column building_data type varchar(255);

alter table if exists tab_person
    alter column inscription_cpf_date type varchar(255);

alter table if exists tab_company_employee
    drop column if exists person_id,
    add column if not exists birth_date       timestamp,
    add column if not exists cpf              varchar(255),
    add column if not exists name             varchar(255),
    add column if not exists resignation_year varchar(255);

-- Alter company colums
alter table if exists tab_company
    rename column main_cnae to main_cnae_id;

alter table if exists tab_company
    rename column main_info to main_info_id;

alter table if exists tab_company
    rename column matrix_info to matrix_info_id;

alter table if exists tab_company
    rename column financial_activity to financial_activity_id;

alter table if exists tab_company
    rename column pat to pat_id;

alter table if exists tab_company
    rename column tax_health to tax_health_id;

alter table if exists tab_company
    rename column debit_mte to debit_mte_id;

alter table if exists tab_company
    rename column foreign_commerce to foreign_commerce_id;

alter table if exists tab_company
    rename column cafir to cafir_id;

alter table if exists tab_company
    rename column heavy_vehicle_info to heavy_vehicle_info_id;

alter table if exists tab_company
    rename column management_contract to management_contract_id;

alter table if exists tab_company
    rename column ibama_cnd to ibama_cnd_id;

alter table if exists tab_company
    rename column procon to procon_id;

alter table if exists tab_company
    rename column judicial_process to judicial_process_id;

alter table if exists tab_company
    rename column antt to antt_id;

alter table if exists tab_company
    rename column open_capital to open_capital_id;

alter table if exists tab_company
    rename column debit_pgfn_dau to debit_pgfn_dau_id;

alter table if exists tab_company
    rename column simple_national to simple_national_id;

alter table if exists tab_company
    rename column suframa to suframa_id;

-- Alter person colums
alter table if exists tab_person
    rename column address to main_address_id;

alter table if exists tab_person
    rename column spouse to spouse_id;

alter table if exists tab_person
    rename column cafir to cafir_id;

alter table if exists tab_person
    rename column debit_pgfn_dau to debit_pgfn_dau_id;

alter table if exists tab_person
    rename column debit_mte to debit_mte_id;

alter table if exists tab_person
    rename column judicial_process to judicial_process_id;

alter table if exists tab_person
    rename column historical_criminal to historical_criminal_id;

