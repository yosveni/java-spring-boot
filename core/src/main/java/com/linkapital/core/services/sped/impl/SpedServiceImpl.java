package com.linkapital.core.services.sped.impl;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.company_user.contract.to.CompanyLearning4TO;
import com.linkapital.core.services.company_user.datasource.domain.CompanyUser;
import com.linkapital.core.services.sped.SpedService;
import com.linkapital.core.services.sped.contract.to.CreateSpedTO;
import com.linkapital.core.services.sped.datasource.domain.Sped;
import com.linkapital.core.services.sped.datasource.domain.SpedUtil;
import com.linkapital.core.services.storage.StorageService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;

import static com.linkapital.core.configuration.context.MessageContextHolder.msg;
import static com.linkapital.core.services.comment.contract.CommentBinder.COMMENT_BINDER;
import static com.linkapital.core.services.sped.contract.SpedBinder.K0000;
import static com.linkapital.core.services.sped.contract.SpedBinder.K0010;
import static com.linkapital.core.services.sped.contract.SpedBinder.L030;
import static com.linkapital.core.services.sped.contract.SpedBinder.L100;
import static com.linkapital.core.services.sped.contract.SpedBinder.L300;
import static com.linkapital.core.services.sped.contract.SpedBinder.P030;
import static com.linkapital.core.services.sped.contract.SpedBinder.P100;
import static com.linkapital.core.services.sped.contract.SpedBinder.P150;
import static com.linkapital.core.services.sped.contract.SpedBinder.SPED_BINDER;
import static com.linkapital.core.services.sped.contract.SpedBinder.bindToSpedBalance;
import static com.linkapital.core.services.sped.contract.SpedBinder.bindToSpedDemonstration;
import static com.linkapital.core.services.sped.contract.SpedBinder.cloneSped;
import static com.linkapital.core.services.sped.contract.SpedBinder.getFinancialAndVerticalAnalysis;
import static com.linkapital.core.services.sped.contract.SpedBinder.getHorizontalAnalysis;
import static com.linkapital.core.services.sped.contract.SpedBinder.toSpedFormat;
import static com.linkapital.core.services.sped.validator.SpedFileValidator.validateSpedFile;
import static com.linkapital.core.services.sped.validator.SpedValidator.validateFiles;
import static com.linkapital.core.services.sped.validator.SpedValidator.validateLists;
import static java.lang.String.valueOf;
import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.YEAR;
import static java.util.Comparator.comparing;
import static java.util.Locale.ENGLISH;
import static java.util.stream.Collectors.toList;
import static org.springframework.util.StringUtils.delimitedListToStringArray;

/**
 * Ativo: code inicia con 1
 * C (Crédito) = Número negativo
 * D (Débito) = Número positivo
 * <p>
 * Passivo: code inicia con 2
 * C (Crédito) = Número positivo
 * D (Débito) = Número negativo
 * <p>
 * DRE (Demonstração de Resultados)
 * C (Crédito) = Número positivo
 * D (Débito) = Número negativo
 */
@Service
public class SpedServiceImpl implements SpedService {

    private static final Logger log = LoggerFactory.getLogger(SpedServiceImpl.class);
    private final boolean validate;
    private final StorageService storageService;
    private final DateTimeFormatter dateTimeFormatter;
    private String CNPJ;

    public SpedServiceImpl(@Value("${sped-to-validate}") boolean validate,
                           StorageService storageService) {
        this.validate = validate;
        this.storageService = storageService;
        this.dateTimeFormatter = new DateTimeFormatterBuilder()
                .appendPattern("ddMMyyyy")
                .parseDefaulting(DAY_OF_MONTH, 1)
                .toFormatter()
                .withLocale(ENGLISH);
    }

    @Override
    public List<Sped> readSpedFiles(@NotNull String cnpj, MultipartFile[] files) throws UnprocessableEntityException {
        CNPJ = cnpj.substring(0, 8);
        validateFiles(files);

        return parse(files)
                .stream()
                .sorted(Sped::compareTo)
                .collect(toList());
    }

    @Override
    public void updateSpedList(CompanyUser companyUser, @NotNull CreateSpedTO to) throws UnprocessableEntityException {
        if (to.getSpeds().isEmpty())
            return;

        var speds = SPED_BINDER.bind(to.getSpeds());
        for (Sped sped : speds)
            validateSpedFile(companyUser.getSpeds(), sped);

        speds.forEach(sped -> sped.getSpedDemonstrations()
                .forEach(spedDemonstration ->
                        toSpedFormat.accept(spedDemonstration, sped.getSpedDemonstrations())));

        companyUser.getSpeds().addAll(speds);
        companyUser.getSpeds().sort(comparing(Sped::getDemonstrativeEndDate));
    }

