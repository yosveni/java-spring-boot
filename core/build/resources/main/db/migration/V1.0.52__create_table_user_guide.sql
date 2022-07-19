create sequence if not exists seq_user_guide start 1 increment 1;

create table if not exists tab_user_guide
(
    id                      int8 not null,
    credit_application_flow integer default 0,
    add_company_audio       boolean default false,
    aval_audio              boolean default false,
    discount_audio          boolean default false,
    re_audio                boolean default false,
    im_audio                boolean default false,
    general_company         boolean default false,
    completed               boolean default false,
    primary key (id)
);

alter table if exists tab_user
    add column if not exists user_guide_id bigint
        constraint tab_user_tab_user_guide_fkey references tab_user_guide (id) on update cascade;

create or replace function update_guides() returns void as
$$
declare
    rec record;
begin
    for rec in
        select id, user_guide_id as user_id
        from tab_user
        where user_guide_id is null
        loop
            insert into tab_user_guide(id, credit_application_flow)
            select nextval('seq_user_guide'),
                   case
                       when
                           exists(select from tab_company_user where user_id = rec.id) is true then 1
                       else 0 end;
            update tab_user
            set user_guide_id = currval('seq_user_guide')
            where id = rec.id;
        end loop;
end;
$$
    language 'plpgsql' volatile;

select update_guides();
drop function update_guides();