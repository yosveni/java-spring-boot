package com.linkapital.core.services.method_k.contract;

import com.linkapital.core.services.company_user.datasource.domain.CompanyUser;
import com.linkapital.core.services.configuration.contract.to.SysConfigurationTO;
import com.linkapital.core.services.cri_cra_debenture.contract.enums.CriCraDebentureType;
import com.linkapital.core.services.cri_cra_debenture.datasource.domain.CriCraDebenture;
import com.linkapital.core.services.method_k.contract.to.CadastralScoreConfigurationTO;
import com.linkapital.core.services.shared.datasource.domain.JudicialProcessQuantity;
import com.linkapital.core.util.functions.ToDoubleTriFunction;
import org.mapstruct.Mapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.ToDoubleBiFunction;
import java.util.function.ToIntFunction;

import static com.linkapital.core.services.method_k.datasource.domain.ScoreCodes.cndTextOne;
import static com.linkapital.core.services.method_k.datasource.domain.ScoreCodes.cndTextThree;
import static com.linkapital.core.services.method_k.datasource.domain.ScoreCodes.cndTextTwo;
import static com.linkapital.core.services.protest.contract.ProtestBinder.PROTEST_BINDER;
import static com.linkapital.core.services.sped.contract.SpedCodeEndValueBinder.getInvoiceCompanyByMonth;
import static com.linkapital.core.util.json.JsonSerdes.convert;
import static java.math.RoundingMode.HALF_UP;
import static java.time.Period.between;

@Mapper
public interface CadastralScoreBinder {

    //Get Configurations
    BiFunction<List<SysConfigurationTO>, String, Optional<Object>> buildCadastralScoreConfig = (configs, name) ->
            configs.stream()
                    .filter(configuration -> configuration.getName().equals(name))
                    .map(SysConfigurationTO::getConfiguration)
                    .findFirst();

    //Calculate o PGFN+PGE/Fat
    ToDoubleBiFunction<CompanyUser, List<SysConfigurationTO>> getPGFNInvoicingInformed =
            (companyUser, configs) -> {
                var conf = buildCadastralScoreConfig.apply(configs, "PGFN_PGE_FAT");
                if (conf.isEmpty())
                    return 0D;

                var configuration = convert(conf.get(), CadastralScoreConfigurationTO.class);

                final var best = configuration.getBest();
                final var worse = configuration.getWorse();
                final var scoreMax = configuration.getScoreMax();
                var score = 0D;

                var invoiceTotal = getInvoiceCompanyByMonth.applyAsDouble(companyUser.getSpeds()) / 12;
                var debitPgfnDau = companyUser.getCompany().getDebitPgfnDau();

                var value = invoiceTotal == 0 || debitPgfnDau == null
                        ? 0D
                        : debitPgfnDau.getTotalDebits() / invoiceTotal;

                if (value == best)
                    score = scoreMax;
                else if (value < worse)
                    score = scoreMax * (worse - value) / (worse - best);

                return BigDecimal
                        .valueOf(score)
                        .setScale(1, HALF_UP)
                        .doubleValue();
            };

    //calculate of protestos and age
    ToDoubleBiFunction<CompanyUser, List<SysConfigurationTO>> getAmountProtestsByAge = (companyUser, configs) -> {
        var conf = buildCadastralScoreConfig.apply(configs, "PROT_IDADE");
        if (conf.isEmpty())
            return 0D;

        var configuration = convert(conf.get(), CadastralScoreConfigurationTO.class);

        final var best = configuration.getBest();
        final var worse = configuration.getWorse();
        final var scoreMax = configuration.getScoreMax();
        var score = 0D;
        var value = 0D;

        var protestInformation = companyUser.getCompany().getProtestInformation();
        if (protestInformation != null && protestInformation.getAnalysis() != null) {
            var dateCreated = companyUser.getCompany().getMainInfo().getOpeningDate();
            var companyAge = between(dateCreated.toLocalDate(), LocalDateTime.now().toLocalDate()).getYears();

            if (companyAge > 0) {
                var protestAnalysis = PROTEST_BINDER.getAnalysis(protestInformation.getAnalysis());
                value = (double) protestAnalysis.getAmountProtest() / companyAge;
            }
        }

        if (value == best)
            score = scoreMax;
        else if (value < worse)
            score = scoreMax * (worse - value) / (worse - best);

        return BigDecimal.valueOf(score)
                .setScale(1, HALF_UP)
                .doubleValue();
    };

    //Valor total dos protestos
    ToDoubleBiFunction<CompanyUser, List<SysConfigurationTO>> getTotalValueProtestsInvoicing =
            (companyUser, configs) -> {
                var conf = buildCadastralScoreConfig.apply(configs, "VALUE_PROT");
                if (conf.isEmpty())
                    return 0D;

                var configuration = convert(conf.get(), CadastralScoreConfigurationTO.class);

                final var best = configuration.getBest();
                final var worse = configuration.getWorse();
                final var scoreMax = configuration.getScoreMax();
                var score = 0D;
                var value = 0D;

                var protestInformation = companyUser.getCompany().getProtestInformation();
                if (protestInformation != null && protestInformation.getAnalysis() != null) {
                    var invoiceTotal = getInvoiceCompanyByMonth.applyAsDouble(companyUser.getSpeds()) / 12;

                    if (invoiceTotal > 0) {
                        var protestAnalysis = PROTEST_BINDER.getAnalysis(protestInformation.getAnalysis());
                        value = protestAnalysis.getTotalValue() / invoiceTotal;
                    }
                }

                if (value == best)
                    score = scoreMax;
                else if (worse > value)
                    score = scoreMax * (worse - value) / (worse - best);

                return BigDecimal
                        .valueOf(score)
                        .setScale(1, HALF_UP)
                        .doubleValue();
            };

