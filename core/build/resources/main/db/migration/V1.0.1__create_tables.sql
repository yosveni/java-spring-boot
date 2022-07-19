create table tab_address
(
    id                    bigint not null
        constraint tab_address_pkey
            primary key,
    address1              varchar(255),
    address2              varchar(255),
    border_municipality   varchar(255),
    building_type         varchar(255),
    code_country          varchar(255),
    code_municipality     varchar(255),
    collective_building   boolean,
    country               varchar(255),
    delivery_restriction  boolean,
    latest_address        boolean,
    latitude              varchar(255),
    m_region              varchar(255),
    micro_region          varchar(255),
    municipality          varchar(255),
    neighborhood          varchar(255),
    number                varchar(255),
    original_neighborhood varchar(255),
    precision             varchar(255),
    region                varchar(255),
    registry_uf           varchar(255),
    residential_address   boolean,
    uf                    varchar(255),
    zip                   varchar(255),
    person_id             bigint
);

alter table tab_address owner to postgres;

create table tab_address_rf_phones
(
    tab_address_id bigint not null
        constraint fk3onkkfxv3a3w0w1uht7gm31rq
            references tab_address,
    rf_phone       varchar(255)
);

alter table tab_address_rf_phones owner to postgres;

create table tab_antt
(
    id              bigint not null
        constraint tab_antt_pkey
            primary key,
    category        varchar(255),
    emission_date   timestamp,
    expiration_date timestamp,
    municipality    varchar(255),
    rntrc_number    varchar(255),
    situation       varchar(255),
    uf              varchar(255)
);

alter table tab_antt owner to postgres;

create table tab_cafir
(
    id                    bigint not null
        constraint tab_cafir_pkey
            primary key,
    quantity_condominiums integer,
    quantity_holder       integer,
    total_area            double precision
);

alter table tab_cafir owner to postgres;

create table tab_cnae
(
    id                bigint not null
        constraint tab_cnae_pkey
            primary key,
    business_activity varchar(255),
    code              varchar(255),
    description       varchar(255),
    sector            integer,
    company_id        bigint
);

alter table tab_cnae owner to postgres;

create table tab_company_property_rural
(
    id                    bigint not null
        constraint tab_company_property_rural_pkey
            primary key,
    quantity_condominiums integer,
    quantity_holder       integer,
    total_area            double precision
);

alter table tab_company_property_rural owner to postgres;

create table tab_debit_mte
(
    id               bigint not null
        constraint tab_debit_mte_pkey
            primary key,
    certificate_type varchar(255),
    code             varchar(255),
    debit_situation  varchar(255),
    emission_date    timestamp
);

alter table tab_debit_mte owner to postgres;

create table tab_debit_mte_process
(
    id                        bigint not null
        constraint tab_debit_mte_process_pkey
            primary key,
    infringement_capitulation varchar(255),
    infringement_category     varchar(255),
    number                    varchar(255),
    situation                 varchar(255),
    debit_mte_id              bigint not null
        constraint fk92pgmd6sllns5ki4yv3gcqutg
            references tab_debit_mte
);

alter table tab_debit_mte_process owner to postgres;

create table tab_debit_pgfn_dau
(
    id           bigint not null
        constraint tab_debit_pgfn_dau_pkey
            primary key,
    total_debits double precision
);

alter table tab_debit_pgfn_dau owner to postgres;

create table tab_debit_pgfn
(
    id                 bigint           not null
        constraint tab_debit_pgfn_pkey
            primary key,
    debit              double precision not null,
    inscription_number varchar(255),
    nature             varchar(255),
    debit_pgfn_dau_id  bigint           not null
        constraint fkjm58f11pm9hiemt861daa0hn3
            references tab_debit_pgfn_dau
);

alter table tab_debit_pgfn owner to postgres;

create table tab_financial_activity
(
    id                   bigint not null
        constraint tab_financial_activity_pkey
            primary key,
    enablement_date      timestamp,
    enablement_number    varchar(255),
    enablement_situation varchar(255),
    query_date           timestamp,
    segment              text
);

alter table tab_financial_activity owner to postgres;

create table tab_foreign_commerce
(
    id                    bigint  not null
        constraint tab_foreign_commerce_pkey
            primary key,
    authorized_operations varchar(255),
    enabled               boolean not null,
    enabled_situation     integer,
    modality              varchar(255),
    situation_date        timestamp,
    sub_modality          varchar(255)
);

alter table tab_foreign_commerce owner to postgres;

create table tab_heavy_vehicle_info
(
    id                     bigint not null
        constraint tab_heavy_vehicle_info_pkey
            primary key,
    between_2_and_5        integer,
    between_5_and_10       integer,
    group_between_2_and_5  integer,
    group_between_5_and_10 integer,
    group_over_10          integer,
    group_up_to_1          integer,
    heavy_vehicles         integer,
    heavy_vehicles_group   integer,
    over_10                integer,
    up_to_1                integer
);

alter table tab_heavy_vehicle_info owner to postgres;

create table tab_heavy_vehicle
(
    id                    bigint  not null
        constraint tab_heavy_vehicle_pkey
            primary key,
    antt                  boolean not null,
    car_plate             text,
    fuel                  varchar(255),
    model                 varchar(255),
    production_year       integer,
    renavam               varchar(255),
    type                  varchar(255),
    uf                    varchar(255),
    heavy_vehicle_info_id bigint
        constraint fktrvxmvsc15w9nwqyjbra4bxpx
            references tab_heavy_vehicle_info
);

