package com.linkapital.core.services.sped.contract;

import com.linkapital.core.services.sped.datasource.domain.Sped;
import com.linkapital.core.services.sped.datasource.domain.SpedBalance;
import com.linkapital.core.services.sped.datasource.domain.SpedDemonstration;
import com.linkapital.core.services.sped.datasource.domain.SpedPattern;
import org.mapstruct.Mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.ToDoubleBiFunction;
import java.util.function.ToDoubleFunction;

import static com.linkapital.core.services.sped.datasource.domain.SpedCode._1;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._1_01;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._1_01_01;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._1_02_01;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._1_02_02;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._1_02_03;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._1_02_05;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._1_02_06;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._2_01;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._2_01_01_07;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._2_02;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._2_02_01_01;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._2_03;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_01_01_01;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_01_01_01_01;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_01_01_01_02;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_01_01_03;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_01_01_05;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_01_01_07;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_01_01_07_01_23;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_01_01_07_01_24;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_01_01_09;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_01_01_09_01_08;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_01_01_11;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_01_05;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_02;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_11_01_01;
import static java.math.RoundingMode.HALF_UP;

@Mapper
public interface SpedCodeEndValueBinder {

    ToDoubleFunction<Sped> getMonth = sped ->
            sped.getDemonstrativeEndDate().getMonthValue() - sped.getDemonstrativeInitDate().getMonthValue();

    ToDoubleBiFunction<List<? extends SpedPattern>, String> getEndValue = (source, code) -> source
            .stream()
            .filter(sped -> code.equals(sped.getCode()))
            .findFirst()
            .flatMap(spedPattern -> Optional.of(spedPattern.getEndValue()))
            .orElse(0D);

    ToDoubleBiFunction<Double, String> setEndValue = (value, situation) -> "C".equals(situation)
            ? value
            : value * -1;

    ToDoubleFunction<SpedPattern> setAndGetEndValue = source -> {
        var value = Double.valueOf(source.getEndValue());
        var situation = source.getEndValueSituation();

        if (source.getCode().startsWith("1"))
            return "C".equals(situation)
                    ? value * -1
                    : value;

        return setEndValue.applyAsDouble(value, situation);
    };

    ToDoubleFunction<List<SpedBalance>> getTotalActive = source -> source
            .stream()
            .filter(spedBalance ->
                    _1.equals(spedBalance.getCode()))
            .mapToDouble(setAndGetEndValue)
            .sum();

    ToDoubleFunction<List<SpedDemonstration>> getInvoicing = source -> source
            .stream()
            .filter(spedDemonstration -> _3_01_01_01_01.equals(spedDemonstration.getCode()) ||
                    _3_01_01_01_02.equals(spedDemonstration.getCode()))
            .mapToDouble(setAndGetEndValue)
            .sum();

    ToDoubleFunction<List<Sped>> getInvoiceCompanyByMonth = source -> {
        if (source.isEmpty())
            return 0D;

        var sped = source.get(source.size() - 1);
        var invoicing = getInvoicing.applyAsDouble(sped.getSpedDemonstrations());

        return invoicing / 12;
    };

    ToDoubleFunction<List<SpedDemonstration>> getFinancialExpenses = source -> source
            .stream()
            .filter(spedDemonstration -> _3_01_01_09_01_08.equals(spedDemonstration.getCode()))
            .mapToDouble(setAndGetEndValue)
            .sum();

    ToDoubleFunction<List<SpedDemonstration>> getTax = source -> source
            .stream()
            .filter(spedDemonstration -> _3_02.equals(spedDemonstration.getCode()))
            .mapToDouble(setAndGetEndValue)
            .sum();

    ToDoubleFunction<List<SpedDemonstration>> getCMV = source -> source
            .stream()
            .filter(spedBalance -> _3_01_01_03.equals(spedBalance.getCode()))
            .mapToDouble(setAndGetEndValue)
            .sum();

