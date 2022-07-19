package com.linkapital.core.services.company.datasource.impl;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.company.datasource.CompanyRepositoryCustom;
import com.linkapital.core.services.company.datasource.domain.Company;
import com.linkapital.core.services.company.datasource.domain.CompanyMainInfo;
import com.linkapital.core.services.company_user.datasource.domain.CompanyUser;
import com.linkapital.core.services.indicative_offer.contract.enums.IndicativeOfferState;
import com.linkapital.core.services.offer.contract.enums.FilterOfferType;
import com.linkapital.core.services.offer.contract.enums.OfferState;
import com.linkapital.core.services.offer.datasource.domain.Offer;
import com.linkapital.core.services.security.datasource.domain.User;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.linkapital.core.configuration.context.MessageContextHolder.msg;
import static com.linkapital.core.services.indicative_offer.contract.enums.IndicativeOfferState.OFFER_REQUEST;
import static com.linkapital.core.services.offer.contract.enums.OfferState.ACCEPTED;
import static com.linkapital.core.services.offer.contract.enums.OfferState.CONTRACT_SIGNED;
import static com.linkapital.core.services.offer.contract.enums.OfferState.IN_CONTRACT;
import static javax.persistence.criteria.JoinType.LEFT;
import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * Indicative (Novas)
 * Son todas las que el state del indicativeOffer es diferente a offerRequest
 * <p>
 * Request (Solicitada)
 * Son todas las que el state del indicativeOffer es offerRequest, menos las que todas sus ofertas están en estate
 * ACCEPTED, IN_CONTRACT o CONTRACT_SIGNED
 * <p>
 * Progress (Andamento)
 * Son todas las que al menos una de la lista de ofertas el state de la oferta sea ACCEPTED o IN_CONTRACT
 * <p>
 * Contracted (Contratadas)
 * Son todas las que al menos una de la lista de ofertas el state de la oferta sea CONTRACT_SIGNED
 */

public class CompanyRepositoryImpl implements CompanyRepositoryCustom {

    private static final String CREDIT_REQUESTED = "creditRequested";
    private static final String INVOICING_INFORMED = "invoicingInformed";
    private static final String CREATED = "created";
    private static final String FANTASY_NAME = "fantasyName";
    private static final String CNPJ = "cnpj";
    private static final String SOCIAL_REASON = "socialReason";
    private static final String OPENING_DATE = "openingDate";
    private static final String NAME_USER = "nameUser";
    private static final String NAME = "name";
    private static final Map<String, Class<?>> filterClassMap = new HashMap<>();
    private static final Map<String, String> filterFieldMap = new HashMap<>();
    private static final String STATE = "state";

    static {
        filterClassMap.put(CREDIT_REQUESTED, CompanyUser.class);
        filterClassMap.put(INVOICING_INFORMED, CompanyUser.class);
        filterClassMap.put(CREATED, CompanyUser.class);
        filterClassMap.put(FANTASY_NAME, Company.class);
        filterClassMap.put(CNPJ, CompanyMainInfo.class);
        filterClassMap.put(SOCIAL_REASON, CompanyMainInfo.class);
        filterClassMap.put(OPENING_DATE, CompanyMainInfo.class);
        filterClassMap.put(NAME_USER, User.class);
    }

    static {
        filterFieldMap.put(CREDIT_REQUESTED, CREDIT_REQUESTED);
        filterFieldMap.put(INVOICING_INFORMED, INVOICING_INFORMED);
        filterFieldMap.put(CREATED, CREATED);
        filterFieldMap.put(FANTASY_NAME, FANTASY_NAME);
        filterFieldMap.put(CNPJ, CNPJ);
        filterFieldMap.put(SOCIAL_REASON, SOCIAL_REASON);
        filterFieldMap.put(OPENING_DATE, OPENING_DATE);
        filterFieldMap.put(NAME_USER, "name");
    }

    private final EntityManager em;

    private CompanyRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public PageImpl getByFilterGlobal(FilterOfferType type,
                                      String filter,
                                      Pageable pageable,
                                      String sort,
                                      String order) throws UnprocessableEntityException {
        var sqlQuery = this.getQuery(type, filter, sort, order);
        var totalRecord = sqlQuery.getResultList().size();

        sqlQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        sqlQuery.setMaxResults(pageable.getPageSize());

        return new PageImpl<>(sqlQuery.getResultList(), pageable, totalRecord);
    }

