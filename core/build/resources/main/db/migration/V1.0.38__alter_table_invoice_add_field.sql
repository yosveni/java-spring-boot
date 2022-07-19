alter table if exists tab_invoice
    add column if not exists due_date timestamp;
