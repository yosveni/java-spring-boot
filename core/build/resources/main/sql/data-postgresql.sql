SET
    search_path TO linkapital;

create
    or replace function employed_growth() returns setof record
as
$$
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
        from tab_company tc
                 inner join tab_company_main_info tcmi on tc.main_info = tcmi.id
        where tc.client
        loop
            return next rec;
        end loop;
    return;
end;
$$
    language 'plpgsql' stable;

create
    or replace function employed_growth_data(bigint)
    returns table
            (
                data_year   int,
                data_growth double precision
            )
as
$$
begin
    return query select teg.year, teg.growth
                 from tab_employee_growth teg
                 where teg.company_id = $1
                   and teg.year >= teg.year - 10;
end;
$$
    language 'plpgsql' stable;

create
    or replace function phones(bigint) returns varchar as
$$
declare
    rec record;
    numbers
        varchar(100)='';
begin
    for rec in
        select tcp.phones
        from tab_company_phones tcp
        where tcp.tab_company_id = $1
        loop
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
    or replace function phones_rf(bigint) returns varchar as
$$
declare
    rec record;
    numbers
        varchar(100)='';
begin
    for rec in
        select tarp.rf_phone
        from tab_address_rf_phones tarp
        where tarp.tab_address_id = $1
        loop
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
    or replace function inventors(bigint) returns varchar as
$$
declare
    rec record;
    inventors
        varchar(255)='';
begin
    for rec in
        select tipi.inventor
        from tab_inpi_patent_inventors tipi
        where tipi.tab_inpi_patent_id = $1
        loop
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
    or replace function authors(bigint) returns varchar as
$$
declare
    rec record;
    authors
        varchar(255)='';
begin
    for rec in
        select tisa.author
        from tab_inpi_software_authors tisa
        where tisa.tab_inpi_software_id = $1
        loop
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
    or replace function enum_activity_level(integer) returns varchar as
$$
begin
    case
        when $1 = 0 then return 'ALTA';
        when $1 = 1 then return 'MEDIO';
        when $1 = 2 then return 'BAIXA';
        when $1 = 3 then return 'MUITO BAIXA';
        when $1 = 4 then return 'INATIVO';
        else return null;
        end case;
end;
$$
    language 'plpgsql' stable;

create
    or replace function enum_company_sector(integer) returns varchar as
$$
begin
    case
        when $1 = 0 then return 'SERVICOS';
        when $1 = 1 then return 'INDUSTRIA';
        when $1 = 2 then return 'COMERCIO';
        else return null;
        end case;
end;
$$
    language 'plpgsql' stable;

create
    or replace function enum_company_size(integer) returns varchar as
$$
begin
    case
        when $1 = 0 then return 'ME';
        when $1 = 1 then return 'EPP';
        when $1 = 2 then return 'DEMAIS';
        else return null;
        end case;
end;
$$
    language 'plpgsql' stable;

create
    or replace function enum_enabled_situation(integer) returns varchar as
$$
begin
    case
        when $1 = 0 then return 'DEFERIDA';
        when $1 = 1 then return 'DEFERIDA CANCELADA';
        when $1 = 2 then return 'DESABILITADA';
        when $1 = 3 then return 'INDEFERIDA';
        when $1 = 4 then return 'SUSPENSA';
        when $1 = 5 then return 'NÃO INFORMADO';
        else return null;
        end case;
end;
$$
    language 'plpgsql' stable;

create
    or replace function enum_registration_situation(integer) returns varchar as
$$
begin
    case
        when $1 = 0 then return 'ATIVA';
        when $1 = 1 then return 'SUSPENSA';
        when $1 = 2 then return 'BAIXADA';
        when $1 = 3 then return 'NULA';
        when $1 = 4 then return 'INAPTA';
        else return null;
        end case;
end;
$$
    language 'plpgsql' stable;

insert into tab_role (id, authority, code, created, modified, name, description)
values (1, 0, 'LKP_SEC', current_timestamp, current_timestamp, 'SISTEMA DE SEGURANÇA', 'Administrador de funções');
insert into tab_role (id, authority, code, created, modified, name, description)
values (2, 1, 'LKP_BACK', current_timestamp, current_timestamp, 'SISTEMA BACKOFFICE', 'Backoffice de função');
insert into tab_role (id, authority, code, created, modified, name, description)
values (3, 3, 'LKP_CL', current_timestamp, current_timestamp, 'SISTEMA CLIENTE', 'Cliente de função');
insert into tab_role (id, authority, code, created, modified, name, description)
values (4, 2, 'LKP_PN', current_timestamp, current_timestamp, 'SISTEMA EMPREENDEDOR', 'Empreendedor de função');

insert into tab_user (id, created, email, enabled, is_token_expired, has_rating, modified, name, password, phone,
                      confirm_token, role_id)
values (1, current_timestamp, 'admin@gmail.com', true, false, false, current_timestamp, 'admin',
        '{bcrypt}$2a$10$80j/pTpAm8rfryWIPDTPVeSRuZl10cFxxA8x0DsL8elr4hUmH5cA.', null, null, 1);

insert into tab_user (id, created, email, enabled, is_token_expired, has_rating, modified, name, password, phone,
                      confirm_token, role_id)
values (2, current_timestamp, 'backoffice@gmail.com', true, false, false, current_timestamp, 'backoffice',
        '{bcrypt}$2a$10$80j/pTpAm8rfryWIPDTPVeSRuZl10cFxxA8x0DsL8elr4hUmH5cA.', null, null, 2);

insert into tab_user (id, created, email, enabled, is_token_expired, has_rating, modified, name, password, phone,
                      confirm_token, role_id)
values (3, current_timestamp, 'client@gmail.com', true, false, false, current_timestamp, 'client_test',
        '{bcrypt}$2a$10$80j/pTpAm8rfryWIPDTPVeSRuZl10cFxxA8x0DsL8elr4hUmH5cA.', null, null, 3);

insert into tab_sys_configuration (id, created, modified, name, description, configuration)
select 1,
       current_timestamp,
       current_timestamp,
       'LearningOffer',
       'Default configuration for learning offer',
       '{"ipca":4.52, "deadline": "60 a 240 meses", "guarantee_min_value": 150000, "guarantee_max_value": 5000000, "percent_volume": 60}'
where not exists(select 1 from tab_sys_configuration where id = 1);

select setval('seq_sys_configuration', 2, true);
select setval('seq_tab_role', 5, true);
select setval('seq_tab_user', 4, true);