package com.linkapital.core.services.sped;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.sped.datasource.domain.Sped;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * The interface Sped excel service.
 */
public interface SpedFileService {

    /**
     * Parse excel sped balance sped.
     *
     * @param speds {@link List}<{@link Sped}> the sped list to update
     * @param file  {@link MultipartFile} the file to read
     * @return {@link Sped}
     * @throws UnprocessableEntityException if any error occurs
     */
    Sped parseSped(List<Sped> speds, MultipartFile file) throws UnprocessableEntityException;

    /**
     * Generate sped balance model.
     *
     * @param response the response
     * @throws UnprocessableEntityException if any error occurs
     */
    void generateSpedBalanceModel(HttpServletResponse response) throws UnprocessableEntityException;

}
