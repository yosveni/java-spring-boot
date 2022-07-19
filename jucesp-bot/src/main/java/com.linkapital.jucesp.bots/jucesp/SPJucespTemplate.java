package com.linkapital.jucesp.bots.jucesp;


import com.gargoylesoftware.css.parser.CSSErrorHandler;
import com.gargoylesoftware.css.parser.CSSException;
import com.gargoylesoftware.css.parser.CSSParseException;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.ScriptException;
import com.gargoylesoftware.htmlunit.UnexpectedPage;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlHiddenInput;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableBody;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.html.parser.HTMLParserListener;
import com.gargoylesoftware.htmlunit.javascript.JavaScriptErrorListener;
import com.linkapital.captcha.CaptchaErrorException;
import com.linkapital.captcha.CaptchaMetadata;
import com.linkapital.captcha.CaptchaSolver;
import com.linkapital.jucesp.bots.exceptions.CannotGetJucespFileException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.logging.Level;

import static java.lang.String.format;


public abstract class SPJucespTemplate {

    private static final String jucespUrl = "https://www.jucesponline.sp.gov.br";
    private static final int capFail = 100;
    protected final WebClient webClient;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final CaptchaSolver captchaSolver;

    public SPJucespTemplate(CaptchaSolver captchaSolver) {
        this.captchaSolver = captchaSolver;
        this.webClient = new WebClient();
    }

