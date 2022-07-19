alter table if exists tab_user
    add column if not exists code_confirmation varchar(6),
    add column if not exists init_change_password timestamp;
