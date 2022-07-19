package com.linkapital.core.services.indicative_offer.contract;

import com.linkapital.core.services.company.datasource.domain.Company;
import com.linkapital.core.services.company.datasource.domain.EmployeeGrowth;
import com.linkapital.core.services.company_user.datasource.domain.CompanyUser;
import com.linkapital.core.services.configuration.contract.to.LearningOfferConfigurationTO;
import com.linkapital.core.services.configuration.contract.to.SysConfigurationTO;
import com.linkapital.core.services.indicative_offer.contract.enums.IndicativeOfferState;
import com.linkapital.core.services.indicative_offer.contract.to.IndicativeOfferDeadLinesTO;
import com.linkapital.core.services.indicative_offer.contract.to.IndicativeOfferTaxTO;
import com.linkapital.core.services.indicative_offer.contract.to.IndicativeOfferTaxYearTO;
import com.linkapital.core.services.indicative_offer.contract.to.IndicativeVolumeTO;
import com.linkapital.core.services.indicative_offer.contract.to.get.IndicativeOfferFourTO;
import com.linkapital.core.services.indicative_offer.contract.to.get.IndicativeOfferOneTO;
import com.linkapital.core.services.indicative_offer.contract.to.get.IndicativeOfferTO;
import com.linkapital.core.services.indicative_offer.contract.to.get.IndicativeOfferThreeTO;
import com.linkapital.core.services.indicative_offer.contract.to.get.IndicativeOfferTwoTO;
import com.linkapital.core.services.indicative_offer.contract.to.update.UpdateIndicativeOfferFourTO;
import com.linkapital.core.services.indicative_offer.contract.to.update.UpdateIndicativeOfferOneTO;
import com.linkapital.core.services.indicative_offer.contract.to.update.UpdateIndicativeOfferTO;
import com.linkapital.core.services.indicative_offer.contract.to.update.UpdateIndicativeOfferThreeTO;
import com.linkapital.core.services.indicative_offer.contract.to.update.UpdateIndicativeOfferTwoTO;
import com.linkapital.core.services.indicative_offer.datasource.domain.IndicativeOffer;
import com.linkapital.core.services.indicative_offer.datasource.domain.IndicativeOfferFour;
import com.linkapital.core.services.indicative_offer.datasource.domain.IndicativeOfferOne;
import com.linkapital.core.services.indicative_offer.datasource.domain.IndicativeOfferThree;
import com.linkapital.core.services.indicative_offer.datasource.domain.IndicativeOfferTwo;
import com.linkapital.core.services.invoice.datasource.domain.Invoice;
import com.linkapital.core.services.property_guarantee.datasource.domain.PropertyGuarantee;
import com.linkapital.core.services.sped.datasource.domain.Sped;
import com.linkapital.core.util.functions.TriFunction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.function.ToDoubleBiFunction;
import java.util.function.ToDoubleFunction;
import java.util.function.UnaryOperator;

import static com.linkapital.core.services.company.contract.enums.ActivityLevel.HALF;
import static com.linkapital.core.services.indicative_offer.contract.enums.IndicativeOfferState.WITH_OFFER;
import static com.linkapital.core.services.offer.contract.OfferBinder.OFFER_BINDER;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_01_01_01_01;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_01_01_05;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_11_01_01_01;
import static com.linkapital.core.services.sped.datasource.domain.SpedCode._3_11_01_05;
import static com.linkapital.core.util.json.JsonSerdes.convert;
import static java.lang.Boolean.TRUE;
import static java.math.RoundingMode.HALF_UP;
import static java.time.Period.between;
import static org.slf4j.LoggerFactory.getLogger;

@Mapper
public interface IndicativeOfferBinder {

    IndicativeOfferBinder LEARNING_OFFER_BINDER = Mappers.getMapper(IndicativeOfferBinder.class);
    Logger log = getLogger(IndicativeOfferBinder.class);

    DoubleBinaryOperator calculateMinDeadline = (volume, min) -> {
        var pF = (1 + min / 100);
        var value = volume * (min / (1 - (Math.pow(pF, -240)))) / 100;

        return BigDecimal
                .valueOf(value)
                .setScale(2, HALF_UP)
                .doubleValue();
    };

    DoubleBinaryOperator calculateMaxDeadline = (volume, max) -> {
        var pF = (1 + max / 100);
        var value = volume * (max / (1 - (Math.pow(pF, -240)))) / 100;

        return BigDecimal
                .valueOf(value)
                .setScale(2, HALF_UP)
                .doubleValue();
    };

