alter table if exists tab_user
    add column if not exists code_country_phone varchar(6),
    add column if not exists complete_phone     varchar(255);

alter table if exists tab_user_temp
    add column if not exists code_country_phone varchar(6),
    add column if not exists complete_phone     varchar(255);

update tab_user
set phone = '+5548991999999'
where id = 1
   or id = 2
   or id = 3;

update tab_user
set complete_phone = phone;

update tab_user_temp
set complete_phone = phone;