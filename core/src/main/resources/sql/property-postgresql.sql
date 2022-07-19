SET
    search_path TO linkapital;

-- COMPANY PROPERTIES
select tcmi.cnpj           as "CNPJ",
       tcmi.fantasy_name   as "RAZÃO SOCIAL",
       tp.registry_number  as "REFERENCIA",
       tp.evaluation_value as "VALOR DE AVALIACAO",
       tp.building_data    as "ANO CONSTRUCAO",
       tp.built_area       as "AREA CONSTRUIDA",
       tp.ground_area      as "AREA DO TERRENO",
       ta.zip              as "CEP",
       ta.neighborhood     as "BARRIO",
       ta.address2         as "COMPLEMENTO",
       ta.municipality     as "MUNICIPIO",
       ta.uf               as "UF",
       ta.registry_uf      as "UF DO REGISTRO"
from linkapital.tab_company tc
         inner join linkapital.tab_company_main_info tcmi on tc.main_info = tcmi.id
         inner join linkapital.tab_property tp on tc.id = tp.company_id
         inner join linkapital.tab_address ta on tp.address = ta.id
where tc.client;

-- RURAL PROPERTIES INFO
select tcmi.cnpj                                    as "CNPJ",
       tcmi.fantasy_name                            as "RAZÃO SOCIAL",
       tcf.total_area                               as "AREA TOTAL",
       tcf.quantity_holder                          as "QUANTIDADE IMOVEIS TITULAR",
       tcf.quantity_condominiums                    as "QUANTIDADE IMOVEIS CONDOMINO",
       (select count(*)
        FROM linkapital.tab_property_rural tpr
        where tpr.cafir_property_rural_id = tcf.id) as "QUANTIDADE IMOVEIS RURAIS"
from linkapital.tab_company tc
         inner join linkapital.tab_company_main_info tcmi on tc.main_info = tcmi.id
         inner join linkapital.tab_cafir tcf on tc.cafir = tcf.id
         left join linkapital.tab_property_rural tp on tcf.id = tp.cafir_property_rural_id
where tc.client;

-- RURAL PROPERTIES DETAILS
select tcmi.cnpj         as "CNPJ",
       tcmi.fantasy_name as "RAZÃO SOCIAL",
       tpr.nirf          as "NIRF",
       tpr.name          as "NOME DO IMOVEL",
       tpr.condominium   as "IMOVEL RURAL EM CONDOMINIO",
       tpr.municipality  as "MUNICIPIO",
       tpr.uf            as "UF",
       tpr.type          as "TITULAR/CONDOMINO",
       tpr.area          as "AREA (HA)"
from linkapital.tab_company tc
         inner join linkapital.tab_company_main_info tcmi on tc.main_info = tcmi.id
         inner join linkapital.tab_cafir tcf on tc.cafir = tcf.id
         left join linkapital.tab_property_rural tpr on tcf.id = tpr.cafir_property_rural_id
where tc.client;

-- INPI BRAND
select tcmi.cnpj          as "CNPJ",
       tcmi.fantasy_name  as "RAZÃO SOCIAL",
       tib.process_number as "NUMERO PROCESSO",
       tib.class_brand    as "CLASSE",
       tib.situation      as "SITUAÇAO",
       tib.brand          as "TITULO MARCA",
       tib.deposit_date   as "DATA DEPOSITO"
from linkapital.tab_company tc
         inner join linkapital.tab_company_main_info tcmi on tc.main_info = tcmi.id
         inner join linkapital.tab_inpi_brand tib on tc.id = tib.company_id
where tc.client;

-- INPI PATENT
select tcmi.cnpj                    as "CNPJ",
       tcmi.fantasy_name            as "RAZÃO SOCIAL",
       tip.process_number           as "NUMERO PROCESSO",
       tip.depositor                as "DEPOSITANTE",
       tip.procurator               as "PROCURADOR",
       tip.title                    as "TITULO PATENTE",
       tip.concession_date          as "DATA CONCESSAO",
       tip.deposit_date             as "DATA DEPOSITO",
       tip.publication_date         as "DATA PUBLICAÇAO",
       linkapital.inventors(tip.id) as "INVENTORES"
from linkapital.tab_company tc
         inner join linkapital.tab_company_main_info tcmi on tc.main_info = tcmi.id
         inner join linkapital.tab_inpi_patent tip on tc.id = tip.company_id
where tc.client;

-- INPI SOFTWARE
select tcmi.cnpj                  as "CNPJ",
       tcmi.fantasy_name          as "RAZÃO SOCIAL",
       tis.process_number         as "PROCESSO",
       tis.procurator             as "PROCURADOR",
       tis.title                  as "TITULO SOFTWARE",
       tis.deposit_date           as "DATA DEPOSITO",
       linkapital.authors(tis.id) as "AUTORES"
from linkapital.tab_company tc
         inner join linkapital.tab_company_main_info tcmi on tc.main_info = tcmi.id
         inner join linkapital.tab_inpi_software tis on tc.id = tis.company_id
where tc.client;

