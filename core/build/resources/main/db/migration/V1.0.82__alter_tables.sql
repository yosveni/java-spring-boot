update tab_cnds
set emitter_name = 1
where emitter_name = 'PGFN';

update tab_cnds
set emitter_name = 2
where emitter_name = 'TCU';

update tab_cnds
set emitter_name = 3
where emitter_name = 'TST';

update tab_cnds
set emitter_name = 4
where emitter_name = 'FGTS';

update tab_cnds
set emitter_name = 5
where emitter_name = 'SIT';

update tab_company
set company_closing_propensity = 1
where company_closing_propensity = 'MUITO BAIXA';

update tab_company
set company_closing_propensity = 2
where company_closing_propensity = 'BAIXA';

update tab_company
set company_closing_propensity = 3
where company_closing_propensity = 'MEDIA';

update tab_company
set company_closing_propensity = 4
where company_closing_propensity = 'ALTA';

update tab_company
set company_closing_propensity = 5
where company_closing_propensity = 'MUITO ALTA';

alter table if exists tab_company
    alter column company_closing_propensity type int4 using company_closing_propensity::integer;

alter table if exists tab_cnds
    alter column emitter_name type int4 using emitter_name::integer,
    add column if not exists document_id int8,
    add constraint FKmlxgv7H65cJHg54xto0mrd4u1 foreign key (document_id) references tab_directory;