alter table tab_heavy_vehicle owner to postgres;

create table tab_historical_criminal
(
    id                bigint not null
        constraint tab_historical_criminal_pkey
            primary key,
    consultation_date timestamp,
    protocol          varchar(255),
    situation         varchar(255),
    status            varchar(255)
);

alter table tab_historical_criminal owner to postgres;

create table tab_ibama_cnd
(
    id                 bigint not null
        constraint tab_ibama_cnd_pkey
            primary key,
    certificate_number varchar(255),
    emit_date          timestamp,
    situation          varchar(255),
    valid_date         timestamp
);

alter table tab_ibama_cnd owner to postgres;

create table tab_judicial_process
(
    id                  bigint not null
        constraint tab_judicial_process_pkey
            primary key,
    total_active_value  double precision,
    total_others_value  double precision,
    total_passive_value double precision,
    total_value         double precision
);

alter table tab_judicial_process owner to postgres;

create table tab_judicial_process_quantity
(
    id                    bigint not null
        constraint tab_judicial_process_quantity_pkey
            primary key,
    quantity_active       integer,
    quantity_active_part  integer,
    quantity_others       integer,
    quantity_passive_part integer,
    quantity_total        integer,
    type                  varchar(255),
    judicial_process_id   bigint
        constraint fkja96lht77hogjb7pcninnda2e
            references tab_judicial_process
);

alter table tab_judicial_process_quantity owner to postgres;

create table tab_learning_offer
(
    offer_type      integer          not null,
    id              bigint           not null
        constraint tab_learning_offer_pkey
            primary key,
    accepted        boolean          not null,
    created         timestamp        not null,
    dead_line       varchar(255),
    has_offer       boolean          not null,
    modified        timestamp        not null,
    precision       double precision not null,
    tax             double precision not null,
    volume          double precision not null,
    max_dead_line   double precision,
    max_offer_tax   double precision,
    min_dead_line   double precision,
    min_offer_tax   double precision,
    parent_offer_id bigint
        constraint fkf2jtkt4acbo90i3nhf9rlqhlb
            references tab_learning_offer
);

alter table tab_learning_offer owner to postgres;

create table tab_management_contract
(
    id          bigint  not null
        constraint tab_management_contract_pkey
            primary key,
    quantity    integer not null,
    total_value double precision
);

alter table tab_management_contract owner to postgres;

create table tab_contract
(
    id                     bigint not null
        constraint tab_contract_pkey
            primary key,
    contract_number        varchar(255),
    end_date               timestamp,
    final_value            double precision,
    init_date              timestamp,
    months_validity        integer,
    organ                  varchar(255),
    sphere                 varchar(255),
    uf                     varchar(255),
    management_contract_id bigint
        constraint fkbmop5uainh8rnb6kevewjxt7a
            references tab_management_contract
);

alter table tab_contract owner to postgres;

create table tab_modality_contract
(
    id                     bigint  not null
        constraint tab_modality_contract_pkey
            primary key,
    quantity               integer not null,
    type                   varchar(255),
    management_contract_id bigint
        constraint fkj4n719cc691npfoo8myt17n68
            references tab_management_contract
);

alter table tab_modality_contract owner to postgres;

create table tab_person
(
    id                   bigint    not null
        constraint tab_person_pkey
            primary key,
    age                  integer   not null,
    birth_date           timestamp,
    cns                  varchar(255),
    cpf                  varchar(11),
    created              timestamp not null,
    data_neo_way         boolean,
    dead                 boolean   not null,
    dead_confirmation    boolean,
    dead_date            integer,
    debt_pfgn_dau        varchar(255),
    deficiency           boolean   not null,
    deficiency_type      varchar(255),
    education_level      varchar(255),
    email                varchar(255),
    father_name          varchar(255),
    inscription_cpf_date timestamp,
    modified             timestamp not null,
    mother_cpf           varchar(255),
    mother_name          varchar(255),
    name                 varchar(255),
    nis                  varchar(255),
    origin_country       varchar(255),
    profession_cbo       varchar(255),
    profession_neoway    varchar(255),
    public_agent         boolean,
    quantity_qsa_unique  integer,
    register_situation   varchar(255),
    sex                  varchar(255),
    situation            varchar(255),
    situation_cpf        varchar(255),
    social_inscription   varchar(255),
    cafir                bigint
        constraint fk3bdua9xtt89gyvjixau8k7wa9
            references tab_cafir,
    debit_mte            bigint
        constraint fk5lvv6jif38e3e9qts2yijin2o
            references tab_debit_mte,
    debit_pgfn_dau       bigint
        constraint fkf2tsi6d2vvcld3o3ojjkykwpw
            references tab_debit_pgfn_dau,
    historical_criminal  bigint
        constraint fk5l3ri56jvnq9g8tttgq9ktvhh
            references tab_historical_criminal,
    judicial_process     bigint
        constraint fk6h683s9rwpp68ixbso7t4f63e
            references tab_judicial_process,
    address              bigint
        constraint fk11bdrt2yeln7fanv3sa840rb
            references tab_address,
    spouse               bigint
        constraint fkkl3togorb7cho92eyhpw9e4oh
            references tab_person
);

