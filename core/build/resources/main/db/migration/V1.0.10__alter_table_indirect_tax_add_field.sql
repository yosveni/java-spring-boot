alter table if exists tab_indirect_tax
    add column if not exists icms double precision default 0;