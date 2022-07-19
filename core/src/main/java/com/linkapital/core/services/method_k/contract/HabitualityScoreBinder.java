package com.linkapital.core.services.method_k.contract;

import com.linkapital.core.services.bank_operation.datasource.domain.BankOperation;
import com.linkapital.core.services.company_user.datasource.domain.CompanyUser;
import com.linkapital.core.services.configuration.contract.to.SysConfigurationTO;
import com.linkapital.core.services.credit_information.datasource.domain.CreditInformation;
import com.linkapital.core.services.credit_information.datasource.domain.Earning;
import com.linkapital.core.services.method_k.contract.to.FinancialStrengthConfigurationTO;
import com.linkapital.core.services.method_k.contract.to.HabitualityScoreConfigurationTO;
import com.linkapital.core.services.sped.datasource.domain.Sped;
import com.linkapital.core.util.functions.ToDoubleTriFunction;
import org.mapstruct.Mapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.ToDoubleBiFunction;
import java.util.function.ToDoubleFunction;

import static com.linkapital.core.services.bank_operation.contract.enums.BankOperationStatus.ACTIVE;
import static com.linkapital.core.services.bank_operation.contract.enums.BankOperationStatus.LIQUIDATED;
import static com.linkapital.core.services.method_k.datasource.domain.ScoreCodes.codeMaxDay;
import static com.linkapital.core.services.method_k.datasource.domain.ScoreCodes.codes;
import static com.linkapital.core.services.sped.contract.SpedCodeEndValueBinder.getPl;
import static com.linkapital.core.services.sped.contract.SpedCodeEndValueBinder.setAndGetEndValue;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_01_01_01;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_01_01_01_01;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_01_01_03;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_11_01_01;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_11_01_01_01;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_11_01_03;
import static com.linkapital.core.util.json.JsonSerdes.convert;
import static java.math.RoundingMode.HALF_UP;
import static java.time.Period.between;
import static java.util.Comparator.comparing;

@Mapper
public interface HabitualityScoreBinder {

    //Get Configurations
    BiFunction<List<SysConfigurationTO>, String, Optional<Object>> buildHabitualidadyScoreConfig = (configs, name) ->
            configs.stream()
                    .filter(configuration -> configuration.getName().equals(name))
                    .map(SysConfigurationTO::getConfiguration)
                    .findFirst();

    ToDoubleBiFunction<CompanyUser, List<SysConfigurationTO>> getDebtBndesCountActual = (companyUser, sysConfig) -> {
        var conf = buildHabitualidadyScoreConfig.apply(sysConfig, "DEBT_BNDES_COUNT_CURRENT");
        if (conf.isEmpty())
            return 0D;

        var configuration = convert(conf.get(), HabitualityScoreConfigurationTO.class);

        final var best = configuration.getBest();
        final var worse = configuration.getWorse();
        final var scoreMax = configuration.getScoreMax();
        var score = 0D;
        var value = 0D;

        var bankOperations = companyUser.getCompany().getBankOperations();
        if (!bankOperations.isEmpty()) {
            var debtBndesActual = Math.toIntExact(bankOperations
                    .stream()
                    .filter(bankOperation -> bankOperation.getStatus().equals(ACTIVE))
                    .count());

            var dateCreated = companyUser.getCompany().getMainInfo().getOpeningDate();
            var companyAge = between(dateCreated.toLocalDate(), LocalDateTime.now().toLocalDate()).getYears();

            if (debtBndesActual > 0 && companyAge > 0)
                value = (double) debtBndesActual / companyAge;
        }

        if ((value == best) || (value > best))
            score = scoreMax;
        else
            score = scoreMax * (worse - value) / (worse - best);

        return BigDecimal
                .valueOf(score)
                .setScale(1, HALF_UP)
                .doubleValue();
    };