-- HEAVY VEHICLE INFO
select tcmi.cnpj                   as "CNPJ",
       tcmi.fantasy_name           as "RAZÃO SOCIAL",
       thvi.up_to_1                as "ATE 1 ANO",
       thvi.over_10                as "ACIMA DE 10 ANOS",
       thvi.between_2_and_5        as "ENTRE 2 E 5 ANOS",
       thvi.between_5_and_10       as "ENTRE 5 E 10 ANOS",
       thvi.group_up_to_1          as "GRUPO ATE 1 ANO",
       thvi.group_between_2_and_5  as "GRUPO ENTRE 2 E 5 ANOS",
       thvi.group_between_5_and_10 as "GRUPO ENTRE 5 E 10 ANOS",
       thvi.group_over_10          as "GRUPO ACIMA DE 10 ANOS",
       thvi.heavy_vehicles         as "VEICULOS PESADOS",
       thvi.heavy_vehicles_group   as "VEICULOS PESADOS GRUPO"
from linkapital.tab_company tc
         inner join linkapital.tab_company_main_info tcmi on tc.main_info = tcmi.id
         inner join linkapital.tab_heavy_vehicle_info thvi on tc.heavy_vehicle_info = thvi.id
where tc.client;

-- HEAVY VEHICLES
select tcmi.cnpj           as "CNPJ",
       tcmi.fantasy_name   as "RAZÃO SOCIAL",
       thv.car_plate       as "MARCA/MODELO",
       thv.type            as "TIPO",
       thv.model           as "MARCA/MODELO",
       thv.production_year as "ANO DE FABRICAÇAO",
       thv.fuel            as "COMBUSTIVEL",
       thv.uf              as "UF",
       thv.renavam         as "RENAVAM",
       thv.antt            as "CONSTA NA ANTT"
from linkapital.tab_company tc
         inner join linkapital.tab_company_main_info tcmi on tc.main_info = tcmi.id
         inner join linkapital.tab_heavy_vehicle_info thvi on tc.heavy_vehicle_info = thvi.id
         left join linkapital.tab_heavy_vehicle thv on thvi.id = thv.heavy_vehicle_info_id
where tc.client;

-- DOMAIN
select tcmi.cnpj            as "CNPJ",
       tcmi.fantasy_name    as "RAZÃO SOCIAL",
       td.name              as "NOME",
       td.responsible       as "RESPONSAVEL",
       td.created_date      as "DATA CRIAÇAO",
       td.modification_date as "DATA ALTERAÇAO",
       td.expiration_date   as "DATA EXPIRAÇAO"
from linkapital.tab_company tc
         inner join linkapital.tab_company_main_info tcmi on tc.main_info = tcmi.id
         inner join linkapital.tab_domain td on tc.id = td.company_id
where tc.client;

--AIRCRAFT
select tcmi.cnpj          as "CNPJ",
       tcmi.fantasy_name  as "RAZÃO SOCIAL",
       ta.registration    as "MATRICULA",
       ta.model           as "MODELO",
       ta.maker           as "FABRICANTE",
       ta.production_year as "ANO DE FABRICAÇAO",
       ta.owner_name      as "PROPRIETARIO",
       ta.operator_name   as "OPERADOR",
       ta.situation       as "SITUAÇAO DE AERONAVEGABILIDADE",
       ta.status_rab      as "SITUAÇAO NO RAB"
from linkapital.tab_company tc
         inner join linkapital.tab_company_main_info tcmi on tc.main_info = tcmi.id
         inner join linkapital.tab_aircraft ta on tc.id = ta.company_id
where tc.client;

-- RELATIONSHIPS
select tcmi.cnpj          as "CNPJ",
       tcmi.fantasy_name  as "RAZÃO SOCIAL",
       tcmir.cnpj         as "CNPJ EMPRESA RELACIONADA",
       tcmir.fantasy_name as "RAZÃO SOCIAL EMPRESA RELACIONADA",
       tcnr.code          as "CNAE EMPRESA RELACIONADA",
       tcmir.opening_date as "DATA ABERTURA EMPRESA RELACIONADA",
       tar.municipality   as "MUNICIPIO EMPRESA RELACIONADA",
       tar.uf             as "UF EMPRESA RELACIONADA"
from linkapital.tab_company tc
         inner join linkapital.tab_company_main_info tcmi on tc.main_info = tcmi.id
         inner join linkapital.tab_company_companies_related tccr on tc.id = tccr.company_id
         inner join linkapital.tab_company tcr on tccr.company_related_id = tcr.id
         inner join linkapital.tab_company_main_info tcmir on tcr.main_info = tcmir.id
         left join linkapital.tab_address tar on tcmir.address_id = tar.id
         left join linkapital.tab_cnae tcnr on tcr.main_cnae = tcnr.id
where tc.client;

