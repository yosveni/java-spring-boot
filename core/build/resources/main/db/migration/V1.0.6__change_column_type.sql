alter table if exists linkapital.tab_cnae
alter
column description set data type text;

alter table if exists linkapital.tab_cnds
alter
column situation set data type text;

alter table if exists linkapital.tab_inpi_software
alter
column title set data type text;

alter table if exists linkapital.tab_debit_mte_process
alter
column infringement_capitulation set data type text,
alter
column infringement_category set data type text;

alter table if exists linkapital.tab_inpi_patent
alter
column depositor set data type text,
alter
column procurator set data type text,
alter
column title set data type text;