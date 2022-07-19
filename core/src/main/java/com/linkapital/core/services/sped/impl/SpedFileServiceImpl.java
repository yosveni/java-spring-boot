package com.linkapital.core.services.sped.impl;

import com.linkapital.core.exceptions.SpedParsingException;
import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.sped.SpedFileService;
import com.linkapital.core.services.sped.contract.to.SpedDeParaTO;
import com.linkapital.core.services.sped.datasource.domain.Sped;
import com.linkapital.core.services.sped.datasource.domain.SpedBalance;
import com.linkapital.core.services.sped.datasource.domain.SpedDemonstration;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import static com.linkapital.core.configuration.context.MessageContextHolder.msg;
import static com.linkapital.core.services.sped.contract.SpedBinder.DATE_FORMAT_EXCEL;
import static com.linkapital.core.services.sped.validator.SpedFileValidator.validateInitEndCells;
import static com.linkapital.core.util.ExcelUtil.autoSizeColumns;
import static com.linkapital.core.util.ExcelUtil.createDateCell;
import static com.linkapital.core.util.ExcelUtil.isRowEmpty;
import static com.linkapital.core.util.ExcelUtil.parseDateCell;
import static com.linkapital.core.util.ExcelUtil.parseNumericCell;
import static com.linkapital.core.util.ExcelUtil.parseStringCell;
import static java.lang.Math.abs;
import static java.lang.String.join;
import static java.time.LocalDateTime.now;
import static java.util.Arrays.copyOf;
import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MILLISECOND;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.SECOND;
import static java.util.Objects.requireNonNull;
import static org.apache.poi.ss.usermodel.WorkbookFactory.create;
import static org.springframework.util.StringUtils.hasText;

/**
 * Ativo:
 * C (Crédito) = Número negativo  (Cambiar el signo)
 * D (Débito) = Número positivo  (Mantener el signo)
 * <p>
 * Passivo:
 * C (Crédito) = Número positivo (Mantener el signo)
 * D (Débito) = Número negativo (Cambiar el signo)
 * <p>
 * DRE (Demonstração de Resultados)
 * C (Crédito) = Número positivo (Mantener el signo)
 * D (Débito) = Número negativo (Cambiar el signo)
 * Ejemplos:
 * -cuenta 3.X con valor -500 000   C-------->500 000 D
 * -cuenta 2.X con valor -500 000     --------->500 000 D
 * -cuenta 3.X con valor -500 000   D---------> 500 000 C
 */
@Service
public class SpedFileServiceImpl implements SpedFileService {

    private static final Logger log = LoggerFactory.getLogger(SpedFileServiceImpl.class);

    @Override
    public Sped parseSped(List<Sped> speds, MultipartFile file) throws UnprocessableEntityException {
        Sped sped = null;
        try (
                var fileInputStream = file.getInputStream();
                var workbook = create(fileInputStream)
        ) {
            if (workbook.getNumberOfSheets() == 1)
                sped = parseSpedFromSheet(workbook.getSheetAt(0), null);
            else if (workbook.getNumberOfSheets() > 2) {
                sped = parseSpedFromSheet(
                        workbook.getSheetAt(1), // sped format of the company
                        workbook.getSheetAt(2)); // sped format reference (De Para)

                if (sped == null)
                    sped = parseSpedFromSheet(workbook.getSheetAt(0), null);
            }

            return sped;
        } catch (IOException e) {
            throw new UnprocessableEntityException(msg("excel.file.read.error", e.getMessage()));
        }
    }

    @Override
    public void generateSpedBalanceModel(@NotNull HttpServletResponse response) throws UnprocessableEntityException {
        var workbook = generateSpedModelStructure();
        BufferedOutputStream bos;
        try {
            bos = new BufferedOutputStream(response.getOutputStream());
            workbook.write(bos);
            bos.close();
            workbook.close();
        } catch (IOException e) {
            throw new UnprocessableEntityException(e.getMessage());
        }
    }

