alter table if exists tab_invoice
    add column if not exists code varchar(255);