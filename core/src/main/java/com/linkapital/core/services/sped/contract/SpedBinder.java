package com.linkapital.core.services.sped.contract;

import com.linkapital.core.services.company_user.contract.to.CompanySpedTO;
import com.linkapital.core.services.sped.contract.to.AnalysisTO;
import com.linkapital.core.services.sped.contract.to.BalanceTO;
import com.linkapital.core.services.sped.contract.to.CashConversionTO;
import com.linkapital.core.services.sped.contract.to.DupontTO;
import com.linkapital.core.services.sped.contract.to.FinancialAnalysisTO;
import com.linkapital.core.services.sped.contract.to.HorizontalAnalysisTO;
import com.linkapital.core.services.sped.contract.to.OperationalMarginTO;
import com.linkapital.core.services.sped.contract.to.SpedBalanceTO;
import com.linkapital.core.services.sped.contract.to.SpedDemonstrationTO;
import com.linkapital.core.services.sped.contract.to.SpedTO;
import com.linkapital.core.services.sped.contract.to.VerticalAnalysisTO;
import com.linkapital.core.services.sped.datasource.domain.Sped;
import com.linkapital.core.services.sped.datasource.domain.SpedBalance;
import com.linkapital.core.services.sped.datasource.domain.SpedDemonstration;
import com.linkapital.core.services.sped.datasource.domain.SpedPattern;
import com.linkapital.core.services.sped.datasource.domain.SpedUtil;
import com.linkapital.core.services.sped.impl.SpedServiceImpl;
import com.linkapital.core.util.functions.TriConsumer;
import com.linkapital.core.util.functions.TriFunction;
import org.apache.commons.lang3.SerializationUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import static com.linkapital.core.services.sped.contract.SpedCodeEndValueBinder.getEndValue;
import static com.linkapital.core.services.sped.contract.SpedCodeEndValueBinder.getEndValueModified;
import static com.linkapital.core.services.sped.contract.SpedCodeEndValueBinder.getLiquidReceipt;
import static com.linkapital.core.services.sped.contract.SpedCodeEndValueBinder.setAndGetEndValue;
import static com.linkapital.core.services.sped.contract.SpedCodeEndValueBinder.setEndValue;
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
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_01;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_01_01;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_01_01_01;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_01_01_01_01;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_01_01_01_02;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_01_01_03;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_01_01_05;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_01_01_07;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_01_01_09;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_01_01_11;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_01_05;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_01_05_01;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_02_01;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_02_01_01;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_11_01;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_11_01_01;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_11_01_01_01;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_11_01_01_02;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_11_01_03;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_11_01_05;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_11_01_07;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_11_01_09;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_11_01_11;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_11_05;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_11_05_01;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_12_01_01;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode.codes;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode.codesForDemonstrations;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static java.math.RoundingMode.HALF_UP;
import static java.time.LocalDateTime.now;
import static java.time.Period.between;
import static java.util.Collections.emptyList;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static org.springframework.util.StringUtils.hasText;

@Mapper
public interface SpedBinder {

    SpedBinder SPED_BINDER = Mappers.getMapper(SpedBinder.class);
    Logger log = LoggerFactory.getLogger(SpedServiceImpl.class);
    String DATE_FORMAT_EXCEL = "dd/MM/yyyy";
    String K0000 = "0000";
    String K0010 = "0010";
    String P030 = "P030";
    String P100 = "P100";
    String P150 = "P150";
    String L030 = "L030";
    String L100 = "L100";
    String L300 = "L300";
    BigDecimal decimal = BigDecimal.valueOf(100);

    UnaryOperator<Sped> cloneSped = source -> new Sped()
            .withId(source.getId())
            .withDemonstrativeInitDate(source.getDemonstrativeInitDate())
            .withDemonstrativeEndDate(source.getDemonstrativeEndDate())
            .withSpedBalances(source.getSpedBalances()
                    .stream()
                    .map(SerializationUtils::clone)
                    .collect(toList()))
            .withSpedDemonstrations(source.getSpedDemonstrations()
                    .stream()
                    .map(SerializationUtils::clone)
                    .collect(toList()));

