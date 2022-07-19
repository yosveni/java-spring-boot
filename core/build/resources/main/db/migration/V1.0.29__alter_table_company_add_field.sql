alter table if exists tab_company add column if not exists sped_form int8 constraint FKjuy6c8ojhfcplo9yj6nuq2ccb references tab_directory (id) on update cascade;