    BiPredicate<IndicativeOffer, IndicativeOfferState> validateState = (indicativeOffer, state) ->
            indicativeOffer != null && indicativeOffer.getState().equals(state);

    DoubleUnaryOperator minTax = ma -> BigDecimal
            .valueOf((1.0075 * (1 + ma / 100) - 1) * 100)
            .setScale(1, HALF_UP)
            .doubleValue();

    DoubleUnaryOperator maxTax = ma -> BigDecimal
            .valueOf((1.015 * (1 + ma / 100) - 1) * 100)
            .setScale(1, HALF_UP)
            .doubleValue();

    /*Compute min and max tax by year*/
    DoubleUnaryOperator minMaxTaxYear = minTaxMonth -> BigDecimal
            .valueOf((Math.pow(1 + minTaxMonth / 100, 12) - 1) * 100)
            .setScale(1, HALF_UP)
            .doubleValue();

    ToDoubleBiFunction<Integer, Double> deadlineConsidered = (deadlineReceived, issuanceNote) ->
            BigDecimal
                    .valueOf(deadlineReceived - issuanceNote)
                    .setScale(2, HALF_UP)
                    .doubleValue();

    ToDoubleFunction<Set<PropertyGuarantee>> getValueOfPropertiesInGuarantee = source -> source
            .stream()
            .mapToDouble(PropertyGuarantee::getEvaluationValue)
            .reduce(0, Double::sum);

    ToDoubleBiFunction<Double, Double> getVolume = (availableCreditVolume, socialCapital) -> {
        var value = availableCreditVolume > socialCapital && socialCapital > 0
                ? socialCapital
                : availableCreditVolume;

        return BigDecimal
                .valueOf(Math.min(value, 100000D))
                .setScale(2, HALF_UP)
                .doubleValue();
    };

    ToDoubleBiFunction<Set<PropertyGuarantee>, LearningOfferConfigurationTO> getVolumeForOfferTwo =
            (properties, configuration) -> {
                if (properties.isEmpty())
                    return 0D;

                var guarantee = getValueOfPropertiesInGuarantee.applyAsDouble(properties);

                if (guarantee < configuration.getGuaranteeMinValue())
                    return 0D;
                else if (guarantee >= configuration.getGuaranteeMinValue() ||
                        guarantee <= configuration.getGuaranteeMaxValue())
                    return (guarantee / 100) * configuration.getPercentVolume();

                return 3000000D;
            };

    BiFunction<Set<PropertyGuarantee>, Map<String, Object>, IndicativeOffer> bindOfferTwo = (properties, config) -> {
        var configuration = convert(config, LearningOfferConfigurationTO.class);
        var ma = configuration.getIpCa() / 12;

        var volume = getVolumeForOfferTwo.applyAsDouble(properties, configuration);
        var minOfferTax = minTax.applyAsDouble(ma);
        var maxOfferTax = maxTax.applyAsDouble(ma);
        var averageTax = BigDecimal
                .valueOf((maxOfferTax + minOfferTax) / 2)
                .setScale(1, HALF_UP)
                .doubleValue();

        return new IndicativeOfferTwo()
                .withMinOfferTax(minOfferTax)
                .withMaxOfferTax(maxOfferTax)
                .withMinOfferDeadLine(calculateMinDeadline.applyAsDouble(volume, minOfferTax))
                .withMaxOfferDeadLine(calculateMaxDeadline.applyAsDouble(volume, maxOfferTax))
                .withIndicativeOfferState(WITH_OFFER)
                .withVolume(volume)
                .withTax(averageTax)
                .withDeadline(configuration.getDeadline());
    };

    BiFunction<List<SysConfigurationTO>, CompanyUser, CompanyUser> setOfferTwo = (sysConfigurations, companyUser) ->
            sysConfigurations
                    .stream()
                    .filter(configuration -> configuration.getName().equals("LearningOffer"))
                    .map(SysConfigurationTO::getConfiguration)
                    .map(configuration -> bindOfferTwo.apply(companyUser.getPropertyGuarantees(), (Map) configuration))
                    .findFirst()
                    .map(offer -> {
                        companyUser.setIndicativeOfferTwo(offer);
                        return companyUser;
                    })
                    .orElse(companyUser);

