package com.linkapital.core.services.shared.contract;

import com.linkapital.core.services.company.contract.enums.EmitterType;
import com.linkapital.core.services.company.datasource.domain.Cnds;
import com.linkapital.core.services.company.datasource.domain.SimpleNational;
import com.linkapital.core.services.company.datasource.domain.SintegraInscription;
import com.linkapital.core.services.company.datasource.domain.Suframa;
import com.linkapital.core.services.shared.datasource.domain.DebitMte;
import com.linkapital.core.services.shared.datasource.domain.DebitMteProcess;
import com.linkapital.core.services.shared.datasource.domain.DebitPgfn;
import com.linkapital.core.services.shared.datasource.domain.DebitPgfnDau;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import static com.linkapital.core.util.DateUtil.convert;
import static java.lang.Double.parseDouble;
import static java.time.format.DateTimeFormatter.ISO_ZONED_DATE_TIME;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toSet;
import static org.slf4j.LoggerFactory.getLogger;

@Mapper
public interface FiscalBinder {

    FiscalBinder FISCAL_BINDER = Mappers.getMapper(FiscalBinder.class);
    Logger log = getLogger(FiscalBinder.class);

    //Level 2
    String code = "codigo";
    String emissionDate = "dataEmissao";
    String debitSituation = "situacaoDebito";
    String certificateType = "tipoCertidao";
    String processes = "processos";
    String debits = "dividas";
    String totalDebits = "totalDividas";
    String simeiDate = "dataOptanteSimei";
    String simpleDate = "dataOptanteSimples";
    String simei = "optanteSimei";
    String simple = "optanteSimples";
    String simpleIrregular = "simplesIrregular";
    String inscription = "inscricao";
    String situation = "situacao";
    String incentiveTypes = "tiposIncentivo";
    //Level 3
    String debit = "valorInscricaoDevido";
    String infringementCapitulation = "capitulacaoInfracao";
    String infringementCategory = "categoriaInfracao";
    String number = "numero";
    String processSituation = "situacaoProcesso";
    String inscriptionNumber = "inscricao";
    String nature = "natureza";
    String certificateNumber = "numeroCertificacao";
    String expirationDate = "dataValidade";
    String situationDescription = "descricaoSituacao";
    String emitterName = "nome";
    String registrationSituationDate = "dataSituacaoCadastral";
    String email = "email";
    String ie = "ie";
    String regime = "regimeApuracao";
    String registrationSituation = "situacaoCadastral";
    String phone = "telefone";
    String uf = "uf";
    String registrationSituationSuframa = "status";
    String icms = "icms";
    String ipi = "ipi";
    String pisCofins = "pisCofins";

    Function<Map, DebitMteProcess> buildDebitMteProcess = source -> {
        var debitMteProcess = new DebitMteProcess();

        debitMteProcess.setInfringementCapitulation(nonNull(source.get(infringementCapitulation))
                ? source.get(infringementCapitulation).toString()
                : null);
        debitMteProcess.setInfringementCategory(nonNull(source.get(infringementCategory))
                ? source.get(infringementCategory).toString()
                : null);
        debitMteProcess.setNumber(nonNull(source.get(number))
                ? source.get(number).toString()
                : null);
        debitMteProcess.setSituation(nonNull(source.get(processSituation))
                ? source.get(processSituation).toString()
                : null);

        return debitMteProcess;
    };

    Function<List<Map>, List<DebitMteProcess>> getDebitMteProcess = source -> source
            .stream()
            .map(buildDebitMteProcess)
            .toList();

    Function<Map<String, Object>, DebitMte> bindDebitMte = source -> {
        var debitMte = new DebitMte();

        debitMte.setCertificateType(nonNull(source.get(certificateType))
                ? source.get(certificateType).toString()
                : null);
        debitMte.setCode(nonNull(source.get(code))
                ? source.get(code).toString()
                : null);
        debitMte.setDebitSituation(nonNull(source.get(debitSituation))
                ? source.get(debitSituation).toString()
                : null);

        try {
            debitMte.setEmissionDate(nonNull(source.get(emissionDate))
                    ? convert(source.get(emissionDate).toString())
                    : null);
        } catch (ParseException e) {
            log.error(e.getMessage());
        }

        if (nonNull(source.get(processes)))
            debitMte.getProcesses().addAll(getDebitMteProcess.apply((List<Map>) source.get(processes)));

        return debitMte;
    };

    Function<Map, DebitPgfn> buildDebitPgfn = source -> {
        var debitPgfn = new DebitPgfn();

        debitPgfn.setNature(nonNull(source.get(nature))
                ? source.get(nature).toString()
                : null);
        debitPgfn.setInscriptionNumber(nonNull(source.get(inscriptionNumber))
                ? source.get(inscriptionNumber).toString()
                : null);
        debitPgfn.setDebit(nonNull(source.get(debit))
                ? parseDouble(source.get(debit).toString())
                : 0);

        return debitPgfn;
    };

    Function<List<Map>, List<DebitPgfn>> getDebitPgfn = source -> source
            .stream()
            .map(buildDebitPgfn)
            .toList();

