create table if not exists tab_user_observed_companies
(
    user_id    bigint not null
        constraint fk_user_id
            references tab_user,
    company_id bigint not null
        constraint fk_company_id
            references tab_company
);

alter table tab_user_observed_companies
    owner to postgres;

create or replace function update_user_observed_companies() returns void as $$
declare
    rec record;
begin
    for rec in
        select c.user_id as user_id, c.id as company_id
        from tab_company c
        where c.user_id is not null loop
            insert into tab_user_observed_companies (user_id, company_id)
            values (rec.user_id, rec.company_id);
        end loop;
end;
$$
language 'plpgsql' volatile;

select update_user_observed_companies();
drop function update_user_observed_companies();

update tab_company set user_id = null where user_id is not null;