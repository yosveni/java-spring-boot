alter table if exists tab_bank_nomenclature
    rename column fase_stage to stage;

alter table if exists tab_bank_nomenclature
    alter column stage set not null;

