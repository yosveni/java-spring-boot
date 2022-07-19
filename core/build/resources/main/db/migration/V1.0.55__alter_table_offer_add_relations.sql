create sequence if not exists seq_offer_contract start 1 increment 1;
create sequence if not exists seq_offer_installment start 1 increment 1;
create sequence if not exists seq_offer_state_logs start 1 increment 1;

create table if not exists tab_offer_contract
(
    id       int8      not null,
    created  timestamp not null,
    modified timestamp not null,
    offer_id int8      not null,
    primary key (id)
);

create table if not exists tab_offer_installment
(
    id         int8      not null,
    created    timestamp not null,
    expiration timestamp not null,
    has_paid   boolean,
    modified   timestamp not null,
    total      float8    not null,
    offer_id   int8,
    primary key (id)
);

create table if not exists tab_offer_state_logs
(
    id           int8      not null,
    created      timestamp not null,
    modified     timestamp not null,
    notification text,
    offer_state  int4      not null,
    offer_id     int8,
    primary key (id)
);

alter table if exists tab_comment
    add column if not exists offer_id int8;

alter table if exists tab_offer
    add column if not exists offer_contract_id int8;

alter table if exists tab_directory
    add column if not exists offer_contract_id int8;

alter table if exists tab_comment
    add constraint FK2nm8y26wv18dtlitsuqin1bp5 foreign key (offer_id) references tab_offer;

alter table if exists tab_offer
    add constraint FK4egwo9xn7qfgj9nrw1y2jcbev foreign key (offer_contract_id) references tab_offer_contract;

alter table if exists tab_directory
    add constraint FKh4u42875g66r1wxdqbhdls0hb foreign key (offer_contract_id) references tab_offer_contract;

alter table if exists tab_offer_contract
    add constraint FKjlchwb4ueudrhq5reguelpq8c foreign key (offer_id) references tab_offer;

alter table if exists tab_offer_installment
    add constraint FKc861j0g38fq05n9yosooviu9u foreign key (offer_id) references tab_offer;

alter table if exists tab_offer_state_logs
    add constraint FKdgxxw5prf8crno024vggwejve foreign key (offer_id) references tab_offer;