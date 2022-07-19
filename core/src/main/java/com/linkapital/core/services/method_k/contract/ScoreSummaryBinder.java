package com.linkapital.core.services.method_k.contract;

import com.linkapital.core.services.company_user.datasource.domain.CompanyUser;
import com.linkapital.core.services.credit_information.datasource.domain.CreditInformation;
import com.linkapital.core.services.method_k.contract.enums.KeyDescriptionSped;
import com.linkapital.core.services.method_k.contract.to.ScoreAnalysisTO;
import com.linkapital.core.services.method_k.contract.to.ScoreSummaryTO;
import com.linkapital.core.services.method_k.contract.to.SpedBaseTO;
import com.linkapital.core.services.method_k.datasource.domain.ScoreAnalysis;
import com.linkapital.core.services.sped.datasource.domain.Sped;
import com.linkapital.core.util.functions.TriFunction;
import lombok.NonNull;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import static com.linkapital.core.services.company.contract.CnaeBinder.CNAE_BINDER;
import static com.linkapital.core.services.credit_information.contract.CreditInformationBinder.CREDIT_INFORMATION_BINDER;
import static com.linkapital.core.services.method_k.contract.enums.KeyDescriptionSped.ACTIVE;
import static com.linkapital.core.services.method_k.contract.enums.KeyDescriptionSped.AVAILABLE_ACTIVE;
import static com.linkapital.core.services.method_k.contract.enums.KeyDescriptionSped.CCL;
import static com.linkapital.core.services.method_k.contract.enums.KeyDescriptionSped.CMV;
import static com.linkapital.core.services.method_k.contract.enums.KeyDescriptionSped.CURRENT_ACTIVE;
import static com.linkapital.core.services.method_k.contract.enums.KeyDescriptionSped.CURRENT_PASSIVE;
import static com.linkapital.core.services.method_k.contract.enums.KeyDescriptionSped.DL_EBITDA;
import static com.linkapital.core.services.method_k.contract.enums.KeyDescriptionSped.EBITDA;
import static com.linkapital.core.services.method_k.contract.enums.KeyDescriptionSped.FINANCIAL_EXPENSES;
import static com.linkapital.core.services.method_k.contract.enums.KeyDescriptionSped.INVOICING;
import static com.linkapital.core.services.method_k.contract.enums.KeyDescriptionSped.NET_DEBT;
import static com.linkapital.core.services.method_k.contract.enums.KeyDescriptionSped.NET_PROFIT;
import static com.linkapital.core.services.method_k.contract.enums.KeyDescriptionSped.NOT_CURRENT_ACTIVE;
import static com.linkapital.core.services.method_k.contract.enums.KeyDescriptionSped.NOT_CURRENT_PASSIVE;
import static com.linkapital.core.services.method_k.contract.enums.KeyDescriptionSped.OPERATIONAL_PANTRY;
import static com.linkapital.core.services.method_k.contract.enums.KeyDescriptionSped.OPERATIONAL_PROFIT;
import static com.linkapital.core.services.method_k.contract.enums.KeyDescriptionSped.PANTRY_AM;
import static com.linkapital.core.services.method_k.contract.enums.KeyDescriptionSped.PASSIVE;
import static com.linkapital.core.services.method_k.contract.enums.KeyDescriptionSped.PERCENT_EBITDA;
import static com.linkapital.core.services.method_k.contract.enums.KeyDescriptionSped.PERCENT_NET_PROFIT;
import static com.linkapital.core.services.method_k.contract.enums.KeyDescriptionSped.PERMANENT_ACTIVE;
import static com.linkapital.core.services.method_k.contract.enums.KeyDescriptionSped.PL;
import static com.linkapital.core.services.method_k.contract.enums.KeyDescriptionSped.TAX;
import static com.linkapital.core.services.sped.contract.SpedCodeEndValueBinder.getActive;
import static com.linkapital.core.services.sped.contract.SpedCodeEndValueBinder.getAvailable;
import static com.linkapital.core.services.sped.contract.SpedCodeEndValueBinder.getCCL;
import static com.linkapital.core.services.sped.contract.SpedCodeEndValueBinder.getCMV;
import static com.linkapital.core.services.sped.contract.SpedCodeEndValueBinder.getCurrentActive;
import static com.linkapital.core.services.sped.contract.SpedCodeEndValueBinder.getCurrentPassive;
import static com.linkapital.core.services.sped.contract.SpedCodeEndValueBinder.getEBITDA;
import static com.linkapital.core.services.sped.contract.SpedCodeEndValueBinder.getFinancialExpenses;
import static com.linkapital.core.services.sped.contract.SpedCodeEndValueBinder.getInvoicing;
import static com.linkapital.core.services.sped.contract.SpedCodeEndValueBinder.getNetDebt;
import static com.linkapital.core.services.sped.contract.SpedCodeEndValueBinder.getNetPantryEBITDA;
import static com.linkapital.core.services.sped.contract.SpedCodeEndValueBinder.getNetProfitMonth;
import static com.linkapital.core.services.sped.contract.SpedCodeEndValueBinder.getNotCurrentActive;
import static com.linkapital.core.services.sped.contract.SpedCodeEndValueBinder.getNotCurrentPassive;
import static com.linkapital.core.services.sped.contract.SpedCodeEndValueBinder.getOperatingProfit;
import static com.linkapital.core.services.sped.contract.SpedCodeEndValueBinder.getOperationPantry;
import static com.linkapital.core.services.sped.contract.SpedCodeEndValueBinder.getPantryAm;
import static com.linkapital.core.services.sped.contract.SpedCodeEndValueBinder.getPassive;
import static com.linkapital.core.services.sped.contract.SpedCodeEndValueBinder.getPercentEBITDA;
import static com.linkapital.core.services.sped.contract.SpedCodeEndValueBinder.getPercentNetProfit;
import static com.linkapital.core.services.sped.contract.SpedCodeEndValueBinder.getPermanentActive;
import static com.linkapital.core.services.sped.contract.SpedCodeEndValueBinder.getPl;
import static com.linkapital.core.services.sped.contract.SpedCodeEndValueBinder.getTax;
import static java.math.RoundingMode.HALF_UP;
import static java.util.Comparator.comparing;
import static org.springframework.util.StringUtils.hasText;