    ToDoubleBiFunction<CompanyUser, List<SysConfigurationTO>> getDebtBndesVolActual = (companyUser, sysConfig) -> {
        var conf = buildHabitualidadyScoreConfig.apply(sysConfig, "DEBT_BNDES_VOL_CURRENT");
        if (conf.isEmpty())
            return 0D;

        var configuration = convert(conf.get(), HabitualityScoreConfigurationTO.class);

        final var best = configuration.getBest();
        final var worse = configuration.getWorse();
        final var scoreMax = configuration.getScoreMax();
        var score = 0D;
        var value = 0D;

        var bankOperations = companyUser.getCompany().getBankOperations();
        if (!bankOperations.isEmpty()) {
            var volOperation = bankOperations
                    .stream()
                    .filter(bankOperation -> bankOperation.getStatus().equals(ACTIVE))
                    .mapToDouble(BankOperation::getContractedValue)
                    .sum();

            var speds = companyUser.getSpeds();
            var totalPl = speds
                    .stream()
                    .mapToDouble(sped -> getPl.applyAsDouble(sped.getSpedBalances()) * 12)
                    .sum();

            value = totalPl == 0
                    ? 0D
                    : volOperation / totalPl;
        }

        if ((value == best) || (value > best))
            score = scoreMax;
        else
            score = scoreMax * (worse - value) / (worse - best);

        return BigDecimal
                .valueOf(score)
                .setScale(1, HALF_UP)
                .doubleValue();
    };

    ToDoubleBiFunction<CompanyUser, List<SysConfigurationTO>> getDebtBndesCountLiquided = (companyUser, sysConfig) -> {
        var conf = buildHabitualidadyScoreConfig.apply(sysConfig, "DEBT_BNDES_COUNT_PAY");
        if (conf.isEmpty())
            return 0D;

        var configuration = convert(conf.get(), HabitualityScoreConfigurationTO.class);

        final var best = configuration.getBest();
        final var worse = configuration.getWorse();
        final var scoreMax = configuration.getScoreMax();
        var score = 0D;
        var value = 0D;

        var bankOperations = companyUser.getCompany().getBankOperations();
        if (!bankOperations.isEmpty()) {
            var debtBndesActual = Math.toIntExact(bankOperations
                    .stream()
                    .filter(bankOperation -> bankOperation.getStatus().equals(LIQUIDATED))
                    .count());

            var dateCreated = companyUser.getCompany().getMainInfo().getOpeningDate();
            var companyAge = between(dateCreated.toLocalDate(), LocalDateTime.now().toLocalDate()).getYears();

            if (debtBndesActual > 0 && companyAge > 0)
                value = (double) debtBndesActual / companyAge;
        }

        if ((value == best) || (value > best))
            score = scoreMax;
        else
            score = scoreMax * (worse - value) / (worse - best);

        return BigDecimal
                .valueOf(score)
                .setScale(1, HALF_UP)
                .doubleValue();
    };

    ToDoubleBiFunction<CompanyUser, List<SysConfigurationTO>> getDebtBndesVolLiquided = (companyUser, sysConfig) -> {
        var conf = buildHabitualidadyScoreConfig.apply(sysConfig, "DEBT_BNDES_VOL_PAY");
        if (conf.isEmpty())
            return 0D;

        var configuration = convert(conf.get(), HabitualityScoreConfigurationTO.class);

        final var best = configuration.getBest();
        final var worse = configuration.getWorse();
        final var scoreMax = configuration.getScoreMax();
        var score = 0D;
        var value = 0D;

        var bankOperations = companyUser.getCompany().getBankOperations();
        if (!bankOperations.isEmpty()) {
            var volOperation = bankOperations
                    .stream()
                    .filter(bankOperation -> bankOperation.getStatus().equals(LIQUIDATED))
                    .mapToDouble(BankOperation::getContractedValue)
                    .sum();

            var listSped = companyUser.getSpeds();
            var totalPl = listSped
                    .stream()
                    .mapToDouble(sped -> getPl.applyAsDouble(sped.getSpedBalances()) * 12)
                    .sum();

            value = totalPl == 0
                    ? 0D
                    : volOperation / totalPl;
        }

        if ((value == best) || (value > best))
            score = scoreMax;
        else
            score = scoreMax * (worse - value) / (worse - best);

        return BigDecimal
                .valueOf(score)
                .setScale(1, HALF_UP)
                .doubleValue();
    };