    TriConsumer<List<String>, List<String>, List<? extends SpedPattern>> updateListCodeAndParents =
            (codes, parents, list) -> {
                codes.clear();
                parents.clear();

                list.forEach(to -> {
                    if (!codes.contains(to.getCode()))
                        codes.add(to.getCode());

                    if (!parents.contains(to.getCodeSynthetic()))
                        parents.add(to.getCodeSynthetic());
                });
            };

    UnaryOperator<List<SpedBalance>> getBalances = source -> {
        var list = source
                .stream()
                .filter(spedBalance -> {
                    if (spedBalance.getCodeLevel() <= 4 && spedBalance.getEndValue() > 0
                            && spedBalance.getEndDate().getMonthValue() == 12 && hasText(spedBalance.getCode())) {
                        setAndGetEndValue.applyAsDouble(spedBalance);

                        return true;
                    }

                    return false;
                })
                .collect(toList());

        var codeList = new ArrayList<String>();
        var parentList = new ArrayList<String>();

        updateListCodeAndParents.accept(codeList, parentList, list);

        list.addAll(source
                .stream()
                .filter(to -> to.getEndValue() > 0 && codes.contains(to.getCode()) && !codeList.contains(to.getCode()))
                .toList());

        updateListCodeAndParents.accept(codeList, parentList, list);

        list.addAll(source
                .stream()
                .filter(to -> parentList.contains(to.getCode()) && !codeList.contains(to.getCode()))
                .toList());

        return list
                .stream()
                .sorted(comparing(SpedPattern::getCode))
                .toList();
    };

    Function<List<String[]>, List<SpedDemonstration>> bindToSpedDemonstration = source -> {
        var spedDemonstrations = new ArrayList<SpedDemonstration>();
        var mapped = source
                .stream()
                .map(parsed -> new SpedDemonstration()
                        .withCode(parsed[2])
                        .withCodeDescription(parsed[3])
                        .withCodeType(parsed[4])
                        .withCodeLevel(hasText(parsed[5])
                                ? parseInt(parsed[5])
                                : 0)
                        .withCodeNature(parsed[6])
                        .withCodeSynthetic(parsed[7])
                        .withEndValue(hasText(parsed[8])
                                ? parseDouble(parsed[8].replace(",", "."))
                                : 0)
                        .withEndValueSituation(parsed[9])
                        .withCreated(now())
                        .withModified(now()))
                .collect(groupingBy(SpedPattern::getCode));

        for (Map.Entry<String, List<SpedDemonstration>> e : mapped.entrySet()) {
            var value = BigDecimal
                    .valueOf(e.getValue()
                            .stream()
                            .mapToDouble(setAndGetEndValue)
                            .sum())
                    .setScale(3, HALF_UP)
                    .doubleValue();
            var demonstration = e.getValue().get(0);

            if (value >= 0) {
                demonstration.setEndValue(value);
                demonstration.setEndValueSituation("C");
            } else {
                demonstration.setEndValue(value * -1);
                demonstration.setEndValueSituation("D");
            }

            spedDemonstrations.add(demonstration);
        }

        return spedDemonstrations
                .stream()
                .sorted(comparing(SpedPattern::getCode))
                .collect(toList());
    };

    Function<List<SpedUtil>, List<SpedBalance>> bindToSpedBalance = source -> source
            .stream()
            .flatMap(parsed -> parsed.getBalances()
                    .stream()
                    .map(array -> new SpedBalance()
                            .withInitDate(parsed.getInitDate())
                            .withEndDate(parsed.getEndDate())
                            .withInitValue(hasText(array[8])
                                    ? parseDouble(array[8].replace(",", "."))
                                    : 0)
                            .withInitValueSituation(array[9])
                            .withDebitValue(hasText(array[10])
                                    ? parseDouble(array[10].replace(",", "."))
                                    : 0)
                            .withCreditValue(hasText(array[11])
                                    ? parseDouble(array[11].replace(",", "."))
                                    : 0)
                            .withCode(array[2])
                            .withCodeDescription(array[3])
                            .withCodeType(array[4])
                            .withCodeLevel(hasText(array[5])
                                    ? parseInt(array[5])
                                    : 0)
                            .withCodeNature(array[6])
                            .withCodeSynthetic(array[7])
                            .withEndValue(hasText(array[12])
                                    ? parseDouble(array[12].replace(",", "."))
                                    : 0)
                            .withEndValueSituation(array[13])
                            .withCreated(now())
                            .withModified(now())))
            .collect(toList());

