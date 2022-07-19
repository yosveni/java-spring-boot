alter table if exists tab_learning_offer
    add column if not exists min_offer_tax double precision default 0,
    add column if not exists max_offer_tax double precision default 0,
    add column if not exists min_offer_tax_year double precision default 0,
    add column if not exists max_offer_tax_year double precision default 0,
    add column if not exists volume_min double precision default 0,
    add column if not exists volume_max double precision default 0;