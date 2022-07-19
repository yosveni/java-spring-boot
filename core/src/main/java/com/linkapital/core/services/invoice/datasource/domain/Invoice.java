package com.linkapital.core.services.invoice.datasource.domain;

import com.linkapital.core.services.company_user.datasource.domain.CompanyUser;
import com.linkapital.core.services.invoice.contract.enums.InvoiceStatus;
import com.linkapital.core.services.invoice.contract.enums.InvoiceType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity(name = "TAB_INVOICE")
@SequenceGenerator(name = "gen_invoice", sequenceName = "seq_invoice", allocationSize = 1)
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_invoice")
    private Long id;

    private String number;

    private String code;

    @Column(name = "nat_op")
    private String natOp;

    private double total;

    @Column(name = "taxed_amount")
    private double taxedAmount;

    @Column(name = "issuance_date")
    private Date issuanceDate;

    @Column(name = "due_date")
    private Date dueDate;

    @Column(name = "dh_emi")
    private Date dhEmi;

    @Column(name = "dh_sai_ent")
    private Date dhSaiEnt;

    @Column(nullable = false)
    private InvoiceType type;

    private InvoiceStatus status;

    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "indirect_tax_id")
    private IndirectTax indirectTax;

    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "sender_id")
    private SenderRecipient sender;

    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "receipt_id")
    private SenderRecipient receipt;

    @ManyToOne
    @JoinColumn(name = "company_invoice_id")
    private CompanyUser companyInvoice;

    @ManyToOne
    @JoinColumn(name = "company_received_id")
    private CompanyUser companyReceived;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "invoice_id")
    private Set<Product> products;

    public Invoice() {
        this.products = new HashSet<>();
    }

    public Invoice withType(InvoiceType type) {
        setType(type);
        return this;
    }

    public Invoice withSender(SenderRecipient sender) {
        setSender(sender);
        return this;
    }

    public Invoice withRecipient(SenderRecipient recipient) {
        setReceipt(recipient);
        return this;
    }

}
