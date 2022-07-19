alter table tab_directory
    drop constraint fkh4u42875g66r1wxdqbhdls0hb;
alter table tab_directory
    drop column if exists offer_contract_id;

alter table tab_offer
    drop constraint fk4egwo9xn7qfgj9nrw1y2jcbev;
alter table tab_offer
    drop column if exists offer_contract_id;

drop table if exists tab_offer_contract;

alter table if exists tab_directory
    add column if not exists offer_id int8;
alter table if exists tab_directory
    add constraint FKev47scaqkwoturw0cddsmdtkq foreign key (offer_id) references tab_offer;

