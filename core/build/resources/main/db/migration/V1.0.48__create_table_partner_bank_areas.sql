create table if not exists tab_partner_bank_areas
(
    tab_partner_bank_id int8 not null,
    area                int4 not null
);

alter table if exists tab_partner_bank_areas
    add constraint FK3q3ii04h30epv58h627lpy0x7 foreign key (tab_partner_bank_id) references tab_partner_bank;