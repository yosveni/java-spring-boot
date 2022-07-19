-- POPULATE_ROLE
insert into tab_role (id, authority, code, created, modified, name, description)
select 1,
       0,
       'LKP_SEC',
       current_timestamp,
       current_timestamp,
       'SISTEMA DE SEGURANÇA',
       'Administrador de funções' where not exists (select 1 from tab_role where id = 1);

insert into tab_role (id, authority, code, created, modified, name, description)
select 2,
       1,
       'LKP_BACK',
       current_timestamp,
       current_timestamp,
       'SISTEMA BACKOFFICE',
       'Backoffice de função' where not exists (select 1 from tab_role where id = 2);

insert into tab_role (id, authority, code, created, modified, name, description)
select 3,
       3,
       'LKP_CL',
       current_timestamp,
       current_timestamp,
       'SISTEMA CLIENTE',
       'Cliente de função' where not exists (select 1 from tab_role where id = 3);

insert into tab_role (id, authority, code, created, modified, name, description)
select 4,
       2,
       'LKP_PN',
       current_timestamp,
       current_timestamp,
       'SISTEMA EMPREENDEDOR',
       'Empreendedor de função' where not exists (select 1 from tab_role where id = 4);

select setval('seq_tab_role', 5, true);

-- POPULATE USER
insert into tab_user (id, created, email, enabled, is_token_expired, has_rating, modified, name, password, phone,
                      confirm_token, role_id)
select 1,
       current_timestamp,
       'admin@gmail.com',
       true,
       false,
       false,
       current_timestamp,
       'admin',
       '{bcrypt}$2a$10$80j/pTpAm8rfryWIPDTPVeSRuZl10cFxxA8x0DsL8elr4hUmH5cA.',
       null,
       null,
       1 where not exists (select 1 from tab_user where id = 1);

insert into tab_user (id, created, email, enabled, is_token_expired, has_rating, modified, name, password, phone,
                      confirm_token, role_id)
select 2,
       current_timestamp,
       'backoffice@gmail.com',
       true,
       false,
       false,
       current_timestamp,
       'backoffice',
       '{bcrypt}$2a$10$80j/pTpAm8rfryWIPDTPVeSRuZl10cFxxA8x0DsL8elr4hUmH5cA.',
       null,
       null,
       2 where not exists (select 1 from tab_user where id = 2);

insert into tab_user (id, created, email, enabled, is_token_expired, has_rating, modified, name, password, phone,
                      confirm_token, role_id)
select 3,
       current_timestamp,
       'client@gmail.com',
       true,
       false,
       false,
       current_timestamp,
       'client_test',
       '{bcrypt}$2a$10$80j/pTpAm8rfryWIPDTPVeSRuZl10cFxxA8x0DsL8elr4hUmH5cA.',
       null,
       null,
       3 where not exists (select 1 from tab_user where id = 3);

select setval('seq_tab_user', 4, true);

-- POPULATE CONFIGURATION
insert into tab_sys_configuration (id, name, description, configuration, created, modified)
select 1,
       'LearningOffer',
       'Default configuration for learning offer',
       '{"ipca":4.52, "deadline": "60 a 240 meses", "guarantee_min_value": 150000, "guarantee_max_value": 5000000, "percent_volume": 60}',
       current_timestamp,
       current_timestamp where not exists (select 1 from tab_sys_configuration where id = 1);

select setval('seq_sys_configuration', 2, true);