alter table tab_person owner to postgres;

alter table tab_address
    add constraint fk9vu0jl2d01ob2wswyh2a3wqla
        foreign key (person_id) references tab_person;

create table tab_disabilities_bacen
(
    id                  bigint  not null
        constraint tab_disabilities_bacen_pkey
            primary key,
    duration            integer not null,
    penalty             varchar(255),
    penalty_period_date timestamp,
    publication_date    timestamp,
    person_id           bigint
        constraint fklhosdrfg5wnb3fdng2xi29iye
            references tab_person
);

alter table tab_disabilities_bacen owner to postgres;

create table tab_historical_functional
(
    id             bigint  not null
        constraint tab_historical_functional_pkey
            primary key,
    admission_date timestamp,
    cnpj           varchar(255),
    dismissed_date timestamp,
    months         integer not null,
    social_reason  varchar(255),
    person_id      bigint
        constraint fkp0w6jy6du784vc6t9k0wqpkqc
            references tab_person
);

alter table tab_historical_functional owner to postgres;

create table tab_irpf
(
    id                bigint not null
        constraint tab_irpf_pkey
            primary key,
    agency            varchar(255),
    availability_date timestamp,
    bank              varchar(255),
    lot               varchar(255),
    statement_status  varchar(255),
    year_exercise     integer,
    person_id         bigint
        constraint fk97cc8njjvbc9h1s8gjv1ed4hp
            references tab_person
);

alter table tab_irpf owner to postgres;

create table tab_open_capital
(
    id                       bigint not null
        constraint tab_open_capital_pkey
            primary key,
    main_activity            varchar(255),
    name_business            varchar(255),
    negotiation_code         varchar(255),
    sectorial_classification varchar(255),
    site                     varchar(255),
    person                   bigint
        constraint fkapxl072iqf1bejrhuqsuueqx8
            references tab_person
);

alter table tab_open_capital owner to postgres;

create table tab_action_position
(
    id                         bigint not null
        constraint tab_action_position_pkey
            primary key,
    common_actions_value       double precision,
    document                   varchar(255),
    preferential_actions_value double precision,
    total_value                double precision,
    open_capital_id            bigint
        constraint fkk47dktdpox20rbev93v5u3134
            references tab_open_capital
);

alter table tab_action_position owner to postgres;

create table tab_pat
(
    id                     bigint not null
        constraint tab_pat_pkey
            primary key,
    benefited_employees    integer,
    exercise_date          timestamp,
    inscription            varchar(255),
    registration_situation integer,
    person_id              bigint
        constraint fk33jmrcpbvgs8j4u2hrswfljqs
            references tab_person
);

alter table tab_pat owner to postgres;

create table tab_meal_provided
(
    id       bigint       not null
        constraint tab_meal_provided_pkey
            primary key,
    quantity integer      not null,
    type     varchar(255) not null,
    pat_id   bigint
        constraint fktm9j56xevdyyf3p7cq7dtlau1
            references tab_pat
);

alter table tab_meal_provided owner to postgres;

create table tab_pat_modality
(
    id                     bigint not null
        constraint tab_pat_modality_pkey
            primary key,
    benefited_employees    integer,
    mode                   varchar(255),
    over_sm                integer,
    provider_cnpj          varchar(255),
    provider_social_season varchar(255),
    to_sm                  integer,
    pat_id                 bigint not null
        constraint fkbj0rim7gswgd6uvbhrs5ybby4
            references tab_pat
);

alter table tab_pat_modality owner to postgres;

create table tab_patrimonial_balance
(
    id                          bigint  not null
        constraint tab_patrimonial_balance_pkey
            primary key,
    active_total                double precision,
    active_value                double precision,
    liquid_patrimony            double precision,
    liquid_patrimony_controller double precision,
    year                        integer not null,
    open_capital_id             bigint
        constraint fklom5y2p3l2nwsid54krwh05ki
            references tab_open_capital
);

alter table tab_patrimonial_balance owner to postgres;

create table tab_penalty
(
    id              bigint not null
        constraint tab_penalty_pkey
            primary key,
    crated          timestamp,
    reason          varchar(255),
    open_capital_id bigint
        constraint fk3thwpxl1u2u48h6t51ihn0ah2
            references tab_open_capital
);

alter table tab_penalty owner to postgres;

create table tab_person_criminal_process
(
    tab_person_id    bigint not null
        constraint fk3agqsqu04aa3wqnt83ff1oa40
            references tab_person,
    criminal_process varchar(255)
);

alter table tab_person_criminal_process owner to postgres;

create table tab_person_phones
(
    tab_person_id bigint not null
        constraint fkp5nmok4ji2ykslsun18actodg
            references tab_person,
    phone         varchar(255)
);

alter table tab_person_phones owner to postgres;

create table tab_procon
(
    id                  bigint not null
        constraint tab_procon_pkey
            primary key,
    group_penalty_value double precision,
    name                varchar(255),
    total_penalty_value double precision,
    update_date         timestamp
);

alter table tab_procon owner to postgres;

create table tab_procon_group
(
    id                  bigint not null
        constraint tab_procon_group_pkey
            primary key,
    cnpj                varchar(255),
    total_penalty_value double precision,
    procon_id           bigint
        constraint fkryn9gmwovik3dy4q38bqj7npc
            references tab_procon
);

