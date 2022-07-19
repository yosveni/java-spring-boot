alter table if exists tab_company
    add column if not exists scr boolean default false;