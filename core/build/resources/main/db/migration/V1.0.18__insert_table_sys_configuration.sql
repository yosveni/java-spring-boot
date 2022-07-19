insert into tab_sys_configuration (id, name, description, configuration, created, modified)
select 2,
       'LearningOffer3',
       'Default configuration for learning offer 3',
       '{"tax":0.9, "min_tax": 0.9, "max_tax": 4.5, "deadline": "Recebíveis"}',
       current_timestamp,
       current_timestamp where not exists (select 1 from tab_sys_configuration where id = 2);

select setval('seq_sys_configuration', 3, true);