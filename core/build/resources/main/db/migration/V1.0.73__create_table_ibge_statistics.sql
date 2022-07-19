create sequence if not exists seq_ibge start 1 increment 1;
create sequence if not exists seq_economic_statistics start 1 increment 1;
create sequence if not exists seq_geographic_statistics start 1 increment 1;
create sequence if not exists seq_work_performance_statistics start 1 increment 1;

create table if not exists tab_ibge
(
    id                             int8      not null,
    created                        timestamp not null,
    modified                       timestamp not null,
    economic_statistics_id         int8,
    geographic_statistics_id       int8,
    work_performance_statistics_id int8,
    primary key (id)
);

create table if not exists tab_economic_statistics
(
    id                              int8   not null,
    idhm                            float8 not null,
    idhm_year                       int4,
    percentage_revenue_sources      float8,
    percentage_revenue_sources_year int4,
    pib                             float8 not null,
    pib_year                        int4,
    total_expenses                  float8,
    total_expenses_year             int4,
    total_revenue                   float8,
    total_revenue_year              int4,
    primary key (id)
);

create table if not exists tab_geographic_statistics
(
    id                                    int8 not null,
    demographic_density                   float8,
    demographic_density_year              int4,
    estimated_population                  float8,
    estimated_population_last_census      float8,
    estimated_population_last_census_year int4,
    estimated_population_year             int4,
    primary key (id)
);

create table if not exists tab_work_performance_statistics
(
    id                                     int8 not null,
    average_salary                         float8,
    average_salary_year                    int4,
    busy_people                            float8,
    busy_people_year                       int4,
    occupied_population                    float8,
    occupied_population_year               int4,
    population_income_monthly_nominal      float8,
    population_income_monthly_nominal_year int4,
    primary key (id)
);

alter table if exists tab_company
    add column ibge_id int8;

alter table if exists tab_company
    add constraint FK56kh7vy5crregdxgyf6w52uj7 foreign key (ibge_id) references tab_ibge;

alter table if exists tab_ibge
    add constraint FK7m7l0a5rgfndg39qx929xl6df foreign key (economic_statistics_id) references tab_economic_statistics,
    add constraint FKc95h0f66f355jkydajlvucdlq foreign key (geographic_statistics_id) references tab_geographic_statistics,
    add constraint FK5c2j9pxgkryqo8ej6urq4721p foreign key (work_performance_statistics_id) references tab_work_performance_statistics;