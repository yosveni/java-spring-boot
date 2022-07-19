package com.linkapital.core.services.method_k.impl;

import com.linkapital.core.services.company_user.datasource.domain.CompanyUser;
import com.linkapital.core.services.configuration.ConfigurationService;
import com.linkapital.core.services.configuration.contract.to.SysConfigurationTO;
import com.linkapital.core.services.interview.contract.enums.InterviewArea;
import com.linkapital.core.services.interview.datasource.domain.AnswerInterview;
import com.linkapital.core.services.method_k.ScoreAnalysisService;
import com.linkapital.core.services.method_k.contract.enums.ScoreType;
import com.linkapital.core.services.method_k.datasource.domain.ScoreAnalysis;
import com.linkapital.core.services.method_k.datasource.domain.ScoreOperation;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.linkapital.core.services.cri_cra_debenture.contract.enums.CriCraDebentureType.CRA;
import static com.linkapital.core.services.cri_cra_debenture.contract.enums.CriCraDebentureType.CRI;
import static com.linkapital.core.services.cri_cra_debenture.contract.enums.CriCraDebentureType.DEBENTURE;
import static com.linkapital.core.services.interview.contract.enums.InterviewArea.RECORD_SCORE;
import static com.linkapital.core.services.interview.contract.enums.InterviewArea.VITALITY_SCORE;
import static com.linkapital.core.services.method_k.contract.CadastralScoreBinder.getAmountProtestsByAge;
import static com.linkapital.core.services.method_k.contract.CadastralScoreBinder.getBdnsOperation;
import static com.linkapital.core.services.method_k.contract.CadastralScoreBinder.getCriCraDebentureValue;
import static com.linkapital.core.services.method_k.contract.CadastralScoreBinder.getPGFNInvoicingInformed;
import static com.linkapital.core.services.method_k.contract.CadastralScoreBinder.getTotalValueProcessInvoicing;
import static com.linkapital.core.services.method_k.contract.CadastralScoreBinder.getTotalValueProtestsInvoicing;
import static com.linkapital.core.services.method_k.contract.CadastralScoreBinder.getValueCertificates;
import static com.linkapital.core.services.method_k.contract.CadastralScoreBinder.getWorkProcessAge;
import static com.linkapital.core.services.method_k.contract.FinancialStrengthBinder.buildOperation;
import static com.linkapital.core.services.method_k.contract.FinancialStrengthBinder.getActivityIndex;
import static com.linkapital.core.services.method_k.contract.FinancialStrengthBinder.getIndebtednessIndex;
import static com.linkapital.core.services.method_k.contract.FinancialStrengthBinder.getLiquidityIndex;
import static com.linkapital.core.services.method_k.contract.FinancialStrengthBinder.getPantryIndex;
import static com.linkapital.core.services.method_k.contract.FinancialStrengthBinder.getProfitabilityIndex;
import static com.linkapital.core.services.method_k.contract.FinancialStrengthBinder.getSizeIndex;
import static com.linkapital.core.services.method_k.contract.HabitualityScoreBinder.getCountOperation;
import static com.linkapital.core.services.method_k.contract.HabitualityScoreBinder.getCurrentVolume;
import static com.linkapital.core.services.method_k.contract.HabitualityScoreBinder.getDebDelayUp90Day;
import static com.linkapital.core.services.method_k.contract.HabitualityScoreBinder.getDebtBndesCountActual;
import static com.linkapital.core.services.method_k.contract.HabitualityScoreBinder.getDebtBndesCountLiquided;
import static com.linkapital.core.services.method_k.contract.HabitualityScoreBinder.getDebtBndesVolActual;
import static com.linkapital.core.services.method_k.contract.HabitualityScoreBinder.getDebtBndesVolLiquided;
import static com.linkapital.core.services.method_k.contract.HabitualityScoreBinder.getDebtVolumeByPl;
import static com.linkapital.core.services.method_k.contract.enums.ScoreType.HABITUALITY;
import static com.linkapital.core.services.method_k.contract.enums.ScoreType.REGISTER;
import static com.linkapital.core.services.method_k.contract.enums.ScoreType.STRENGTH;
import static com.linkapital.core.services.method_k.datasource.domain.ScoreCodes.amountProtestsByAge;
import static com.linkapital.core.services.method_k.datasource.domain.ScoreCodes.bdnsOperation;
import static com.linkapital.core.services.method_k.datasource.domain.ScoreCodes.countOperBndes;
import static com.linkapital.core.services.method_k.datasource.domain.ScoreCodes.craValue;
import static com.linkapital.core.services.method_k.datasource.domain.ScoreCodes.criValue;
import static com.linkapital.core.services.method_k.datasource.domain.ScoreCodes.currentVolRawScore;
import static com.linkapital.core.services.method_k.datasource.domain.ScoreCodes.debentureDebentureValue;
import static com.linkapital.core.services.method_k.datasource.domain.ScoreCodes.debtBndesCount;
import static com.linkapital.core.services.method_k.datasource.domain.ScoreCodes.debtBndesCountLiqui;
import static com.linkapital.core.services.method_k.datasource.domain.ScoreCodes.debtBndesVol;
import static com.linkapital.core.services.method_k.datasource.domain.ScoreCodes.debtBndesVolLiqui;
import static com.linkapital.core.services.method_k.datasource.domain.ScoreCodes.debtUp90day;
import static com.linkapital.core.services.method_k.datasource.domain.ScoreCodes.debtVolByPl;
import static com.linkapital.core.services.method_k.datasource.domain.ScoreCodes.nameOperationFive;
import static com.linkapital.core.services.method_k.datasource.domain.ScoreCodes.nameOperationFour;
import static com.linkapital.core.services.method_k.datasource.domain.ScoreCodes.nameOperationOne;
import static com.linkapital.core.services.method_k.datasource.domain.ScoreCodes.nameOperationSix;
import static com.linkapital.core.services.method_k.datasource.domain.ScoreCodes.nameOperationThree;
import static com.linkapital.core.services.method_k.datasource.domain.ScoreCodes.nameOperationTwo;
import static com.linkapital.core.services.method_k.datasource.domain.ScoreCodes.pgfnInvoicingInformed;
import static com.linkapital.core.services.method_k.datasource.domain.ScoreCodes.pointInterview;
import static com.linkapital.core.services.method_k.datasource.domain.ScoreCodes.totalValueProcessInvoicing;
import static com.linkapital.core.services.method_k.datasource.domain.ScoreCodes.valueCertificates;
import static com.linkapital.core.services.method_k.datasource.domain.ScoreCodes.valueTotalProtestsInvoicing;
import static com.linkapital.core.services.sped.contract.SpedBinder.cloneSped;
import static java.math.RoundingMode.HALF_UP;
import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.YEAR;
import static java.util.Arrays.asList;
import static java.util.Comparator.comparing;
import static java.util.Locale.ENGLISH;

