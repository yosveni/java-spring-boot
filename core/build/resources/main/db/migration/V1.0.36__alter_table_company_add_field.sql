alter table if exists tab_company add column if not exists date_registered timestamp;

update tab_company
set date_registered = subquery.created from
(
	select c.id, c.created from tab_company c
) as subquery
where tab_company.id = subquery.id;