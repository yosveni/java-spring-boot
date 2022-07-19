create sequence if not exists seq_company_user start 1 increment 1;

create table if not exists tab_company_user
(
    id                        int8      not null,
    avg_receipt_term_invoices int4,
    created                   timestamp not null,
    credit_requested          float8,
    init_learning_offer1      boolean,
    init_learning_offer2      boolean,
    init_learning_offer3      boolean,
    init_learning_offer4      boolean,
    invoicing_informed        float8,
    modified                  timestamp not null,
    owner                     boolean   not null,
    company_id                int8      not null,
    learning_analysis         int8,
    learning_offer1           int8,
    learning_offer2           int8,
    learning_offer3           int8,
    learning_offer4           int8,
    sped_form                 int8,
    user_id                   int8      not null,
    primary key (id)
);

create or replace function update_tab_company_user() returns void as
$$
declare
    rec1 record;
    rec2 record;
begin
    for rec1 in
        select id,
               avg_receipt_term_invoices,
               created,
               credit_requested,
               init_learning_offer1,
               init_learning_offer2,
               init_learning_offer3,
               init_learning_offer4,
               invoicing_informed,
               modified,
               learning_analysis,
               learning_offer1,
               learning_offer2,
               learning_offer3,
               learning_offer4,
               sped_form,
               user_id
        from tab_company c
        where c.user_id is not null
        loop
            insert
            into tab_company_user (id, avg_receipt_term_invoices, created, credit_requested,
                                   init_learning_offer1, init_learning_offer2, init_learning_offer3,
                                   init_learning_offer4, invoicing_informed, modified, owner, company_id,
                                   learning_analysis, learning_offer1, learning_offer2, learning_offer3,
                                   learning_offer4, sped_form, user_id)
            values (rec1.id, rec1.avg_receipt_term_invoices, rec1.created, rec1.credit_requested,
                    rec1.init_learning_offer1,
                    rec1.init_learning_offer2, rec1.init_learning_offer3, rec1.init_learning_offer4,
                    rec1.invoicing_informed, rec1.modified, true, rec1.id, rec1.learning_analysis,
                    rec1.learning_offer1, rec1.learning_offer2, rec1.learning_offer3, rec1.learning_offer4,
                    rec1.sped_form, rec1.user_id);
        end loop;

    for rec2 in
        select id,
               avg_receipt_term_invoices,
               created,
               credit_requested,
               init_learning_offer1,
               init_learning_offer2,
               init_learning_offer3,
               init_learning_offer4,
               invoicing_informed,
               modified,
               learning_analysis,
               learning_offer1,
               learning_offer2,
               learning_offer3,
               learning_offer4,
               sped_form,
               c2.user_id as user_id
        from tab_company c
                 join tab_user_observed_companies c2 on c.id = c2.company_id
        where c.user_id is null
        loop
            insert
            into tab_company_user (id, avg_receipt_term_invoices, created, credit_requested,
                                   init_learning_offer1, init_learning_offer2, init_learning_offer3,
                                   init_learning_offer4, invoicing_informed, modified, owner, company_id,
                                   learning_analysis, learning_offer1, learning_offer2, learning_offer3,
                                   learning_offer4, sped_form, user_id)
            values (rec2.id, rec2.avg_receipt_term_invoices, rec2.created, rec2.credit_requested,
                    rec2.init_learning_offer1,
                    rec2.init_learning_offer2, rec2.init_learning_offer3, rec2.init_learning_offer4,
                    rec2.invoicing_informed, rec2.modified, false, rec2.id, rec2.learning_analysis,
                    rec2.learning_offer1, rec2.learning_offer2, rec2.learning_offer3, rec2.learning_offer4,
                    rec2.sped_form, rec2.user_id);
        end loop;
end;
$$
    language 'plpgsql' volatile;

select update_tab_company_user();
drop function update_tab_company_user();

alter table if exists tab_company_learning_sessions
    rename to tab_company_user_learning_sessions;
alter table if exists tab_company_user_learning_sessions
    rename column tab_company_id to tab_company_user_id;
alter table if exists tab_comment
    rename column company_id to company_user_id;
alter table if exists tab_company_bank_document
    rename column company_id to company_user_id;
alter table if exists tab_property_guarantee
    rename column company_id to company_user_id;
alter table if exists tab_sped
    rename column company_id to company_user_id;
alter table if exists tab_sped_document
    rename column company_id to company_user_id;

alter table if exists tab_comment
    add constraint FKl57apnipgr8up49ix1us6ry3 foreign key (company_user_id) references tab_company_user;
alter table if exists tab_company_bank_document
    add constraint FKp4bil09pdmcpg7sxnd6tdtqy8 foreign key (bank_nomenclature_id) references tab_bank_nomenclature;
alter table if exists tab_company_bank_document
    add constraint FKpqxggiplxy7n4bbr73hu5tebw foreign key (company_user_id) references tab_company_user;
