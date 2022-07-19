alter table if exists tab_company_user
    add column if not exists company_state integer not null default 0;

update tab_company_user
set company_state = subQuery.company_state
from (
         select c.id, c.company_state
         from tab_company as c
     ) as subQuery
where tab_company_user.company_id = subQuery.id;

alter table if exists tab_company
    drop column company_state;