    ToDoubleFunction<Sped> getCalculateGrossRevenue = source -> {
        var sumTwo = new AtomicReference<>(0D);

        source.getSpedDemonstrations().forEach(spedDemonstration -> {
            if (_3_01_01_01_01.equals(spedDemonstration.getCode()) ||
                    _3_11_01_01_01.equals(spedDemonstration.getCode()))
                sumTwo.updateAndGet(v -> v + setAndGetEndValue.applyAsDouble(spedDemonstration));
        });

        var months = source.getDemonstrativeEndDate().getMonthValue() - source.getDemonstrativeInitDate().getMonthValue();
        return months == 0
                ? 0D
                : (sumTwo.get() / months) * 12;
    };

    // Margem Bruta
    ToDoubleBiFunction<Sped, List<SysConfigurationTO>> calculateGrossMargin = (source, sysConfig) -> {
        var conf = buildHabitualidadyScoreConfig.apply(sysConfig, "GrossMargin");
        if (conf.isEmpty())
            return 0D;

        var sumOne = new AtomicReference<>(0D);
        var sumTwo = new AtomicReference<>(0D);
        var configuration = convert(conf.get(), FinancialStrengthConfigurationTO.class);
        final var best = configuration.getBest();
        final var worse = configuration.getWorse();
        final var scoreMax = configuration.getScoreMax();
        var score = 0D;

        source.getSpedDemonstrations().forEach(spedDemonstration -> {
            if (_3_01_01_01.equals(spedDemonstration.getCode()) ||
                    _3_01_01_03.equals(spedDemonstration.getCode()) ||
                    _3_11_01_01.equals(spedDemonstration.getCode()) ||
                    _3_11_01_03.equals(spedDemonstration.getCode()))
                sumOne.updateAndGet(v -> v + setAndGetEndValue.applyAsDouble(spedDemonstration));
            else if (_3_01_01_01_01.equals(spedDemonstration.getCode()) ||
                    _3_11_01_01_01.equals(spedDemonstration.getCode()))
                sumTwo.updateAndGet(v -> v + setAndGetEndValue.applyAsDouble(spedDemonstration));
        });

        var months = source.getDemonstrativeEndDate().getMonthValue() - source.getDemonstrativeInitDate().getMonthValue();
        var rawScore = months == 0
                ? 0D
                : (sumOne.get() / months) * 12;
        var grossRevenue = getCalculateGrossRevenue.applyAsDouble(source);

        var value = grossRevenue == 0
                ? 0D
                : rawScore / grossRevenue;

        if (value > best)
            score = scoreMax;
        else if (value <= best && value >= worse)
            score = scoreMax * ((value - worse) / (best - worse));

        return score;
    };

    ToDoubleTriFunction<CompanyUser, CreditInformation, List<SysConfigurationTO>> getCurrentVolume =
            (companyUser, creditInformation, sysConfig) -> {
                var conf = buildHabitualidadyScoreConfig.apply(sysConfig, "DEBT_IF_VOL_RB");
                if (conf.isEmpty())
                    return 0D;

                var configuration = convert(conf.get(), HabitualityScoreConfigurationTO.class);

                final var best = configuration.getBest();
                final var worse = configuration.getWorse();
                final var scoreMax = configuration.getScoreMax();
                var score = 0D;
                var value = 0D;

                var speds = companyUser.getSpeds();
                if (!speds.isEmpty()) {
                    var rawScore = speds
                            .stream()
                            .map(sped -> calculateGrossMargin.applyAsDouble(sped, sysConfig))
                            .reduce(0.0, Double::sum);
                    var currentVolume = creditInformation == null
                            ? 0D
                            : creditInformation.getAssumedObligation();

                    value = rawScore == 0
                            ? 0D
                            : currentVolume / rawScore;
                }

                if (value == best)
                    score = scoreMax;
                else if (value < best)
                    score = scoreMax * (worse - value) / (worse - best);

                return BigDecimal
                        .valueOf(score)
                        .setScale(1, HALF_UP)
                        .doubleValue();
            };