alter table tab_procon_group owner to postgres;

create table tab_procon_processes
(
    id             bigint not null
        constraint tab_procon_processes_pkey
            primary key,
    penalty_value  double precision,
    process_number varchar(255),
    status         varchar(255),
    procon_id      bigint
        constraint fkdh32x0vs8jhg35v0x4o2hxp4l
            references tab_procon
);

alter table tab_procon_processes owner to postgres;

create table tab_property_rural
(
    id                        bigint           not null
        constraint tab_property_rural_pkey
            primary key,
    area                      double precision not null,
    condominium               varchar(255),
    municipality              varchar(255),
    name                      varchar(255),
    nirf                      varchar(255),
    type                      varchar(255),
    uf                        varchar(255),
    cafir_property_rural_id   bigint
        constraint fka0clbv1gjqr2sp33hq3lhonfr
            references tab_cafir,
    company_property_rural_id bigint
        constraint fkbmqvbbc1wjk0nxbseyyq5lgjx
            references tab_company_property_rural
);

alter table tab_property_rural owner to postgres;

create table tab_relationship
(
    id          bigint not null
        constraint tab_relationship_pkey
            primary key,
    cpf         varchar(255),
    description varchar(255),
    name        varchar(255),
    person_id   bigint
        constraint fktbdrl62ek0qv45tqwe3pksxgq
            references tab_person
);

alter table tab_relationship owner to postgres;

create table tab_reputation
(
    id           bigint           not null
        constraint tab_reputation_pkey
            primary key,
    avg          double precision not null,
    avg1         double precision not null,
    avg2         double precision not null,
    avg3         double precision not null,
    avg4         double precision not null,
    avg5         double precision not null,
    count_1      integer          not null,
    count_2      integer          not null,
    count_3      integer          not null,
    count_4      integer          not null,
    count_5      integer          not null,
    total_rating integer          not null
);

alter table tab_reputation owner to postgres;

create table tab_rating
(
    id            bigint       not null
        constraint tab_rating_pkey
            primary key,
    comment       text         not null,
    email         varchar(255) not null
        constraint uk_grwub2r760h8u9nxpgvssft6g
            unique,
    rating_value  integer      not null,
    reputation_id bigint
        constraint fkl1or7nsjs64kk25kbi0xk52wt
            references tab_reputation
);

alter table tab_rating owner to postgres;

create table tab_result_demonstration
(
    id                             bigint  not null
        constraint tab_result_demonstration_pkey
            primary key,
    benefit_period                 double precision,
    benefit_period_controller      double precision,
    recipe_sale                    double precision,
    recipe_brute                   double precision,
    result_financial               double precision,
    result_liquid_operations       double precision,
    result_patrimonial_equivalence double precision,
    year                           integer not null,
    open_capital_id                bigint
        constraint fk256ctxtplvd67464fylym4fwl
            references tab_open_capital
);

alter table tab_result_demonstration owner to postgres;

create table tab_role
(
    id          bigint       not null
        constraint tab_role_pkey
            primary key,
    authority   integer      not null,
    code        varchar(255) not null,
    created     timestamp    not null,
    description varchar(255),
    modified    timestamp    not null,
    name        varchar(255) not null
);

alter table tab_role owner to postgres;

create table tab_sender_recipient
(
    id           bigint not null
        constraint tab_sender_recipient_pkey
            primary key,
    cnae         varchar(255),
    cnpj         varchar(255),
    email        varchar(255),
    fantasy_name varchar(255),
    name         varchar(255),
    address_id   bigint
        constraint fkoce13me20qi25n12wwyjqj3s8
            references tab_address
);

alter table tab_sender_recipient owner to postgres;

create table tab_sys_configuration
(
    id            bigint       not null
        constraint tab_sys_configuration_pkey
            primary key,
    configuration jsonb        not null,
    created       timestamp    not null,
    description   varchar(255),
    modified      timestamp    not null,
    name          varchar(255) not null
);

alter table tab_sys_configuration owner to postgres;

create table tab_tax_health
(
    id         bigint not null
        constraint tab_tax_health_pkey
            primary key,
    tax_health varchar(255)
);

alter table tab_tax_health owner to postgres;

create table tab_cnds
(
    id                 bigint not null
        constraint tab_cnds_pkey
            primary key,
    certificate_number varchar(255),
    emission_date      timestamp,
    emitter_name       varchar(255),
    expiration_date    timestamp,
    situation          varchar(255),
    tax_health_id      bigint
        constraint fkbduiid7ycic1pcwkbc3qvfvtt
            references tab_tax_health
);

alter table tab_cnds owner to postgres;

