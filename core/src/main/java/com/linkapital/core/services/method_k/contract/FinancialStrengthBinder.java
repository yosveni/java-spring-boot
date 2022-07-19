package com.linkapital.core.services.method_k.contract;

import com.linkapital.core.services.configuration.contract.to.SysConfigurationTO;
import com.linkapital.core.services.method_k.contract.to.FinancialStrengthConfigurationTO;
import com.linkapital.core.services.method_k.datasource.domain.ScoreOperation;
import com.linkapital.core.services.sped.datasource.domain.Sped;
import com.linkapital.core.util.functions.ToDoubleFourFunction;
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

import static com.linkapital.core.services.sped.contract.SpedCodeEndValueBinder.getNetProfitMonth;
import static com.linkapital.core.services.sped.contract.SpedCodeEndValueBinder.setAndGetEndValue;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._1;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._1_01;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._1_01_01;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._1_01_02_02;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._1_01_03;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._2_01;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._2_01_01_03;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._2_01_01_05;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._2_01_01_07;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._2_02_01_01;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._2_03;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_01_01_01;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_01_01_01_01;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_01_01_03;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_01_01_05;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_01_01_07;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_01_01_07_01_23;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_01_01_07_01_24;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_01_01_09;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_01_01_09_01_08;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_01_01_11;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_01_05;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_11_01_01;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_11_01_01_01;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_11_01_03;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_11_01_05;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_11_01_07;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_11_01_07_01_23;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_11_01_07_01_24;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_11_01_09;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_11_01_09_01_08;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_11_01_11;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_11_05;
import static com.linkapital.core.util.json.JsonSerdes.convert;
import static java.math.RoundingMode.HALF_UP;
import static java.time.Period.between;

@Mapper
public interface FinancialStrengthBinder {

    BiFunction<List<SysConfigurationTO>, String, Optional<Object>> buildFinancialStrengthConfig = (configs, name) ->
            configs.stream()
                    .filter(configuration -> configuration.getName().equals(name))
                    .map(SysConfigurationTO::getConfiguration)
                    .findFirst();

    // Retorno Sobre Ativos
    ToDoubleBiFunction<Sped, Optional<Object>> getReturnOnAssets = (source, config) -> {
        if (config.isEmpty())
            return 0D;

        var configuration = convert(config.get(), FinancialStrengthConfigurationTO.class);

        final var best = configuration.getBest();
        final var worse = configuration.getWorse();
        final var scoreMax = configuration.getScoreMax();
        var score = 0D;

        var totalActive = source.getSpedBalances()
                .stream()
                .filter(spedBalance ->
                        _1.equals(spedBalance.getCode()))
                .mapToDouble(setAndGetEndValue)
                .sum();

        var netProfit = getNetProfitMonth.applyAsDouble(source);
        var value = totalActive == 0
                ? 0D
                : netProfit / totalActive;

        if (value > best)
            score = scoreMax;
        else if (value <= best && value >= worse)
            score = scoreMax * ((value - worse) / (best - worse));

        return score;
    };

    // Retorno Sobre PL
    ToDoubleBiFunction<Sped, Optional<Object>> getReturnOverPl = (source, config) -> {
        if (config.isEmpty())
            return 0D;

        var configuration = convert(config.get(), FinancialStrengthConfigurationTO.class);

        final var best = configuration.getBest();
        final var worse = configuration.getWorse();
        final var scoreMax = configuration.getScoreMax();
        var score = 0D;

        var sumTwo = source.getSpedBalances()
                .stream()
                .filter(spedBalance ->
                        _2_03.equals(spedBalance.getCode()))
                .mapToDouble(setAndGetEndValue)
                .sum();

        var netProfit = getNetProfitMonth.applyAsDouble(source);
        var value = sumTwo == 0
                ? 0D
                : netProfit / sumTwo;

        if (value > best)
            score = scoreMax;
        else if (value <= best && value >= worse)
            score = scoreMax * ((value - worse) / (best - worse));

        return score;
    };