    BiFunction<SpedDemonstration, List<SpedDemonstration>, SpedDemonstration> toAnalysisFormat =
            (source, demonstrations) -> {
                var endValue = setEndValue.applyAsDouble(source.getEndValue(), source.getEndValueSituation());
                switch (source.getCode()) {
                    case _3_01_01_01_01 -> {
                        source.setEndValue(endValue + getEndValueModified.applyAsDouble(demonstrations,
                                _3_11_01_01_01));
                        source.setCodeSynthetic("");
                    }
                    case _3_01_01_01_02 -> {
                        source.setEndValue(endValue + getEndValueModified.applyAsDouble(demonstrations,
                                _3_11_01_01_02));
                        source.setCodeSynthetic("");
                    }
                    case _3_01_01_01 -> {
                        source.setEndValue(endValue + getEndValueModified.applyAsDouble(demonstrations,
                                _3_11_01_01));
                        source.setCodeSynthetic(_3_01_01_01_02);
                    }
                    case _3_01_01_03 -> {
                        source.setEndValue(endValue + getEndValueModified.applyAsDouble(demonstrations,
                                _3_11_01_03));
                        source.setCodeSynthetic(_3_01_01_01_02);
                    }
                    case _3_01_01_05 -> {
                        source.setEndValue(endValue + getEndValueModified.applyAsDouble(demonstrations,
                                _3_11_01_05));
                        source.setCodeSynthetic(_3_01_01_01_02);
                    }
                    case _3_01_01_07 -> {
                        source.setEndValue(endValue + getEndValueModified.applyAsDouble(demonstrations,
                                _3_11_01_07));
                        source.setCodeSynthetic(_3_01_01_01_02);
                    }
                    case _3_01_01_09 -> {
                        source.setEndValue(endValue + getEndValueModified.applyAsDouble(demonstrations,
                                _3_11_01_09));
                        source.setCodeSynthetic(_3_01_01_01_02);
                    }
                    case _3_01_01_11 -> {
                        source.setEndValue(endValue + getEndValueModified.applyAsDouble(demonstrations,
                                _3_11_01_11));
                        source.setCodeSynthetic(_3_01_01_01_02);
                    }
                    case _3_01_05_01 -> {
                        source.setEndValue(endValue + getEndValueModified.applyAsDouble(demonstrations,
                                _3_11_05_01));
                        source.setCodeSynthetic(_3_01_01_01_02);
                    }
                    case _3_01_01 -> {
                        source.setEndValue(endValue + getEndValueModified.applyAsDouble(demonstrations, _3_01_05)
                                + getEndValueModified.applyAsDouble(demonstrations, _3_11_01) +
                                getEndValueModified.applyAsDouble(demonstrations, _3_11_05));
                        source.setCodeSynthetic(_3_01_05_01);
                    }
                    case _3_02_01_01 -> {
                        source.setEndValue(endValue + getEndValueModified.applyAsDouble(demonstrations,
                                _3_12_01_01));
                        source.setCodeSynthetic(_3_01_01_01_02);
                    }
                    case _3 -> source.setCodeSynthetic(_3_02_01_01);
                    default -> {
                    }
                }

                return source;
            };

    UnaryOperator<List<SpedDemonstration>> getDemonstrations = source -> {
        var list = new ArrayList<SpedDemonstration>();
        codesForDemonstrations.forEach(code -> source
                .stream()
                .filter(spedDemonstration -> code.equals(spedDemonstration.getCode()))
                .findFirst()
                .ifPresent(spedDemonstration -> list.add(toAnalysisFormat.apply(spedDemonstration, source))));

        return list;
    };

    Function<Sped, List<VerticalAnalysisTO>> buildVerticalAnalysis = source -> {
        var demonstrations = getDemonstrations.apply(source.getSpedDemonstrations());
        var value = getEndValueModified.applyAsDouble(demonstrations, _3_01_01_01);

        return demonstrations
                .stream()
                .map(sped -> {
                    var endValue = setEndValue.applyAsDouble(sped.getEndValue(), sped.getEndValueSituation());
                    var result = value == 0
                            ? 0
                            : (endValue * 100) / value;

                    return SPED_BINDER.bindToVerticalAnalysisTO(sped)
                            .withPercent(BigDecimal
                                    .valueOf(result)
                                    .setScale(2, HALF_UP)
                                    .doubleValue() + " %");
                })
                .toList();
    };

