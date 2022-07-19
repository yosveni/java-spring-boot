package com.linkapital.jucesp.bots.jucesp;

import com.linkapital.captcha.CaptchaSolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SPJucespBuilderImpl implements SPJucespBuilder {

    @Autowired
    private CaptchaSolver captchaSolver;

    @Override
    public SPJucespTemplate createJucespRegistration() {
        return SPJucespRegistration.getInstance(captchaSolver);
    }

    @Override
    public SPJucespTemplate createJucespSimplifiedCertification() {
        return SPJucespSimplifiedCertification.getInstance(captchaSolver);
    }

    @Override
    public SPJucespTemplate createJucespArchivedDocument() {
        return SPJucespArchivedDocument.getInstance(captchaSolver);
    }

}
