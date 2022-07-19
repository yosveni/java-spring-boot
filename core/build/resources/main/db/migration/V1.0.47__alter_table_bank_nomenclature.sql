create sequence if not exists seq_bank_nomenclature_urgency start 1 increment 1;

create table if not exists tab_bank_nomenclature_urgency
(
    id                   int8 not null,
    area                 int4 not null,
    urgency              int4 not null,
    bank_nomenclature_id int8 not null,
    primary key (id)
);

alter table if exists tab_bank_nomenclature_urgency
    add constraint FK92pco2e69ln042bm6ke0l3v26 foreign key (bank_nomenclature_id) references tab_bank_nomenclature;

create or replace function update_bank_nomenclature_urgencies() returns void as
$$
declare
    rec record;
begin
    for rec in
        select id, tbna.area as area, urgency
        from tab_bank_nomenclature tbn
                 join tab_bank_nomenclature_areas tbna on tbn.id = tbna.tab_bank_nomenclature_id
        loop
            insert into tab_bank_nomenclature_urgency (id, area, urgency, bank_nomenclature_id)
            values (nextval('seq_bank_nomenclature_urgency'), rec.area, rec.urgency, rec.id);
        end loop;
end;
$$
    language 'plpgsql' volatile;

select update_bank_nomenclature_urgencies();
drop function update_bank_nomenclature_urgencies();

alter table if exists tab_bank_nomenclature drop column urgency;
drop table if exists tab_bank_nomenclature_areas;