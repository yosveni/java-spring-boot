insert into tab_sys_configuration (id, name, description, configuration, created, modified)
select 3,
       'WhatsApp',
       'Default configuration for whatsapp message',
       '{"message": "Bem-vindo ao LinKapital"}',
       current_timestamp,
       current_timestamp where not exists (select 1 from tab_sys_configuration where id = 3);

select setval('seq_sys_configuration', 4, true);