@Service
public class ScoreAnalysisServiceImpl implements ScoreAnalysisService {

    private static final String CONSULT_DATE_FORMAT = "yyyy-MM";
    private final DateTimeFormatter dateTimeFormatter;
    private final List<SysConfigurationTO> configs;

    public ScoreAnalysisServiceImpl(@NonNull ConfigurationService configurationService) {
        this.configs = configurationService.loadAll().getElements();
        this.dateTimeFormatter = new DateTimeFormatterBuilder()
                .appendPattern(CONSULT_DATE_FORMAT)
                .parseDefaulting(DAY_OF_MONTH, 1)
                .toFormatter()
                .withLocale(ENGLISH);
    }

    @Override
    public void updateResumeScore(@NonNull CompanyUser companyUser, @NonNull ScoreType... scoreTypes) {
        Arrays
                .stream(scoreTypes)
                .toList()
                .forEach(type -> {
                    switch (type) {
                        case STRENGTH -> buildFinancialStrengthScore(companyUser);
                        case REGISTER -> buildRegisterScore(companyUser);
                        case HABITUALITY -> buildHabitualityScore(companyUser);
                        default -> {
                            buildFinancialStrengthScore(companyUser);
                            buildRegisterScore(companyUser);
                            buildHabitualityScore(companyUser);
                        }
                    }
                });
    }

