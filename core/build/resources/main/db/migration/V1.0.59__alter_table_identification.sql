alter table if exists tab_identification
    add column if not exists state          int4 not null default 0,
    add column if not exists selfie_capture bytea,
    drop column if exists completed;
