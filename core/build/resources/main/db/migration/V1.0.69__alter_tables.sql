alter table if exists tab_user
    add column if not exists address_id int8,
    add column if not exists image      bytea,
    add constraint FKmlxcjy244gy535ext7vt5d4u1 foreign key (address_id) references tab_address;

alter table if exists tab_directory
    add column if not exists company_nfe_duplicity_id int8,
    add constraint Fhb157jyhvce7n5ujh57o0mrd4u1 foreign key (company_nfe_duplicity_id) references tab_company_user,
    drop column if exists sped_document_id;

alter table if exists tab_company_user
    add column if not exists sped_document_id int8,
    add constraint Fhnc14jy2456yn5953d7y3mrd4u foreign key (sped_document_id) references tab_directory;