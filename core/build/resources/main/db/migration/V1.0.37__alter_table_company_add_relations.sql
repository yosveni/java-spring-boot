alter sequence if exists seq_sped_directory
    rename to seq_sped_document;
alter table if exists tab_sped_directory
    rename to tab_sped_document;
alter table if exists tab_directory
    rename column sped_directory_id to sped_document_id;

create sequence if not exists seq_bank_nomenclature start 1 increment 1;
create sequence if not exists seq_company_bank_document start 1 increment 1;

create table if not exists tab_bank_nomenclature
(
    id          int8         not null,
    created     timestamp    not null,
    description TEXT,
    modified    timestamp    not null,
    urgency     int4         not null,
    primary key (id)
);

create table if not exists tab_bank_nomenclature_areas
(
    tab_bank_nomenclature_id int8 not null,
    area                     int4 not null
);

create table if not exists tab_bank_nomenclature_extensions
(
    tab_bank_nomenclature_id int8         not null,
    ext                      varchar(255) not null
);

create table if not exists tab_company_bank_document
(
    id                   int8 not null,
    bank_nomenclature_id int8 not null,
    company_id           int8,
    primary key (id)
);

alter table if exists tab_bank_nomenclature_areas
    add constraint FK3q3ii0ko30epv8jg627lpy0x7 foreign key (tab_bank_nomenclature_id) references tab_bank_nomenclature;

alter table if exists tab_bank_nomenclature_extensions
    add constraint FK3q3i9hko6d4pv8kc227l85ex7 foreign key (tab_bank_nomenclature_id) references tab_bank_nomenclature;

alter table if exists tab_directory
    add column if not exists company_bank_document_id int8
        constraint FK0x3vgm5o30eb58jgy27l3ee2s references tab_company_bank_document;