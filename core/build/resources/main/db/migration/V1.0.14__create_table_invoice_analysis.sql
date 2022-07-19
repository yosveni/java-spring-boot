create sequence if not exists seq_learning_analysis start 1 increment 1;

alter sequence seq_learning_analysis owner to postgres;

create table if not exists tab_learning_analysis
(
    id
    bigint
    not
    null
    constraint
    tab_learning_analysis_pkey
    primary
    key,
    learning_two
    jsonb,
    learning_three
    jsonb,
    learning_four
    jsonb
);

alter table tab_learning_analysis owner to postgres;

alter table if exists tab_company add column if not exists learning_analysis bigint constraint fkbfgkk9jyner44786bbe4011u8 references tab_learning_analysis (id) on update cascade;