create table tab_company
(
    id                            bigint    not null
        constraint tab_company_pkey
            primary key,
    activity_level                integer,
    age                           integer   not null,
    client                        boolean   not null,
    company_closing_propensity    text,
    company_size                  integer,
    company_state                 integer   not null,
    covid19_individual            varchar(255),
    covid19_segment               varchar(255),
    created                       timestamp not null,
    credit_requested              double precision,
    date_registration_situation   varchar(255),
    date_special_situation        varchar(255),
    delivery_propensity           varchar(255),
    e_commerce_propensity         varchar(255),
    employee_analyst_count        integer,
    employee_base_count           integer,
    employee_buyer_count          integer,
    employee_doctor_count         integer,
    employee_engineer_count       integer,
    employee_layer_count          integer,
    employee_manager_count        integer,
    employee_other_count          integer,
    employee_pdv_count            integer,
    employee_seller_count         integer,
    employee_supervisor_count     integer,
    employee_teacher_count        integer,
    estimated_billing             varchar(255),
    estimated_billing_group       varchar(255),
    fantasy_name                  varchar(255),
    franchise_name                varchar(255),
    gross_billing                 double precision,
    group_multinational           boolean,
    has_accountant_contact        boolean,
    has_divergent_qsa             boolean,
    has_franchise_indicative      boolean,
    indicative_proposal           boolean   not null,
    invoicing_informed            double precision,
    latest_registration_form      boolean,
    legal_nature_code             varchar(255),
    legal_nature_description      text,
    matrix                        boolean   not null,
    modified                      timestamp not null,
    multinational                 boolean   not null,
    origin_country                varchar(255),
    quantity_active_branches      integer,
    quantity_employee             integer,
    quantity_ex_employee          integer,
    registered                    boolean   not null,
    registration_situation_reason text,
    remote_working_capacity       varchar(255),
    rf_email                      varchar(255),
    social_capital                double precision,
    special_situation             varchar(255),
    antt                          bigint
        constraint fk4ysp8luyi8n3ty30435x6alnj
            references tab_antt,
    cafir                         bigint
        constraint fk1ry3k6b0ttxeq6irqjwadpdfi
            references tab_cafir,
    debit_mte                     bigint
        constraint fkgfycy8pl315ymwfodqd3ygqis
            references tab_debit_mte,
    debit_pgfn_dau                bigint
        constraint fkjvndbvwv5opqwsx7b4pc0nfg4
            references tab_debit_pgfn_dau,
    financial_activity            bigint
        constraint fksihcwc4cjtr8l3w4a3qs30ism
            references tab_financial_activity,
    foreign_commerce              bigint
        constraint fkkq6d2ud5wg605wi7hk8okicos
            references tab_foreign_commerce,
    heavy_vehicle_info            bigint
        constraint fkmjfp9ntshoioxwddbutn1ce8j
            references tab_heavy_vehicle_info,
    ibama_cnd                     bigint
        constraint fkoaomo9dno53pf8irrrs78998d
            references tab_ibama_cnd,
    judicial_process              bigint
        constraint fkipv1cxpym5ix26yfnk3el9dg4
            references tab_judicial_process,
    learning_offer1               bigint
        constraint fk4chbvvfamefdfhv85tu5t9cya
            references tab_learning_offer,
    learning_offer2               bigint
        constraint fkksbg1d7ikbgyuamw0rgl4b98l
            references tab_learning_offer,
    learning_offer3               bigint
        constraint fkat6qd5v6tysm2uplllqp1cbg0
            references tab_learning_offer,
    learning_offer4               bigint
        constraint fkmpylhxi0hlkbmo6awhit76awp
            references tab_learning_offer,
    main_cnae                     bigint
        constraint fkr8y5puaxv909lk8ajxvyv4ap6
            references tab_cnae,
    main_info                     bigint    not null,
    management_contract           bigint
        constraint fkf8dj3k3nbkuss1ffdef6jimix
            references tab_management_contract,
    matrix_info                   bigint,
    open_capital                  bigint
        constraint fkq1xx14gsvrisx378jlvcnf20y
            references tab_open_capital,
    pat                           bigint
        constraint fkpffx5b4d79npar6uxoep32enn
            references tab_pat,
    procon                        bigint
        constraint fkp5nnm058qlvmhrhmi3036oxiu
            references tab_procon,
    tax_health                    bigint
        constraint fk54ibejug1toesfd7191sxq9ia
            references tab_tax_health
);

alter table tab_company owner to postgres;

create table tab_aircraft
(
    id              bigint not null
        constraint tab_aircraft_pkey
            primary key,
    maker           varchar(255),
    model           varchar(255),
    operator_name   varchar(255),
    owner_name      varchar(255),
    production_year integer,
    registration    varchar(255),
    situation       varchar(255),
    status_rab      varchar(255),
    company_id      bigint
        constraint fkesg5x77m66jsrgyw6sm7acmyl
            references tab_company
);

alter table tab_aircraft owner to postgres;

create table tab_ceis
(
    id                    bigint not null
        constraint tab_ceis_pkey
            primary key,
    end_sanction_date     timestamp,
    information_date      timestamp,
    information_entity    varchar(255),
    init_sanction_date    timestamp,
    legal_substantiation  varchar(255),
    organ_complement      varchar(255),
    process_number        varchar(255),
    sanction              varchar(255),
    sanctioning_entity    varchar(255),
    uf_sanctioning_entity varchar(255),
    company_id            bigint
        constraint fke8j8xgu3e1fcclyhx8pupxogd
            references tab_company
);

alter table tab_ceis owner to postgres;

create table tab_cepim
(
    id                bigint not null
        constraint tab_cepim_pkey
            primary key,
    contract          varchar(255),
    end_contract_date timestamp,
    grantor_entity    varchar(255),
    impediment        varchar(255),
    value_released    double precision,
    company_id        bigint
        constraint fkb7n6bwmbdk06w39j5j5nkmpud
            references tab_company
);

alter table tab_cepim owner to postgres;

