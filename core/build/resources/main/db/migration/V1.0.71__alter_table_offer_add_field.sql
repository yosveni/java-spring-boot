alter table if exists tab_offer
    add column if not exists accepted boolean default false,
    add column if not exists user_id  int8 not null default 3
        constraint Fklm4xx587nerhvt96u3lo46nht references tab_user (id) on update cascade;
