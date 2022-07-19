alter table if exists tab_company
    add column if not exists init_learning_offer1 boolean default false;
alter table if exists tab_company
    add column if not exists init_learning_offer2 boolean default false;
alter table if exists tab_company
    add column if not exists init_learning_offer3 boolean default false;
alter table if exists tab_company
    add column if not exists init_learning_offer4 boolean default false;

update tab_company tc
set init_learning_offer1 = true from tab_learning_offer lo
where tc.learning_offer1 = lo.id and lo.has_offer = true;

update tab_company tc
set init_learning_offer2 = true from tab_learning_offer lo
where tc.learning_offer2 = lo.id and lo.has_offer = true;

update tab_company tc
set init_learning_offer3 = true from tab_learning_offer lo
where tc.learning_offer3 = lo.id and lo.has_offer = true;