    BiFunction<String, List<Sped>, CompanySpedTO> bindToCompanySped = (cnpj, source) -> new CompanySpedTO()
            .withCnpj(cnpj)
            .withSpeds(source
                    .stream()
                    .map(sped -> {
                        var clone = cloneSped.apply(sped);
                        var balances = getBalances.apply(clone.getSpedBalances());
                        var demonstrations = getDemonstrations.apply(clone.getSpedDemonstrations());

                        return new SpedTO()
                                .withDemonstrativeInitDate(clone.getDemonstrativeInitDate())
                                .withDemonstrativeEndDate(clone.getDemonstrativeEndDate())
                                .withSpedBalances(SPED_BINDER.bindToSpedBalanceTO(balances))
                                .withSpedDemonstrations(SPED_BINDER.bindToSpedDemonstrationTO(demonstrations));
                    })
                    .sorted(comparing(SpedTO::getDemonstrativeEndDate))
                    .toList());

    Function<Sped, DupontTO> getDupontAnalysis = source -> {
        //Margem Líquida = Lucro Líquido / Receita Líquida em %
        //Giro do ativo = Receita Líquida / Ativo em %
        //ROE = Lucro Líquido / Patrimônio Líquido em %
        var balances = new ArrayList<>(source.getSpedBalances());
        var demonstrations = new ArrayList<>(source.getSpedDemonstrations());

        var active = BigDecimal.valueOf(getEndValue.applyAsDouble(balances, _1));
        var liquidEquity = BigDecimal.valueOf(getEndValue.applyAsDouble(balances, _2_03));
        var liquidProfit = BigDecimal.valueOf(getEndValue.applyAsDouble(demonstrations, _3));
        var liquidReceipt = getLiquidReceipt.apply(demonstrations);

        var mlResult = liquidReceipt.doubleValue() == 0
                ? 0D
                : liquidProfit
                        .divide(liquidReceipt, 3, HALF_UP)
                        .multiply(decimal)
                        .setScale(2, HALF_UP)
                        .doubleValue();

        var gaResult = active.doubleValue() == 0
                ? 0D
                : liquidReceipt
                        .divide(active, 3, HALF_UP)
                        .doubleValue();

        var leverageResult = liquidEquity.doubleValue() == 0
                ? 0D
                : active
                        .divide(liquidEquity, 3, HALF_UP)
                        .doubleValue();

        var roeResult = liquidEquity.doubleValue() == 0
                ? 0D
                : liquidProfit
                        .divide(liquidEquity, 3, HALF_UP)
                        .multiply(decimal)
                        .setScale(2, HALF_UP)
                        .doubleValue();

        return new DupontTO()
                .withLiquidProfit(liquidProfit.doubleValue())
                .withLiquidReceipt(liquidReceipt.doubleValue())
                .withLiquidEquity(liquidEquity.doubleValue())
                .withActive(active.doubleValue())
                .withMlResult(mlResult)
                .withGaResult(gaResult)
                .withLeverageResult(leverageResult)
                .withRoeResult(roeResult)
                .init();
    };

    Function<Sped, OperationalMarginTO> getOperationMarginAnalysis = source -> {
        //Margem Líquida = Lucro Líquido / Receita Líquida em %
        var demonstrations = new ArrayList<>(source.getSpedDemonstrations());

        var d1 = BigDecimal.valueOf(getEndValue.applyAsDouble(demonstrations, _3_01_01));
        var d2 = BigDecimal.valueOf(getEndValue.applyAsDouble(demonstrations, _3_01_05));
        var d3 = BigDecimal.valueOf(getEndValue.applyAsDouble(demonstrations, _3_11_01));
        var d4 = BigDecimal.valueOf(getEndValue.applyAsDouble(demonstrations, _3_11_05));

        var operationalResult = d1.add(d2).add(d3).add(d4)
                .setScale(3, HALF_UP);

        var liquidReceipt = getLiquidReceipt.apply(demonstrations);

        var moResult = liquidReceipt.doubleValue() == 0
                ? 0D
                : operationalResult
                        .divide(liquidReceipt, 3, HALF_UP)
                        .multiply(decimal)
                        .setScale(2, HALF_UP)
                        .doubleValue();

        return new OperationalMarginTO()
                .withMoResult(moResult)
                .withOperationalResult(operationalResult.doubleValue())
                .withLiquidReceipt(liquidReceipt.doubleValue())
                .init();
    };

