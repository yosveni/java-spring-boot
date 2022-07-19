package com.linkapital.core.services.sped;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.company_user.contract.to.CompanyLearning4TO;
import com.linkapital.core.services.company_user.datasource.domain.CompanyUser;
import com.linkapital.core.services.sped.contract.to.CreateSpedTO;
import com.linkapital.core.services.sped.datasource.domain.Sped;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Default interface for {@link SpedService}
 * Has the responsibility to make operations over the {@link Sped} entity
 *
 * @since 0.0.1
 */
public interface SpedService {

    /**
     * Updates the list of sped documents of the company
     *
     * @param companyUser {@link CompanyUser} the company to update
     * @param to          {@link CreateSpedTO} the sped data
     * @throws UnprocessableEntityException if any error occur retrieving the person data
     */
    void updateSpedList(CompanyUser companyUser, CreateSpedTO to) throws UnprocessableEntityException;

    /**
     * Receives an array of documents speed, to convert it to an entity {@see Sped} and return the list of said entity
     *
     * @param cnpj  {@link String} the CNPJ of the company to which the SPED belongs
     * @param files {@link MultipartFile}[] the files to read
     * @return {@link List}<{@link Sped}> the converted sped list
     * @throws UnprocessableEntityException if any error occur retrieving the person data
     */
    List<Sped> readSpedFiles(String cnpj, MultipartFile[] files) throws UnprocessableEntityException;

    /**
     * Perform the financial, vertical and horizontal analysis of the company's sped list
     *
     * @param companyUser {@link CompanyUser} the company to update
     * @return {@link CompanyLearning4TO} the analysis data
     */
    CompanyLearning4TO getAnalysis(CompanyUser companyUser);

}
