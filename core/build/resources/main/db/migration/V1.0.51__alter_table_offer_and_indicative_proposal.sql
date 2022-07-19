alter table if exists tab_offer rename to tab_indicative_offer;
alter sequence if exists seq_offer rename to seq_indicative_offer;

alter table if exists tab_company_user rename column init_learning_offer1 to init_indictive_offer_one;
alter table if exists tab_company_user rename column init_learning_offer2 to init_indictive_offer_two;
alter table if exists tab_company_user rename column init_learning_offer3 to init_indicative_offer_three;
alter table if exists tab_company_user rename column init_learning_offer4 to init_indicative_offer_four;
alter table if exists tab_company_user rename column learning_offer1 to indicative_offer_one;
alter table if exists tab_company_user rename column learning_offer2 to indicative_offer_two;
alter table if exists tab_company_user rename column learning_offer3 to indicative_offer_three;
alter table if exists tab_company_user rename column learning_offer4 to indicative_offer_four;

alter table if exists tab_indicative_offer rename column offer_type to type;
alter table if exists tab_indicative_offer rename column offer_state to state;

alter table if exists tab_indicative_proposal rename to tab_offer;
alter sequence if exists seq_indicative_proposal rename to seq_offer;

alter table if exists tab_offer rename column pai_by_installment to pay_by_installment;
alter table if exists tab_offer rename column offer_id to indicative_offer_id;
alter table if exists tab_offer rename column proposal_type to type;
alter table if exists tab_offer rename column proposal_state to state;

alter table if exists tab_company drop column indicative_proposal;
alter table if exists tab_company drop column offer_accepted;