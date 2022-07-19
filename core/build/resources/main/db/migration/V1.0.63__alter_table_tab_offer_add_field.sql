alter table if exists tab_offer
    add column if not exists contract_date timestamp;
