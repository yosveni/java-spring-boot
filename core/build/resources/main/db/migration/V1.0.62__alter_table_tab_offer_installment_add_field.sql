alter table if exists tab_offer_installment
    add column if not exists document_id int8;

alter table if exists tab_offer_installment
    add constraint FKev4efjaqkwj28dr0cddsmdtkq foreign key (document_id) references tab_directory;
