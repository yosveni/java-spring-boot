create sequence if not exists seq_authorization_answer start 1 increment 1;
create sequence if not exists seq_authorization_question start 1 increment 1;
create sequence if not exists seq_owner_authorization start 1 increment 1;
create sequence if not exists seq_authorization_trace start 1 increment 1;

create table if not exists tab_authorization_answer
(
    id                        int8      not null,
    created                   timestamp not null,
    modified                  timestamp not null,
    yes_answer                boolean,
    authorization_question_id int8      not null,
    owner_authorization_id    int8,
    primary key (id)
);

create table if not exists tab_authorization_question
(
    id           int8      not null,
    created      timestamp not null,
    detail       TEXT,
    detail_title TEXT,
    modified     timestamp not null,
    question     TEXT,
    primary key (id)
);

create table if not exists tab_owner_authorization
(
    id                       int8      not null,
    authorization_trace_id   int8,
    belongs_company          boolean   not null,
    cancelled_reason         TEXT,
    consult_scr              boolean   not null,
    cpf                      varchar(11),
    created                  timestamp not null,
    email                    varchar(255),
    has_power                boolean   not null,
    has_representative_power boolean   not null,
    modified                 timestamp not null,
    name                     varchar(255),
    state                    int4      not null,
    token                    varchar(255),
    primary key (id)
);

create table if not exists tab_authorization_trace
(
    id           int8         not null,
    cep          varchar(255),
    city         varchar(255),
    country_name varchar(255),
    created      timestamp    not null,
    device       varchar(255),
    ip           varchar(255) not null,
    latitude     double precision,
    longitude    double precision,
    modified     timestamp    not null,
    navigator    varchar(255),
    primary key (id)
);

alter table if exists tab_company
    add column if not exists user_owner_id int8;

alter table if exists tab_company_user
    add column if not exists owner_authorization_id int8;

alter table if exists tab_authorization_answer
    add constraint FKdvp1petn41gsb43lh77v19tu8 foreign key (authorization_question_id) references tab_authorization_question;

alter table if exists tab_authorization_answer
    add constraint FK9aaemjxj0983emf77lq9qe1bl foreign key (owner_authorization_id) references tab_owner_authorization;

alter table if exists tab_company_user
    add constraint FKo2q605yk3sb1bsofyt2w29gxa foreign key (owner_authorization_id) references tab_owner_authorization;

alter table if exists tab_owner_authorization
    add constraint FKgl1p5y0e9hyi8poywpkfdou6d foreign key (authorization_trace_id) references tab_authorization_trace;

update tab_company
set user_owner_id = sub.user_id
from (
         select tcu.user_id, tcu.company_id
         from tab_company_user tcu
         where tcu.owner
     ) as sub
where id = sub.company_id;

