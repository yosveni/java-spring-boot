package com.linkapital.core.services.cnd.impl;

import com.gargoylesoftware.css.parser.CSSErrorHandler;
import com.gargoylesoftware.css.parser.CSSException;
import com.gargoylesoftware.css.parser.CSSParseException;
import com.gargoylesoftware.htmlunit.ScriptException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.html.parser.HTMLParserListener;
import com.gargoylesoftware.htmlunit.javascript.JavaScriptErrorListener;
import com.linkapital.core.services.cnd.CndService;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
import java.util.logging.Level;

import static com.linkapital.core.util.CnpjUtil.format;

@Service
public class CndServiceImpl implements CndService {

    private static final String urlTcu = "https://contas.tcu.gov.br/ords/f?p=704144:3:9697056895562::NO:3,4,6::" +
            "&cs=3FNPnH1AEv6jNHC_fkP2nNzX_okw";
    private static final String urlTst = "https://cndt-certidao.tst.jus.br/inicio.faces";
    private static final int captchaFail = 100;
    private static final Logger log = LoggerFactory.getLogger(CndServiceImpl.class);

    @Override
    public byte[] searchTcu(String cnpj) {
        byte[] file = null;
        var r = new Random();
        try (var client = getClient()) {
            HtmlPage page = client.getPage(urlTcu);

            var form = (HtmlForm) page.getElementById("wwvFlowForm");
            if (form != null) {
                var cnpjForm = (HtmlTextInput) page.getElementById("P3_CNPJ_INI");
                var button = (HtmlButton) page.getElementById("B28282078504980490841");
                if (cnpjForm != null && button != null) {
                    cnpjForm.setValueAttribute(format(cnpj));
                    Thread.sleep(3000L + r.nextInt(2000));
                    page = button.click();

                    Thread.sleep(3000L);
                    var pagePdf = page.getAnchors().get(10).openLinkInNewWindow();
                    file = pagePdf.getWebResponse().getContentAsStream().readAllBytes();
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return file;
    }

    //Todo terminar
    @Override
    public byte[] searchTst(String cnpj) {
        cnpj = format(cnpj);
        try (var client = getClient()) {
            HtmlPage htmlPage;
            htmlPage = client.getPage(urlTcu);
            HtmlSubmitInput submitInput = htmlPage.getElementByName("j_id_jsp_992698495_2:j_id_jsp_992698495_3");
            htmlPage = submitInput.click();

            HtmlTextInput inputCnpj = htmlPage.getHtmlElementById("gerarCertidaoForm:cpfCnpj");
            HtmlTextInput inputCaptchaValue = htmlPage.getHtmlElementById("idCaptcha");
            inputCnpj.setText(cnpj);

            HtmlImage image = htmlPage.getHtmlElementById("idImgBase64");
            boolean flag = false;
            while (image != null && !flag) {


            }


            log.info(htmlPage.asXml());
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        return new byte[0];
    }

    private @NonNull WebClient getClient() {
        var client = new WebClient();
        var options = client.getOptions();
        options.setJavaScriptEnabled(true);
        options.setCssEnabled(false);
        options.setThrowExceptionOnScriptError(false);
        options.setThrowExceptionOnFailingStatusCode(false);

        java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.commons.WebConsole").setLevel(Level.OFF);

        client.setIncorrectnessListener((arg0, arg1) -> {
            // TODO Auto-generated method stub
        });

        client.setCssErrorHandler(new CSSErrorHandler() {
            @Override
            public void warning(CSSParseException exception) throws CSSException {
                //document why this method is empty
            }

            @Override
            public void error(CSSParseException exception) throws CSSException {
                //document why this method is empty
            }

            @Override
            public void fatalError(CSSParseException exception) throws CSSException {
                //document why this method is empty
            }
        });

        client.setJavaScriptErrorListener(new JavaScriptErrorListener() {
            @Override
            public void scriptException(HtmlPage page, ScriptException scriptException) {
                //document why this method is empty
            }

            @Override
            public void timeoutError(HtmlPage page, long allowedTime, long executionTime) {
                //document why this method is empty
            }

            @Override
            public void malformedScriptURL(HtmlPage page, String url, MalformedURLException malformedURLException) {
                //document why this method is empty
            }

            @Override
            public void loadScriptError(HtmlPage page, URL scriptUrl, Exception exception) {
                //document why this method is empty
            }

            @Override
            public void warn(String message, String sourceName, int line, String lineSource, int lineOffset) {
                //document why this method is empty
            }
        });

        client.setHTMLParserListener(new HTMLParserListener() {
            @Override
            public void error(String message, URL url, String html, int line, int column, String key) {
                //document why this method is empty
            }

            @Override
            public void warning(String message, URL url, String html, int line, int column, String key) {
                //document why this method is empty
            }
        });

        return client;
    }

}
