create sequence if not exists seq_user_whatsapp start 1 increment 1;

create table if not exists tab_user_whatsapp
(
    id            bigint       not null
        constraint tab_user_whatsapp_pkey primary key,
    name          varchar(255) not null,
    last_name     varchar(255),
    number        varchar(255) not null,
    work_position varchar(255),
    created       timestamp    not null,
    modified      timestamp    not null,
    enabled       boolean
);

alter table tab_user_whatsapp owner to postgres;