    public List<DocumentMetadata> getDocuments(String socialReason) throws CannotGetJucespFileException {
        List<DocumentMetadata> results = new ArrayList<>();
        var failCount = 0;

        try {
            SPJucespCredentials credentials = SPJucespCredentialsGenerator.getInstance().getCredentials();
            var cpf = credentials.getCpf();
            var password = credentials.getPassword();
            logger.info(format("%s Credenciales de jucesp usadas ---- %s", cpf, password));

            HtmlTable resultTable = null;
            var r = new Random();
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            webClient.addRequestHeader("Sec-Fetch-Dest", "document");
            webClient.addRequestHeader("Sec-Fetch-Mode", "cors");
            webClient.addRequestHeader("Sec-Fetch-Site", "same-origin");
            webClient.addRequestHeader("Sec-Fetch-User", "?1");
            this.setLogConfig(webClient);

            HtmlPage page = webClient.getPage(getUrlHomePage());
            HtmlTextInput textField = (HtmlTextInput) page.getElementById("ctl00_cphContent_frmBuscaSimples_txtPalavraChave");
            HtmlSubmitInput searchButton = (HtmlSubmitInput) page.getElementById("ctl00_cphContent_frmBuscaSimples_btPesquisar");
            textField.setText(socialReason);
            HtmlForm submitForm = searchButton.getEnclosingForm();
            page = webClient.getPage(submitForm.getWebRequest(searchButton));

            var flag = true;
            while (resultTable == null && failCount < capFail && flag) {
                var captcha = getCaptcha(page);
                if (captcha != null) {
                    HtmlTextInput captcha1Input = page.getFirstByXPath("//input[@name='ctl00$cphContent$gdvResultadoBusca$CaptchaControl1']");
                    captcha1Input.setText(captcha.getText());
                    HtmlSubmitInput captcha1SubmitInput = (HtmlSubmitInput) page.getElementById("ctl00_cphContent_gdvResultadoBusca_btEntrar");
                    HtmlForm captcha1Form = captcha1SubmitInput.getEnclosingForm();

                    page = webClient.getPage(captcha1Form.getWebRequest(captcha1SubmitInput));
                    resultTable = (HtmlTable) page.getElementById("ctl00_cphContent_gdvResultadoBusca_gdvContent");

                    if (Objects.isNull(resultTable))
                        captchaSolver.report(captcha.getId());

                    failCount++;
                    Thread.sleep(3000L + r.nextInt(2000));
                } else {
                    flag = false;
                    resultTable = (HtmlTable) page.getElementById("ctl00_cphContent_gdvResultadoBusca_gdvContent");
                }
            }

            Thread.sleep(3000L + r.nextInt(2000));
            checkCapFail(failCount, socialReason);
            failCount = 0;
            Iterator iteratorTable = resultTable.getChildElements().iterator();
            HtmlTableBody tableBody = (HtmlTableBody) iteratorTable.next();
            Iterator rowIterator = tableBody.getChildElements().iterator();
            rowIterator.next();
            HtmlTableRow row = (HtmlTableRow) rowIterator.next();
            HtmlTableCell tableCell = row.getCell(0);
            Iterator cellIterator = tableCell.getChildElements().iterator();
            HtmlAnchor documentLink = (HtmlAnchor) cellIterator.next();
            Page linkPage = getDocumentPage(documentLink);

            if (linkPage instanceof UnexpectedPage unexpected) {
                results.add(DocumentMetadata
                        .builder()
                        .data(unexpected.getInputStream())
                        .build());
            } else {
                page = (HtmlPage) linkPage;
                Page pageResult;
                flag = true;

                while (flag && failCount < capFail) {
                    var captcha = getCaptcha(page);
                    if (captcha != null) {
                        HtmlTextInput cpfInput = (HtmlTextInput) page.getElementById("ctl00_cphContent_txtEmail");
                        cpfInput.setText(cpf);
                        HtmlPasswordInput passwordInput = (HtmlPasswordInput) page.getElementById("ctl00_cphContent_txtSenha");
                        passwordInput.setText(password);
                        HtmlTextInput captchaInput = page.getFirstByXPath("//input[@name='ctl00$cphContent$CaptchaControl1']");
                        HtmlSubmitInput enterSubmitButton = (HtmlSubmitInput) page.getElementById("ctl00_cphContent_btEntrar");
                        captchaInput.setText(captcha.getText());
                        HtmlForm captcha1Form = enterSubmitButton.getEnclosingForm();
                        pageResult = webClient.getPage(captcha1Form.getWebRequest(enterSubmitButton));
                        results = getDocuments(pageResult);

                        if (Objects.isNull(results) || !results.isEmpty()) {
                            flag = false;
                        } else {
                            captchaSolver.report(captcha.getId());
                            page = (HtmlPage) pageResult;
                            failCount++;
                            Thread.sleep(3000L + r.nextInt(2000));
                        }
                    } else {
                        results = getDocuments(page);
                        flag = false;
                    }
                }

                page = webClient.getPage(getUrlHomePage());
                logout(page);
                checkCapFail(failCount, socialReason);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(format("%s -- social reason: %s", e.getMessage(), socialReason));
            throw new CannotGetJucespFileException(e.getMessage());
        }

        return results;
    }

    protected abstract String getUrlHomePage();

    protected Page getDocumentPage(HtmlAnchor documentLink) throws IOException {
        return documentLink.click();
    }

    protected List<DocumentMetadata> getDocuments(Page pageResult) throws IOException {
        var results = new ArrayList<DocumentMetadata>();
        if (pageResult instanceof UnexpectedPage unexpected)
            results.add(DocumentMetadata
                    .builder()
                    .data(unexpected.getInputStream())
                    .build());

        return results;
    }

    private CaptchaMetadata getCaptcha(HtmlPage page) throws IOException, CaptchaErrorException {
        HtmlImage image = page.getFirstByXPath("//img[contains(@src,'Captcha')]");
        if (image == null)
            return null;

        var url = new URL(format("%s/%s", jucespUrl, image.getSrcAttribute()));
        return captchaSolver.solve(url.openStream());
    }

    private void checkCapFail(int failCount, String socialReason) throws CannotGetJucespFileException {
        if (failCount >= capFail) {
            var message = format("Cannot decode captcha -- social reason: %s", socialReason);
            logger.error(message);
            throw new CannotGetJucespFileException(message);
        }
    }

    private void logout(HtmlPage page) throws IOException {
        HtmlHiddenInput hiddenInput = (HtmlHiddenInput) page.getElementById("__EVENTTARGET");
        HtmlForm submitForm = hiddenInput.getEnclosingForm();
        hiddenInput.setAttribute("value", "ctl00$frmLogin$lbtSair");
        webClient.getPage(submitForm.getWebRequest(hiddenInput));
    }

    private void setLogConfig(WebClient webClient) {
        java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);

        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);

        webClient.setIncorrectnessListener((arg0, arg1) -> {
            // TODO Auto-generated method stub
        });

        webClient.setCssErrorHandler(new CSSErrorHandler() {
            @Override
            public void warning(CSSParseException exception) throws CSSException {
            }

            @Override
            public void error(CSSParseException exception) throws CSSException {
            }

            @Override
            public void fatalError(CSSParseException exception) throws CSSException {
            }
        });

        webClient.setJavaScriptErrorListener(new JavaScriptErrorListener() {
            @Override
            public void scriptException(HtmlPage page, ScriptException scriptException) {
            }

            @Override
            public void timeoutError(HtmlPage page, long allowedTime, long executionTime) {
            }

            @Override
            public void malformedScriptURL(HtmlPage page, String url, MalformedURLException malformedURLException) {
            }

            @Override
            public void loadScriptError(HtmlPage page, URL scriptUrl, Exception exception) {
            }

            @Override
            public void warn(String message, String sourceName, int line, String lineSource, int lineOffset) {
            }
        });

        webClient.setHTMLParserListener(new HTMLParserListener() {
            @Override
            public void error(String message, URL url, String html, int line, int column, String key) {
            }

            @Override
            public void warning(String message, URL url, String html, int line, int column, String key) {
            }
        });
    }

}
