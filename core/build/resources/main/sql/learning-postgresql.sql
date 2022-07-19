SET
    search_path TO linkapital;

-- LEARNING 1------------------------------------------------------------------------------------------------
-- Informações gerais da empresa
select tcmi.cnpj                                         as "CNPJ",
       tc.fantasy_name                                   as "NOME FANTASIA",
       tcmi.fantasy_name                                 as "RAZÃO SOCIAL",
       tcmi.opening_date                                 as "DATA DE ABERTURA",
       tc.age                                            as "IDADE",
       tc.rf_email                                       as "E-MAIL",
       linkapital.phones(tc.id)                          as "TELEFONES",
       linkapital.enum_activity_level(tc.activity_level) as "NIVEL DE ATIVIDADE",
       tc.has_divergent_qsa                              as "POSSUI QSA DIVERGENTE",
       linkapital.enum_company_size(tc.company_size)     as "PORTE",
       tc.legal_nature_code                              as "COD NATUREZA JURÍDICA",
       tc.legal_nature_description                       as "DESCRIÇÃO NATUREZA JURÍDICA",
       tc.date_registration_situation                    as "DATA SITUAÇÃO CADASTRAL",
       tc.registration_situation_reason                  as "RAZÃO SITUAÇÃO CADASTRAL",
       tc.company_closing_propensity                     as "PROPENSÃO FECHAMENTO EMPRESA",
       tc.special_situation                              as "SITUAÇÃO ESPECIAL",
       tc.date_special_situation                         as "DATA SITUAÇÃO ESPECIAL",
       tc.delivery_propensity                            as "PROPENSÃO FAZER DELIVERY",
       tc.e_commerce_propensity                          as "ROPENSÃO FAZER E-COMMERCE",
       ta.address1                                       as "LOGRADOURO",
       ta.address2                                       as "COMPLEMENTO",
       ta.municipality                                   as "MUNICÍPIO",
       ta.neighborhood                                   as "BARRIO",
       ta.number                                         as "Número",
       ta.original_neighborhood                          as "BAIRRO ORIGINAL",
       ta.precision                                      as "PRECISÃO",
       ta.region                                         as "REGIÃO",
       ta.uf                                             as "UF",
       ta.zip                                            as "CEP",
       ta.residential_address                            as "ENDEREÇO RESIDENCIAL",
       linkapital.phones_rf(ta.id)                       as "TELEFONES RF",
       ta.micro_region                                   as "MICRORREGIÃO",
       ta.m_region                                       as "MESORREGIÁO",
       ta.border_municipality                            as "MUNICÍPIO FRONTEIRICO",
       ta.building_type                                  as "TIPO DE EDIFÍCIO",
       ta.collective_building                            as "EDIFÍCIO COLETIVO",
       ta.delivery_restriction                           as "ENDEREÇO RESTRIÇÃO ENTREGA",
       tcn.code                                          as "CNAE PRINCIPAL",
       tcn.description                                   as "DESCRICÃO CNAE PRINCIPAL",
       tcn.business_activity                             as "RAMO DE ATIVIDADE",
       linkapital.enum_company_sector(tcn.sector)        as "SETOR",
       tp.cpf                                            as "CPF PAT RESPONSAVEL",
       tp.name                                           as "NOME PAT RESPONSAVEL",
       tp.email                                          as "E-MAIL PAT RESPONSAVEL",
       tu.cpf                                            as "CPF DO CLIENTE",
       tu.name                                           as "NOME DO CLIENTE",
       tu.email                                          as "E-MAIL DO CLIENTE",
       tu.phone                                          as "TELEFONE DO CLIENTE"
from linkapital.tab_company tc
         inner join linkapital.tab_company_main_info tcmi on tc.main_info = tcmi.id
         left join linkapital.tab_pat tpt on tc.pat = tpt.id
         left join linkapital.tab_person tp on tpt.person_id = tp.id
         inner join linkapital.tab_address ta on tcmi.address_id = ta.id
         inner join linkapital.tab_cnae tcn on tc.main_cnae = tcn.id
         left join linkapital.tab_user tu on tc.user_property = tu.email
