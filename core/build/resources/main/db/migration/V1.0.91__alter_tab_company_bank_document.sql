alter table if exists tab_company_bank_document
    add column if not exists description_state text,
    add column if not exists state             integer not null default 0;