create sequence if not exists seq_identification start 1 increment 1;

create table if not exists tab_identification
(
    id               int8         not null,
    created          timestamp    not null,
    completed        boolean default false,
    document         bytea        not null,
    reverse_document bytea,
    modified         timestamp    not null,
    type             varchar(255) not null,
    valid            timestamp    not null,
    primary key (id)
);

alter table if exists tab_user
    add column if not exists identification_id int8;
alter table if exists tab_user
    add constraint FKjp423m9jo2r006r44i5cwo1x0 foreign key (identification_id) references tab_identification;