    //region Create the structure for Sped Model
    private @NotNull XSSFWorkbook generateSpedModelStructure() {
        var workbook = new XSSFWorkbook();
        XSSFSheet sheet;
        Row row;

        // BALANCETE_SPED
        sheet = workbook.createSheet(msg("sped.excel.balancete.sped"));
        generateMockDateCells(workbook, sheet);

        row = sheet.createRow(3);
        row.createCell(0).setCellValue(msg("sped.excel.code.sped.7"));
        row.createCell(1).setCellValue(msg("sped.excel.code.description.sped.7"));
        row.createCell(2).setCellValue(msg("sped.excel.init.value"));
        row.createCell(3).setCellValue(msg("sped.excel.init.value.situation"));
        row.createCell(4).setCellValue(msg("sped.excel.debit.value"));
        row.createCell(5).setCellValue(msg("sped.excel.credit.value"));
        row.createCell(6).setCellValue(msg("sped.excel.end.value"));
        row.createCell(7).setCellValue(msg("sped.excel.end.value.situation"));
        autoSizeColumns(sheet, row);

        // BALANCETE_EMPRESA
        sheet = workbook.createSheet(msg("sped.excel.balancete.empresa"));
        generateMockDateCells(workbook, sheet);

        row = sheet.createRow(3);
        row.createCell(0).setCellValue(msg("sped.excel.code"));
        row.createCell(1).setCellValue(msg("sped.excel.code.description"));
        row.createCell(2).setCellValue(msg("sped.excel.init.value"));
        row.createCell(3).setCellValue(msg("sped.excel.init.value.situation"));
        row.createCell(4).setCellValue(msg("sped.excel.debit.value"));
        row.createCell(5).setCellValue(msg("sped.excel.credit.value"));
        row.createCell(6).setCellValue(msg("sped.excel.end.value"));
        row.createCell(7).setCellValue(msg("sped.excel.end.value.situation"));
        autoSizeColumns(sheet, row);

        // BALANCETE_EMPRESA_DE_PARA
        sheet = workbook.createSheet(msg("sped.excel.balancete.empresa.de.para"));
        row = sheet.createRow(0);

        row.createCell(0).setCellValue(msg("sped.excel.code"));
        row.createCell(1).setCellValue(msg("sped.excel.code.description"));
        row.createCell(2).setCellValue(msg("sped.excel.code.sped.7"));
        row.createCell(3).setCellValue(msg("sped.excel.code.description.sped.7"));
        autoSizeColumns(sheet, row);

        return workbook;
    }
    //endregion

    //region Parse Sped From Balance Excel Sheet
    private @Nullable Sped parseSpedFromSheet(Sheet sheet, Sheet dePara) throws UnprocessableEntityException {
        var sped = new Sped();
        var formatter = new DataFormatter();
        var mapDeParas = parseDePara(dePara);

        if (dePara != null && mapDeParas.isEmpty())
            return null;

        Row currentRow = null;
        parseDates(sheet, sped);

        try {
            for (int rowNum = 4; rowNum <= sheet.getLastRowNum(); rowNum++) {
                currentRow = sheet.getRow(rowNum);
                if (!isRowEmpty(currentRow)) {
                    String[] codeSplitted;
                    var code = formatter.formatCellValue(currentRow.getCell(0));
                    var codeDescription = "";
                    var endValue = parseNumericCell(currentRow.getCell(6));
                    var endValueSituation = parseStringCell(currentRow.getCell(7));

                    if (code.isEmpty())
                        throw new SpedParsingException(msg("sped.excel.error.attribute.required",
                                msg("sped.excel.code")));

                    if (mapDeParas.containsKey(code)) {
                        codeSplitted = mapDeParas.get(code).getCode().split("\\.");
                        codeDescription = mapDeParas.get(code).getDescription();
                    } else {
                        codeSplitted = code.split("\\.");
                        codeDescription = currentRow.getCell(1).getStringCellValue();
                    }

                    if (codeSplitted[0].equals("1") || codeSplitted[0].equals("2")) {
                        var spedBalance = new SpedBalance()
                                .withInitDate(sped.getDemonstrativeInitDate())
                                .withEndDate(sped.getDemonstrativeEndDate())
                                .withInitValue(parseNumericCell(currentRow.getCell(2)))
                                .withInitValueSituation(parseStringCell(currentRow.getCell(3)))
                                .withDebitValue(parseNumericCell(currentRow.getCell(4)))
                                .withCreditValue(parseNumericCell(currentRow.getCell(5)))
                                .withCode(code)
                                .withCodeDescription(codeDescription)
                                .withCodeLevel(codeSplitted.length)
                                .withCodeSynthetic(parseCodeSynthetic(codeSplitted))
                                .withEndValueSituation(parseEndValueSituation(endValueSituation, code, endValue))
                                .withEndValue(abs(endValue))
                                .withCreated(now())
                                .withModified(now());
                        sped.getSpedBalances().add(spedBalance);
                    } else if (codeSplitted[0].equals("3")) {
                        var spedDemonstration = new SpedDemonstration()
                                .withCode(code)
                                .withCodeDescription(codeDescription)
                                .withCodeSynthetic(parseCodeSynthetic(codeSplitted))
                                .withCodeLevel(codeSplitted.length)
                                .withEndValueSituation(parseEndValueSituation(endValueSituation, code, endValue))
                                .withEndValue(abs(endValue))
                                .withCreated(now())
                                .withModified(now());
                        sped.getSpedDemonstrations().add(spedDemonstration);
                    }
                }
            }
        } catch (Exception e) {
            throw new UnprocessableEntityException(
                    generateDetailedErrorMessage(requireNonNull(currentRow).getRowNum() + 1,
                            sheet.getSheetName(),
                            e instanceof SpedParsingException
                                    ? e.getMessage()
                                    : null));
        }

        return sped.getSpedBalances().isEmpty() || sped.getSpedDemonstrations().isEmpty()
                ? null
                : sped;
    }