    Function<Sped, CashConversionTO> getCashConversionAnalysis = source -> {
        //IME = Estoque / CMV * Número de Dias (em dias)
        //PMR = Contas a Receber / Receita Bruta * Número de Dias (em dias)
        //PMP = Contas a Pagar / Compras * Número de Dias (em dias)
        //CCC = IME + PMR - PMP (em dias)
        var balances = new ArrayList<>(source.getSpedBalances());
        var demonstrations = new ArrayList<>(source.getSpedDemonstrations());

        var b1 = BigDecimal.valueOf(getEndValue.applyAsDouble(balances, _2_01_01_03));
        var b2 = BigDecimal.valueOf(getEndValue.applyAsDouble(balances, _2_01_01_05));

        var d1 = BigDecimal.valueOf(getEndValue.applyAsDouble(demonstrations, _3_01_01_03));
        var d2 = BigDecimal.valueOf(getEndValue.applyAsDouble(demonstrations, _3_01_01_01_01));
        var d3 = BigDecimal.valueOf(getEndValue.applyAsDouble(demonstrations, _3_11_01_03));
        var d4 = BigDecimal.valueOf(getEndValue.applyAsDouble(demonstrations, _3_11_01_01_01));

        var stock = BigDecimal.valueOf(getEndValue.applyAsDouble(balances, _1_01_03));
        var billsToReceive = BigDecimal.valueOf(getEndValue.applyAsDouble(balances, _1_01_02_02));

        var days = BigDecimal
                .valueOf(between(source.getDemonstrativeInitDate(), source.getDemonstrativeEndDate()).getDays());

        var cmv = d1.add(d3)
                .setScale(3, HALF_UP);

        var grossReceipt = d2.add(d4)
                .setScale(3, HALF_UP);

        var accountsPayable = b1.add(b2)
                .setScale(3, HALF_UP);

        var purchases = cmv.subtract(stock)
                .setScale(3, HALF_UP);

        var imeResult = cmv.doubleValue() == 0
                ? 0
                : stock
                        .divide(cmv, 3, HALF_UP)
                        .multiply(days)
                        .intValue();

        var pmrResult = grossReceipt.doubleValue() == 0
                ? 0
                : billsToReceive
                        .divide(grossReceipt, 3, HALF_UP)
                        .multiply(days)
                        .intValue();

        var pmpResult = purchases.doubleValue() == 0
                ? 0
                : accountsPayable
                        .divide(purchases, 3, HALF_UP)
                        .multiply(days)
                        .intValue();

        var cccResult = imeResult + pmrResult - pmpResult;

        return new CashConversionTO()
                .withImeResult(imeResult)
                .withPmrResult(pmrResult)
                .withPmpResult(pmpResult)
                .withCccResult(cccResult)
                .withStock(stock.doubleValue())
                .withCmv(cmv.doubleValue())
                .withBillsToReceive(billsToReceive.doubleValue())
                .withGrossReceipt(grossReceipt.doubleValue())
                .withAccountsPayable(accountsPayable.doubleValue())
                .withPurchases(purchases.doubleValue())
                .init();
    };