-- CEIS
select tcmi.cnpj               as "CNPJ",
       tcmi.fantasy_name       as "RAZÃO SOCIAL",
       t.process_number        as "NUMERO DO PROCESSO",
       t.sanction              as "TIPO DE SANÇAO",
       t.organ_complement      as "COMPLEMENTO DO ORGAO",
       t.sanctioning_entity    as "ORGAO SANCIONADOR",
       t.uf_sanctioning_entity as "UF DO ORGAO",
       t.legal_substantiation  as "FUNDAMENTACAO LEGAL",
       t.information_entity    as "ORIGEM INFORMACAO",
       t.init_sanction_date    as "DATA DE INICIO DA SANÇAO",
       t.information_date      as "DATA INFORMAÇAO",
       t.end_sanction_date     as "DATA DE FIM DA SANÇAO"
from linkapital.tab_company tc
         inner join linkapital.tab_company_main_info tcmi on tc.main_info = tcmi.id
         inner join linkapital.tab_ceis t on tc.id = t.company_id
where tc.client;

-- CEPIM
select tcmi.cnpj           as "CNPJ",
       tcmi.fantasy_name   as "RAZÃO SOCIAL",
       t.contract          as "NUMERO DO CONVENIO SIAFI",
       t.grantor_entity    as "CONCEDENTE",
       t.impediment        as "MOTIVO",
       t.end_contract_date as "FIM DA VIGENCIA",
       t.value_released    as "VALOR LIBERADO"
from linkapital.tab_company tc
         inner join linkapital.tab_company_main_info tcmi on tc.main_info = tcmi.id
         inner join linkapital.tab_cepim t on tc.id = t.company_id
where tc.client;

-- CENEP
select tcmi.cnpj               as "CNPJ",
       tcmi.fantasy_name       as "RAZÃO SOCIAL",
       t.process_number        as "NUMERO PROCESSO",
       t.sanction              as "TIPO SANÇAO",
       t.sanctioning_entity    as "ORGAO SANCIONADOR",
       t.uf_sanctioning_entity as "UF ORGAO",
       t.penalty_value         as "VALOR MULTA",
       t.init_sanction_date    as "DATA INICIO SANÇAO",
       t.end_sanction_date     as "DATA FINAL SANÇAO"
from linkapital.tab_company tc
         inner join linkapital.tab_company_main_info tcmi on tc.main_info = tcmi.id
         inner join linkapital.tab_cnep t on tc.id = t.company_id
where tc.client;

-- COAF FINANCIAL ACTIVITY
select tcmi.cnpj                as "CNPJ",
       tcmi.fantasy_name        as "RAZÃO SOCIAL",
       tfa.segment              as "SEGMENTOS",
       tfa.enablement_number    as "NUMERO DA HABILITAÇAO",
       tfa.enablement_situation as "SITUAÇAO DA HABILITAÇAO",
       tfa.enablement_date      as "DATA DA HABILITAÇAO",
       tfa.query_date           as "DATA DA CONSULTA"
from linkapital.tab_company tc
         inner join linkapital.tab_company_main_info tcmi on tc.main_info = tcmi.id
         inner join linkapital.tab_financial_activity tfa on tc.financial_activity = tfa.id
where tc.client;

-- INTERNATIONAL LIST
select tcmi.cnpj         as "CNPJ",
       tcmi.fantasy_name as "RAZÃO SOCIAL",
       til.name          as "NOME NA LISTA",
       til.query_date    as "ULTIMA CAPTURA"
from linkapital.tab_company tc
         inner join linkapital.tab_company_main_info tcmi on tc.main_info = tcmi.id
         inner join linkapital.tab_international_list til on tc.id = til.company_id
where tc.client;

-- FOREIGN_COMMERCE
select tcmi.cnpj                                                as "CNPJ",
       tcmi.fantasy_name                                        as "RAZÃO SOCIAL",
       tfc.modality                                             as "MODALIDADE",
       tfc.sub_modality                                         as "SUBMODALIDADE",
       tfc.authorized_operations                                as "OPERACOES AUTORIZADAS",
       linkapital.enum_enabled_situation(tfc.enabled_situation) as "SITUAÇAO",
       tfc.enabled                                              as "HABILITADO",
       tfc.situation_date                                       as "DATA SITUAÇAO"
from linkapital.tab_company tc
         inner join linkapital.tab_company_main_info tcmi on tc.main_info = tcmi.id
         inner join linkapital.tab_foreign_commerce tfc on tc.foreign_commerce = tfc.id
where tc.client;

--IMPORT
select tcmi.cnpj         as "CNPJ",
       tcmi.fantasy_name as "RAZÃO SOCIAL",
       tce.year          as "ANO",
       tce.value         as "VALOR"
from linkapital.tab_company tc
         inner join linkapital.tab_company_main_info tcmi on tc.main_info = tcmi.id
         inner join linkapital.tab_company_export tce on tc.id = tce.company_import_id
where tc.client;

--EXPORT
select tcmi.cnpj         as "CNPJ",
       tcmi.fantasy_name as "RAZÃO SOCIAL",
       tce.year          as "ANO",
       tce.value         as "VALOR"
from linkapital.tab_company tc
         inner join linkapital.tab_company_main_info tcmi on tc.main_info = tcmi.id
         inner join linkapital.tab_company_export tce on tc.id = tce.company_export_id
where tc.client;
