create sequence if not exists seq_physical_production start 1 increment 1;
create sequence if not exists seq_physical_production_variable start 1 increment 1;

create table if not exists tab_physical_production_variable
(
    id           bigint not null,
    measure_unit varchar(255),
    value        double precision,
    name         varchar(255),
    primary key (id)
);

create table if not exists tab_physical_production
(
    id                                              bigint not null,
    code_description                                text,
    date                                            timestamp,
    territorial_level                               varchar(255),
    cnae_id                                         bigint
        constraint FKdaskjhasdkadkjahdsag2i8pk references tab_cnae,
    monthly_index_id                                bigint
        constraint FKlkjlkj34534534lkjnasda8pk references tab_physical_production_variable,
    fixed_base_index_without_seasonal_adjustment_id bigint
        constraint FKplktrdftyetkjdsf4fty5i8pk references tab_physical_production_variable,
    monthly_percentage_change_id                    bigint
        constraint FK854l6k546j546456nypyuytpk references tab_physical_production_variable,
    percentage_change_accumulated_year_id           bigint
        constraint FKklrtjer4534kjhwwrerweljpk references tab_physical_production_variable,
    year_to_date_index_id                           bigint
        constraint FKkjlwerewr54ruruiidfdfs9pk references tab_physical_production_variable,
    percentage_change_accumulated_last_12_months_id bigint
        constraint FK098kjsdhfsdkfsdmfnsdfispk references tab_physical_production_variable,
    index_accumulated_last_12_months_id             bigint
        constraint FK098kj4d67sdkfsrgfnb6uispk references tab_physical_production_variable,
    primary key (id)
);
