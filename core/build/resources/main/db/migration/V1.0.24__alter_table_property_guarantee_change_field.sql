alter table if exists tab_property
    add column if not exists reference_property int8 default 0;

alter table if exists tab_property_rural
    add column if not exists reference_property int8 default 0;

alter table if exists tab_property_guarantee
alter
column reference_property type int8,
alter
column reference_property set default 0;

update tab_property
set reference_property = floor(random() * 1000000) + 1
where reference_property = 0;

update tab_property_rural
set reference_property = floor(random() * 1000000) + 1
where reference_property = 0;