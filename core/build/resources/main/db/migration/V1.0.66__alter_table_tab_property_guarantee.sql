alter table if exists tab_property_guarantee
    add column if not exists owner_type      int4 not null default 0,
    add column if not exists document_id     int8;

alter table if exists tab_property_guarantee
    add constraint FKmlxcjy245cq9q5exto0mrd4u1 foreign key (document_id) references tab_directory;