    ToDoubleFunction<List<Sped>> calculateAvgAnnualInvoicing = source -> {
        var sum301010101 = new AtomicReference<>(0D);
        var sum311010101 = new AtomicReference<>(0D);
        var sum3010105 = new AtomicReference<>(0D);
        var sum3110105 = new AtomicReference<>(0D);

        source.forEach(sped -> {
            if (sped.getDemonstrativeEndDate().getMonthValue() == 12) {
                sped.getSpedDemonstrations()
                        .forEach(spedDemonstration -> {
                            if (_3_01_01_01_01.equals(spedDemonstration.getCode()))
                                sum301010101.updateAndGet(v -> v + spedDemonstration.getEndValue());

                            else if (_3_11_01_01_01.equals(spedDemonstration.getCode()))
                                sum311010101.updateAndGet(v -> v + spedDemonstration.getEndValue());

                            else if (_3_01_01_05.equals(spedDemonstration.getCode()))
                                sum3010105.updateAndGet(v -> v + spedDemonstration.getEndValue());

                            else if (_3_11_01_05.equals(spedDemonstration.getCode()))
                                sum3110105.updateAndGet(v -> v + spedDemonstration.getEndValue());
                        });
            }
        });

        var sum1 = sum301010101.get() + sum311010101.get();
        var sum2 = sum3010105.get() + sum3110105.get();

        return sum1 != 0
                ? sum1
                : sum2;
    };

    TriFunction<Set<Invoice>, Double, Integer, Double> getVolumeForOfferThree =
            (invoices, minMaxTaxMonth, deadlineReceived) -> {
                var res = 0D;
                for (Invoice invoice : invoices) {
                    var issuance = (double) (new Date().getTime() - invoice.getIssuanceDate().getTime()) / 86400000;

                    if (issuance <= deadlineReceived)
                        res += invoice.getTotal() / Math.pow(1 + minMaxTaxMonth / 100,
                                (deadlineConsidered.applyAsDouble(deadlineReceived, issuance) / 365));
                }

                return BigDecimal
                        .valueOf(Math.min(res, 100000D))
                        .setScale(2, HALF_UP)
                        .doubleValue();
            };

    BiFunction<CompanyUser, Map<String, Object>, CompanyUser> bindOfferThree = (source, config) -> {
        var configuration = convert(config, LearningOfferConfigurationTO.class);
        var minTaxMonth = minMaxTaxYear.applyAsDouble(configuration.getMinTax());
        var maxTaxMonth = minMaxTaxYear.applyAsDouble(configuration.getMaxTax());
        var deadlineReceived = source.getAvgReceiptTermInvoices();
        var volumeMin = getVolumeForOfferThree.apply(source.getIssuedInvoices(), maxTaxMonth, deadlineReceived);
        var volumeMax = getVolumeForOfferThree.apply(source.getIssuedInvoices(), minTaxMonth, deadlineReceived);
        var volumeOne = source.getIndicativeOfferOne() == null
                ? 0D
                : source.getIndicativeOfferOne().getVolume();
        volumeMax = Math.max(volumeMax, volumeOne);

        ((IndicativeOfferThree) source.getIndicativeOfferThree())
                .withMinOfferTax(configuration.getMinTax())
                .withMaxOfferTax(configuration.getMaxTax())
                .withMinOfferTaxYear(minTaxMonth)
                .withMaxOfferTaxYear(maxTaxMonth)
                .withVolumeMin(volumeMin)
                .withVolumeMax(volumeMax)
                .withVolume(volumeMax)
                .withTax(configuration.getTax())
                .withDeadline(configuration.getDeadline())
                .withIndicativeOfferState(WITH_OFFER);

        return source;
    };

    BiFunction<List<SysConfigurationTO>, CompanyUser, CompanyUser> setOfferThree = (sysConfigurations, companyUser) ->
            sysConfigurations
                    .stream()
                    .filter(config -> config.getName().equals("LearningOffer3"))
                    .map(SysConfigurationTO::getConfiguration)
                    .map(config -> bindOfferThree.apply(companyUser, (Map) config))
                    .findFirst()
                    .orElse(companyUser);


    ToDoubleBiFunction<Integer, Collection<EmployeeGrowth>> calculateGrowEmployee = (years, source) -> {
        var growEmployee = 0D;
        var currentYear = new AtomicInteger(Calendar.getInstance().get(Calendar.YEAR) - 1);
        var count = new AtomicInteger(0);

        while (count.get() < years) {
            growEmployee += source
                    .stream()
                    .filter(employeesGrowth -> employeesGrowth.getYear() == currentYear.get())
                    .mapToDouble(EmployeeGrowth::getGrowth)
                    .sum();
            currentYear.getAndDecrement();
            count.getAndIncrement();
        }

        return growEmployee;
    };

