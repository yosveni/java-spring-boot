update tab_role
set name        = 'AGENTE K',
    description = 'Agente K'
where tab_role.id = 4;

insert into tab_role(id, authority, code, name, description, created, modified)
values (5, 4, 'LKP_ENT', 'SISTEMA EMPREENDEDOR', 'Empreendedor de função', now(), now());

select setval('seq_tab_role', 6, true);
