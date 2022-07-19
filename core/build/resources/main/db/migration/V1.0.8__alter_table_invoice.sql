alter table if exists linkapital.tab_invoice
    add column if not exists cnpj varchar (255),
    add column if not exists name varchar (255);

alter table if exists linkapital.tab_invoice
alter
column company_invoice_id drop
not null;

alter table if exists linkapital.tab_invoice
alter
column company_received_id drop
not null;