    ToIntFunction<List<JudicialProcessQuantity>> getAmountProcess = source -> source
            .stream()
            .filter(judicialProcessQuantity -> !judicialProcessQuantity.getType().equals("TRABALHISTA"))
            .mapToInt(JudicialProcessQuantity::getQuantityPassivePart)
            .sum();

    //Valor total trabalhistas
    ToDoubleBiFunction<CompanyUser, List<SysConfigurationTO>> getTotalValueProcessInvoicing =
            (companyUser, configs) -> {
                var conf = buildCadastralScoreConfig.apply(configs, "VAL_PROT_TRAB");
                if (conf.isEmpty())
                    return 0D;

                var configuration = convert(conf.get(), CadastralScoreConfigurationTO.class);

                final var best = configuration.getBest();
                final var worse = configuration.getWorse();
                final var scoreMax = configuration.getScoreMax();
                var score = 0D;
                var value = 0D;

                var judicialProcess = companyUser.getCompany().getJudicialProcess();
                if (judicialProcess != null) {
                    var invoiceTotal = getInvoiceCompanyByMonth.applyAsDouble(companyUser.getSpeds()) / 12;
                    if (invoiceTotal > 0)
                        value = judicialProcess.getTotalValue() / invoiceTotal;
                }

                if (value == best)
                    score = scoreMax;
                else if (worse > value)
                    score = scoreMax * (worse - value) / (worse - best);

                return BigDecimal
                        .valueOf(score)
                        .setScale(1, HALF_UP)
                        .doubleValue();
            };

    //Qtde de Processos ex-trabalhistas
    ToDoubleBiFunction<CompanyUser, List<SysConfigurationTO>> getWorkProcessAge = (companyUser, configs) -> {
        var conf = buildCadastralScoreConfig.apply(configs, "PROT_TRAB");
        if (conf.isEmpty())
            return 0D;

        var configuration = convert(conf.get(), CadastralScoreConfigurationTO.class);

        final var best = configuration.getBest();
        final var worse = configuration.getWorse();
        final var scoreMax = configuration.getScoreMax();
        var score = 0D;
        var value = 0D;

        var judicialProcess = companyUser.getCompany().getJudicialProcess();
        if (judicialProcess != null) {
            var dateCreated = companyUser.getCompany().getMainInfo().getOpeningDate();
            var companyAge = between(dateCreated.toLocalDate(), LocalDateTime.now().toLocalDate()).getYears();

            if (companyAge > 0) {
                var amountProcess = getAmountProcess.applyAsInt(judicialProcess.getJudicialProcessQuantities());
                value = (double) amountProcess / companyAge;
            }
        }

        if (value == best)
            score = scoreMax;
        else if (worse > value)
            score = scoreMax * (worse - value) / (worse - best);

        return BigDecimal
                .valueOf(score)
                .setScale(1, HALF_UP)
                .doubleValue();
    };

    //Calculate of the BNDES
    ToDoubleBiFunction<CompanyUser, List<SysConfigurationTO>> getBdnsOperation = (companyUser, configs) -> {
        var conf = buildCadastralScoreConfig.apply(configs, "BNDES");
        if (conf.isEmpty())
            return 0D;

        var configuration = convert(conf.get(), CadastralScoreConfigurationTO.class);
        final var scoreMax = configuration.getScoreMax();
        var score = 0D;

        var sizeOperation = companyUser.getCompany().getBankOperations().size();
        if (sizeOperation != 0)
            score = scoreMax;

        return BigDecimal
                .valueOf(score)
                .setScale(1, HALF_UP)
                .doubleValue();
    };

    BiPredicate<Set<CriCraDebenture>, CriCraDebentureType> isTypeOperation = (source, type) -> source
            .stream()
            .allMatch(criCraDebenture -> criCraDebenture.getType().equals(type));

    //Calculate of the CRI, CRA AND DEBENTURE
    ToDoubleTriFunction<CriCraDebentureType, CompanyUser, List<SysConfigurationTO>> getCriCraDebentureValue =
            (type, companyUser, configs) -> {
                var conf = buildCadastralScoreConfig.apply(configs, type.name());
                var criCraDebentures = companyUser.getCompany().getCriCraDebentures();
                if (conf.isEmpty() || criCraDebentures.isEmpty())
                    return 0D;

                var configuration = convert(conf.get(), CadastralScoreConfigurationTO.class);
                final var scoreMax = configuration.getScoreMax();
                var score = 0D;

                if (isTypeOperation.test(criCraDebentures, type))
                    score = scoreMax;

                return BigDecimal
                        .valueOf(score)
                        .setScale(1, HALF_UP)
                        .doubleValue();
            };

    //calculate of the certidoes
    ToDoubleBiFunction<CompanyUser, List<SysConfigurationTO>> getValueCertificates = (company, configs) -> {
        var conf = buildCadastralScoreConfig.apply(configs, "CERTIFICATE");
        var taxHealth = company.getCompany().getTaxHealth();
        if (conf.isEmpty() || taxHealth == null || taxHealth.getCnds().isEmpty())
            return 0D;

        var configuration = convert(conf.get(), CadastralScoreConfigurationTO.class);
        var point = configuration.getPoint();

        var count = taxHealth.getCnds()
                .stream()
                .filter(cnds -> cnds.getSituation().equalsIgnoreCase(cndTextOne) ||
                        cnds.getSituation().equalsIgnoreCase(cndTextTwo) ||
                        cnds.getSituation().equalsIgnoreCase(cndTextThree))
                .count();

        return count * point;
    };

}