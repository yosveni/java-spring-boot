package com.linkapital.jucesp.bots.jucesp;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.linkapital.captcha.CaptchaSolver;
import lombok.NonNull;

import java.io.IOException;

public class SPJucespRegistration extends SPJucespTemplate {

    protected static SPJucespTemplate instance;

    private SPJucespRegistration(CaptchaSolver captchaSolver) {
        super(captchaSolver);
    }

    static SPJucespTemplate getInstance(CaptchaSolver captchaSolver) {
        if (instance == null)
            instance = new SPJucespRegistration(captchaSolver);

        return instance;
    }

    @Override
    protected String getUrlHomePage() {
        return "https://www.jucesponline.sp.gov.br/Pesquisa.aspx?IDProduto=2";
    }

    @Override
    protected Page getDocumentPage(@NonNull HtmlAnchor documentLink) throws IOException {
        var nireCode = documentLink.getFirstChild().getTextContent();
        var newUrl = "https://www.jucesponline.sp.gov.br/login.aspx?ReturnUrl=%2fRestricted%2fGeraDocumento.aspx%3fnire%3d"
                + nireCode + "%26tipoDocumento%3d2&nire=" + nireCode + "&tipoDocumento=2";

        return webClient.getPage(newUrl);
    }

}
