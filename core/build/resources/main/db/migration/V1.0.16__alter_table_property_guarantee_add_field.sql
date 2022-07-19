alter table if exists tab_property_guarantee
    add column if not exists reference_property bigint not null default 0