    BiFunction<Sped, Double, BalanceTO> getBalanceAnalysis = (source, volume) -> {
        //CCL = Ativo Circulante - Passivo Circulante em $
        //DL = Dívidas de Curto Prazo + Dívidas de Longo Prazo - Disponibilidades em $
        //DLNO = Dívida Líquida + Valor da Operação em $
        //DLRO = Dívida Líquida / Resultado Operacional em %
        //DLNORO = Dívida Líquida Após Nova Operação / Resultado Operacional em %
        var balances = new ArrayList<>(source.getSpedBalances());
        var demonstrations = new ArrayList<>(source.getSpedDemonstrations());

        var currentAssets = BigDecimal.valueOf(getEndValue.applyAsDouble(balances, _1_01));
        var currentLiabilities = BigDecimal.valueOf(getEndValue.applyAsDouble(balances, _2_01));
        var shortTermDebt = BigDecimal.valueOf(getEndValue.applyAsDouble(balances, _2_01_01_07));
        var longTermDebt = BigDecimal.valueOf(getEndValue.applyAsDouble(balances, _2_02_01_01));
        var availabilities = BigDecimal.valueOf(getEndValue.applyAsDouble(balances, _1_01_01));

        var d1 = BigDecimal.valueOf(getEndValue.applyAsDouble(demonstrations, _3_01_01));
        var d2 = BigDecimal.valueOf(getEndValue.applyAsDouble(demonstrations, _3_01_05));
        var d3 = BigDecimal.valueOf(getEndValue.applyAsDouble(demonstrations, _3_11_01));
        var d4 = BigDecimal.valueOf(getEndValue.applyAsDouble(demonstrations, _3_11_05));

        var cclResult = currentAssets
                .subtract(currentLiabilities)
                .setScale(3, HALF_UP);

        var dlResult = shortTermDebt
                .add(longTermDebt)
                .subtract(availabilities)
                .setScale(3, HALF_UP);

        var dlNoResult = dlResult
                .add(BigDecimal.valueOf(volume))
                .setScale(3, HALF_UP);

        var operationalResult = d1.add(d2).add(d3).add(d4)
                .setScale(3, HALF_UP);

        var dlRoResult = 0D;
        var dlRoNoResult = 0D;

        if (operationalResult.doubleValue() != 0) {
            dlRoResult = dlResult
                    .divide(operationalResult, 3, HALF_UP)
                    .multiply(decimal)
                    .setScale(2, HALF_UP)
                    .doubleValue();

            dlRoNoResult = dlNoResult
                    .divide(operationalResult, 3, HALF_UP)
                    .multiply(decimal)
                    .setScale(2, HALF_UP)
                    .doubleValue();
        }

        return new BalanceTO()
                .withCclResult(cclResult.doubleValue())
                .withDlResult(dlResult.doubleValue())
                .withDlNoResult(dlNoResult.doubleValue())
                .withDlRoResult(dlRoResult)
                .withDlRoNoResult(dlRoNoResult)
                .withCurrentAssets(currentAssets.doubleValue())
                .withCurrentLiabilities(currentLiabilities.doubleValue())
                .withShortTermDebt(shortTermDebt.doubleValue())
                .withLongTermDebt(longTermDebt.doubleValue())
                .withAvailabilities(availabilities.doubleValue())
                .withOperationValue(volume)
                .withOperationalResult(operationalResult.doubleValue())
                .init();
    };

    BiFunction<Sped, Double, FinancialAnalysisTO> buildFinancialAnalysis = (sped, volume) -> new FinancialAnalysisTO()
            .withDupont(getDupontAnalysis.apply(sped))
            .withCashConversion(getCashConversionAnalysis.apply(sped))
            .withOperationalMargin(getOperationMarginAnalysis.apply(sped))
            .withBalance(getBalanceAnalysis.apply(sped, volume));

    BiFunction<Double, List<Sped>, List<AnalysisTO>> getFinancialAndVerticalAnalysis = (volume, source) -> source
            .stream()
            .map(sped -> new AnalysisTO()
                    .withDate(sped.getDemonstrativeEndDate())
                    .withFinancialAnalysis(buildFinancialAnalysis.apply(sped, volume))
                    .withVerticalAnalysis(buildVerticalAnalysis.apply(sped)))
            .sorted(comparing(AnalysisTO::getDate))
            .toList();

    BiConsumer<SpedDemonstration, List<SpedDemonstration>> toSpedFormat = (source, demonstration) -> {
        switch (source.getCode()) {
            case _3_01_01_01_01, _3_01_01_01_02 -> source.setCodeSynthetic(_3_01_01_01);
            case _3_01_01_01, _3_01_01_03, _3_01_01_05, _3_01_01_07, _3_01_01_09, _3_01_01_11 -> source.setCodeSynthetic(_3_01_01);
            case _3_01_05_01 -> source.setCodeSynthetic(_3_01_05);
            case _3_01_01 -> source.setCodeSynthetic(_3_01);
            case _3_02_01_01 -> source.setCodeSynthetic(_3_02_01);
            case _3 -> source.setCodeSynthetic("");
            default -> {
            }
        }
    };