    private void buildFinancialStrengthScore(@NonNull CompanyUser companyUser) {
        var company = companyUser.getCompany();
        var quantityEmployee = company.getQuantityEmployee();
        var openingDate = company.getMainInfo().getOpeningDate();

        companyUser.getScoreAnalysis().removeIf(scoreAnalysis -> scoreAnalysis.getType().equals(STRENGTH));
        companyUser.getSpeds()
                .forEach(sped -> {
                    var cloned = cloneSped.apply(sped);
                    var indebtednessIndex = getIndebtednessIndex.applyAsDouble(configs, cloned);
                    var profitIndex = getProfitabilityIndex.applyAsDouble(configs, cloned);
                    var liquidityIndex = getLiquidityIndex.applyAsDouble(configs, cloned);
                    var sizeIndex = getSizeIndex.applyAsDouble(quantityEmployee, openingDate, cloned, configs);
                    var pantryIndex = getPantryIndex.applyAsDouble(configs, cloned);
                    var activityIndex = getActivityIndex.applyAsDouble(configs, cloned);

                    var values = asList(indebtednessIndex, profitIndex, liquidityIndex, sizeIndex,
                            pantryIndex, activityIndex);
                    var total = values
                            .stream()
                            .reduce(0.0, Double::sum);

                    var operations = new HashSet<ScoreOperation>();
                    operations.add(buildOperation.apply(nameOperationOne, indebtednessIndex));
                    operations.add(buildOperation.apply(nameOperationTwo, liquidityIndex));
                    operations.add(buildOperation.apply(nameOperationThree, profitIndex));
                    operations.add(buildOperation.apply(nameOperationFour, sizeIndex));
                    operations.add(buildOperation.apply(nameOperationFive, pantryIndex));
                    operations.add(buildOperation.apply(nameOperationSix, activityIndex));

                    var calculatedTotal = BigDecimal
                            .valueOf(total / 480 * 500)
                            .setScale(3, HALF_UP)
                            .doubleValue();

                    companyUser.getScoreAnalysis().add(new ScoreAnalysis()
                            .withYear(sped.getDemonstrativeEndDate().get(YEAR))
                            .withTotal(calculatedTotal)
                            .withType(STRENGTH)
                            .withOperations(operations));
                });
    }

    private void buildRegisterScore(@NonNull CompanyUser companyUser) {
        companyUser.getScoreAnalysis().removeIf(scoreAnalysis -> scoreAnalysis.getType().equals(REGISTER));
        var operations = new HashSet<ScoreOperation>();

        var debtPgfnFat = getPGFNInvoicingInformed.applyAsDouble(companyUser, configs);
        var numberProtestAge = getAmountProtestsByAge.applyAsDouble(companyUser, configs);
        var valueProtestInvoice = getTotalValueProtestsInvoicing.applyAsDouble(companyUser, configs);
        var amountWorkProcessAge = getWorkProcessAge.applyAsDouble(companyUser, configs);
        var valueTotalProtest = getTotalValueProcessInvoicing.applyAsDouble(companyUser, configs);
        var certificates = getValueCertificates.applyAsDouble(companyUser, configs);
        var bnds = getBdnsOperation.applyAsDouble(companyUser, configs);
        var debentures = getCriCraDebentureValue.applyAsDouble(DEBENTURE, companyUser, configs);
        var cri = getCriCraDebentureValue.applyAsDouble(CRI, companyUser, configs);
        var cra = getCriCraDebentureValue.applyAsDouble(CRA, companyUser, configs);
        var interviewPoint = getPointInterview(RECORD_SCORE, companyUser.getAnswerInterviews());

        var total = debtPgfnFat + numberProtestAge + valueProtestInvoice + amountWorkProcessAge +
                valueTotalProtest + certificates + bnds + debentures + cri + cra + interviewPoint;

        operations.add(buildOperation.apply(pgfnInvoicingInformed, debtPgfnFat));
        operations.add(buildOperation.apply(amountProtestsByAge, numberProtestAge));
        operations.add(buildOperation.apply(valueTotalProtestsInvoicing, valueProtestInvoice));
        operations.add(buildOperation.apply(amountProtestsByAge, amountWorkProcessAge));
        operations.add(buildOperation.apply(totalValueProcessInvoicing, valueTotalProtest));
        operations.add(buildOperation.apply(valueCertificates, certificates));
        operations.add(buildOperation.apply(bdnsOperation, bnds));
        operations.add(buildOperation.apply(debentureDebentureValue, debentures));
        operations.add(buildOperation.apply(criValue, cri));
        operations.add(buildOperation.apply(craValue, cra));
        operations.add(buildOperation.apply(pointInterview, (double) interviewPoint));

        companyUser.getScoreAnalysis().add(new ScoreAnalysis()
                .withYear(LocalDateTime.now().getYear())
                .withTotal(total)
                .withType(REGISTER)
                .withOperations(operations));
    }

