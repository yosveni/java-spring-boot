alter table if exists tab_company add column if not exists user_id int8 constraint FKytgt49e65p0rnloc2yfeki7y references tab_user(id) on update cascade;

update tab_company
set user_id = subquery.id from
(
	select u.id, u.email from tab_user as u
) as subquery
where tab_company.user_property = subquery.email;

alter table if exists tab_company drop column if exists user_property;
