alter table if exists tab_company_main_info
    alter column opening_date type timestamp using opening_date::timestamp without time zone;