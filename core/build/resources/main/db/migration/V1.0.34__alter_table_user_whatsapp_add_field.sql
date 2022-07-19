alter table if exists tab_user_whatsapp
    add column if not exists times_called int4 default 0;
