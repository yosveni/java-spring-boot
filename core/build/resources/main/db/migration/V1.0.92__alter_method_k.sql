alter table if exists tab_company_user
    drop column if exists score_summary,
    drop column if exists sped_form;

alter table if exists tab_sped
    drop column if exists financial_strength_id;

drop table if exists tab_financial_strength_operation;
drop table if exists tab_financial_strength;
drop table if exists tab_score_summary;

drop sequence if exists seq_financial_strength;
drop sequence if exists seq_financial_strength_operation;
drop sequence if exists seq_score_summary;

create sequence if not exists seq_score_analysis start 1 increment 1;
create sequence if not exists seq_score_operation start 1 increment 1;

create table if not exists tab_score_analysis
(
    id              bigint           not null,
    year            integer,
    total           double precision not null,
    type            integer          not null,
    created         timestamp        not null,
    modified        timestamp        not null,
    company_user_id bigint
        constraint FK3cbg9qtude44dv751vt8o4iy7 references tab_company_user,
    primary key (id)
);

create table if not exists tab_score_operation
(
    id                bigint           not null,
    description       text             not null,
    value             double precision not null,
    created           timestamp        not null,
    modified          timestamp        not null,
    score_analysis_id bigint
        constraint FK5ygn7qtb3y34dv751vt57bny8 references tab_score_analysis,
    primary key (id)
);
