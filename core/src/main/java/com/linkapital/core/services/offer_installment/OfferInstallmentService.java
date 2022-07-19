package com.linkapital.core.services.offer_installment;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.offer_installment.contract.to.CreateOfferInstallmentTO;
import com.linkapital.core.services.offer_installment.contract.to.OfferInstallmentTO;
import com.linkapital.core.services.offer_installment.contract.to.UpdateOfferInstallmentTO;
import com.linkapital.core.services.offer_installment.datasource.domain.OfferInstallment;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Default interface for {@link OfferInstallmentService}
 * Service with the responsibility of performing operations on the {@link OfferInstallment} entity
 *
 * @since 0.0.1
 */
public interface OfferInstallmentService {

    /**
     * Create new offer installment
     *
     * @param to {@link CreateOfferInstallmentTO} the data to create the installment of the offer
     * @return {@link List}<{@link OfferInstallmentTO}>
     * @throws UnprocessableEntityException if any error occurs
     */
    List<OfferInstallmentTO> create(CreateOfferInstallmentTO to) throws UnprocessableEntityException;

    /**
     * Update offer installment
     *
     * @param to {@link UpdateOfferInstallmentTO} the data to create the installment of the offer
     * @return {@link OfferInstallmentTO}
     * @throws UnprocessableEntityException if any error occurs
     */
    OfferInstallmentTO update(UpdateOfferInstallmentTO to) throws UnprocessableEntityException;

    /**
     * Upload payment ticket to offer installment.
     *
     * @param offerInstallmentId {@link long} the offer installment id
     * @param file               {@link MultipartFile} the file of voucher or payment slip
     * @return {@link OfferInstallmentTO}
     * @throws UnprocessableEntityException if {@link OfferInstallment} not found by id
     */
    OfferInstallmentTO uploadPaymentTicket(long offerInstallmentId, MultipartFile file) throws UnprocessableEntityException;

    /**
     * Get offer installment by id
     *
     * @param id {@link Long} the offer installment id
     * @return {@link OfferInstallment}
     * @throws UnprocessableEntityException if any error occurs
     */
    OfferInstallment getById(long id) throws UnprocessableEntityException;

    /**
     * Delete offer installment
     *
     * @param offerId {@link Long} the offer id
     * @param id      {@link Long} the offer installment id
     * @throws UnprocessableEntityException if any error occurs
     */
    void delete(long offerId, long id) throws UnprocessableEntityException;

}