    // Composicion de endividamento
    ToDoubleBiFunction<Sped, Optional<Object>> getDebtComposition = (source, config) -> {
        if (config.isEmpty())
            return 0D;

        var sumOne = new AtomicReference<>(0D);
        var sumTwo = new AtomicReference<>(0D);
        var configuration = convert(config.get(), FinancialStrengthConfigurationTO.class);

        final var best = configuration.getBest();
        final var worse = configuration.getWorse();
        final var scoreMax = configuration.getScoreMax();
        var score = 0D;

        source.getSpedBalances().forEach(spedBalance -> {
            if (_2_01_01_07.equals(spedBalance.getCode())) {
                var value = setAndGetEndValue.applyAsDouble(spedBalance);
                sumOne.updateAndGet(v -> v + value);
                sumTwo.updateAndGet(v -> v + value);
            } else if (_2_02_01_01.equals(spedBalance.getCode()))
                sumTwo.updateAndGet(v -> v + setAndGetEndValue.applyAsDouble(spedBalance));
        });

        var value = sumTwo.get() == 0
                ? 0D
                : sumOne.get() / sumTwo.get();

        if (value < best)
            score = scoreMax;
        else if (value >= best && value <= worse)
            score = scoreMax * ((worse - value) / (worse - best));

        return BigDecimal
                .valueOf(score)
                .setScale(1, HALF_UP)
                .doubleValue();
    };

    // Function that computes the Operational Result
    ToDoubleFunction<Sped> getOperationalResult = source -> {
        var sumNegative = source.getSpedDemonstrations()
                .stream()
                .filter(spedDemonstration ->
                        _3_01_01_07_01_23.equals(spedDemonstration.getCode()) ||
                                _3_01_01_07_01_24.equals(spedDemonstration.getCode()) ||
                                _3_01_01_09_01_08.equals(spedDemonstration.getCode()) ||
                                _3_11_01_09_01_08.equals(spedDemonstration.getCode()) ||
                                _3_11_01_07_01_23.equals(spedDemonstration.getCode()) ||
                                _3_11_01_07_01_24.equals(spedDemonstration.getCode()))
                .mapToDouble(setAndGetEndValue)
                .sum();

        var sumTwo = source.getSpedDemonstrations()
                .stream()
                .filter(spedDemonstration ->
                        _3_01_01_01.equals(spedDemonstration.getCode()) ||
                                _3_01_01_03.equals(spedDemonstration.getCode()) ||
                                _3_01_01_05.equals(spedDemonstration.getCode()) ||
                                _3_01_01_07.equals(spedDemonstration.getCode()) ||
                                _3_01_01_09.equals(spedDemonstration.getCode()) ||
                                _3_01_01_11.equals(spedDemonstration.getCode()) ||
                                _3_01_05.equals(spedDemonstration.getCode()) ||
                                _3_11_01_01.equals(spedDemonstration.getCode()) ||
                                _3_11_01_03.equals(spedDemonstration.getCode()) ||
                                _3_11_01_05.equals(spedDemonstration.getCode()) ||
                                _3_11_01_07.equals(spedDemonstration.getCode()) ||
                                _3_11_01_09.equals(spedDemonstration.getCode()) ||
                                _3_11_01_11.equals(spedDemonstration.getCode()) ||
                                _3_11_05.equals(spedDemonstration.getCode()))
                .mapToDouble(setAndGetEndValue)
                .sum();

        var months = source.getDemonstrativeEndDate().getMonthValue() - source.getDemonstrativeInitDate().getMonthValue();
        return months == 0
                ? 0D
                : ((sumTwo + sumNegative) / months) * 12;
    };

    //Cobertura Juros
    ToDoubleBiFunction<Sped, Optional<Object>> getJuroCoverage = (source, config) -> {
        if (config.isEmpty())
            return 0D;

        var configuration = convert(config.get(), FinancialStrengthConfigurationTO.class);

        final var best = configuration.getBest();
        final var worse = configuration.getWorse();
        final var scoreMax = configuration.getScoreMax();
        var score = 0D;

        var sumOne = source.getSpedDemonstrations()
                .stream()
                .filter(spedDemonstration ->
                        _3_01_01_09_01_08.equals(spedDemonstration.getCode()) ||
                                _3_11_01_09_01_08.equals(spedDemonstration.getCode()))
                .mapToDouble(setAndGetEndValue)
                .sum();

        var months = source.getDemonstrativeEndDate().getMonthValue() - source.getDemonstrativeInitDate().getMonthValue();
        var result = months == 0
                ? 0D
                : (sumOne / months) * 12;

        var resultOperational = getOperationalResult.applyAsDouble(source);
        var value = result == 0
                ? 0D
                : resultOperational / result * (-1);

        if (value > best)
            score = scoreMax;
        else if (value <= best && value >= worse)
            score = scoreMax * ((value - worse) / (best - worse));

        return BigDecimal
                .valueOf(score)
                .setScale(1, HALF_UP)
                .doubleValue();
    };

