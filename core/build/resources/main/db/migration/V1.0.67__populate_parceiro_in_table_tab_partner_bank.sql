create table if not exists tab_bank_nomenclature_partners_bank
(
    bank_nomenclature_id int8 not null,
    partner_bank_id      int8 not null
);

alter table if exists tab_bank_nomenclature_partners_bank
    add constraint fk48bgdiqeospt70xrc24nqa91y foreign key (bank_nomenclature_id) references tab_bank_nomenclature,
    add constraint fk2sebhoonlic1ooyxhkvgd88l9 foreign key (partner_bank_id) references tab_partner_bank;

alter table if exists tab_partner_bank
    add constraint name_unique unique (name);

insert into tab_partner_bank(id, created, days, modified, name)
values ((select last_value from seq_partner_bank) + 1, current_timestamp, 7, current_timestamp, 'LINKAPITAL');
select setval('seq_partner_bank', (select last_value from seq_partner_bank) + 2, true);

insert into tab_partner_bank_areas(tab_partner_bank_id, area)
values ((select last_value from seq_partner_bank) - 1, 1),
       ((select last_value from seq_partner_bank) - 1, 2),
       ((select last_value from seq_partner_bank) - 1, 3),
       ((select last_value from seq_partner_bank) - 1, 4);