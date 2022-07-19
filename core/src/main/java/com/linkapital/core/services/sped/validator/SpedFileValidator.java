package com.linkapital.core.services.sped.validator;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.sped.datasource.domain.Sped;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.List;

import static com.linkapital.core.configuration.context.MessageContextHolder.msg;
import static com.linkapital.core.util.ExcelUtil.parseDateCell;
import static java.lang.String.valueOf;
import static java.time.LocalDate.now;
import static java.time.temporal.ChronoField.YEAR;

public class SpedFileValidator {

    private SpedFileValidator() {
    }

    //region Validate Init and End Date from Balance Sheet
    public static void validateInitEndCells(Cell initDateCell,
                                            Cell endDateCell,
                                            Sheet sheet) throws UnprocessableEntityException {
        if (initDateCell == null || endDateCell == null)
            throw new UnprocessableEntityException(msg("sped.excel.error.date.required", sheet.getSheetName()));

        // Sped dates must be same year
        var dataInitParsed = parseDateCell(initDateCell);
        var dataEndParsed = parseDateCell(endDateCell);
        if (dataInitParsed.get(YEAR) == dataEndParsed.get(YEAR))
            throw new UnprocessableEntityException(msg("sped.excel.error.date.mismatched.years",
                    sheet.getSheetName()));
    }
    //endregion

    //region Validate that the speed document (s) belong to the last 3 years
    public static void validateSpedFile(List<Sped> speds, Sped sped) throws UnprocessableEntityException {
        if (sped == null)
            throw new UnprocessableEntityException(msg("sped.processing.error"));

        var dif = now().get(YEAR) - 3;
        var lastYear = sped.getDemonstrativeEndDate().get(YEAR);

        if (dif > lastYear)
            throw new UnprocessableEntityException(msg("sped.last.acceptable.year.is", valueOf(dif)));

        if (!speds.isEmpty()) {
            var existsSpedWithSameYear = speds
                    .stream()
                    .anyMatch(sped1 ->
                            sped1.getDemonstrativeEndDate().get(YEAR) == sped.getDemonstrativeEndDate().get(YEAR));

            if (existsSpedWithSameYear)
                throw new UnprocessableEntityException(msg("sped.for.year.already.exists",
                        valueOf(sped.getDemonstrativeEndDate().get(YEAR))));
        }
    }
    //endregion

}