    ToDoubleFunction<List<SpedDemonstration>> getOperationPantry = source -> source
            .stream()
            .filter(spedBalance ->
                    _3_01_01_05.equals(spedBalance.getCode()) ||
                            _3_01_01_07.equals(spedBalance.getCode()) ||
                            _3_01_01_09.equals(spedBalance.getCode()) ||
                            _3_01_01_11.equals(spedBalance.getCode()) ||
                            _3_01_05.equals(spedBalance.getCode()))
            .mapToDouble(setAndGetEndValue)
            .sum();

    ToDoubleFunction<List<SpedDemonstration>> getEBITDA = source -> {
        var netInvoicing = getInvoicing.applyAsDouble(source);
        var cmv = getCMV.applyAsDouble(source);
        var operationPantry = getOperationPantry.applyAsDouble(source);

        return netInvoicing + cmv + operationPantry;
    };

    ToDoubleFunction<Sped> getPercentEBITDA = source -> {
        var month = getMonth.applyAsDouble(source);
        if (month < 12)
            return 0D;

        var spedDemonstrations = source.getSpedDemonstrations();
        var ebitda = getEBITDA.applyAsDouble(spedDemonstrations);
        var invoicing = getInvoicing.applyAsDouble(spedDemonstrations);

        return invoicing == 0
                ? 0D
                : ebitda / invoicing;
    };

    ToDoubleFunction<List<SpedBalance>> getPl = source -> source
            .stream()
            .filter(spedBalance -> _2_03.equals(spedBalance.getCode()))
            .mapToDouble(setAndGetEndValue)
            .sum();
    ToDoubleFunction<Sped> getNetProfitMonth = source -> {
        var pl = getPl.applyAsDouble(source.getSpedBalances());
        var months = source.getDemonstrativeEndDate().getMonthValue() - source.getDemonstrativeInitDate().getMonthValue();

        return months == 0
                ? 0D
                : pl * 12 / (double) (months);
    };
    ToDoubleFunction<List<SpedDemonstration>> getPantryAm = source -> source
            .stream()
            .filter(spedDemonstration ->
                    _3_01_01_07_01_23.equals(spedDemonstration.getCode()) ||
                            _3_01_01_07_01_24.equals(spedDemonstration.getCode()))
            .mapToDouble(setAndGetEndValue)
            .sum();
    ToDoubleFunction<List<SpedDemonstration>> getOperatingProfit = source -> {
        var ebitda = getEBITDA.applyAsDouble(source);
        var pantryAm = getPantryAm.applyAsDouble(source);

        return ebitda + pantryAm;
    };
    ToDoubleFunction<List<SpedDemonstration>> getNetProfit = source -> {
        var operatingProfit = getOperatingProfit.applyAsDouble(source);
        var financialExpenses = getFinancialExpenses.applyAsDouble(source);
        var tax = getTax.applyAsDouble(source);

        return operatingProfit + financialExpenses + tax;
    };
    ToDoubleFunction<Sped> getPercentNetProfit = source -> {
        var month = getMonth.applyAsDouble(source);
        if (month < 12)
            return 0D;

        var spedDemonstrations = source.getSpedDemonstrations();
        var netProfit = getNetProfit.applyAsDouble(spedDemonstrations);
        var invoicing = getInvoicing.applyAsDouble(spedDemonstrations);

        return invoicing == 0
                ? 0D
                : netProfit / invoicing;
    };

    ToDoubleFunction<List<SpedBalance>> getNetDebt = source -> source
            .stream()
            .filter(spedBalance ->
                    _1_01_01.equals(spedBalance.getCode()) ||
                            _2_01_01_07.equals(spedBalance.getCode()) ||
                            _2_02_01_01.equals(spedBalance.getCode()))
            .mapToDouble(setAndGetEndValue)
            .sum();

