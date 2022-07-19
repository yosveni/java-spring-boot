alter table if exists tab_commission
    add column if not exists campaign jsonb,
    drop column if exists campaign_id;

alter table if exists tab_commission_campaign_condition
    alter column campaign_attribute_id set not null;

alter table if exists tab_commission_installment
    add column if not exists installment_type int8 not null default 0;