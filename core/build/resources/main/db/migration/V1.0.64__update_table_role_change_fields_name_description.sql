update tab_role
set name        = 'Admin',
    description = 'Administrador do sistema'
where id = 1;

update tab_role
set name        = 'Backoffice',
    description = 'Backoffice'
where id = 2;

update tab_role
set name        = 'Curioso',
    description = 'Usuário curioso da plataforma',
    code        = 'LKP_CLIENT'
where id = 3;

update tab_role
set name        = 'Agente K',
    description = 'Agente K',
    code        = 'LKP_AGENT'
where id = 4;

update tab_role
set name        = 'Empreendedor',
    description = 'Empreendedor financeiro',
    code        = 'LKP_EMP'
where id = 5;