    //Alavancagem Bruta
    ToDoubleBiFunction<Sped, Optional<Object>> getGrossLeverage = (source, config) -> {
        if (config.isEmpty())
            return 0D;

        var sumOne = new AtomicReference<>(0D);
        var sumTwo = new AtomicReference<>(0D);
        var configuration = convert(config.get(), FinancialStrengthConfigurationTO.class);

        final var best = configuration.getBest();
        final var worse = configuration.getWorse();
        final var scoreMax = configuration.getScoreMax();
        var score = 0D;

        source.getSpedBalances().forEach(balance -> {
            if (_2_02_01_01.equals(balance.getCode()) || _2_01_01_07.equals(balance.getCode()))
                sumOne.updateAndGet(v -> v + setAndGetEndValue.applyAsDouble(balance));
            else if (_1.equals(balance.getCode()))
                sumTwo.updateAndGet(v -> setAndGetEndValue.applyAsDouble(balance));
        });

        var value = sumTwo.get() == 0
                ? 0D
                : sumOne.get() / sumTwo.get();

        if (value < best)
            score = scoreMax;
        else if (value >= best && value <= worse)
            score = scoreMax * ((worse - value) / (worse - best));

        return BigDecimal.valueOf(score)
                .setScale(1, HALF_UP)
                .doubleValue();
    };

    //Alavancagem Liquida
    ToDoubleBiFunction<Sped, Optional<Object>> getNetLeverage = (source, config) -> {
        if (config.isEmpty())
            return 0D;

        var sumOne = new AtomicReference<>(0D);
        var configuration = convert(config.get(), FinancialStrengthConfigurationTO.class);

        final var best = configuration.getBest();
        final var worse = configuration.getWorse();
        final var scoreMax = configuration.getScoreMax();
        var score = 0D;

        source.getSpedBalances().forEach(spedBalance -> {
            if (_2_01_01_07.equals(spedBalance.getCode()) ||
                    _2_02_01_01.equals(spedBalance.getCode()))
                sumOne.updateAndGet(v -> v + setAndGetEndValue.applyAsDouble(spedBalance));
            else if (_1_01_01.equals(spedBalance.getCode()))
                sumOne.updateAndGet(v -> v - setAndGetEndValue.applyAsDouble(spedBalance));
        });

        var resultOperational = getOperationalResult.applyAsDouble(source);
        var value = resultOperational == 0
                ? 0D
                : sumOne.get() / resultOperational;

        if (value < best)
            score = scoreMax;
        else if (value >= best && value <= worse)
            score = scoreMax * ((worse - value) / (worse - best));

        return BigDecimal
                .valueOf(score)
                .setScale(1, HALF_UP)
                .doubleValue();
    };

    ToDoubleBiFunction<List<SysConfigurationTO>, Sped> getIndebtednessIndex = (configs, source) -> {
        var grossLeverage = getGrossLeverage.applyAsDouble(source,
                buildFinancialStrengthConfig.apply(configs, "GrossLeverage"));
        var netLeverage = getNetLeverage.applyAsDouble(source,
                buildFinancialStrengthConfig.apply(configs, "NetLeverage"));
        var juroCoverage = getJuroCoverage.applyAsDouble(source,
                buildFinancialStrengthConfig.apply(configs, "JuroCoverage"));
        var debtComposition = getDebtComposition.applyAsDouble(source,
                buildFinancialStrengthConfig.apply(configs, "DebtComposition"));

        var totalValue = grossLeverage + netLeverage + juroCoverage + debtComposition;

        return BigDecimal
                .valueOf(totalValue).setScale(1, HALF_UP).doubleValue();
    };