    @Override
    public CompanyLearning4TO getAnalysis(@NotNull CompanyUser companyUser) {
        var clone = companyUser.getSpeds()
                .stream()
                .map(cloneSped)
                .toList();
        var analysis = getFinancialAndVerticalAnalysis.apply(companyUser.getCreditRequested(), clone);
        var horizontalAnalysis = getHorizontalAnalysis.apply(analysis);
        var comments = COMMENT_BINDER.filterComments(companyUser.getComments(), 4);

        return new CompanyLearning4TO()
                .withCnpj(companyUser.getCompany().getMainInfo().getCnpj())
                .withAnalysis(analysis)
                .withHorizontalAnalysis(horizontalAnalysis)
                .withComments(comments);
    }

    //region Parse the file list
    private @NotNull List<Sped> parse(MultipartFile @NotNull [] files) throws UnprocessableEntityException {
        var speds = new ArrayList<Sped>();

        for (MultipartFile file : files) {
            var parsed = readSpedFile(file);
            var sped = buildSped(parsed);

            validateLists(sped.getDemonstrativeEndDate(), sped.getSpedBalances(), sped.getSpedDemonstrations());
            validateSpedFile(speds, sped);
            speds.add(sped);
        }

        return speds;
    }
    //endregion

    //region Build the Sped object
    private Sped buildSped(@NotNull List<SpedUtil> source) throws UnprocessableEntityException {
        if (source.isEmpty())
            throw new UnprocessableEntityException(msg("sped.data.not.found"));

        source.sort(comparing(SpedUtil::getEndDate));
        var demonstrations = new ArrayList<String[]>();
        source.forEach(spedUtil1 -> demonstrations.addAll(spedUtil1.getDemonstrations()));

        return new Sped()
                .withDemonstrativeInitDate(source.get(0).getInitDate())
                .withDemonstrativeEndDate(source.get(source.size() - 1).getEndDate())
                .withSpedBalances(bindToSpedBalance.apply(source))
                .withSpedDemonstrations(bindToSpedDemonstration.apply(demonstrations));
    }
    //endregion

    private List<SpedUtil> readSpedFile(@NotNull MultipartFile file) throws UnprocessableEntityException {
        FileReader reader;
        try {
            reader = new FileReader(storageService.getFile(file.getInputStream()), ISO_8859_1);
        } catch (UnprocessableEntityException | IOException e) {
            throw new UnprocessableEntityException(e.getMessage());
        }

        try (var bufferedReader = new BufferedReader(reader)) {
            return parseSped(bufferedReader);
        } catch (IOException e) {
            throw new UnprocessableEntityException(e.getMessage());
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }

    private List<SpedUtil> parseSped(@NotNull BufferedReader bufferedReader) throws UnprocessableEntityException {
        var spedUtils = new ArrayList<SpedUtil>();
        var oldVersion = false;
        var annual = false;

        try {
            var line = bufferedReader.readLine();
            while (line != null) {
                var result = delimitedListToStringArray(line, "|");

                if (result.length < 2)
                    break;

                var data = result[1];

                switch (data) {
                    case K0000 -> {
                        var year = LocalDate.parse(result[11], dateTimeFormatter).get(YEAR);
                        if (validate && !result[4].startsWith(CNPJ))
                            throw new UnprocessableEntityException(msg("sped.cnpj.error", valueOf(year)));

                        if (year < 2020)
                            oldVersion = true;
                        line = bufferedReader.readLine();
                    }
                    case K0010 -> {
                        annual = "A".equals(oldVersion ? result[6] : result[5]);
                        line = bufferedReader.readLine();
                    }
                    case P030, L030 -> line = extractSpedUtil(annual, result, bufferedReader, spedUtils);
                    default -> line = bufferedReader.readLine();
                }
            }

            return spedUtils;
        } catch (IOException e) {
            throw new UnprocessableEntityException(e.getMessage());
        }
    }

    private @Nullable String extractSpedUtil(boolean annual,
                                             String @NotNull [] result,
                                             BufferedReader bufferedReader,
                                             List<SpedUtil> spedUtils) throws UnprocessableEntityException {
        var value = result[4];

        try {
            if (annual && !"A00".equals(value))
                return bufferedReader.readLine();

            var spedUtil = new SpedUtil()
                    .withInitDate(LocalDate.parse(result[2], dateTimeFormatter))
                    .withEndDate(LocalDate.parse(result[3], dateTimeFormatter));

            var endPeriod = false;
            String line = null;

            while (!endPeriod && (line = bufferedReader.readLine()) != null) {
                result = delimitedListToStringArray(line, "|");

                if (result.length < 2)
                    break;

                switch (result[1]) {
                    case L100, P100:
                        if (annual || "T04".equals(value))
                            spedUtil.getBalances().add(result);
                        break;
                    case L300, P150:
                        spedUtil.getDemonstrations().add(result);
                        break;
                    case L030, P030:
                        endPeriod = true;
                        break;
                    default:
                }
            }

            validateLists(spedUtil.getEndDate(), spedUtil.getBalances(), spedUtil.getDemonstrations());
            spedUtils.add(spedUtil);

            return annual
                    ? null
                    : line;
        } catch (IOException e) {
            throw new UnprocessableEntityException(e.getMessage());
        }
    }

}
