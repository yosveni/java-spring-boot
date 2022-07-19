alter table if exists tab_credit_information
    add column if not exists find boolean not null default true;