alter table tab_cnae
    add constraint fknhh5mak81f9jgoxpbwcefw08l
        foreign key (company_id) references tab_company;

create table tab_cnep
(
    id                    bigint not null
        constraint tab_cnep_pkey
            primary key,
    end_sanction_date     timestamp,
    init_sanction_date    timestamp,
    penalty_value         double precision,
    process_number        varchar(255),
    sanction              varchar(255),
    sanctioning_entity    varchar(255),
    uf_sanctioning_entity varchar(255),
    company_id            bigint
        constraint fkmbb6lk60q9d1tkh7s6apikvtn
            references tab_company
);

alter table tab_cnep owner to postgres;

create table tab_cnjcnia
(
    id                 bigint           not null
        constraint tab_cnjcnia_pkey
            primary key,
    description_entity varchar(255),
    process_number     varchar(255),
    registration_date  timestamp,
    sphere             varchar(255),
    uf                 varchar(255),
    value              double precision not null,
    company_id         bigint
        constraint fk7rvypv8hgtmpv4pqemo5fsmlv
            references tab_company
);

alter table tab_cnjcnia owner to postgres;

create table tab_cnjcnia_related_issues
(
    tab_cnjcnia_id bigint not null
        constraint fka6pwk1ltrqa3kcqix31nroi7r
            references tab_cnjcnia,
    related_issues varchar(255)
);

alter table tab_cnjcnia_related_issues owner to postgres;

create table tab_comment
(
    id               bigint    not null
        constraint tab_comment_pkey
            primary key,
    comment          text      not null,
    created          timestamp not null,
    learning_number  integer   not null,
    learning_session integer   not null,
    modified         timestamp not null,
    company_id       bigint    not null
        constraint fk3ctj0l1hh2knpwnhn1ysew62q
            references tab_company
);

alter table tab_comment owner to postgres;

create table tab_company_beneficiaries
(
    id                bigint           not null
        constraint tab_company_beneficiaries_pkey
            primary key,
    grade             integer          not null,
    grade_qsa         integer,
    participation     double precision not null,
    participation_qsa double precision,
    company_id        bigint           not null
        constraint fkmfoi8n2attkanc4maotl1db70
            references tab_company,
    person_id         bigint           not null
        constraint fk8jsyj0rbf3q9snobl8jrtw9ym
            references tab_person
);

alter table tab_company_beneficiaries owner to postgres;

create table tab_company_companies_related
(
    company_id         bigint not null
        constraint fk5xtn0iqeospt70xrc24nqa91y
            references tab_company,
    company_related_id bigint not null
        constraint fk25tosyonlic1ooyxhx3mor8l9
            references tab_company
);

alter table tab_company_companies_related owner to postgres;

create table tab_company_employee
(
    id                     bigint not null
        constraint tab_company_employee_pkey
            primary key,
    admission_date         timestamp,
    resignation_date       timestamp,
    company_employee_id    bigint
        constraint fkc51qt9gygvgc4v8eigwbw8cme
            references tab_company,
    company_ex_employee_id bigint
        constraint fkpiih7aio8hnx1b667xm6y4pgg
            references tab_company,
    person_id              bigint not null
        constraint fkkaa6gclwqumwobm7khoumdb50
            references tab_person
);

alter table tab_company_employee owner to postgres;

create table tab_company_export
(
    id                bigint       not null
        constraint tab_company_export_pkey
            primary key,
    value             varchar(255) not null,
    year              integer      not null,
    company_export_id bigint
        constraint fkdqdndansc3396faaju8jbqdpn
            references tab_company,
    company_import_id bigint
        constraint fk4q1741wfwdxf8t7652oijnkxl
            references tab_company
);

alter table tab_company_export owner to postgres;

create table tab_company_learning_sessions
(
    tab_company_id    bigint not null
        constraint fk1fs9op05qo6grd5ng7vp6xq3n
            references tab_company,
    learning_sessions integer
);

alter table tab_company_learning_sessions owner to postgres;

create table tab_company_main_info
(
    id                     bigint      not null
        constraint tab_company_main_info_pkey
            primary key,
    cnpj                   varchar(18) not null,
    opening_date           varchar(255),
    registration_situation integer,
    fantasy_name           text        not null,
    address_id             bigint
        constraint fkk9tfn0pf0iudy2a6num8q6ttg
            references tab_address,
    company_id             bigint
        constraint fk9idfhrmu7e1sfbnpibxwulp5t
            references tab_company
);

alter table tab_company_main_info owner to postgres;

alter table tab_company
    add constraint fkgflme0at3ruaa2sa6fl4pj551
        foreign key (main_info) references tab_company_main_info;

alter table tab_company
    add constraint fkjirss18k3u7n4q2mmt5lbvdhd
        foreign key (matrix_info) references tab_company_main_info;

create table tab_company_partners
(
    id                              bigint           not null
        constraint tab_company_partners_pkey
            primary key,
    entry_date                      timestamp,
    level_preparation               varchar(255),
    level_preparation_rf            varchar(255),
    participation                   double precision not null,
    participation_rf                double precision,
    participation_social_capital    double precision,
    participation_social_capital_rf double precision,
    qualification                   varchar(255),
    qualification_rf                varchar(255),
    company_id                      bigint           not null
        constraint fk3vdlfu7scxx1kw604wu8fa6im
            references tab_company,
    company_partner_id              bigint
        constraint fk2uk6qmaqlaynggx8tein677n0
            references tab_company,
    person_id                       bigint
        constraint fkbepjnkr12wt1y6gblrvo4v23l
            references tab_person
);