    ToDoubleTriFunction<CompanyUser, CreditInformation, List<SysConfigurationTO>> getCountOperation =
            (companyUser, creditInformation, sysConfig) -> {
                var conf = buildHabitualidadyScoreConfig.apply(sysConfig, "DEBT_IF_COUNT");
                if (conf.isEmpty())
                    return 0D;

                var configuration = convert(conf.get(), HabitualityScoreConfigurationTO.class);

                final var best = configuration.getBest();
                final var worse = configuration.getWorse();
                final var scoreMax = configuration.getScoreMax();
                var score = 0D;

                var countOperation = creditInformation == null
                        ? 0
                        : creditInformation.getOperations().size();

                if (countOperation > best)
                    score = scoreMax;
                else if (countOperation < best)
                    score = scoreMax * (worse - countOperation) / (worse - best);

                return BigDecimal
                        .valueOf(score)
                        .setScale(1, HALF_UP)
                        .doubleValue();
            };

    ToDoubleTriFunction<CompanyUser, CreditInformation, List<SysConfigurationTO>> getDebtVolumeByPl =
            (companyUser, creditInformation, sysConfig) -> {
                var conf = buildHabitualidadyScoreConfig.apply(sysConfig, "DEBT_IF_VOL_PL");
                if (conf.isEmpty())
                    return 0D;

                var configuration = convert(conf.get(), HabitualityScoreConfigurationTO.class);

                final var best = configuration.getBest();
                final var worse = configuration.getWorse();
                final var scoreMax = configuration.getScoreMax();
                var score = 0D;
                var value = 0D;

                var speds = companyUser.getSpeds();

                if (!speds.isEmpty() && creditInformation != null) {
                    var totalPl = speds
                            .stream()
                            .mapToDouble(sped -> getPl.applyAsDouble(sped.getSpedBalances()) * 12)
                            .sum();

                    var delayVolume = creditInformation.getOperations()
                            .stream()
                            .mapToDouble(resumeOperation -> resumeOperation.getEarnings()
                                    .stream()
                                    .filter(earning -> codes.contains(earning.getCode()))
                                    .mapToDouble(Earning::getValue)
                                    .sum())
                            .sum();

                    value = totalPl == 0
                            ? 0D
                            : delayVolume / totalPl;
                }

                if (value == best)
                    score = scoreMax;
                else if (value < worse)
                    score = scoreMax * (worse - value) / (worse - best);

                return BigDecimal
                        .valueOf(score)
                        .setScale(1, HALF_UP)
                        .doubleValue();
            };

    ToDoubleTriFunction<CompanyUser, CreditInformation, List<SysConfigurationTO>> getDebDelayUp90Day =
            (companyUser, creditInformation, sysConfig) -> {
                var conf = buildHabitualidadyScoreConfig.apply(sysConfig, "DEBT_IF_UPTO_90_DAY");
                if (conf.isEmpty())
                    return 0D;

                var configuration = convert(conf.get(), HabitualityScoreConfigurationTO.class);

                final var best = configuration.getBest();
                final var worse = configuration.getWorse();
                final var scoreMax = configuration.getScoreMax();
                var score = 0D;
                var value = 0D;

                if (creditInformation != null) {
                    var numDay = creditInformation.getOperations()
                            .stream()
                            .flatMap(resumeOperation -> resumeOperation.getEarnings().stream())
                            .max(comparing(Earning::getValue))
                            .stream()
                            .mapToInt(habitualScore ->
                                    codeMaxDay.getOrDefault(habitualScore.getCode(), 0))
                            .sum();

                    value = (double) numDay / 90;
                }

                if (value == best)
                    score = scoreMax;
                else if (value < worse)
                    score = scoreMax * (worse - value) / (worse - best);

                return BigDecimal
                        .valueOf(score)
                        .setScale(1, HALF_UP)
                        .doubleValue();
            };

}
