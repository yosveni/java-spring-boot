alter table if exists tab_product
    add column if not exists cfop       int8,
    add column if not exists cean       varchar(255),
    add column if not exists cest       varchar(255),
    add column if not exists ucom       varchar(255),
    add column if not exists v_un_com   double precision default 0,
    add column if not exists v_prod     double precision default 0,
    add column if not exists c_ean_trib varchar(255),
    add column if not exists u_trib     varchar(255),
    add column if not exists q_trib     double precision default 0,
    add column if not exists ind_tot    varchar(255);

alter table if exists tab_invoice
    drop column if exists cnpj,
    add column if not exists nat_op     varchar(255),
    add column if not exists dh_emi     timestamp,
    add column if not exists dh_sai_ent timestamp,
    add column if not exists status     int8,
    add column if not exists receipt_id bigint
        constraint fklm478dfynerhvt96u3lo00nju references tab_sender_recipient (id) on update cascade;

alter table if exists tab_invoice
    rename column sender_recipient_id TO sender_id;

alter table if exists tab_sender_recipient
    add column if not exists ie  varchar(255),
    add column if not exists im  varchar(255),
    add column if not exists crt varchar(255);

alter table if exists tab_indirect_tax
    add column if not exists v_bc           double precision default 0,
    add column if not exists v_icms_deson   double precision default 0,
    add column if not exists v_fcpuf_dest   double precision default 0,
    add column if not exists v_icmsuf_dest  double precision default 0,
    add column if not exists v_icmsuf_remet double precision default 0,
    add column if not exists v_fcp          double precision default 0,
    add column if not exists v_bcst         double precision default 0,
    add column if not exists v_st           double precision default 0,
    add column if not exists v_fcpst        double precision default 0,
    add column if not exists v_fcpst_ret    double precision default 0,
    add column if not exists v_prod         double precision default 0,
    add column if not exists v_frete        double precision default 0,
    add column if not exists v_seg          double precision default 0,
    add column if not exists v_desc         double precision default 0,
    add column if not exists v_ii           double precision default 0,
    add column if not exists v_Ipi_devol    double precision default 0,
    add column if not exists v_outro        double precision default 0,
    add column if not exists v_nf           double precision default 0;