    ToDoubleFunction<Sped> getNetPantryEBITDA = source -> {
        var month = getMonth.applyAsDouble(source);
        if (month < 12)
            return 0D;

        var netPantry = getNetDebt.applyAsDouble(source.getSpedBalances());
        var ebitda = getEBITDA.applyAsDouble(source.getSpedDemonstrations());

        return ebitda == 0
                ? 0D
                : netPantry / ebitda;
    };

    ToDoubleFunction<List<SpedBalance>> getAvailable = source -> source
            .stream()
            .filter(spedBalance -> _1_01_01.equals(spedBalance.getCode()))
            .mapToDouble(setAndGetEndValue)
            .sum();

    ToDoubleFunction<List<SpedBalance>> getCurrentActive = source -> source
            .stream()
            .filter(spedBalance -> _1_01.equals(spedBalance.getCode()))
            .mapToDouble(setAndGetEndValue)
            .sum();

    ToDoubleFunction<List<SpedBalance>> getNotCurrentActive = source -> source
            .stream()
            .filter(spedBalance -> _1_02_01.equals(spedBalance.getCode()))
            .mapToDouble(setAndGetEndValue)
            .sum();

    ToDoubleFunction<List<SpedBalance>> getCurrentPassive = source -> source
            .stream()
            .filter(spedBalance -> _2_01.equals(spedBalance.getCode()))
            .mapToDouble(setAndGetEndValue)
            .sum();

    ToDoubleFunction<List<SpedBalance>> getCCL = source -> {
        var available = getAvailable.applyAsDouble(source);
        var activeCurrent = getCurrentActive.applyAsDouble(source);
        var passiveCurrent = getCurrentPassive.applyAsDouble(source);

        return available + activeCurrent - passiveCurrent;
    };

    ToDoubleFunction<List<SpedBalance>> getNotCurrentPassive = source -> source
            .stream()
            .filter(spedBalance -> _2_02.equals(spedBalance.getCode()))
            .mapToDouble(setAndGetEndValue)
            .sum();
    ToDoubleFunction<List<SpedBalance>> getPassive = source -> {
        var current = getCurrentPassive.applyAsDouble(source);
        var notcurrent = getNotCurrentPassive.applyAsDouble(source);
        var pl = getPl.applyAsDouble(source);

        return current + notcurrent + pl;
    };
    ToDoubleFunction<List<SpedBalance>> getPermanentActive = source -> source
            .stream()
            .filter(spedBalance ->
                    _1_02_02.equals(spedBalance.getCode()) ||
                            _1_02_03.equals(spedBalance.getCode()) ||
                            _1_02_05.equals(spedBalance.getCode()) ||
                            _1_02_06.equals(spedBalance.getCode()))
            .mapToDouble(setAndGetEndValue)
            .sum();
    ToDoubleFunction<List<SpedBalance>> getActive = source -> {
        var available = getAvailable.applyAsDouble(source);
        var currentActive = getCurrentActive.applyAsDouble(source);
        var notCurrentActive = getNotCurrentActive.applyAsDouble(source);
        var permanent = getPermanentActive.applyAsDouble(source);

        return available + currentActive + notCurrentActive + permanent;
    };
    Function<List<SpedDemonstration>, BigDecimal> getLiquidReceipt = source -> {
        var d5 = BigDecimal.valueOf(getEndValue.applyAsDouble(source, _3_01_01_01));
        var d6 = BigDecimal.valueOf(getEndValue.applyAsDouble(source, _3_11_01_01));

        return d5.add(d6)
                .setScale(3, HALF_UP);
    };

    ToDoubleBiFunction<List<? extends SpedPattern>, String> getEndValueModified = (source, code) -> source
            .stream()
            .filter(sped -> code.equals(sped.getCode()))
            .findFirst()
            .flatMap(sped -> Optional.of(setEndValue.applyAsDouble(sped.getEndValue(), sped.getEndValueSituation())))
            .orElse(0D);

}