where tc.client;
-- select getPhones(1);
-- drop function getNumbers(bigint);

-- LEARNING 2-------------------------------------------------------------------------------------------------
-- Informações gerais sobre a socios
select tcmi.cnpj                                        as "CNPJ",
       tcmi.fantasy_name                                as "RAZÃO SOCIAL",
       tp.name                                          as "NOME",
       tp.cpf                                           as "CPF",
       tcp.level_preparation                            as "NÍVEL DE PREPARAÇÃO",
       tcp.level_preparation_rf                         as "NÍVEL DE PREPARAÇÃO RF",
       concat(tcp.participation, '%')                   as "PARTICIPAÇÃO",
       concat(tcp.participation_rf, '%')                as "PARTICIPAÇÃO RF",
       concat(tcp.participation_social_capital, '%')    as "NÍVEL DE PREPARAÇÃO SOCIAL",
       concat(tcp.participation_social_capital_rf, '%') as "NÍVEL DE PREPARAÇÃO SOCIAL rf",
       tcp.qualification                                as "QUALIFICAÇÃO",
       tcp.qualification_rf                             as "QUALIFICAÇÃO RF"
from linkapital.tab_company tc
         inner join linkapital.tab_company_main_info tcmi on tc.main_info = tcmi.id
         inner join linkapital.tab_company_partners tcp on tc.id = tcp.company_id
         inner join linkapital.tab_person tp on tcp.person_id = tp.id
where tc.client;

-- Informações gerais sobre a beneficiarios
select tcmi.cnpj                                                           as "CNPJ",
       tcmi.fantasy_name                                                   as "RAZÃO SOCIAL",
       tp.name                                                             as "NOME",
       tp.cpf                                                              as "CPF",
       tcb.grade                                                           as "GRADO",
       tcb.grade_qsa                                                       as "GRADO RF",
       concat(tcb.participation, '%')                                      as "PARTICIPAÇÃO",
       concat(tcb.participation_qsa, '%')                                  as "PARTICIPAÇÃO RF",
       linkapital.enum_registration_situation(tcmi.registration_situation) as "SITUAÇÃO"
from linkapital.tab_company tc
         inner join linkapital.tab_company_main_info tcmi on tc.main_info = tcmi.id
         inner join linkapital.tab_company_beneficiaries tcb on tc.id = tcb.company_id
         inner join linkapital.tab_person tp on tcb.person_id = tp.id
where tc.client;

-- Learning offert 1
select tcmi.cnpj                  as "CNPJ",
       tcmi.fantasy_name          as "RAZÃO SOCIAL",
       concat('R$ ', tlo1.volume) as "VOLUME",
       tlo1.tax                   as "TAX",
       tlo1.dead_line             as "PRAZO EM MESES",
       tlo1.accepted              as "ACEITARAM"
from linkapital.tab_company tc
         inner join linkapital.tab_company_main_info tcmi on tc.main_info = tcmi.id
         left join linkapital.tab_learning_offer tlo1 on tc.learning_offer1 = tlo1.id
where tc.client;

-- Capital social
select tcmi.cnpj                        as "CNPJ",
       tcmi.fantasy_name                as "RAZÃO SOCIAL",
       concat('R$ ', tc.social_capital) as "CAPITAL SOCIAL"
from linkapital.tab_company tc
         inner join linkapital.tab_company_main_info tcmi on tc.main_info = tcmi.id
         inner join linkapital.tab_company_beneficiaries tcb on tc.id = tcb.company_id
where tc.client;

-- LEARNING 3-------------------------------------------------------------------------------------------------
-- Faturamento mensal relatado
select tcmi.cnpj                            as "CNPJ",
       tcmi.fantasy_name                    as "RAZÃO SOCIAL",
       concat('R$ ', tc.gross_billing)      as "FATURAMENTO BRUTO",
       concat('R$ ', tc.invoicing_informed) as "FATURAMENTO INFORMADO",
       concat('R$ ', tc.credit_requested)   as "QUANTIDADE SOLICITADA"
