alter table if exists tab_user
    add column if not exists intensity int4 default 0;

alter table if exists tab_comment
    add column if not exists read boolean default false;