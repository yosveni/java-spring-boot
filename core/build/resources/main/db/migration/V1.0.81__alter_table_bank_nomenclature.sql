alter table if exists tab_bank_nomenclature
    add column if not exists fase_stage int4 default 0;