from linkapital.tab_company tc
         inner join linkapital.tab_company_main_info tcmi on tc.main_info = tcmi.id
where tc.client;

-- LEARNING 4-------------------------------------------------------------------------------------------------
-- Evolução de Funcionários
select tcmi.cnpj          as "CNPJ",
       tcmi.fantasy_name  as "RAZÃO SOCIAL",
       tp.name            as "NOME",
       tp.cpf             as "CPF",
       tp.birth_date      as "DATA DE NASCIMENTO",
       tce.admission_date as "DATA ADMISSÃO"
from linkapital.tab_company tc
         inner join linkapital.tab_company_main_info tcmi on tc.main_info = tcmi.id
         inner join linkapital.tab_company_employee tce on tc.id = tce.company_employee_id
         inner join linkapital.tab_person tp on tce.person_id = tp.id
where tc.client;

select *
from linkapital.employed_growth() as (
                                      "CNPJ" varchar,
                                      "RAZÃO SOCIAL" text,
                                      "ANO_10" double precision,
                                      "CRESCIMENTO_10" double precision,
                                      "ANO_9" double precision,
                                      "CRESCIMENTO_9" double precision,
                                      "ANO_8" double precision,
                                      "CRESCIMENTO_8" double precision,
                                      "ANO_7" double precision,
                                      "CRESCIMENTO_7" double precision,
                                      "ANO_6" double precision,
                                      "CRESCIMENTO_6" double precision,
                                      "ANO_5" double precision,
                                      "CRESCIMENTO_5" double precision,
                                      "ANO_4" double precision,
                                      "CRESCIMENTO_4" double precision,
                                      "ANO_3" double precision,
                                      "CRESCIMENTO_3" double precision,
                                      "ANO_2" double precision,
                                      "CRESCIMENTO_2" double precision,
                                      "ANO_1" double precision,
                                      "CRESCIMENTO_1" double precision);

-- LEARNING 5-------------------------------------------------------------------------------------------------
-- Consutas fiscales
select tcmi.cnpj            as "CNPJ",
       tcmi.fantasy_name    as "RAZÃO SOCIAL",
       tth.tax_health       as "SAÚDE FISCAL",
       t.emitter_name       as "NOME DO EMISSOR",
       t.emission_date      as "DATA DA EMISSÃO",
       t.certificate_number as "NUMÉRO CERTIFICAÇÃO",
       t.expiration_date    as "DATA DE VALIDADE",
       t.situation          as "SITUACÃO"
from linkapital.tab_company tc
         inner join linkapital.tab_company_main_info tcmi on tc.main_info = tcmi.id
         left join linkapital.tab_company_employee tce on tc.id = tce.company_employee_id
         left join linkapital.tab_tax_health tth on tc.tax_health = tth.id
         left join linkapital.tab_cnds t on tth.id = t.tax_health_id
where tc.client;

-- Lista de debitos PGFN/DAU
select tcmi.cnpj                        as "CNPJ",
       tcmi.fantasy_name                as "RAZÃO SOCIAL",
       concat('R$ ', tdpd.total_debits) as "VALOR TOTAL (R$)",
       tdp.nature                       as "NATURALEZA",
       tdp.inscription_number           as "NÚMERO DA INSCRIÇÃO",
       concat('R$ ', tdp.debit)         as "VALOR DE DÉBITO (R$)"
from linkapital.tab_company tc
         inner join linkapital.tab_company_main_info tcmi on tc.main_info = tcmi.id
         inner join linkapital.tab_debit_pgfn_dau tdpd on tc.debit_pgfn_dau = tdpd.id
         left join linkapital.tab_debit_pgfn tdp on tdpd.id = tdp.debit_pgfn_dau_id
where tc.client;