alter table if exists tab_company_user
    add constraint FKlj7nbvwcpk9h082mfyt2ilchl foreign key (company_id) references tab_company;
alter table if exists tab_company_user
    add constraint FK830dl8ud24ny9mty9f1xo75hl foreign key (learning_analysis) references tab_learning_analysis;
alter table if exists tab_company_user
    add constraint FKqq51ed7v2wwxxi5c4t7qwy44n foreign key (learning_offer1) references tab_offer;
alter table if exists tab_company_user
    add constraint FK9bpujj4kguk3gxtf4k5wp6gqi foreign key (learning_offer2) references tab_offer;
alter table if exists tab_company_user
    add constraint FKrjcickbofh6k62fv8j87y2rh6 foreign key (learning_offer3) references tab_offer;
alter table if exists tab_company_user
    add constraint FKdjmj7r64tdx1745v5tpo4ia8i foreign key (learning_offer4) references tab_offer;
alter table if exists tab_company_user
    add constraint FKs405s8qcmj7qoxsjviuxhpkeu foreign key (sped_form) references tab_directory;
alter table if exists tab_company_user
    add constraint FK638o00grl4kso99o7oaaiofoo foreign key (user_id) references tab_user;
alter table if exists tab_company_user_learning_sessions
    add constraint FKeewjwjotxyuhowwrd8srcwlyt foreign key (tab_company_user_id) references tab_company_user;
alter table if exists tab_directory
    add constraint FK7u8bemijle2s812t53elyfeuu foreign key (company_debits_id) references tab_company_user;
alter table if exists tab_invoice
    add constraint FK7tvljs0k0ua6bqd54thi1ay5b foreign key (company_invoice_id) references tab_company_user;
alter table if exists tab_invoice
    add constraint FK5wsk7q4oaa00ppmud6tg62v4b foreign key (company_received_id) references tab_company_user;
alter table if exists tab_property_guarantee
    add constraint FK2tvdqrgreuv8vmr5v8inow23a foreign key (company_user_id) references tab_company_user;
alter table if exists tab_sintegra_inscription
    add constraint FK2eltu9fnk5y72vjs7vruvrpaf foreign key (company_id) references tab_company;
alter table if exists tab_sped
    add constraint FK929j8w0p0ck8jr29ksmgdxeiu foreign key (company_user_id) references tab_company_user;
alter table if exists tab_sped_document
    add constraint FKoh6p3fnyiy2iy0jqse3ebfiav foreign key (company_user_id) references tab_company_user;

alter table if exists tab_comment
    drop constraint fk3ctj0l1hh2knpwnhn1ysew62q;
alter table if exists tab_directory
    drop constraint fkfbh4ypy6quqk79gxj3qdp873s;
alter table if exists tab_invoice
    drop constraint fkofpdwm9ssdol8p540j17wgnmb;
alter table if exists tab_invoice
    drop constraint fks9gfect24bdqt2nvy0auqx9x8;
alter table if exists tab_property_guarantee
    drop constraint fkbqxlj5p3dfd3rfadxmunbf3w0;
alter table if exists tab_sped
    drop constraint fk1ecyc3rjhfcpgx3yj6vsq2viw;
alter table if exists tab_sped_document
    drop constraint fkmedj17qcwt52fvw0s35avi6qu;
alter table if exists tab_company_user_learning_sessions
    drop constraint fk1fs9op05qo6grd5ng7vp6xq3n;
alter table if exists tab_company
    drop constraint fk4chbvvfamefdfhv85tu5t9cya;
alter table if exists tab_company
    drop constraint fkat6qd5v6tysm2uplllqp1cbg0;
alter table if exists tab_company
    drop constraint fkbfgkk9jyner44786bbe4011u8;
alter table if exists tab_company
    drop constraint fkjuy6c8ojhfcplo9yj6nuq2ccb;
alter table if exists tab_company
    drop constraint fkksbg1d7ikbgyuamw0rgl4b98l;
alter table if exists tab_company
    drop constraint fkmpylhxi0hlkbmo6awhit76awp;
alter table if exists tab_company
    drop constraint fkytgt49e65p0rnloc2yfeki7y;

alter table if exists tab_company
    drop column date_registered;
alter table if exists tab_company
    drop column avg_receipt_term_invoices;
alter table if exists tab_company
    drop column credit_requested;
alter table if exists tab_company
    drop column invoicing_informed;
alter table if exists tab_company
    drop column init_learning_offer1;
alter table if exists tab_company
    drop column init_learning_offer2;
alter table if exists tab_company
    drop column init_learning_offer3;
alter table if exists tab_company
    drop column init_learning_offer4;
alter table if exists tab_company
    drop column learning_offer1;
alter table if exists tab_company
    drop column learning_offer2;
alter table if exists tab_company
    drop column learning_offer3;
alter table if exists tab_company
    drop column learning_offer4;
alter table if exists tab_company
    drop column sped_form;
alter table if exists tab_company
    drop column user_id;

drop table if exists tab_user_cnpj;
drop table if exists tab_user_observed_companies;