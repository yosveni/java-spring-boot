package com.linkapital.core.services.commission;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.commission.contract.to.create.CreateCommissionTO;
import com.linkapital.core.services.commission.contract.to.get.CommissionInstallmentTO;
import com.linkapital.core.services.commission.contract.to.get.CommissionTO;
import com.linkapital.core.services.commission.contract.to.get.CommissionUserTO;
import com.linkapital.core.services.commission.datasource.domain.Commission;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Default interface for {@link CommissionService}
 * Service with the responsibility of carrying out operations and business logic in the entities {@link Commission}
 *
 * @since 0.0.1
 */
public interface CommissionService {

    /**
     * Create commission.
     *
     * @param to {@link CreateCommissionTO} the commission data
     * @return {@link CommissionTO}
     */
    CommissionTO create(CreateCommissionTO to) throws UnprocessableEntityException;

    /**
     * Get all commissions given a user id
     *
     * @param userId {@link Long} the user id
     * @return {@link List}<{@link CommissionUserTO}> the commission list
     */
    List<CommissionUserTO> getAllCommissionByUserId(Long userId);

    /**
     * Upload fiscal notes to commission installments.
     *
     * @param commissionInstallmentIds {@link Long} the commissions installments id
     * @param file                     {@link MultipartFile} the note fiscal
     * @return {@link List}<{@link CommissionInstallmentTO}> the commissions installments list
     */
    List<CommissionInstallmentTO> uploadFiscalNotesToCommissionInstalments(List<Long> commissionInstallmentIds, MultipartFile file);

}