    TriFunction<AnalysisTO, AnalysisTO, AnalysisTO, List<HorizontalAnalysisTO>> buildHorizontalAnalysis =
            (currentPeriodAnalysis, previousPeriodAnalysis, lastPeriodAnalysis) -> {
                var currentPeriod = currentPeriodAnalysis.getVerticalAnalysis();
                var previousPeriod = previousPeriodAnalysis.getVerticalAnalysis();
                var horizontalAnalysis = new ArrayList<HorizontalAnalysisTO>();
                var currentPeriodMonth = currentPeriodAnalysis.getDate().getMonthValue();
                var previousPeriodMonth = previousPeriodAnalysis.getDate().getMonthValue();
                var lastPeriodMonth = 0;
                List<VerticalAnalysisTO> lastPeriod = null;

                if (lastPeriodAnalysis != null) {
                    lastPeriodMonth = lastPeriodAnalysis.getDate().getMonthValue();
                    lastPeriod = lastPeriodAnalysis.getVerticalAnalysis();
                }

                var pos = 0;
                while (pos < currentPeriod.size()) {
                    var currentPeriodDRE = currentPeriod.get(pos);

                    var currentPeriodValue = BigDecimal
                            .valueOf(currentPeriodDRE.getEndValue() / currentPeriodMonth)
                            .setScale(2, HALF_UP)
                            .doubleValue();

                    var previousPeriodValue = previousPeriod.isEmpty()
                            ? 0
                            : BigDecimal
                                    .valueOf(previousPeriod.get(pos).getEndValue() / previousPeriodMonth)
                                    .setScale(2, HALF_UP)
                                    .doubleValue();

                    var percentCurrentPeriod = previousPeriodValue == 0
                            ? "0 %"
                            : BigDecimal
                                    .valueOf(((currentPeriodValue / previousPeriodValue) - 1) * 100)
                                    .setScale(2, HALF_UP)
                                    .doubleValue() + " %";

                    var percentPreviousPeriod = "N/A";
                    if (lastPeriod != null && !lastPeriod.isEmpty() && lastPeriodMonth != 0) {
                        var lastPeriodValue = BigDecimal
                                .valueOf(lastPeriod.get(pos).getEndValue() / lastPeriodMonth)
                                .setScale(2, HALF_UP)
                                .doubleValue();

                        percentPreviousPeriod = lastPeriodValue == 0
                                ? "0 %"
                                : BigDecimal
                                        .valueOf(((previousPeriodValue / lastPeriodValue) - 1) * 100)
                                        .setScale(2, HALF_UP)
                                        .doubleValue() + " %";
                    }

                    horizontalAnalysis.add(SPED_BINDER.bindToHorizontalAnalysisTO(currentPeriodDRE)
                            .withPercentCurrentPeriod(percentCurrentPeriod)
                            .withPercentPreviousPeriod(percentPreviousPeriod));
                    pos++;
                }

                return horizontalAnalysis;
            };

    Function<List<AnalysisTO>, List<HorizontalAnalysisTO>> getHorizontalAnalysis = source -> {
        if (source.size() < 2)
            return emptyList();

        return source.size() == 2
                ? buildHorizontalAnalysis.apply(source.get(1), source.get(0), null)
                : buildHorizontalAnalysis.apply(source.get(2), source.get(1), source.get(0));
    };

    @Mapping(target = "percent", ignore = true)
    VerticalAnalysisTO bindToVerticalAnalysisTO(SpedPattern source);

    @Mapping(target = "percentCurrentPeriod", ignore = true)
    @Mapping(target = "percentPreviousPeriod", ignore = true)
    HorizontalAnalysisTO bindToHorizontalAnalysisTO(VerticalAnalysisTO source);

    Sped bind(SpedTO source);

    List<Sped> bind(List<SpedTO> source);

    List<SpedBalanceTO> bindToSpedBalanceTO(List<SpedBalance> source);

    List<SpedDemonstrationTO> bindToSpedDemonstrationTO(List<SpedDemonstration> source);

}