    public Query getQuery(FilterOfferType type,
                          String filter,
                          String sort,
                          String order) throws UnprocessableEntityException {
        var cb = em.getCriteriaBuilder();
        var query = cb.createQuery(CompanyUser.class);
        var predicatesLike = new ArrayList<>();
        Predicate predicateForType;
        Predicate predicate = null;

        var rootCompanyUser = query.from(CompanyUser.class);
        var joinCompany = rootCompanyUser.join("company", LEFT);
        var joinMainInfo = joinCompany.join("mainInfo", LEFT);
        var joinUser = rootCompanyUser.join("user", LEFT);
        var joinAddress = joinMainInfo.join("address", LEFT);
        var joinCompanyPartners = joinCompany.join("partners", LEFT);
        var joinPerson = joinCompanyPartners.join("person", LEFT);

        var joinIndicativeOfferOne = rootCompanyUser.join("indicativeOfferOne", LEFT);
        var joinIndicativeOfferTwo = rootCompanyUser.join("indicativeOfferTwo", LEFT);
        var joinIndicativeOfferThree = rootCompanyUser.join("indicativeOfferThree", LEFT);
        var joinIndicativeOfferFour = rootCompanyUser.join("indicativeOfferFour", LEFT);

        predicateForType = switch (type) {
            case INDICATIVE -> getPredicateForTypeIndicative(cb, joinIndicativeOfferOne,
                    joinIndicativeOfferTwo, joinIndicativeOfferThree, joinIndicativeOfferFour, OFFER_REQUEST, rootCompanyUser);
            case REQUESTED -> getPredicateForTypeRequested(query, cb, joinIndicativeOfferOne,
                    joinIndicativeOfferTwo, joinIndicativeOfferThree, joinIndicativeOfferFour,
                    OFFER_REQUEST, new ArrayList<>(List.of(ACCEPTED, IN_CONTRACT, CONTRACT_SIGNED)));
            case PROGRESS -> getPredicateForTypeProgressOrContracted(query, cb, joinIndicativeOfferOne,
                    joinIndicativeOfferTwo, joinIndicativeOfferThree, joinIndicativeOfferFour,
                    new ArrayList<>(List.of(ACCEPTED, IN_CONTRACT)));
            case CONTRACTED -> getPredicateForTypeProgressOrContracted(query, cb, joinIndicativeOfferOne,
                    joinIndicativeOfferTwo, joinIndicativeOfferThree, joinIndicativeOfferFour,
                    new ArrayList<>(List.of(CONTRACT_SIGNED)));
        };

        if (filter != null) {
            var lowerCaseFilter = filter.toLowerCase();
            predicatesLike.add(cb.like(cb.lower(joinCompany.get(FANTASY_NAME)).as(String.class),
                    "%" + lowerCaseFilter + "%"));
            predicatesLike.add(cb.like(cb.lower(joinCompany.get("estimatedBilling")).as(String.class),
                    "%" + lowerCaseFilter + "%"));

            /*For CompanyMainInfo*/
            predicatesLike.add(cb.like(cb.lower(joinMainInfo.get(CNPJ)).as(String.class),
                    "%" + lowerCaseFilter + "%"));
            predicatesLike.add(cb.like(cb.lower(joinMainInfo.get(SOCIAL_REASON)),
                    "%" + lowerCaseFilter + "%"));

            /*For Address*/
            predicatesLike.add(cb.like(cb.lower(joinAddress.get("address1")).as(String.class),
                    "%" + lowerCaseFilter + "%"));
            predicatesLike.add(cb.like(cb.lower(joinAddress.get("zip")).as(String.class),
                    "%" + lowerCaseFilter + "%"));
            predicatesLike.add(cb.like(cb.lower(joinAddress.get("uf")).as(String.class),
                    "%" + lowerCaseFilter + "%"));

            /*For username*/
            predicatesLike.add(cb.like(cb.lower(joinUser.get(NAME)).as(String.class),
                    "%" + lowerCaseFilter + "%"));

            /*For partner*/
            predicatesLike.add(cb.like(cb.lower(joinPerson.get(NAME)).as(String.class),
                    "%" + lowerCaseFilter + "%"));

            //predicates.add(cb.or(predicatesLike.toArray(new Predicate[predicatesLike.size()])));
            predicate = cb.or(predicatesLike.toArray(new Predicate[predicatesLike.size()]));
        }

        query.groupBy(rootCompanyUser, joinIndicativeOfferOne, joinIndicativeOfferTwo, joinIndicativeOfferThree,
                joinIndicativeOfferFour, joinCompany, joinMainInfo, joinUser);

        var finalPredicate = predicateForType;
        if (predicate != null)
            finalPredicate = cb.and(finalPredicate, predicate);

        query.select(rootCompanyUser).where(finalPredicate);

        if (!isEmpty(sort) && !isEmpty(order)) {
            var sortClass = getClass(sort);
            From<?, ?> from = null;

            if (sortClass.equals(CompanyUser.class))
                from = rootCompanyUser;
            if (sortClass.equals(Company.class))
                from = joinCompany;
            if (sortClass.equals(CompanyMainInfo.class))
                from = joinMainInfo;
            if (sortClass.equals(User.class))
                from = joinUser;

            var expr = from.get(getSort(sort));

            if (order.equalsIgnoreCase("ASC"))
                query.orderBy(cb.asc(expr));
            else
                query.orderBy(cb.desc(expr));
        } else {
            /*var caseExpression = cb.selectCase()
                    .when(cb.equal(joinIndicativeOfferOne.get(STATE), OFFER_REQUEST), 1)
                    .when(cb.equal(joinIndicativeOfferTwo.get(STATE), OFFER_REQUEST), 2)
                    .when(cb.equal(joinIndicativeOfferThree.get(STATE), OFFER_REQUEST), 3)
                    .when(cb.equal(joinIndicativeOfferFour.get(STATE), OFFER_REQUEST), 4);
            var offerTypeNumbersOrder = cb.asc(caseExpression);
            query.orderBy(offerTypeNumbersOrder);*/
            query.orderBy(cb.desc(rootCompanyUser.get("created")));
        }

        return em.createQuery(query);
    }