    ToDoubleBiFunction<Double, Company> decreaseTaxByGrowEmployee = (base, source) -> {
        var growEmployee = source.getEmployeeGrowths();
        var last = calculateGrowEmployee.applyAsDouble(1, growEmployee);
        var last3 = calculateGrowEmployee.applyAsDouble(3, growEmployee);

        if (last > 15)
            base -= 0.2;

        if (last3 > 7)
            base -= 0.3;

        return BigDecimal
                .valueOf(base)
                .setScale(1, HALF_UP)
                .doubleValue();
    };

    TriFunction<Boolean, Double, Company, Double> calculateBaseTax = (offerOne, tax, source) -> {
        var dataOpeningDate = source.getMainInfo().getOpeningDate();
        var diff = between(dataOpeningDate.toLocalDate(), LocalDateTime.now().toLocalDate()).getYears();
        if (diff >= 20)
            tax -= TRUE.equals(offerOne)
                    ? 0.5
                    : 0.6;
        else if (diff >= 10)
            tax -= 0.4;
        else if (diff >= 5)
            tax -= 0.2;

        if (source.getActivityLevel().equals(HALF))
            tax -= 0.2;

        if (source.getTaxHealth() != null && !source.getTaxHealth().getCnds().isEmpty())
            tax -= 0.5;

        return decreaseTaxByGrowEmployee.applyAsDouble(tax, source);
    };

    UnaryOperator<CompanyUser> setOfferOne = source -> {
        var company = source.getCompany();
        var mainCnae = company.getMainCnae();

        if (mainCnae == null || mainCnae.getSector() == null)
            return source;

        var companySector = mainCnae.getSector();
        var presumedProfit = 0.20;
        var creditOfferFactor = 1D;
        var deadline = "12 a 24";

        switch (companySector) {
            case INDUSTRY -> {
                presumedProfit = 0.12;
                creditOfferFactor = 0.75;
                deadline = "9 a 24";
            }
            case COMMERCE -> {
                presumedProfit = 0.8;
                creditOfferFactor = 1.5;
                deadline = "6 a 18";
            }
            default -> {
            }
        }

        var annualInvoicingInformed = source.getInvoicingInformed() * 12;
        var avgAnnualInvoicingInformed = Double.sum(annualInvoicingInformed, company.getGrossBilling()) / 2;
        var annualPresumedProfit = avgAnnualInvoicingInformed * presumedProfit;
        var availableCreditVolume = annualPresumedProfit * creditOfferFactor;
        var volume = getVolume.applyAsDouble(availableCreditVolume, company.getSocialCapital());
        var baseTax = calculateBaseTax.apply(true, 3.5, company);

        source.getIndicativeOfferOne()
                .withTax(baseTax)
                .withDeadline(deadline)
                .withVolume(volume)
                .withIndicativeOfferState(WITH_OFFER);

        return source;
    };

    UnaryOperator<CompanyUser> setLearningOfferFour = source -> {
        var company = source.getCompany();
        var mainCnae = company.getMainCnae();

        if (mainCnae == null || mainCnae.getSector() == null)
            return source;

        var companySector = mainCnae.getSector();
        var presumedProfit = 0.32;
        var deadline = "12 a 30";
        var creditOfferFactor = 1D;

        switch (companySector) {
            case INDUSTRY -> {
                presumedProfit = 0.12;
                deadline = "6 a 24";
            }
            case COMMERCE -> {
                presumedProfit = 0.8;
                creditOfferFactor = 2D;
                deadline = "9 a 30";
            }
            default -> {
            }
        }

        var annualPresumedProfit = calculateAvgAnnualInvoicing.applyAsDouble(source.getSpeds()) * presumedProfit;
        var availableCreditVolume = annualPresumedProfit * creditOfferFactor;
        var volume = getVolume.applyAsDouble(availableCreditVolume, company.getSocialCapital());
        var baseTax = calculateBaseTax.apply(false, 2.8, company);

        source.getIndicativeOfferFour()
                .withTax(baseTax)
                .withDeadline(deadline)
                .withVolume(volume)
                .withIndicativeOfferState(WITH_OFFER);

        return source;
    };

    Function<IndicativeOfferTwo, IndicativeOfferTwoTO> buildTwo = source ->
            (IndicativeOfferTwoTO) new IndicativeOfferTwoTO()
                    .withIndicativeOfferTax(new IndicativeOfferTaxTO()
                            .withMaxOfferTax(source.getMaxOfferTax())
                            .withMinOfferTax(source.getMinOfferTax()))
                    .withOfferDeadLines(new IndicativeOfferDeadLinesTO()
                            .withMinOfferDeadLine(source.getMinOfferDeadLine())
                            .withMaxOfferDeadLine(source.getMaxOfferDeadLine()))
                    .withId(source.getId())
                    .withDeadline(source.getDeadline())
                    .withState(source.getState())
                    .withPrecision(source.getPrecision())
                    .withTax(source.getTax())
                    .withVolume(source.getVolume())
                    .withType(2)
                    .withOffers(OFFER_BINDER.bind(source.getOffers()));

