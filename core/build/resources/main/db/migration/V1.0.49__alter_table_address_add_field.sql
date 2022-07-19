alter table if exists tab_address
    add column if not exists longitude double precision;
alter table if exists tab_address
    add column if not exists formatted_address text;

alter table if exists tab_address
    alter column latitude type double precision using (latitude::double precision);