    private Predicate getPredicateForTypeIndicative(CriteriaBuilder cb,
                                                    Path<?> paramOne,
                                                    Path<?> paramTwo,
                                                    Path<?> paramThree,
                                                    Path<?> paramFour,
                                                    IndicativeOfferState indicativeOfferState,
                                                    Root<CompanyUser> rootCompanyUser) {
        var predicateIndicativeOfferOne = cb.and(cb.notEqual(paramOne.get(STATE), indicativeOfferState));
        var predicateIndicativeOfferTwo = cb.and(cb.notEqual(paramTwo.get(STATE), indicativeOfferState));
        var predicateIndicativeOfferThree = cb.and(cb.notEqual(paramThree.get(STATE), indicativeOfferState));
        var predicateIndicativeOfferFour = cb.and(cb.notEqual(paramFour.get(STATE), indicativeOfferState));

        var predicatesIndicativeOffers = new ArrayList<>();
        predicatesIndicativeOffers.add(predicateIndicativeOfferOne);
        predicatesIndicativeOffers.add(predicateIndicativeOfferTwo);
        predicatesIndicativeOffers.add(predicateIndicativeOfferThree);
        predicatesIndicativeOffers.add(predicateIndicativeOfferFour);
        Predicate predicatesIndicativeOffer = cb.and(predicatesIndicativeOffers.toArray(new Predicate[predicatesIndicativeOffers.size()]));

        //Predicado para filtrar ademas las empresas creadas desde el backoffice
        var predicateCompanyUserReady = cb.or(cb.equal(rootCompanyUser.get("companyState"), 0));

        return cb.or(predicatesIndicativeOffer, predicateCompanyUserReady);
    }