    Function<IndicativeOfferThree, IndicativeOfferThreeTO> buildThree = source ->
            (IndicativeOfferThreeTO) new IndicativeOfferThreeTO()
                    .withIndicativeOfferTax(new IndicativeOfferTaxTO()
                            .withMinOfferTax(source.getMinOfferTax())
                            .withMaxOfferTax(source.getMaxOfferTax()))
                    .withIndicativeOfferTaxYear(new IndicativeOfferTaxYearTO()
                            .withMinOfferTaxYear(source.getMinOfferTaxYear())
                            .withMaxOfferTaxYear(source.getMaxOfferTaxYear()))
                    .withIndicativeVolume(new IndicativeVolumeTO()
                            .withVolumeMin(source.getVolumeMin())
                            .withVolumeMax(source.getVolumeMax()))
                    .withId(source.getId())
                    .withTax(source.getTax())
                    .withState(source.getState())
                    .withDeadline(source.getDeadline())
                    .withPrecision(source.getPrecision())
                    .withVolume(source.getVolume())
                    .withType(3)
                    .withOffers(OFFER_BINDER.bind(source.getOffers()));

    Function<IndicativeOfferFour, IndicativeOfferFourTO> buildFour = source ->
            (IndicativeOfferFourTO) new IndicativeOfferFourTO()
                    .withTax(source.getTax())
                    .withDeadline(source.getDeadline())
                    .withPrecision(source.getPrecision())
                    .withVolume(source.getVolume())
                    .withId(source.getId())
                    .withState(source.getState())
                    .withType(4)
                    .withOffers(OFFER_BINDER.bind(source.getOffers()));

    BiFunction<IndicativeOffer, UpdateIndicativeOfferTO, IndicativeOffer> set = (target, request) -> {
        if (request instanceof UpdateIndicativeOfferOneTO source) {
            return (IndicativeOfferOne) target
                    .withTax(source.getTax())
                    .withVolume(source.getVolume())
                    .withDeadline(source.getDeadline());
        } else if (request instanceof UpdateIndicativeOfferTwoTO source) {
            var result = (IndicativeOfferTwo) target;
            var offerTax = source.getIndicativeOfferTax();

            return result
                    .withMinOfferTax(offerTax.getMinOfferTax())
                    .withMaxOfferTax(offerTax.getMaxOfferTax())
                    .withVolume(source.getVolume())
                    .withDeadline(source.getDeadline())
                    .withTax(source.getTax());
        } else if (request instanceof UpdateIndicativeOfferThreeTO source) {
            return target
                    .withTax(source.getTax())
                    .withVolume(source.getVolume())
                    .withDeadline(source.getDeadline());
        } else if (request instanceof UpdateIndicativeOfferFourTO source) {
            var result = (IndicativeOfferFour) target;

            return result
                    .withTax(source.getTax())
                    .withVolume(source.getVolume())
                    .withDeadline(source.getDeadline());
        }

        return target;
    };

    @Mapping(target = "offers", ignore = true)
    IndicativeOfferOneTO bindOfferOneTO(IndicativeOfferOne source);

    default IndicativeOfferTO bind(IndicativeOffer learningOffer) {
        if (learningOffer == null)
            return null;
        else if (learningOffer instanceof IndicativeOfferOne offer)
            return buildOne(offer);
        else if (learningOffer instanceof IndicativeOfferTwo offer)
            return buildTwo.apply(offer);
        else if (learningOffer instanceof IndicativeOfferThree offer)
            return buildThree.apply(offer);

        return buildFour.apply((IndicativeOfferFour) learningOffer);
    }

    default IndicativeOfferOneTO buildOne(IndicativeOfferOne source) {
        var target = bindOfferOneTO(source);

        return (IndicativeOfferOneTO) target
                .withTax(source.getTax())
                .withDeadline(source.getDeadline())
                .withPrecision(source.getPrecision())
                .withVolume(source.getVolume())
                .withId(source.getId())
                .withState(source.getState())
                .withType(1)
                .withOffers(OFFER_BINDER.bind(source.getOffers()));
    }

}
