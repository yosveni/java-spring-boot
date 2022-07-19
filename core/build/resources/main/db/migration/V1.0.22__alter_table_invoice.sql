alter table if exists tab_invoice
drop
column if exists cnpj;

alter table if exists tab_invoice
drop
column if exists name;

alter table if exists tab_invoice
drop
column if exists sender_id;

alter table if exists tab_invoice
drop
column if exists recipient_id;

alter table if exists tab_invoice add column if not exists sender_recipient_id bigint not null constraint fkfnnb8dfynerr6yu6u3lo00nju references tab_sender_recipient (id) on update cascade;