alter table tab_company_partners owner to postgres;

create table tab_company_phones
(
    tab_company_id bigint not null
        constraint fku4uhs7w43plqj9mx94p28ks2
            references tab_company,
    phones         varchar(255)
);

alter table tab_company_phones owner to postgres;

create table tab_crsfn
(
    id              bigint not null
        constraint tab_crsfn_pkey
            primary key,
    agreed_number   varchar(255),
    part            varchar(255),
    process_number  varchar(255),
    resource_number varchar(255),
    resource_type   varchar(255),
    company_id      bigint
        constraint fkqlorxnjvjucdywp60yyekjd8v
            references tab_company
);

alter table tab_crsfn owner to postgres;

create table tab_directory
(
    id                     bigint       not null
        constraint tab_directory_pkey
            primary key,
    created                timestamp    not null,
    ext                    varchar(255) not null,
    modified               timestamp    not null,
    name                   varchar(255) not null,
    type                   integer      not null,
    url                    varchar(255) not null,
    company_debits_id      bigint
        constraint fkfbh4ypy6quqk79gxj3qdp873s
            references tab_company,
    company_jucesp_id      bigint
        constraint fkag3thtkqg4mkj2u9asy64xqx4
            references tab_company,
    person_irpf_id         bigint
        constraint fkcqx47b61uaoi5lky4gl6rgux0
            references tab_person,
    person_irpf_receipt_id bigint
        constraint fkletwivooj0fm6ug4a9sdr7il9
            references tab_person,
    comment_id             bigint
        constraint fkj39p4rj4l8sackw2rgvwqrnbn
            references tab_comment
);

alter table tab_directory owner to postgres;

create table tab_domain
(
    id                bigint not null
        constraint tab_domain_pkey
            primary key,
    created_date      timestamp,
    expiration_date   timestamp,
    modification_date timestamp,
    name              varchar(255),
    responsible       varchar(255),
    company_id        bigint
        constraint fk24rxo77s5g9e2183ernl75p25
            references tab_company
);

alter table tab_domain owner to postgres;

create table tab_employee_growth
(
    id              bigint           not null
        constraint tab_employee_growth_pkey
            primary key,
    employee_growth integer          not null,
    growth          double precision not null,
    year            integer          not null,
    company_id      bigint           not null
        constraint fkivtanr2mpsn1vxgudbajkpxgu
            references tab_company
);

alter table tab_employee_growth owner to postgres;

create table tab_environmental_license
(
    id                   bigint not null
        constraint tab_environmental_license_pkey
            primary key,
    description_typology text,
    emit_data            timestamp,
    municipality         varchar(255),
    process_number       varchar(255),
    situation            varchar(255),
    type                 varchar(255),
    typology_number      varchar(255),
    uf                   varchar(255),
    update_data          timestamp,
    company_id           bigint
        constraint fk608ism47ck04k1tp0a6a0kfcb
            references tab_company
);

alter table tab_environmental_license owner to postgres;

create table tab_financial_indicator
(
    id         bigint           not null
        constraint tab_financial_indicator_pkey
            primary key,
    increase   double precision not null,
    margin     double precision not null,
    company_id bigint
        constraint fkokpdb5354ub8j0789y5vf0ert
            references tab_company
);

alter table tab_financial_indicator owner to postgres;

create table tab_health_establishment
(
    id                     bigint not null
        constraint tab_health_establishment_pkey
            primary key,
    last_update            timestamp,
    quantity_beds          integer,
    quantity_professionals integer,
    unit_type              varchar(255),
    company_id             bigint
        constraint fk3yksnkipj86k95tonr7h74axp
            references tab_company
);

alter table tab_health_establishment owner to postgres;

create table tab_inpi_brand
(
    id             bigint not null
        constraint tab_inpi_brand_pkey
            primary key,
    brand          varchar(255),
    class_brand    varchar(255),
    deposit_date   timestamp,
    process_number varchar(255),
    situation      varchar(255),
    company_id     bigint
        constraint fktmyg3qagy95536d6mvxkldvab
            references tab_company
);

alter table tab_inpi_brand owner to postgres;

create table tab_inpi_patent
(
    id               bigint not null
        constraint tab_inpi_patent_pkey
            primary key,
    concession_date  timestamp,
    deposit_date     timestamp,
    depositor        varchar(255),
    process_number   varchar(255),
    procurator       varchar(255),
    publication_date timestamp,
    title            varchar(255),
    company_id       bigint
        constraint fklx12p2kbg55nfknh33f0uxs2c
            references tab_company
);

alter table tab_inpi_patent owner to postgres;

create table tab_inpi_patent_inventors
(
    tab_inpi_patent_id bigint not null
        constraint fkhsu10g8t6931k0xl5hjbsjcf9
            references tab_inpi_patent,
    inventor           varchar(255)
);

alter table tab_inpi_patent_inventors owner to postgres;

create table tab_inpi_software
(
    id             bigint not null
        constraint tab_inpi_software_pkey
            primary key,
    deposit_date   timestamp,
    process_number varchar(255),
    procurator     varchar(255),
    title          varchar(255),
    company_id     bigint
        constraint fk6htp97igwubof6vosdwvdmxy3
            references tab_company
);

