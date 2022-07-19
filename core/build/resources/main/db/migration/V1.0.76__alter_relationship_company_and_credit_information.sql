alter table if exists tab_credit_information
    add column if not exists created    timestamp,
    add column if not exists modified   timestamp,
    add column if not exists company_id int8
        constraint Fklm425fb7neres066u3lo46nht references tab_company (id) on update cascade;

update tab_credit_information
set company_id = sub.company_id,
    created    = sub.company_created,
    modified   = sub.company_modified
from (
         select tc.id       as company_id,
                tc.credit_information,
                tc.created  as company_created,
                tc.modified as company_modified
         from tab_company tc
     ) as sub
where id = sub.credit_information;

alter table if exists tab_credit_information
    alter column created set not null,
    alter column modified set not null;

alter table if exists tab_company
    drop column if exists credit_information;