    Function<Map<String, Object>, DebitPgfnDau> bindDebitPgfnDau = source -> {
        var debit = new DebitPgfnDau();

        debit.setTotalDebits(source.get(totalDebits) == null
                ? 0D
                : parseDouble(source.get(totalDebits).toString()));
        if (source.get(debits) != null)
            debit.getDebitPgfns().addAll(getDebitPgfn.apply((List<Map>) source.get(debits)));

        return debit;
    };

    Function<Map, Cnds> buildCnds = source -> {
        var companyCnds = new Cnds();

        companyCnds.setCertificateNumber(source.get(certificateNumber) == null
                ? null
                : source.get(certificateNumber).toString());
        companyCnds.setEmitterName(source.get(emitterName) == null
                ? null
                : EmitterType.valueOf(source.get(emitterName).toString()));
        companyCnds.setSituation(source.get(situationDescription) == null
                ? null
                : source.get(situationDescription).toString());
        companyCnds.setEmissionDate(source.get(emissionDate) == null
                ? null
                : LocalDate.parse(source.get(emissionDate).toString(), ISO_ZONED_DATE_TIME));
        companyCnds.setExpirationDate(source.get(expirationDate) == null
                ? null
                : LocalDate.parse(source.get(expirationDate).toString(), ISO_ZONED_DATE_TIME));

        return companyCnds;
    };

    Function<List<Map>, List<Cnds>> getCnds = source -> source
            .stream()
            .map(buildCnds)
            .toList();

    Function<Map<String, Object>, SimpleNational> bindSimpleNational = source -> {
        var simpleNational = new SimpleNational();

        simpleNational.setSimei(nonNull(source.get(simei)) && Boolean.parseBoolean(source.get(simei).toString()));
        simpleNational.setSimple(nonNull(source.get(simple)) && Boolean.parseBoolean(source.get(simple).toString()));
        simpleNational.setSimpleIrregular(nonNull(source.get(simpleIrregular))
                && Boolean.parseBoolean(source.get(simpleIrregular).toString()));

        try {
            simpleNational.setSimeiDate(nonNull(source.get(simeiDate))
                    ? convert(source.get(simeiDate).toString())
                    : null);
        } catch (ParseException e) {
            log.error(e.getMessage());
        }

        try {
            simpleNational.setSimpleDate(nonNull(source.get(simpleDate))
                    ? convert(source.get(simpleDate).toString())
                    : null);
        } catch (ParseException e) {
            log.error(e.getMessage());
        }

        return simpleNational;
    };

    Function<Map, SintegraInscription> buildInscriptionSintegra = source -> {
        var inscriptionSintegra = new SintegraInscription();

        try {
            inscriptionSintegra.setRegistrationSituationDate(nonNull(source.get(registrationSituationDate))
                    ? convert(source.get(registrationSituationDate).toString())
                    : null);
        } catch (ParseException e) {
            log.error(e.getMessage());
        }
        inscriptionSintegra.setEmail(nonNull(source.get(email))
                ? source.get(email).toString()
                : null);
        inscriptionSintegra.setRegistrationNumber(nonNull(source.get(ie))
                ? source.get(ie).toString()
                : null);
        inscriptionSintegra.setRegime(nonNull(source.get(regime))
                ? source.get(regime).toString()
                : null);
        inscriptionSintegra.setRegistrationSituation(nonNull(source.get(registrationSituation))
                ? source.get(registrationSituation).toString()
                : null);
        inscriptionSintegra.setUf(nonNull(source.get(uf))
                ? source.get(uf).toString()
                : null);

        if (nonNull(source.get(phone))) {
            var phoneLevel3 = (Map) source.get(phone);

            inscriptionSintegra.setPhone(nonNull(phoneLevel3.get(phone))
                    ? phoneLevel3.get(phone).toString()
                    : null);
        }

        return inscriptionSintegra;
    };

    Function<List<Map>, Set<SintegraInscription>> bindInscriptionSintegra = source -> source
            .stream()
            .map(buildInscriptionSintegra)
            .collect(toSet());

    Function<Map<String, Object>, Suframa> bindSuframa = source -> {
        var suframa = new Suframa();

        suframa.setRegistrationNumber(nonNull(source.get(inscription))
                ? source.get(inscription).toString()
                : null);

        if (nonNull(source.get(situation))) {
            var situationLevel2 = (Map) source.get(situation);

            try {
                suframa.setExpirationDate(nonNull(situationLevel2.get(expirationDate))
                        ? convert(situationLevel2.get(expirationDate).toString())
                        : null);
            } catch (ParseException e) {
                log.error(e.getMessage());
            }
            suframa.setRegistrationSituation(nonNull(source.get(registrationSituationSuframa))
                    ? source.get(registrationSituationSuframa).toString()
                    : null);
        }

        if (nonNull(source.get(incentiveTypes))) {
            var incentiveTypesLevel2 = (Map) source.get(incentiveTypes);

            suframa.setIcms(nonNull(incentiveTypesLevel2.get(icms))
                    ? incentiveTypesLevel2.get(icms).toString()
                    : null);
            suframa.setIcms(nonNull(incentiveTypesLevel2.get(ipi))
                    ? incentiveTypesLevel2.get(ipi).toString()
                    : null);
            suframa.setIcms(nonNull(incentiveTypesLevel2.get(pisCofins))
                    ? incentiveTypesLevel2.get(pisCofins).toString()
                    : null);
        }

        return suframa;
    };

}
