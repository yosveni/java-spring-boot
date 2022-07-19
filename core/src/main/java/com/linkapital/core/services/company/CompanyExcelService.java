package com.linkapital.core.services.company;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * The interface Company excel service.
 */
public interface CompanyExcelService {

    /**
     * Parse cnpj list.
     *
     * @param file the file
     * @return the list
     * @throws UnprocessableEntityException the unprocessable entity exception
     */
    List<String> parseCnpj(MultipartFile file) throws UnprocessableEntityException;

}