@Mapper
public interface ScoreSummaryBinder {

    ScoreSummaryBinder SCORE_SUMMARY_BINDER = Mappers.getMapper(ScoreSummaryBinder.class);

    TriFunction<KeyDescriptionSped, Double, Integer, SpedBaseTO> buildSpedBase = (key, value, year) ->
            new SpedBaseTO()
                    .withCodeDescription(key)
                    .withEndValue(value)
                    .withYear(year);

    Function<List<Sped>, List<SpedBaseTO>> buildSpedBaseBalance = source -> source
            .stream()
            .map(sped -> {
                var spedBalances = sped.getSpedBalances();

                var available = getAvailable.applyAsDouble(spedBalances);
                var activeCurrent = getCurrentActive.applyAsDouble(spedBalances);
                var notActiveCurrent = getNotCurrentActive.applyAsDouble(spedBalances);
                var activePermanent = getPermanentActive.applyAsDouble(spedBalances);
                var active = getActive.applyAsDouble(spedBalances);

                var passiveCurrent = getCurrentPassive.applyAsDouble(spedBalances);
                var passiveNotCurrent = getNotCurrentPassive.applyAsDouble(spedBalances);
                var netWorth = getPl.applyAsDouble(spedBalances);
                var passive = getPassive.applyAsDouble(spedBalances);
                var ccl = getCCL.applyAsDouble(spedBalances);
                var netDebt = getNetDebt.applyAsDouble(spedBalances);

                var balances = new ArrayList<SpedBaseTO>();
                var year = sped.getDemonstrativeEndDate().getYear();
                balances.add(buildSpedBase.apply(AVAILABLE_ACTIVE, available, year));
                balances.add(buildSpedBase.apply(CURRENT_ACTIVE, activeCurrent, year));
                balances.add(buildSpedBase.apply(NOT_CURRENT_ACTIVE, notActiveCurrent, year));
                balances.add(buildSpedBase.apply(PERMANENT_ACTIVE, activePermanent, year));
                balances.add(buildSpedBase.apply(ACTIVE, active, year));
                balances.add(buildSpedBase.apply(CURRENT_PASSIVE, passiveCurrent, year));
                balances.add(buildSpedBase.apply(NOT_CURRENT_PASSIVE, passiveNotCurrent, year));
                balances.add(buildSpedBase.apply(PL, netWorth, year));
                balances.add(buildSpedBase.apply(PASSIVE, passive, year));
                balances.add(buildSpedBase.apply(CCL, ccl, year));
                balances.add(buildSpedBase.apply(NET_DEBT, netDebt, year));

                return balances;
            })
            .flatMap(Collection::stream)
            .toList();

