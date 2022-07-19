package com.linkapital.core.util;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;

import static com.linkapital.core.configuration.context.MessageContextHolder.msg;
import static com.linkapital.core.util.CnpjUtil.validate;
import static java.lang.String.valueOf;
import static java.time.LocalDate.now;
import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.util.Locale.ENGLISH;
import static org.apache.poi.ss.usermodel.CellType.FORMULA;
import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import static org.apache.poi.ss.usermodel.CellType.STRING;
import static org.apache.poi.ss.usermodel.WorkbookFactory.create;
import static org.springframework.util.StringUtils.hasText;

/**
 * Has the responsibility of performing operations on excel files.
 */
public class ExcelUtil {

    private static final DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder()
            .appendPattern("dd/MM/yyyy")
            .parseDefaulting(DAY_OF_MONTH, 1)
            .toFormatter()
            .withLocale(ENGLISH);

    private ExcelUtil() {
    }

    /**
     * Enters an excel file content into a list.
     *
     * @param file {@link MultipartFile} The file whose content will be put in to a list.
     * @return {@link List}<{@link String}>
     * @throws UnprocessableEntityException If any error occurs.
     */
    public static List<String> converter(MultipartFile file) throws UnprocessableEntityException {
        try {
            var workbook = generateWorkbook(file);
            var rows = workbook.getSheetAt(0).iterator();
            var cnpjList = new ArrayList<String>();
            var cnpj = "";
            var rowNumber = 0;

            while (rows.hasNext()) {
                var currentRow = rows.next();
                var currentCell = currentRow.getCell(0);

                // skip header
                if (rowNumber == 0) {
                    if (!currentCell.getStringCellValue().equalsIgnoreCase("cnpj"))
                        throw new UnprocessableEntityException(msg("excel.cnpj.position.error"));

                    rowNumber++;
                    continue;
                }

                if (currentCell != null) {
                    if (currentCell.getCellType().equals(NUMERIC))
                        cnpj = valueOf(currentCell.getNumericCellValue()).replaceAll("[^\\d]", "");

                    else if (currentCell.getCellType().equals(STRING))
                        cnpj = currentCell.getStringCellValue().replaceAll("[^\\d]", "");

                    if (hasText(cnpj) && validate(cnpj)) {
                        cnpjList.add(cnpj);
                        cnpj = "";
                    }
                }
            }
            workbook.close();

            return cnpjList;
        } catch (IOException e) {
            throw new UnprocessableEntityException(msg("excel.file.read.error", e.getMessage()));
        }
    }

    /**
     * Generate {@link Workbook} workbook.
     *
     * @param file {@link MultipartFile} the file
     * @return {@link Workbook}
     * @throws UnprocessableEntityException the unprocessable entity exception
     */
    public static Workbook generateWorkbook(MultipartFile file) throws UnprocessableEntityException {
        try (var excelFileToRead = file.getInputStream()) {
            return create(excelFileToRead);
        } catch (IOException e) {
            throw new UnprocessableEntityException(msg("excel.file.read.error", e.getMessage()));
        }
    }

    //region Set all columns witdhs by the supplied row columns widths only.
    public static void autoSizeColumns(Sheet sheet, @NotNull Row row) {
        var cellIterator = row.cellIterator();
        while (cellIterator.hasNext()) {
            var cell = cellIterator.next();
            int columnIndex = cell.getColumnIndex();
            sheet.autoSizeColumn(columnIndex);
        }
    }
    //endregion

    //region Creates a Date Type Cell
    public static Cell createDateCell(@NotNull Workbook workbook, @NotNull Cell cell, String format) {
        var createHelper = workbook.getCreationHelper();
        var cellStyle = workbook.createCellStyle();
        cellStyle.setDataFormat(
                createHelper.createDataFormat().getFormat(format));
        cell.setCellStyle(cellStyle);

        return cell;
    }
    //endregion

    //region Parse date from cell
    public static LocalDate parseDateCell(@NotNull Cell cell) {
        return switch (cell.getCellType()) {
            case NUMERIC -> cell.getLocalDateTimeCellValue().toLocalDate();
            case STRING -> LocalDate.parse(cell.getStringCellValue(), dateTimeFormatter);
            default -> now();
        };
    }
    //endregion

    //region Parses a Numeric Cell and returns 0 if it's empty
    public static double parseNumericCell(Cell cell) {
        return cell != null && (cell.getCellType().equals(NUMERIC) || cell.getCellType().equals(FORMULA))
                ? cell.getNumericCellValue()
                : 0;
    }
    //endregion

    //region Parses a String Cell and returns "" if it's empty
    public static String parseStringCell(Cell cell) {
        return cell != null && (cell.getCellType().equals(STRING) || cell.getCellType().equals(FORMULA))
                ? cell.getStringCellValue()
                : "";
    }
    //endregion

    public static boolean isRowEmpty(Row row) {
        boolean isEmpty = true;
        var dataFormatter = new DataFormatter();
        if (row != null) {
            for (Cell cell : row) {
                if (dataFormatter.formatCellValue(cell).trim().length() > 0) {
                    isEmpty = false;
                    break;
                }
            }
        }
        return isEmpty;
    }

}
