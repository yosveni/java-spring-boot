package com.linkapital.core.services.offer;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.bank_nomenclature.contract.to.get.CompanyBankNomenclatureTO;
import com.linkapital.core.services.company.contract.to.SelectIndicativeOfferTO;
import com.linkapital.core.services.company_user.contract.to.CompanyClientTO;
import com.linkapital.core.services.company_user.contract.to.RequestOfferTO;
import com.linkapital.core.services.directory.contract.to.DirectoryTO;
import com.linkapital.core.services.offer.contract.to.AcceptOfferTO;
import com.linkapital.core.services.offer.contract.to.AcceptPartnerOfferTO;
import com.linkapital.core.services.offer.contract.to.CreateOfferLogTO;
import com.linkapital.core.services.offer.contract.to.UpdateOfferStateTO;
import com.linkapital.core.services.offer.contract.to.create.CreateOfferTO;
import com.linkapital.core.services.offer.contract.to.get.OfferTO;
import com.linkapital.core.services.offer.contract.to.update.UpdateOfferTO;
import com.linkapital.core.services.offer.datasource.domain.Offer;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * Default interface for {@link OfferService}
 * Service with the responsibility of performing operations on the {@link Offer} entity
 *
 * @since 0.0.1
 */
public interface OfferService {

    /**
     * Save offer.
     *
     * @param offer {@link Offer} the offer data
     * @return {@link Offer}
     */
    Offer save(Offer offer);

    /**
     * Create offer list.
     *
     * @param to {@link List}<{@link CreateOfferTO}> the offer list data
     * @return {@link List}<{@link OfferTO}>
     * @throws UnprocessableEntityException if any error occurs
     */
    List<OfferTO> create(List<CreateOfferTO> to) throws UnprocessableEntityException;

    /**
     * Create notification offer.
     *
     * @param to {@link CreateOfferLogTO} the notification offer
     * @return {@link OfferTO}
     * @throws UnprocessableEntityException if any error occurs
     */
    OfferTO createNotification(CreateOfferLogTO to) throws UnprocessableEntityException;

    /**
     * Update offer.
     *
     * @param to {@link UpdateOfferTO} the offer data to update
     * @return {@link OfferTO}
     * @throws UnprocessableEntityException if any error occurs
     */
    OfferTO update(UpdateOfferTO to) throws UnprocessableEntityException;

    /**
     * Accept offer from bank partner
     *
     * @param to {@link AcceptPartnerOfferTO} the data to accept the partner offer
     * @return {@link OfferTO}
     * @throws UnprocessableEntityException if any error occurs
     */
    OfferTO acceptPartnerOffer(AcceptPartnerOfferTO to) throws UnprocessableEntityException;

    /**
     * Update offer state
     *
     * @param to {@link UpdateOfferStateTO} the data to set the offer state
     * @return {@link OfferTO}
     * @throws UnprocessableEntityException if any error occurs
     */
    OfferTO updateState(UpdateOfferStateTO to) throws UnprocessableEntityException;

    /**
     * Upload the offer contract, and change the offer status to in contract
     *
     * @param offerId      {@link Long} the offer id
     * @param contractDate {@link Date} the date of the contract
     * @param files        {@link MultipartFile}[] the signed contract
     * @return {@link OfferTO}
     * @throws UnprocessableEntityException if any error occurs
     */
    OfferTO uploadOfferContract(long offerId, Date contractDate, MultipartFile[] files) throws UnprocessableEntityException;

    /**
     * Select indicative offer
     *
     * @param to {@link SelectIndicativeOfferTO} the offert data
     * @return {@link CompanyClientTO} the company client to
     * @throws UnprocessableEntityException if any error occurs
     */
    CompanyClientTO selectIndicativeOffer(SelectIndicativeOfferTO to) throws UnprocessableEntityException;

    /**
     * Request offer
     *
     * @param to {@link RequestOfferTO} the request offer data
     * @return {@link CompanyClientTO}
     * @throws UnprocessableEntityException if any error occurs
     */
    CompanyClientTO requestOffer(RequestOfferTO to) throws UnprocessableEntityException;

    /**
     * Accept offer
     *
     * @param to {@link AcceptOfferTO} the accept offer data
     * @return {@link CompanyClientTO}
     * @throws UnprocessableEntityException if any error occurs
     */
    CompanyClientTO acceptOffer(AcceptOfferTO to) throws UnprocessableEntityException;

    /**
     * Get offer by id.
     *
     * @param id {@link Long} the id
     * @return {@link Offer}
     * @throws UnprocessableEntityException if any error occurs
     */
    Offer getById(Long id) throws UnprocessableEntityException;

    /**
     * Get all offers
     *
     * @return {@link List}<{@link OfferTO}>
     */
    List<OfferTO> getAll();

    /**
     * Get all offers by cnpj
     *
     * @param cnpj {@link String} the company cnpj
     * @return {@link List}<{@link OfferTO}>
     */
    List<OfferTO> getAllByCnpj(String cnpj);

    /**
     * Get all offers by user id and commission is not null.
     *
     * @param id {@link Long} the user id
     * @return {@link List}<{@link Offer}> the offers list
     */
    List<Offer> getAllByUserIdAndCommissionIsNotNull(long id);

    /**
     * Returns a directory list of comments for an offer.
     *
     * @param id {@link Long} the offer id
     * @return {@link List}<{@link DirectoryTO}> the directories list
     * @throws UnprocessableEntityException if any error occurs
     */
    List<DirectoryTO> getDocumentsForCommentsOffer(long id) throws UnprocessableEntityException;

    /**
     * Get all the bank nomenclature for the company by offer
     *
     * @param id {@link Long} the offer id
     * @return {@link List}<{@link CompanyBankNomenclatureTO}>
     */
    List<CompanyBankNomenclatureTO> getAllNomenclaturesById(long id) throws UnprocessableEntityException;

    /**
     * Delete offer by id
     *
     * @param id {@link Long} the id
     * @throws UnprocessableEntityException if any error occurs
     */
    void delete(Long id) throws UnprocessableEntityException;

}