    Function<List<Sped>, List<SpedBaseTO>> buildSpedBaseDemostration = source -> source
            .stream()
            .map(sped -> {
                var spedDemonstrations = sped.getSpedDemonstrations();

                var invoicing = getInvoicing.applyAsDouble(spedDemonstrations);
                var cmv = getCMV.applyAsDouble(spedDemonstrations);
                var operatingPantry = getOperationPantry.applyAsDouble(spedDemonstrations);
                var ebitda = getEBITDA.applyAsDouble(spedDemonstrations);
                var pantryAm = getPantryAm.applyAsDouble(spedDemonstrations);
                var operatingProfit = getOperatingProfit.applyAsDouble(spedDemonstrations);
                var financialExpenses = getFinancialExpenses.applyAsDouble(spedDemonstrations);
                var tax = getTax.applyAsDouble(spedDemonstrations);
                var netProfit = getNetProfitMonth.applyAsDouble(sped);
                var percentEbitda = getPercentEBITDA.applyAsDouble(sped);
                var netPantryEbitda = getNetPantryEBITDA.applyAsDouble(sped);
                var percentNetProfit = getPercentNetProfit.applyAsDouble(sped);

                var demostrations = new HashSet<SpedBaseTO>();
                var year = sped.getDemonstrativeEndDate().getYear();
                demostrations.add(buildSpedBase.apply(INVOICING, invoicing, year));
                demostrations.add(buildSpedBase.apply(CMV, cmv, year));
                demostrations.add(buildSpedBase.apply(OPERATIONAL_PANTRY, operatingPantry, year));
                demostrations.add(buildSpedBase.apply(EBITDA, ebitda, year));
                demostrations.add(buildSpedBase.apply(PANTRY_AM, pantryAm, year));
                demostrations.add(buildSpedBase.apply(OPERATIONAL_PROFIT, operatingProfit, year));
                demostrations.add(buildSpedBase.apply(FINANCIAL_EXPENSES, financialExpenses, year));
                demostrations.add(buildSpedBase.apply(TAX, tax, year));
                demostrations.add(buildSpedBase.apply(NET_PROFIT, netProfit, year));
                demostrations.add(buildSpedBase.apply(PERCENT_EBITDA, percentEbitda, year));
                demostrations.add(buildSpedBase.apply(DL_EBITDA, netPantryEbitda, year));
                demostrations.add(buildSpedBase.apply(PERCENT_NET_PROFIT, percentNetProfit, year));

                return demostrations;
            })
            .flatMap(Collection::stream)
            .toList();

    List<ScoreAnalysisTO> bind(Set<ScoreAnalysis> source);

    default ScoreSummaryTO bind(@NonNull CompanyUser source) {
        var company = source.getCompany();
        var mainInfo = company.getMainInfo();
        var speds = source.getSpeds();
        var creditInformation = company.getCreditsInformation()
                .stream()
                .sorted(comparing(CreditInformation::getConsultDate).reversed())
                .toList();

        var analysis = source.getScoreAnalysis();
        var strength = 0D;
        var register = 0D;
        var habituality = 0D;
        var year = 0;

        for (ScoreAnalysis scoreAnalysis : analysis) {
            var total = scoreAnalysis.getTotal();
            switch (scoreAnalysis.getType()) {
                case STRENGTH -> {
                    var data = scoreAnalysis.getYear();
                    if (data > year) {
                        strength = total;
                        year = data;
                    }
                }
                case REGISTER -> register = register + total;
                case HABITUALITY -> habituality = habituality + total;
                default -> {
                }
            }
        }

        return new ScoreSummaryTO()
                .withCnpj(mainInfo.getCnpj())
                .withFantasyName(hasText(company.getFantasyName())
                        ? company.getFantasyName()
                        : mainInfo.getSocialReason())
                .withUf(mainInfo.getAddress() == null
                        ? null
                        : mainInfo.getAddress().getUf())
                .withQuantityEmployee(company.getQuantityEmployee())
                .withStrength(BigDecimal
                        .valueOf(strength)
                        .setScale(0, HALF_UP)
                        .intValue())
                .withRegister(BigDecimal
                        .valueOf(register)
                        .setScale(0, HALF_UP)
                        .intValue())
                .withHabituality(BigDecimal
                        .valueOf(habituality)
                        .setScale(0, HALF_UP)
                        .intValue())
                .withOpeningDate(mainInfo.getOpeningDate())
                .withCnae(CNAE_BINDER.bind(company.getMainCnae()))
                .withBalances(buildSpedBaseBalance.apply(speds))
                .withDemostrations(buildSpedBaseDemostration.apply(speds))
                .withCreditsInformations(CREDIT_INFORMATION_BINDER.bind(creditInformation))
                .withAnalysis(bind(analysis));
    }

}
