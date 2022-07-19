create sequence if not exists seq_commission start 1 increment 1;
create sequence if not exists seq_commission_campaign start 1 increment 1;
create sequence if not exists seq_commission_campaign_attribute start 1 increment 1;
create sequence if not exists seq_commission_campaign_condition start 1 increment 1;
create sequence if not exists seq_commission_installment start 1 increment 1;
create sequence if not exists seq_commission_payment_percent start 1 increment 1;
create sequence if not exists seq_commission_percent start 1 increment 1;
create sequence if not exists seq_commission_campaign_limit start 1 increment 1;
create sequence if not exists seq_commission_campaign_user start 1 increment 1;

create table if not exists tab_commission
(
    id           int8      not null,
    amortization float8    not null,
    created      timestamp not null,
    disbursement float8    not null,
    liquidation  float8    not null,
    modified     timestamp not null,
    release_date timestamp,
    total        float8    not null,
    campaign_id  int8      not null,
    primary key (id)
);

create table if not exists tab_commission_campaign
(
    id                 int8      not null,
    active             boolean   not null,
    base               boolean   not null,
    created            timestamp not null,
    modified           timestamp not null,
    title              TEXT      not null,
    limit_id           int8,
    payment_percent_id int8      not null,
    primary key (id)
);

create table if not exists tab_commission_campaign_attribute
(
    id             int8         not null,
    attribute_type int4         not null,
    name           varchar(255) not null,
    primary key (id)
);

create table if not exists tab_commission_campaign_condition
(
    id                     int8         not null,
    connector              int4,
    operator               int4         not null,
    value                  varchar(255) not null,
    campaign_attribute_id  int8,
    commission_campaign_id int8,
    primary key (id)
);

create table if not exists tab_commission_installment
(
    id            int8      not null,
    created       timestamp not null,
    has_paid      boolean,
    modified      timestamp not null,
    payment_date  timestamp,
    total         float8    not null,
    total_base    float8,
    commission_id int8      not null,
    document_id   int8,
    primary key (id)
);

create table if not exists tab_commission_payment_percent
(
    id           int8   not null,
    amortization float8 not null,
    disbursement float8 not null,
    liquidation  float8 not null,
    primary key (id)
);

create table if not exists tab_commission_percent
(
    id                     int8   not null,
    month_max              int4   not null,
    month_min              int4   not null,
    percent                float8 not null,
    commission_campaign_id int8,
    primary key (id)
);

create table if not exists tab_commission_campaign_limit
(
    id             int8 not null,
    campaign_limit int4 not null,
    limit_count    int4 not null,
    user_limit     int4 not null,
    primary key (id)
);

create table if not exists tab_commission_campaign_user
(
    id          int8 not null,
    count       int4 not null,
    campaign_id int8 not null,
    user_id     int8 not null,
    primary key (id)
);

alter table if exists tab_offer
    add column if not exists commission_id int8;

alter table if exists tab_commission_campaign
    add constraint FKfs4g0kqsrcwnchdpri44aebm8 foreign key (limit_id) references tab_commission_campaign_limit;
alter table if exists tab_commission_campaign_user
    add constraint FKfls2oe8m73hnin91tm8n0ioyu foreign key (campaign_id) references tab_commission_campaign;
alter table if exists tab_commission_campaign_user
    add constraint FK7wpu1tiflgwp208327jw6ju2s foreign key (user_id) references tab_user;
alter table if exists tab_offer
    add constraint FK2pfohmv4ouh2bqis0753vdf14 foreign key (commission_id) references tab_commission;
alter table if exists tab_commission
    add constraint FK4pb2su1osolhbwd5wpv222fqy foreign key (campaign_id) references tab_commission_campaign;
alter table if exists tab_commission_campaign
    add constraint FKcd4a6ou99q0o572t48vd6hhhm foreign key (payment_percent_id) references tab_commission_payment_percent;
alter table if exists tab_commission_campaign_condition
    add constraint FKc76w97kjq7e1tpv8tiul3u6kh foreign key (campaign_attribute_id) references tab_commission_campaign_attribute;
alter table if exists tab_commission_campaign_condition
    add constraint FKlbgpgfeapaf5lkv9fa8ehv33o foreign key (commission_campaign_id) references tab_commission_campaign;
alter table if exists tab_commission_installment
    add constraint FKt6umcrvabng96j31fmjrbeiax foreign key (commission_id) references tab_commission;
alter table if exists tab_commission_installment
    add constraint FKs2de8k0h6in0ypb1rfslvugww foreign key (document_id) references tab_directory;
alter table if exists tab_commission_percent
    add constraint FKh60fhi24mmwh6aucr6ri49sfe foreign key (commission_campaign_id) references tab_commission_campaign;

insert into tab_commission_campaign_attribute (id, attribute_type, name)
values (1, 0, 'Produto');
insert into tab_commission_campaign_attribute (id, attribute_type, name)
values (2, 1, 'Data do contrato');
insert into tab_commission_campaign_attribute (id, attribute_type, name)
values (3, 2, 'Valor da operação');
select setval('seq_commission_campaign_attribute', 4, true);

insert into tab_commission_payment_percent(id, amortization, disbursement, liquidation)
values (1, 0.40, 0.40, 0.20);
select setval('seq_commission_payment_percent', 2, true);

insert into tab_commission_campaign(id, active, base, created, modified, title, limit_id, payment_percent_id)
values (1, true, true, current_timestamp, current_timestamp, 'Campanha de base', null, 1);
select setval('seq_commission_campaign', 2, true);

insert into tab_commission_percent(id, month_max, month_min, percent, commission_campaign_id)
values (1, 1, 1, 0.10, 1);
insert into tab_commission_percent(id, month_max, month_min, percent, commission_campaign_id)
values (2, 2, 2, 0.20, 1);
insert into tab_commission_percent(id, month_max, month_min, percent, commission_campaign_id)
values (3, 3, 3, 0.30, 1);
insert into tab_commission_percent(id, month_max, month_min, percent, commission_campaign_id)
values (4, 4, 4, 0.40, 1);
insert into tab_commission_percent(id, month_max, month_min, percent, commission_campaign_id)
values (5, 5, 5, 0.50, 1);
insert into tab_commission_percent(id, month_max, month_min, percent, commission_campaign_id)
values (6, 6, 6, 0.60, 1);
insert into tab_commission_percent(id, month_max, month_min, percent, commission_campaign_id)
values (7, 7, 7, 0.70, 1);
insert into tab_commission_percent(id, month_max, month_min, percent, commission_campaign_id)
values (8, 8, 8, 0.80, 1);
insert into tab_commission_percent(id, month_max, month_min, percent, commission_campaign_id)
values (9, 9, 9, 0.90, 1);
insert into tab_commission_percent(id, month_max, month_min, percent, commission_campaign_id)
values (10, 10, 12, 1.00, 1);
insert into tab_commission_percent(id, month_max, month_min, percent, commission_campaign_id)
values (11, 13, 18, 1.50, 1);
insert into tab_commission_percent(id, month_max, month_min, percent, commission_campaign_id)
values (12, 19, 36, 2.00, 1);
insert into tab_commission_percent(id, month_max, month_min, percent, commission_campaign_id)
values (13, 37, 60, 2.50, 1);
select setval('seq_commission_percent', 14, true);