    //region Get endValueSituation depending on its value and, if empty, set it depending on endValue positivity
    private String parseEndValueSituation(String endValueSituation, @NotNull String code, double endValue) {
        var credit = "C";
        var debit = "D";
        var value = code.charAt(0);

        if (hasText(endValueSituation)) {
            switch (value) {
                case '1': {
                    if (endValueSituation.equals(credit))
                        return (endValue * -1) > 0
                                ? debit
                                : credit;
                    return endValue > 0
                            ? debit
                            : credit;
                }
                case '2', '3':
                    if (endValueSituation.equals(debit))
                        return (endValue * -1) > 0
                                ? credit
                                : debit;
                    return endValue > 0
                            ? credit
                            : debit;
                default:
                    return "-";
            }
        }

        return getEndValueSituation(value, endValue);
    }
    //endregion

    //region Get end value situation
    private String getEndValueSituation(char value, double endValue) {
        var credit = "C";
        var debit = "D";

        return switch (value) {
            case '1' -> endValue < 0
                    ? credit
                    : debit;
            case '2', '3' -> endValue > 0
                    ? credit
                    : debit;
            default -> "-";
        };
    }
    //endregion

    //region Generate Code Synthetic
    private @NotNull String parseCodeSynthetic(String @NotNull [] code) {
        return code.length > 1
                ? join(".", copyOf(code, code.length - 1))
                : "";
    }
    //endregion

    //region Generate cells with date from first and last day of the year
    private void generateMockDateCells(Workbook workbook, @NotNull Sheet sheet) {
        Row row;
        var calendar = Calendar.getInstance();
        calendar.set(HOUR_OF_DAY, 0);
        calendar.set(MINUTE, 0);
        calendar.set(SECOND, 0);
        calendar.set(MILLISECOND, 0);

        row = sheet.createRow(0);
        row.createCell(0).setCellValue(msg("sped.excel.init.date", DATE_FORMAT_EXCEL.toUpperCase()));
        row.createCell(1).setCellValue(msg("sped.excel.end.date", DATE_FORMAT_EXCEL.toUpperCase()));

        row = sheet.createRow(1);

        calendar.set(MONTH, 0);
        calendar.set(DAY_OF_MONTH, 1);
        createDateCell(workbook, row.createCell(0), DATE_FORMAT_EXCEL).setCellValue(calendar.getTime());

        calendar.set(MONTH, 11);
        calendar.set(DAY_OF_MONTH, 31);
        createDateCell(workbook, row.createCell(1), DATE_FORMAT_EXCEL).setCellValue(calendar.getTime());
    }
    //endregion

    //region Parse Init and End Date from Balance Sheet
    private void parseDates(@NotNull Sheet sheet, @NotNull Sped sped) throws UnprocessableEntityException {
        var datesRow = sheet.getRow(1);

        validateInitEndCells(datesRow.getCell(0), datesRow.getCell(1), sheet);
        sped.setDemonstrativeInitDate(parseDateCell(datesRow.getCell(0)));
        sped.setDemonstrativeEndDate(parseDateCell(datesRow.getCell(1)));
    }
    //endregion

    //region Parse DePara Excel Sheet
    private @NotNull HashMap<String, SpedDeParaTO> parseDePara(Sheet deParaSheet) throws UnprocessableEntityException {
        var deParas = new HashMap<String, SpedDeParaTO>();
        var formatter = new DataFormatter();
        Row currentRow = null;

        try {
            if (deParaSheet != null)
                for (Row row : deParaSheet) {
                    currentRow = row;
                    // skip header
                    if (row.getRowNum() == 0)
                        continue;

                    if (row.getCell(0) != null)
                        deParas.put(formatter.formatCellValue(row.getCell(0)),
                                new SpedDeParaTO()
                                        .withCode(formatter.formatCellValue(row.getCell(0)))
                                        .withDescription(row.getCell(1).getStringCellValue())
                                        .withCodeSped7(formatter.formatCellValue(row.getCell(2)))
                                        .withDescriptionSped7(row.getCell(3).getStringCellValue()));
                }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new UnprocessableEntityException(
                    generateDetailedErrorMessage(requireNonNull(currentRow).getRowNum() + 1,
                            deParaSheet.getSheetName(), null));
        }

        return deParas;
    }
    //endregion

    //region Generate generic detailed error message for Sped parsing
    private @NotNull String generateDetailedErrorMessage(int rowNum, String sheetName, String specificMessage) {
        return msg("sped.excel.error.detailed", rowNum, sheetName, specificMessage);
    }
    //endregion

}
