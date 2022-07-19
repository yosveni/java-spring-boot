alter table if exists tab_learning_offer rename to tab_offer;
alter sequence if exists seq_learning_offer rename to seq_offer;

alter table if exists tab_offer add column if not exists offer_state int4 default 0;

update tab_offer set offer_state = 2 where has_offer = true and accepted = true;
update tab_offer set offer_state = 1 where has_offer = true and accepted = false;

alter table if exists tab_offer drop column if exists has_offer, drop column if exists accepted;

