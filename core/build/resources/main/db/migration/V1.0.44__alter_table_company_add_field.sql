alter table if exists tab_company add column if not exists offer_accepted boolean default false;
update tab_company set offer_accepted = true where user_id is not null;