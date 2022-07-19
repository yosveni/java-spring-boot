create sequence if not exists seq_answer_interview start 1 increment 1;
create sequence if not exists seq_question_interview start 1 increment 1;

create table if not exists tab_answer_interview
(
    id                    int8      not null,
    answer                int4      not null,
    created               timestamp not null,
    modified              timestamp not null,
    question_interview_id int8      not null,
    company_user_id       int8,
    primary key (id)
);

create table if not exists tab_question_interview
(
    id             int8         not null,
    area           int4         not null,
    created        timestamp    not null,
    field          int4         not null,
    modified       timestamp    not null,
    possible_value varchar(255) not null,
    question       TEXT         not null,
    primary key (id)
);

alter table if exists tab_answer_interview
    add constraint FKarlx9liybj9mpd8nyvaawh4m2 foreign key (question_interview_id) references tab_question_interview;

alter table if exists tab_answer_interview
    add constraint FKlcdb2axhfwv2rqga2w31diqvg foreign key (company_user_id) references tab_company_user;
