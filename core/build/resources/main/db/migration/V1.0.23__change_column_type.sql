alter table if exists tab_cnep
alter
column sanction set data type text;

alter table if exists tab_ceis
alter
column organ_complement set data type text,
alter
column legal_substantiation set data type text,
alter
column information_entity set data type text,
alter
column sanction set data type text;