create
or replace function employed_growth() returns setof record
as $$
declare
rec record;

data_current_1
double precision = (select date_part('year', current_date)) - 1;

data_current_2
double precision = (select date_part('year', current_date)) - 2;

data_current_3
double precision = (select date_part('year', current_date)) - 3;

data_current_4
double precision = (select date_part('year', current_date)) - 4;

data_current_5
double precision = (select date_part('year', current_date)) - 5;

data_current_6
double precision = (select date_part('year', current_date)) - 6;

data_current_7
double precision = (select date_part('year', current_date)) - 7;

data_current_8
double precision = (select date_part('year', current_date)) - 8;

data_current_9
double precision = (select date_part('year', current_date)) - 9;

data_current_10
double precision = (select date_part('year', current_date)) - 10;

begin
for rec in
select tcmi.cnpj,
       tcmi.fantasy_name,
       data_current_1,
       (select data_growth from employed_growth_data(tc.id) where data_year = data_current_1),
       data_current_2,
       (select data_growth from employed_growth_data(tc.id) where data_year = data_current_2),
       data_current_3,
       (select data_growth from employed_growth_data(tc.id) where data_year = data_current_3),
       data_current_4,
       (select data_growth from employed_growth_data(tc.id) where data_year = data_current_4),
       data_current_5,
       (select data_growth from employed_growth_data(tc.id) where data_year = data_current_5),
       data_current_6,
       (select data_growth from employed_growth_data(tc.id) where data_year = data_current_6),
       data_current_7,
       (select data_growth from employed_growth_data(tc.id) where data_year = data_current_7),
       data_current_8,
       (select data_growth from employed_growth_data(tc.id) where data_year = data_current_8),
       data_current_9,
       (select data_growth from employed_growth_data(tc.id) where data_year = data_current_9),
       data_current_10,
       (select data_growth from employed_growth_data(tc.id) where data_year = data_current_10)
from linkapital.tab_company tc
         inner join linkapital.tab_company_main_info tcmi on tc.main_info = tcmi.id
where tc.client loop return next rec;
end loop;
return;
end;
$$
language 'plpgsql' stable;

create
or replace function employed_growth_data(bigint) returns table (data_year int, data_growth double precision) as
$$
begin
return query select teg.year,teg.growth
from linkapital.tab_employee_growth teg
where teg.company_id = $1
  and teg.year >= teg.year - 10;
end;
$$
language 'plpgsql' stable;

create
or replace function phones(bigint) returns varchar as $$
declare
rec record;
numbers
varchar(100)='';
begin
for rec in
select tcp.phones
from linkapital.tab_company_phones tcp
where tcp.tab_company_id = $1 loop
        if numbers = '' then
select concat(numbers, rec.phones)
into numbers;
else
select concat(numbers, ', ', rec.phones)
into numbers;
end if;
end loop;
return numbers;
end;
$$
language 'plpgsql' stable;

create
or replace function phones_rf(bigint) returns varchar as $$
declare
rec record;
numbers
varchar(100)='';
begin
for rec in
select tarp.rf_phone
from linkapital.tab_address_rf_phones tarp
where tarp.tab_address_id = $1 loop
        if numbers = '' then
select concat(numbers, rec.rf_phone)
into numbers;
else
select concat(numbers, ', ', rec.rf_phone)
into numbers;
end if;
end loop;
return numbers;
end;
$$
language 'plpgsql' stable;

create
or replace function inventors(bigint) returns varchar as $$
declare
rec record;
inventors
varchar(255)='';
begin
for rec in
select tipi.inventor
from linkapital.tab_inpi_patent_inventors tipi
where tipi.tab_inpi_patent_id = $1 loop
    if inventors = '' then
select concat(inventors, rec.inventor)
into inventors;
else
select concat(inventors, ', ', rec.inventor)
into inventors;
end if;
end loop;
return inventors;
end;
$$
language 'plpgsql' stable;

create
or replace function authors(bigint) returns varchar as $$
declare
rec record;
authors
varchar(255)='';
begin
for rec in
select tisa.author
from linkapital.tab_inpi_software_authors tisa
where tisa.tab_inpi_software_id = $1 loop
    if authors = '' then
select concat(authors, rec.author)
into authors;
else
select concat(authors, ', ', rec.author)
into authors;
end if;
end loop;
return authors;
end;
$$
language 'plpgsql' stable;

create
or replace function enum_activity_level(integer) returns varchar as $$
begin
case
    when $1 = 0 then return 'ALTA';
when $1 = 1 then return 'MEDIO';
when $1 = 2 then return 'BAIXA';
when $1 = 3 then return 'MUITO BAIXA';
when $1 = 4 then return 'INATIVO';
else return null;
end
case;
end;
$$
language 'plpgsql' stable;

create
or replace function enum_company_sector(integer) returns varchar as $$
begin
case
    when $1 = 0 then return 'SERVICOS';
when $1 = 1 then return 'INDUSTRIA';
when $1 = 2 then return 'COMERCIO';
else return null;
end
case;
end;
$$
language 'plpgsql' stable;

create
or replace function enum_company_size(integer) returns varchar as $$
begin
case
    when $1 = 0 then return 'ME';
when $1 = 1 then return 'EPP';
when $1 = 2 then return 'DEMAIS';
else return null;
end
case;
end;
$$
language 'plpgsql' stable;

create
or replace function enum_enabled_situation(integer) returns varchar as $$
begin
case
    when $1 = 0 then return 'DEFERIDA';
when $1 = 1 then return 'DEFERIDA CANCELADA';
when $1 = 2 then return 'DESABILITADA';
when $1 = 3 then return 'INDEFERIDA';
when $1 = 4 then return 'SUSPENSA';
when $1 = 5 then return 'NÃO INFORMADO';
else return null;
end
case;
end;
$$
language 'plpgsql' stable;

create
or replace function enum_registration_situation(integer) returns varchar as $$
begin
case
    when $1 = 0 then return 'ATIVA';
when $1 = 1 then return 'SUSPENSA';
when $1 = 2 then return 'BAIXADA';
when $1 = 3 then return 'NULA';
when $1 = 4 then return 'INAPTA';
else return null;
end
case;
end;
$$
language 'plpgsql' stable;