alter table tab_inpi_software owner to postgres;

create table tab_inpi_software_authors
(
    tab_inpi_software_id bigint not null
        constraint fk6u8scf6i78536pavayd53xv7t
            references tab_inpi_software,
    author               varchar(255)
);

alter table tab_inpi_software_authors owner to postgres;

create table tab_international_list
(
    id         bigint not null
        constraint tab_international_list_pkey
            primary key,
    name       varchar(255),
    query_date timestamp,
    company_id bigint
        constraint fksb30n2398h5dqrjyko7rviol9
            references tab_company
);

alter table tab_international_list owner to postgres;

create table tab_property
(
    id               bigint not null
        constraint tab_property_pkey
            primary key,
    building_data    timestamp,
    built_area       double precision,
    evaluation_value double precision,
    ground_area      double precision,
    registry_number  varchar(255),
    address          bigint
        constraint fk7odyt6e8i1fagnavjrue16sta
            references tab_address,
    person_id        bigint
        constraint fkm4wqr1fvhhn5lcx1sileo7yh7
            references tab_person,
    company_id       bigint
        constraint fkjgfqe8ra2tmkxk01jarke8tmq
            references tab_company
);

alter table tab_property owner to postgres;

create table tab_property_guarantee
(
    id               bigint           not null
        constraint tab_property_guarantee_pkey
            primary key,
    built_area       double precision,
    evaluation_value double precision not null,
    ground_area      double precision,
    registry_number  varchar(255),
    type             integer          not null,
    address          bigint
        constraint fkhr6wlbjowrv7fsqpubwsw5h2u
            references tab_address,
    company_id       bigint
        constraint fkbqxlj5p3dfd3rfadxmunbf3w0
            references tab_company
);

alter table tab_property_guarantee owner to postgres;

create table tab_user
(
    id                bigint       not null
        constraint tab_user_pkey
            primary key,
    confirm_token     varchar(255),
    created           timestamp    not null,
    email             varchar(255) not null,
    enabled           boolean      not null,
    has_rating        boolean      not null,
    is_token_expired  boolean      not null,
    linkeding_contact varchar(255),
    modified          timestamp    not null,
    name              varchar(255) not null,
    password          varchar(255) not null,
    phone             varchar(255),
    role_id           bigint       not null
        constraint fkk761fdltulfg6j16d7p11hhwc
            references tab_role
);

alter table tab_user owner to postgres;

create table tab_user_cnpj
(
    user_id bigint not null
        constraint fkdk8cqkeyo5xaihs3qeflkru20
            references tab_user,
    cnpj    varchar(255)
);

alter table tab_user_cnpj owner to postgres;

create table tab_user_temp
(
    id                bigint       not null
        constraint tab_user_temp_pkey
            primary key,
    code_confirmation varchar(6)   not null,
    created           timestamp    not null,
    email             varchar(255) not null,
    linkeding_contact varchar(255),
    modified          timestamp    not null,
    name              varchar(255) not null,
    partner           boolean      not null,
    password          varchar(255) not null,
    phone             varchar(255)
);

alter table tab_user_temp owner to postgres;

create table tab_work_mte
(
    id                       bigint not null
        constraint tab_work_mte_pkey
            primary key,
    fiscal_action_year       integer,
    provenance_decision_date timestamp,
    quantity_workers         integer,
    address                  bigint
        constraint fk58iecequfxqxat1i5qyb9y7i0
            references tab_address,
    company_id               bigint
        constraint fk1894861iqewhecl06j0avi76c
            references tab_company
);

alter table tab_work_mte owner to postgres;

create table tab_indirect_tax
(
    id     bigint           not null
        constraint tb_indirect_tax_pkey
            primary key,
    cofins double precision not null,
    ipi    double precision not null,
    pis    double precision not null
);

alter table tab_indirect_tax owner to postgres;

create table tab_invoice
(
    id                  bigint           not null
        constraint tab_invoice_pkey
            primary key,
    issuance_date       timestamp,
    number              varchar(255),
    taxed_amount        double precision,
    total               double precision not null,
    type                integer          not null,
    company_invoice_id  bigint           not null
        constraint fks9gfect24bdqt2nvy0auqx9x8
            references tab_company,
    company_received_id bigint           not null
        constraint fkofpdwm9ssdol8p540j17wgnmb
            references tab_company,
    indirect_tax_id     bigint           not null
        constraint fk8rv400w7gdqfqdc22b5wcklrt
            references tab_indirect_tax,
    recipient_id        bigint           not null
        constraint fkjbaibsklx8iiiayct6066jc8d
            references tab_sender_recipient,
    sender_id           bigint           not null
        constraint fkrp3bpiq4vjmb7scqqwu4flap6
            references tab_sender_recipient
);

alter table tab_invoice owner to postgres;

create table tab_product
(
    id          bigint           not null
        constraint tab_product_pkey
            primary key,
    code        varchar(255),
    count       double precision not null,
    description varchar(255),
    ncm         varchar(255),
    price       double precision not null,
    invoice_id  bigint
        constraint fko8c7omxvfoj42kg5ic04vlv5h
            references tab_invoice
);

alter table tab_product owner to postgres;