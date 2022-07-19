alter table if exists tab_comment
    drop column if exists read,
    alter column company_user_id drop not null,
    add column if not exists user_id int8,
    add constraint FK9v47sghtuwotud20c4r5mderh foreign key (user_id) references tab_user;

create table if not exists tab_comment_users_views
(
    tab_comment_id int8 not null,
    users_views    int8 not null
);

alter table if exists tab_comment_users_views
    add constraint FK2d9suyw745bkv7go8tdiwromd foreign key (tab_comment_id) references tab_comment