    // Liquidez Corrente
    ToDoubleBiFunction<Sped, Optional<Object>> getCurrentLiquidity = (source, config) -> {
        if (config.isEmpty())
            return 0D;

        var sumOne = new AtomicReference<>(0D);
        var sumTwo = new AtomicReference<>(0D);
        var configuration = convert(config.get(), FinancialStrengthConfigurationTO.class);

        final var best = configuration.getBest();
        final var worse = configuration.getWorse();
        final var scoreMax = configuration.getScoreMax();
        var score = 0D;

        source.getSpedBalances().forEach(spedBalance -> {
            if (_1_01.equals(spedBalance.getCode()))
                sumOne.updateAndGet(v -> v + setAndGetEndValue.applyAsDouble(spedBalance));
            else if (_2_01.equals(spedBalance.getCode()))
                sumTwo.updateAndGet(v -> v + setAndGetEndValue.applyAsDouble(spedBalance));
        });

        var value = sumTwo.get() == 0
                ? 0D
                : sumOne.get() / sumTwo.get();

        if (value > best)
            score = scoreMax;
        else if (value <= best && value >= worse)
            score = scoreMax * (value - worse) / (best - worse);

        return score;
    };

    // Liquidez Imediata
    ToDoubleBiFunction<Sped, Optional<Object>> getImmediateLiquidity = (source, config) -> {
        if (config.isEmpty())
            return 0D;

        var sumOne = new AtomicReference<>(0D);
        var sumTwo = new AtomicReference<>(0D);
        var configuration = convert(config.get(), FinancialStrengthConfigurationTO.class);

        final var best = configuration.getBest();
        final var worse = configuration.getWorse();
        final var scoreMax = configuration.getScoreMax();
        var score = 0D;

        source.getSpedBalances().forEach(spedBalance -> {
            if (_1_01_01.equals(spedBalance.getCode()))
                sumOne.updateAndGet(v -> v + setAndGetEndValue.applyAsDouble(spedBalance));
            else if (_2_01.equals(spedBalance.getCode()))
                sumTwo.updateAndGet(v -> v + setAndGetEndValue.applyAsDouble(spedBalance));
        });

        var value = sumTwo.get() == 0
                ? 0D
                : sumOne.get() / sumTwo.get();

        if (value > best)
            score = scoreMax;
        else if (value <= best && value >= worse)
            score = scoreMax * ((value - worse) / (best - worse));

        return score;
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
    ToDoubleBiFunction<Sped, Optional<Object>> getGrossMargin = (source, config) -> {
        if (config.isEmpty())
            return 0D;

        var sumOne = new AtomicReference<>(0D);
        var sumTwo = new AtomicReference<>(0D);
        var configuration = convert(config.get(), FinancialStrengthConfigurationTO.class);
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

    // Margem Operacional
    ToDoubleBiFunction<Sped, Optional<Object>> getOperationalMargin = (source, config) -> {
        if (config.isEmpty())
            return 0D;

        var configuration = convert(config.get(), FinancialStrengthConfigurationTO.class);
        final var best = configuration.getBest();
        final var worse = configuration.getWorse();
        final var scoreMax = configuration.getScoreMax();
        var score = 0D;

        var resultOperational = getOperationalResult.applyAsDouble(source);
        var rawScore = getCalculateGrossRevenue.applyAsDouble(source);

        var value = rawScore == 0
                ? 0D
                : resultOperational / rawScore;

        if (value > best)
            score = scoreMax;
        else if (value <= best && value >= worse)
            score = scoreMax * ((value - worse) / (best - worse));

        return BigDecimal
                .valueOf(score)
                .setScale(1, HALF_UP)
                .doubleValue();
    };

    ToDoubleBiFunction<List<SysConfigurationTO>, Sped> getProfitabilityIndex = (sysConfigurations, source) -> {
        var grossMargin = getGrossMargin.applyAsDouble(source,
                buildFinancialStrengthConfig.apply(sysConfigurations, "GrossMargin"));
        var operationalMargin = getOperationalMargin.applyAsDouble(source,
                buildFinancialStrengthConfig.apply(sysConfigurations, "OperationalMargin"));
        var returnOnAssets = getReturnOnAssets.applyAsDouble(source,
                buildFinancialStrengthConfig.apply(sysConfigurations, "ReturnOnAssets"));
        var returnOverPl = getReturnOverPl.applyAsDouble(source,
                buildFinancialStrengthConfig.apply(sysConfigurations, "ReturnOverPl"));

        var totalValue = grossMargin + operationalMargin + returnOnAssets + returnOverPl;

        return BigDecimal
                .valueOf(totalValue)
                .setScale(1, HALF_UP).doubleValue();
    };

    // Custo Operacional
    ToDoubleBiFunction<Sped, Optional<Object>> getOperatingCost = (source, config) -> {
        if (config.isEmpty())
            return 0D;

        var sumOne = new AtomicReference<>(0D);
        var sumNegative = new AtomicReference<>(0D);
        var configuration = convert(config.get(), FinancialStrengthConfigurationTO.class);

        final var best = configuration.getBest();
        final var worse = configuration.getWorse();
        final var scoreMax = configuration.getScoreMax();
        var score = 0D;

        source.getSpedDemonstrations().forEach(spedDemonstration -> {
            if (_3_01_01_05.equals(spedDemonstration.getCode()) ||
                    _3_01_01_07.equals(spedDemonstration.getCode()) ||
                    _3_01_01_09.equals(spedDemonstration.getCode()) ||
                    _3_01_01_11.equals(spedDemonstration.getCode()) ||
                    _3_01_05.equals(spedDemonstration.getCode()) ||
                    _3_11_01_05.equals(spedDemonstration.getCode()) ||
                    _3_11_01_07.equals(spedDemonstration.getCode()) ||
                    _3_11_01_09.equals(spedDemonstration.getCode()) ||
                    _3_11_01_11.equals(spedDemonstration.getCode()) ||
                    _3_11_05.equals(spedDemonstration.getCode()))
                sumOne.updateAndGet(v -> v + setAndGetEndValue.applyAsDouble(spedDemonstration));
            else if (_3_01_01_07_01_23.equals(spedDemonstration.getCode()) ||
                    _3_01_01_07_01_24.equals(spedDemonstration.getCode()) ||
                    _3_01_01_09_01_08.equals(spedDemonstration.getCode()) ||
                    _3_11_01_07_01_23.equals(spedDemonstration.getCode()) ||
                    _3_11_01_07_01_24.equals(spedDemonstration.getCode()) ||
                    _3_11_01_09_01_08.equals(spedDemonstration.getCode()))
                sumNegative.updateAndGet(v -> v - setAndGetEndValue.applyAsDouble(spedDemonstration));
        });

        var months = source.getDemonstrativeEndDate().getMonthValue() - source.getDemonstrativeInitDate().getMonthValue();
        var operationalExpenses = months == 0
                ? 0D
                : ((sumOne.get() + sumNegative.get()) / months) * 12;
        var grossRevenue = getCalculateGrossRevenue.applyAsDouble(source);
        var value = grossRevenue == 0
                ? 0D
                : operationalExpenses / grossRevenue;

        if (value < best)
            score = scoreMax;
        else if (value >= best && value <= worse)
            score = scoreMax * ((worse - value) / (worse - best));

        return score;
    };

    // Custo Financiero
    ToDoubleBiFunction<Sped, Optional<Object>> getFinancialCost = (source, config) -> {
        if (config.isEmpty())
            return 0D;

        var configuration = convert(config.get(), FinancialStrengthConfigurationTO.class);

        final var best = configuration.getBest();
        final var worse = configuration.getWorse();
        final var scoreMax = configuration.getScoreMax();
        var score = 0D;

        var sumOne = source.getSpedDemonstrations()
                .stream()
                .filter(spedDemonstration ->
                        _3_01_01_09_01_08.equals(spedDemonstration.getCode()))
                .mapToDouble(setAndGetEndValue)
                .sum();

        var months = source.getDemonstrativeEndDate().getMonthValue() - source.getDemonstrativeInitDate().getMonthValue();
        var financialExpenses = months == 0
                ? 0D
                : ((-1) * sumOne / months) * 12;
        var grossRevenue = getCalculateGrossRevenue.applyAsDouble(source);

        var value = grossRevenue == 0
                ? 0D
                : financialExpenses / grossRevenue;

        if (value < best)
            score = scoreMax;
        else if (value >= best && value <= worse)
            score = scoreMax * ((worse - value) / (worse - best));

        return score;
    };

    ToDoubleBiFunction<List<SysConfigurationTO>, Sped> getPantryIndex = (sysConfigurations, source) -> {
        var operatingCost = getOperatingCost.applyAsDouble(source,
                buildFinancialStrengthConfig.apply(sysConfigurations, "OperatingCost"));
        var financialCost = getFinancialCost.applyAsDouble(source,
                buildFinancialStrengthConfig.apply(sysConfigurations, "FinancialCost"));

        return operatingCost + financialCost;
    };

    // Ciclo Operacional
    ToDoubleBiFunction<Sped, Optional<Object>> getOperationalCycle = (source, config) -> {
        if (config.isEmpty())
            return 0D;

        var sumOne = new AtomicReference<>(0D);
        var sumTwo = new AtomicReference<>(0D);
        var sumThree = new AtomicReference<>(0D);
        var sumFour = new AtomicReference<>(0D);
        var configuration = convert(config.get(), FinancialStrengthConfigurationTO.class);

        final var best = configuration.getBest();
        final var worse = configuration.getWorse();
        final var scoreMax = configuration.getScoreMax();
        final var numDay = configuration.getNumDay();
        var score = 0D;

        source.getSpedBalances().forEach(spedBalance -> {
            if (_1_01_03.equals(spedBalance.getCode()))
                sumOne.updateAndGet(v -> v + setAndGetEndValue.applyAsDouble(spedBalance));
            else if (_1_01_02_02.equals(spedBalance.getCode()))
                sumThree.updateAndGet(v -> v + setAndGetEndValue.applyAsDouble(spedBalance));
        });

        source.getSpedDemonstrations().forEach(spedDemonstration -> {
            if (_3_01_01_03.equals(spedDemonstration.getCode()) ||
                    _3_11_01_03.equals(spedDemonstration.getCode()))
                sumTwo.updateAndGet(v -> v + setAndGetEndValue.applyAsDouble(spedDemonstration));
            else if (_3_01_01_01_01.equals(spedDemonstration.getCode()) ||
                    _3_11_01_01_01.equals(spedDemonstration.getCode()))
                sumFour.updateAndGet(v -> v + setAndGetEndValue.applyAsDouble(spedDemonstration));
        });

        var ime = sumTwo.get() == 0
                ? 0D
                : (sumOne.get() / (-1) * sumTwo.get()) * numDay;

        var pmr = sumFour.get() == 0
                ? 0D
                : (sumThree.get() / sumFour.get()) * numDay;

        var value = ime + pmr;
        if (value < best)
            score = scoreMax;
        else if (value >= best && value <= worse)
            score = scoreMax * ((worse - value) / (worse - best));

        return score;
    };

    // Ciclo Financeiro
    ToDoubleBiFunction<Sped, Optional<Object>> getFinancialCycle = (source, config) -> {
        if (config.isEmpty())
            return 0D;

        var sumOne = new AtomicReference<>(0D);
        var sumTwo = new AtomicReference<>(0D);
        var sumThree = new AtomicReference<>(0D);
        var sumFour = new AtomicReference<>(0D);
        var sumFive = new AtomicReference<>(0D);
        var configuration = convert(config.get(), FinancialStrengthConfigurationTO.class);

        final var best = configuration.getBest();
        final var worse = configuration.getWorse();
        final var scoreMax = configuration.getScoreMax();
        final var numDay = configuration.getNumDay();
        var score = 0D;

        source.getSpedBalances().forEach(spedBalance -> {
            if (_1_01_03.equals(spedBalance.getCode()))
                sumOne.updateAndGet(v -> v + setAndGetEndValue.applyAsDouble(spedBalance));
            else if (_1_01_02_02.equals(spedBalance.getCode()))
                sumThree.updateAndGet(v -> v + setAndGetEndValue.applyAsDouble(spedBalance));
            else if (_2_01_01_03.equals(spedBalance.getCode()) ||
                    _2_01_01_05.equals(spedBalance.getCode()))
                sumFive.updateAndGet(v -> v + setAndGetEndValue.applyAsDouble(spedBalance));
        });

        source.getSpedDemonstrations().forEach(spedDemonstration -> {
            if (_3_01_01_03.equals(spedDemonstration.getCode()) ||
                    _3_11_01_03.equals(spedDemonstration.getCode()))
                sumTwo.updateAndGet(v -> v + setAndGetEndValue.applyAsDouble(spedDemonstration));
            else if (_3_01_01_01_01.equals(spedDemonstration.getCode()) ||
                    _3_11_01_01_01.equals(spedDemonstration.getCode()))
                sumFour.updateAndGet(v -> v + setAndGetEndValue.applyAsDouble(spedDemonstration));
        });

        var ime = sumTwo.get() == 0
                ? 0D
                : (sumOne.get() / (-1) * sumTwo.get()) * numDay;

        var pmr = sumFour.get() == 0
                ? 0D
                : (sumThree.get() / sumFour.get()) * numDay;

        var pmp = sumFour.get() == 0
                ? 0D
                : (sumFive.get() / -(sumFour.get() + sumOne.get())) * numDay;

        var value = ime + pmr - pmp;

        if (value < best)
            score = scoreMax;
        else if (value >= best && value <= worse)
            score = scoreMax * ((worse - value) / (worse - best));

        return score;
    };

    // Longevidade da empresa
    ToDoubleBiFunction<LocalDateTime, Optional<Object>> getLongevity = (source, config) -> {
        if (config.isEmpty())
            return 0D;

        var configuration = convert(config.get(), FinancialStrengthConfigurationTO.class);
        final var best = configuration.getBest();
        final var worse = configuration.getWorse();
        final var scoreMax = configuration.getScoreMax();
        var diff = between(source.toLocalDate(), LocalDateTime.now().toLocalDate()).getYears();
        var val = scoreMax * (diff - worse);

        return diff > best
                ? scoreMax
                : val / (best - worse);
    };

    //Rentabilidade de Funcionario
    ToDoubleTriFunction<Integer, Sped, Optional<Object>> getProfitabilityPerEmployee =
            (quantityEmployee, source, optional) -> {
                if (optional.isEmpty())
                    return 0D;

                var config = convert(optional.get(), FinancialStrengthConfigurationTO.class);
                final var best = config.getBest();
                final var worse = config.getWorse();
                final var scoreMax = config.getScoreMax();
                var score = 0D;

                var netProfit = getNetProfitMonth.applyAsDouble(source);
                var value = quantityEmployee == 0
                        ? 0D
                        : netProfit / quantityEmployee;

                if (value > best)
                    score = scoreMax;
                else if (value <= best && value >= worse)
                    score = scoreMax * ((value - worse) / (best - worse));

                return score;
            };

    ToDoubleFourFunction<Integer, LocalDateTime, Sped, List<SysConfigurationTO>> getSizeIndex =
            (quantityEmployee, date, sped, sysConfigurations) -> {
                var profitabilityPerEmployee = getProfitabilityPerEmployee.applyAsDouble(quantityEmployee,
                        sped, buildFinancialStrengthConfig.apply(sysConfigurations, "ProfitabilityPerEmployee"));
                var longevity = getLongevity.applyAsDouble(date,
                        buildFinancialStrengthConfig.apply(sysConfigurations, "Longevity"));

                var totalValue = profitabilityPerEmployee + longevity;

                return BigDecimal
                        .valueOf(totalValue)
                        .setScale(1, HALF_UP)
                        .doubleValue();
            };

    ToDoubleBiFunction<List<SysConfigurationTO>, Sped> getActivityIndex = (sysConfigurations, source) -> {
        var operationalCycle = getOperationalCycle.applyAsDouble(source,
                buildFinancialStrengthConfig.apply(sysConfigurations, "OperationalCycle"));
        var financialCycle = getFinancialCycle.applyAsDouble(source,
                buildFinancialStrengthConfig.apply(sysConfigurations, "FinancialCycle"));

        return operationalCycle + financialCycle;
    };

    ToDoubleBiFunction<List<SysConfigurationTO>, Sped> getLiquidityIndex = (sysConfigurations, source) -> {
        var currentLiquidity = getCurrentLiquidity.applyAsDouble(source,
                buildFinancialStrengthConfig.apply(sysConfigurations, "CurrentLiquidity"));
        var immediateLiquidity = getImmediateLiquidity.applyAsDouble(source,
                buildFinancialStrengthConfig.apply(sysConfigurations, "ImmediateLiquidity"));

        var totalValue = currentLiquidity + immediateLiquidity;

        return BigDecimal
                .valueOf(totalValue)
                .setScale(1, HALF_UP)
                .doubleValue();
    };

    BiFunction<String, Double, ScoreOperation> buildOperation = (name, value) ->
            new ScoreOperation()
                    .withDescription(name)
                    .withValue(value);

}