    private Predicate getPredicateForTypeRequested(CriteriaQuery<CompanyUser> query,
                                                   CriteriaBuilder cb,
                                                   Join<?, ?> joinIndicativeOfferOne,
                                                   Join<?, ?> joinIndicativeOfferTwo,
                                                   Join<?, ?> joinIndicativeOfferThree,
                                                   Join<?, ?> joinIndicativeOfferFour,
                                                   IndicativeOfferState indicativeOfferState,
                                                   List<OfferState> offerState) {
        var joinOfferIndicativeOfferOne = joinIndicativeOfferOne.join("offers", LEFT);
        var joinOfferIndicativeOfferTwo = joinIndicativeOfferTwo.join("offers", LEFT);
        var joinOfferIndicativeOfferThree = joinIndicativeOfferThree.join("offers", LEFT);
        var joinOfferIndicativeOfferFour = joinIndicativeOfferFour.join("offers", LEFT);

        var predicatesIndicativeOffer = new ArrayList<>();
        var predicateIndicativeOfferOne = cb.and(
                cb.equal(joinIndicativeOfferOne.get(STATE), indicativeOfferState),
                cb.in(joinOfferIndicativeOfferOne).value(getSubQueryOfferByState(query, cb,
                        joinOfferIndicativeOfferOne, offerState)).not());
        var predicateIndicativeOfferTwo = cb.and(
                cb.equal(joinIndicativeOfferTwo.get(STATE), indicativeOfferState),
                cb.in(joinOfferIndicativeOfferTwo).value(getSubQueryOfferByState(query, cb,
                        joinOfferIndicativeOfferTwo, offerState)).not());
        var predicateIndicativeOfferThree = cb.and(
                cb.equal(joinIndicativeOfferThree.get(STATE), indicativeOfferState),
                cb.in(joinOfferIndicativeOfferThree).value(getSubQueryOfferByState(query, cb,
                        joinOfferIndicativeOfferThree, offerState)).not());
        var predicateIndicativeOfferFour = cb.and(
                cb.equal(joinIndicativeOfferFour.get(STATE), indicativeOfferState),
                cb.in(joinOfferIndicativeOfferFour).value(getSubQueryOfferByState(query, cb,
                        joinOfferIndicativeOfferFour, offerState)).not());

        predicatesIndicativeOffer.add(predicateIndicativeOfferOne);
        predicatesIndicativeOffer.add(predicateIndicativeOfferTwo);
        predicatesIndicativeOffer.add(predicateIndicativeOfferThree);
        predicatesIndicativeOffer.add(predicateIndicativeOfferFour);

        return cb.or(predicatesIndicativeOffer.toArray(new Predicate[predicatesIndicativeOffer.size()]));
    }

    private Predicate getPredicateForTypeProgressOrContracted(CriteriaQuery<CompanyUser> query,
                                                              CriteriaBuilder cb,
                                                              Join<?, ?> joinIndicativeOfferOne,
                                                              Join<?, ?> joinIndicativeOfferTwo,
                                                              Join<?, ?> joinIndicativeOfferThree,
                                                              Join<?, ?> joinIndicativeOfferFour,
                                                              List<OfferState> offerState) {
        var joinOfferIndicativeOfferOne = joinIndicativeOfferOne.join("offers", LEFT);
        var joinOfferIndicativeOfferTwo = joinIndicativeOfferTwo.join("offers", LEFT);
        var joinOfferIndicativeOfferThree = joinIndicativeOfferThree.join("offers", LEFT);
        var joinOfferIndicativeOfferFour = joinIndicativeOfferFour.join("offers", LEFT);

        var predicatesOffersIndicativeOffer = new ArrayList<>();
        predicatesOffersIndicativeOffer.add(cb.in(joinOfferIndicativeOfferOne)
                .value(getSubQueryOfferByState(query, cb, joinOfferIndicativeOfferOne, offerState)));
        predicatesOffersIndicativeOffer.add(cb.in(joinOfferIndicativeOfferTwo)
                .value(getSubQueryOfferByState(query, cb, joinOfferIndicativeOfferTwo, offerState)));
        predicatesOffersIndicativeOffer.add(cb.in(joinOfferIndicativeOfferThree)
                .value(getSubQueryOfferByState(query, cb, joinOfferIndicativeOfferThree, offerState)));
        predicatesOffersIndicativeOffer.add(cb.in(joinOfferIndicativeOfferFour)
                .value(getSubQueryOfferByState(query, cb, joinOfferIndicativeOfferFour, offerState)));

        return cb.or(predicatesOffersIndicativeOffer.toArray(new Predicate[predicatesOffersIndicativeOffer.size()]));
    }

    private Subquery<Offer> getSubQueryOfferByState(CriteriaQuery<CompanyUser> query,
                                                    CriteriaBuilder cb,
                                                    Path<?> param,
                                                    List<OfferState> offerStates) {
        var subQuery = query.subquery(Offer.class);
        var rootOffer = subQuery.from(Offer.class);

        return subQuery.select(rootOffer).where(cb.in(param.get(STATE)).value(offerStates) /*equal(param.get(STATE), offerState)*/);
    }

    private Class<?> getClass(String sort) throws UnprocessableEntityException {
        if (!filterClassMap.containsKey(sort))
            throw new UnprocessableEntityException(msg("property.not.found.to.sort"));

        return filterClassMap.get(sort);
    }

    private String getSort(String sort) throws UnprocessableEntityException {
        if (!filterFieldMap.containsKey(sort))
            throw new UnprocessableEntityException(msg("property.not.found.to.sort"));

        return filterFieldMap.get(sort);
    }

}
