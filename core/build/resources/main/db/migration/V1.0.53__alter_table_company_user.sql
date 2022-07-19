alter table if exists tab_company_user
    add column if not exists latest_registration_form boolean default false;

update tab_company_user
set latest_registration_form = subQuery.latest_registration_form
from (
         select id, latest_registration_form
         from tab_company
     ) as subQuery
where tab_company_user.company_id = subQuery.id;

alter table if exists tab_company
    drop column latest_registration_form;