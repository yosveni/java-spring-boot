alter table if exists tab_indicative_proposal
    drop column if exists area;
alter table if exists tab_indicative_proposal
    add column if not exists cnpj varchar(14) not null default '';
alter table if exists tab_indicative_proposal
    drop column if exists rejected;