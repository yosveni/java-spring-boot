package com.linkapital.core.services.company.impl;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.company.CompanyExcelService;
import com.linkapital.core.util.ExcelUtil;
import com.linkapital.core.util.file_analizer.FileAnalizerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.linkapital.core.configuration.context.MessageContextHolder.msg;
import static com.linkapital.core.util.file_analizer.FileAnalizer.MULTIPART;


@Service
public class CompanyExcelServiceImpl implements CompanyExcelService {

    @Override
    public List<String> parseCnpj(MultipartFile file) throws UnprocessableEntityException {
        var fileAnalizer = FileAnalizerFactory.create(MULTIPART);
        if (!fileAnalizer.isExcel(file))
            throw new UnprocessableEntityException(msg("excel.invalid.format"));

        return ExcelUtil.converter(file);
    }

}