    private void buildHabitualityScore(@NonNull CompanyUser companyUser) {
        companyUser.getScoreAnalysis().removeIf(scoreAnalysis -> scoreAnalysis.getType().equals(HABITUALITY));
        var operations = new HashSet<ScoreOperation>();

        var creditInformation = companyUser.getCompany().getCreditsInformation()
                .stream()
                .max(comparing(ci -> LocalDate.parse(ci.getConsultDate(), dateTimeFormatter)))
                .orElse(null);

        var debtBndesCountActual = getDebtBndesCountActual.applyAsDouble(companyUser, configs);
        var debtBndesVolActual = getDebtBndesVolActual.applyAsDouble(companyUser, configs);
        var debtBndesCountLiquided = getDebtBndesCountLiquided.applyAsDouble(companyUser, configs);
        var debtBndesVolLiquided = getDebtBndesVolLiquided.applyAsDouble(companyUser, configs);
        var currentVolumeRawScore = getCurrentVolume.applyAsDouble(companyUser, creditInformation, configs);
        var countOperation = getCountOperation.applyAsDouble(companyUser, creditInformation, configs);
        var debtVolumeByPl = getDebtVolumeByPl.applyAsDouble(companyUser, creditInformation, configs);
        var debtDelay90 = getDebDelayUp90Day.applyAsDouble(companyUser, creditInformation, configs);
        var interviewPoint = getPointInterview(VITALITY_SCORE, companyUser.getAnswerInterviews());

        var total = debtBndesCountActual + debtBndesVolActual + debtBndesCountLiquided + debtBndesVolLiquided +
                interviewPoint + currentVolumeRawScore + countOperation + debtVolumeByPl + debtDelay90;

        operations.add(buildOperation.apply(debtBndesCount, debtBndesCountActual));
        operations.add(buildOperation.apply(debtBndesVol, debtBndesVolActual));
        operations.add(buildOperation.apply(debtBndesCountLiqui, debtBndesCountLiquided));
        operations.add(buildOperation.apply(debtBndesVolLiqui, debtBndesVolLiquided));
        operations.add(buildOperation.apply(currentVolRawScore, currentVolumeRawScore));
        operations.add(buildOperation.apply(countOperBndes, countOperation));
        operations.add(buildOperation.apply(debtVolByPl, debtVolumeByPl));
        operations.add(buildOperation.apply(debtUp90day, debtDelay90));
        operations.add(buildOperation.apply(pointInterview, (double) interviewPoint));

        companyUser.getScoreAnalysis().add(new ScoreAnalysis()
                .withYear(LocalDateTime.now().getYear())
                .withTotal(total)
                .withType(HABITUALITY)
                .withOperations(operations));
    }

    private int getPointInterview(InterviewArea area, @NonNull Set<AnswerInterview> source) {
        return source
                .stream()
                .filter(answer -> answer.getQuestionInterview().getArea().equals(area))
                .mapToInt(AnswerInterview::getAnswer)
                .sum();
    }

}
