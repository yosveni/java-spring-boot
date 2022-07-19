package com.linkapital.jucesp.bots.jucesp;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.UnexpectedPage;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlHiddenInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlRadioButtonInput;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableBody;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.linkapital.captcha.CaptchaSolver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SPJucespArchivedDocument extends SPJucespTemplate {

    private static SPJucespTemplate instance;

    private SPJucespArchivedDocument(CaptchaSolver captchaSolver) {
        super(captchaSolver);
    }

    static SPJucespTemplate getInstance(CaptchaSolver captchaSolver) {
        if (instance == null)
            instance = new SPJucespArchivedDocument(captchaSolver);

        return instance;
    }

    @Override
    protected String getUrlHomePage() {
        return "https://www.jucesponline.sp.gov.br/Pesquisa.aspx?IDProduto=12";
    }

    @Override
    protected List<DocumentMetadata> getDocuments(Page pageResult) throws IOException {
        var results = new ArrayList<DocumentMetadata>();

        if (pageResult instanceof HtmlPage htmlPage
                && htmlPage.getElementById("ctl00_cphContent_txtEmail") == null) {
            var flag = true;

            while (flag) {
                fillAllDocs(htmlPage, results);
                HtmlAnchor next = (HtmlAnchor) htmlPage.getElementById(
                        "ctl00_cphContent_GdvArquivamento_pgrGridView_btrNext_lbtText");
                if (next != null) {
                    HtmlHiddenInput hiddenInput = (HtmlHiddenInput) htmlPage.getElementById(
                            "__EVENTTARGET");
                    HtmlForm submitForm = hiddenInput.getEnclosingForm();
                    hiddenInput.setAttribute("value",
                            "ctl00$cphContent$GdvArquivamento$pgrGridView$btrNext$lbtText");
                    htmlPage = webClient.getPage(submitForm.getWebRequest(hiddenInput));
                } else {
                    flag = false;
                }
            }

            if (results.isEmpty())
                results = null;
        }

        return results;
    }

    private void fillAllDocs(HtmlPage htmlPage, List<DocumentMetadata> results) throws IOException {
        HtmlTable documentsTable = (HtmlTable) htmlPage.getElementById(
                "ctl00_cphContent_GdvArquivamento_gdvContent");
        HtmlSubmitInput continueSubmitButton = (HtmlSubmitInput) htmlPage.getElementById(
                "ctl00_cphContent_btnContinuar");
        HtmlForm submitForm = continueSubmitButton.getEnclosingForm();
        Iterator iteratorTable = documentsTable.getChildElements().iterator();
        HtmlTableBody tableBody = (HtmlTableBody) iteratorTable.next();
        Iterator rowIterator = tableBody.getChildElements().iterator();
        rowIterator.next();

        while (rowIterator.hasNext()) {
            HtmlTableRow row = (HtmlTableRow) rowIterator.next();
            HtmlTableCell radioCell = row.getCell(0);
            HtmlTableCell dateCell = row.getCell(1);
            HtmlTableCell documentCell = row.getCell(4);
            Iterator<DomElement> radioCellIterator = radioCell.getChildElements().iterator();
            DomElement element = radioCellIterator.next();
            Iterator<DomElement> documentCellIterator = documentCell.getChildElements().iterator();
            HtmlSpan descriptionSpan = (HtmlSpan) documentCellIterator.next();

            if (element instanceof HtmlRadioButtonInput radioButtonInput) {
                radioButtonInput.setChecked(true);
                UnexpectedPage documentPage = webClient.getPage(submitForm.getWebRequest(continueSubmitButton));
                results.add(DocumentMetadata.builder().data(documentPage.getInputStream())
                        .date(dateCell.getTextContent())
                        .description(descriptionSpan.getTextContent()).build());
            }
        }
    }

}
