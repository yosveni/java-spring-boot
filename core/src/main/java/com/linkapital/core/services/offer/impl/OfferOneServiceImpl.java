package com.linkapital.core.services.offer.impl;

import com.linkapital.core.services.bank_nomenclature.BankNomenclatureService;
import com.linkapital.core.services.company_user.CompanyUserService;
import com.linkapital.core.services.directory.DirectoryService;
import com.linkapital.core.services.indicative_offer.IndicativeOfferService;
import com.linkapital.core.services.notification.EmailService;
import com.linkapital.core.services.notification.WebsocketService;
import com.linkapital.core.services.offer.datasource.OfferRepository;
import com.linkapital.core.services.partner_bank.PartnerBankService;
import com.linkapital.core.services.security.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OfferOneServiceImpl extends BaseOfferServiceImpl {

    public OfferOneServiceImpl(UserService userService,
                               CompanyUserService companyUserService,
                               PartnerBankService partnerBankService,
                               IndicativeOfferService indicativeOfferService,
                               DirectoryService directoryService,
                               OfferRepository offerRepository,
                               EmailService emailService,
                               BankNomenclatureService bankNomenclatureService,
                               WebsocketService webSocketService) {
        super(userService,
                companyUserService,
                partnerBankService,
                indicativeOfferService,
                directoryService,
                offerRepository,
                emailService,
                bankNomenclatureService,
                webSocketService);
    }

}
