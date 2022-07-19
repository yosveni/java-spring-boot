package com.linkapital.core.services.company.datasource.domain;

import com.linkapital.core.services.bank_operation.datasource.domain.BankOperation;
import com.linkapital.core.services.company.contract.enums.ActivityLevel;
import com.linkapital.core.services.company.contract.enums.CompanyClosingPropensity;
import com.linkapital.core.services.company.contract.enums.CompanySize;
import com.linkapital.core.services.company_user.datasource.domain.CompanyUser;
import com.linkapital.core.services.credit_information.datasource.domain.CreditInformation;
import com.linkapital.core.services.cri_cra_debenture.datasource.domain.CriCraDebenture;
import com.linkapital.core.services.directory.datasource.domain.Directory;
import com.linkapital.core.services.ibge.datasource.domain.Ibge;
import com.linkapital.core.services.protest.datasource.domain.ProtestInformation;
import com.linkapital.core.services.shared.datasource.domain.Cafir;
import com.linkapital.core.services.shared.datasource.domain.DebitMte;
import com.linkapital.core.services.shared.datasource.domain.DebitPgfnDau;
import com.linkapital.core.services.shared.datasource.domain.JudicialProcess;
import com.linkapital.core.services.shared.datasource.domain.Property;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REMOVE;

@Getter
@Setter
@Entity(name = "TAB_COMPANY")
@SequenceGenerator(name = "gen_company", sequenceName = "seq_company", allocationSize = 1)
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_company")
    private Long id;

    @Column(name = "fantasy_name")
    private String fantasyName;

    @Column(name = "legal_nature_code")
    private String legalNatureCode;

    @Column(name = "legal_nature_description", columnDefinition = "TEXT")
    private String legalNatureDescription;

    @Column(name = "date_registration_situation")
    private String dateRegistrationSituation;

    @Column(name = "registration_situation_reason", columnDefinition = "TEXT")
    private String registrationSituationReason;

    @Column(name = "date_special_situation")
    private String dateSpecialSituation;

    @Column(name = "special_situation")
    private String specialSituation;

    @Column(name = "rf_email")
    private String rfEmail;

    @Column(name = "remote_working_capacity")
    private String remoteWorkingCapacity;

    @Column(name = "franchise_name")
    private String franchiseName;

    @Column(name = "delivery_propensity")
    private String deliveryPropensity;

    @Column(name = "e_commerce_propensity")
    private String eCommercePropensity;

    @Column(name = "origin_country")
    private String originCountry;

    @Column(name = "estimated_billing")
    private String estimatedBilling;

    @Column(name = "estimated_billing_group")
    private String estimatedBillingGroup;

    @Column(name = "covid19_individual")
    private String covid19Individual;

    @Column(name = "covid19_segment")
    private String covid19Segment;

    @Column(name = "user_owner_id")
    private Long userOwnerId;

    @Column(name = "quantity_employee")
    private int quantityEmployee;

    @Column(name = "quantity_ex_employee")
    private int quantityExEmployee;

    @Column(name = "employee_base_count")
    private int employeeBaseCount;

    @Column(name = "employee_layer_count")
    private int employeeLawyerCount;

    @Column(name = "employee_analyst_count")
    private int employeeAnalystCount;

    @Column(name = "employee_buyer_count")
    private int employeeBuyerCount;

    @Column(name = "employee_engineer_count")
    private int employeeEngineerCount;

    @Column(name = "employee_manager_count")
    private int employeeManagerCount;

    @Column(name = "employee_doctor_count")
    private int employeeDoctorCount;

    @Column(name = "employee_teacher_count")
    private int employeeTeacherCount;

    @Column(name = "employee_supervisor_count")
    private int employeeSupervisorCount;

    @Column(name = "employee_seller_count")
    private int employeeSellerCount;

    @Column(name = "employee_other_count")
    private int employeeOtherCount;

    @Column(name = "employee_pdv_count")
    private int employeePdvCount;

    private int age;

    @Column(name = "quantity_active_branches")
    private int quantityActiveBranches;

    @Column(name = "social_capital")
    private double socialCapital;

    @Column(name = "gross_billing")
    private double grossBilling;

    private boolean client;

    @Column(name = "has_divergent_qsa")
    private boolean hasDivergentQSA;

    private boolean matrix;

    @Column(name = "has_accountant_contact")
    private boolean hasAccountantContact;

    private boolean multinational;

    @Column(name = "group_multinational")
    private boolean groupMultinational;

    @Column(name = "has_franchise_indicative")
    private boolean hasFranchiseIndicative;

    private boolean registered;

    private boolean scr;

    @Column(name = "passive_iiss")
    private boolean passiveIISS;

    @Column(nullable = false)
    private Date created;

    @Column(nullable = false)
    private Date modified;

    @Column(name = "activity_level")
    private ActivityLevel activityLevel;

    @Column(name = "company_size")
    private CompanySize companySize;

    @Column(name = "company_closing_propensity")
    private CompanyClosingPropensity companyClosingPropensity;

    @OneToOne(orphanRemoval = true, cascade = PERSIST)
    @JoinColumn(name = "main_info_id", nullable = false)
    private CompanyMainInfo mainInfo;

    @OneToOne(orphanRemoval = true, cascade = PERSIST)
    @JoinColumn(name = "matrix_info_id")
    private CompanyMainInfo matrixInfo;

    @OneToOne(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "financial_activity_id")
    private FinancialActivity financialActivity;

    @OneToOne(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "main_cnae_id")
    private Cnae mainCnae;

    @OneToOne(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "pat_id")
    private Pat pat;

    @OneToOne(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "tax_health_id")
    private TaxHealth taxHealth;

    @OneToOne(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "debit_mte_id")
    private DebitMte debitMte;

    @OneToOne(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "foreign_commerce_id")
    private ForeignCommerce foreignCommerce;

    @OneToOne(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "cafir_id")
    private Cafir cafir;

    @OneToOne(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "heavy_vehicle_info_id")
    private HeavyVehicleInfo heavyVehicleInfo;

    @OneToOne(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "management_contract_id")
    private ManagementContract managementContract;

    @OneToOne(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "ibama_cnd_id")
    private IbamaCnd ibamaCnd;

    @OneToOne(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "procon_id")
    private Procon procon;

    @OneToOne(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "judicial_process_id")
    private JudicialProcess judicialProcess;

    @OneToOne(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "antt_id")
    private Antt antt;

    @OneToOne(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "open_capital_id")
    private OpenCapital openCapital;

    @OneToOne(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "debit_pgfn_dau_id")
    private DebitPgfnDau debitPgfnDau;

    @OneToOne(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "simple_national_id")
    private SimpleNational simpleNational;

    @OneToOne(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "suframa_id")
    private Suframa suframa;

    @OneToOne(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "ibge_id")
    private Ibge ibge;

    @OneToOne(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "protest_information_id")
    private ProtestInformation protestInformation;

    @ElementCollection(targetClass = String.class)
    private Set<String> phones;

    @OneToMany(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "company_id")
    @OrderBy("consultDate desc")
    private Set<CreditInformation> creditsInformation;

    @OneToMany(cascade = PERSIST)
    @JoinColumn(name = "company_id")
    private Set<CompanyMainInfo> affiliates;

    @OneToMany(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "company_id", nullable = false)
    private Set<EmployeeGrowth> employeeGrowths;

    @OneToMany(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "company_id")
    private Set<Ceis> ceis;

    @OneToMany(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "company_id")
    private Set<Cepim> cepims;

    @OneToMany(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "company_id")
    private Set<Cnep> cneps;

    @OneToMany(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "company_id")
    private Set<InternationalList> internationalLists;

    @OneToMany(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "company_id")
    private Set<Property> properties;

    @OneToMany(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "company_id")
    private Set<Aircraft> aircraft;

    @OneToMany(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "company_id")
    private Set<Domain> domains;

    @OneToMany(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "company_id")
    private Set<InpiBrand> inpiBrands;

    @OneToMany(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "company_id")
    private Set<InpiPatent> inpiPatents;

    @OneToMany(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "company_id")
    private Set<InpiSoftware> inpiSoftwares;

    @OneToMany(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "company_id")
    private Set<FinancialIndicator> financialIndicators;

    @OneToMany(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "company_id")
    private Set<EnvironmentalLicense> environmentalLicenses;

    @OneToMany(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "company_id")
    private Set<WorkMte> workMtes;

    @OneToMany(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "company_id")
    private Set<CnjCnia> cnjCnias;

    @OneToMany(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "company_id")
    private Set<Crsfn> crsfns;

    @OneToMany(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "company_id")
    private Set<HealthEstablishment> healthEstablishments;

    @OneToMany(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "company_id")
    private Set<SintegraInscription> sintegraInscriptions;

    @OneToMany(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "company_id")
    @OrderBy("contractedDate desc")
    private Set<BankOperation> bankOperations;

    @OneToMany(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "company_id")
    private Set<CompanyRelated> companiesRelated;

    @OneToMany(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "company_id")
    private Set<CompanyBeneficiary> beneficiaries;

    @OneToMany(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "company_employee_id")
    private Set<CompanyEmployee> employees;

    @OneToMany(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "company_ex_employee_id")
    private Set<CompanyEmployee> exEmployees;

    @OneToMany(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "company_id")
    private Set<CriCraDebenture> criCraDebentures;

    @OneToMany(mappedBy = "company", orphanRemoval = true, cascade = REMOVE)
    private Set<CompanyPartners> partners;

    @OneToMany(mappedBy = "companyPartner", orphanRemoval = true, cascade = REMOVE)
    private Set<CompanyPartners> partnersOf;

    @OneToMany(mappedBy = "companyImport", orphanRemoval = true, cascade = ALL)
    private Set<CompanyExport> imports;

    @OneToMany(mappedBy = "companyExport", orphanRemoval = true, cascade = ALL)
    private Set<CompanyExport> exports;

    @OneToMany(mappedBy = "companyJucesp", orphanRemoval = true, cascade = ALL)
    private Set<Directory> jucespDocuments;

    @OneToMany(mappedBy = "company", orphanRemoval = true, cascade = ALL)
    private Set<CompanyUser> companyUsers;

    @ManyToMany(cascade = {PERSIST, MERGE})
    @JoinTable(name = "TAB_COMPANY_CNAE",
            joinColumns = @JoinColumn(name = "company_id"),
            inverseJoinColumns = @JoinColumn(name = "cnae_id"))
    private Set<Cnae> cnaes;

    public Company() {
        this.phones = new HashSet<>();
        this.creditsInformation = new HashSet<>();
        this.affiliates = new HashSet<>();
        this.employeeGrowths = new HashSet<>();
        this.ceis = new HashSet<>();
        this.cepims = new HashSet<>();
        this.cneps = new HashSet<>();
        this.internationalLists = new HashSet<>();
        this.properties = new HashSet<>();
        this.aircraft = new HashSet<>();
        this.domains = new HashSet<>();
        this.inpiBrands = new HashSet<>();
        this.inpiPatents = new HashSet<>();
        this.inpiSoftwares = new HashSet<>();
        this.financialIndicators = new HashSet<>();
        this.environmentalLicenses = new HashSet<>();
        this.workMtes = new HashSet<>();
        this.cnjCnias = new HashSet<>();
        this.crsfns = new HashSet<>();
        this.healthEstablishments = new HashSet<>();
        this.sintegraInscriptions = new HashSet<>();
        this.companyUsers = new HashSet<>();
        this.beneficiaries = new HashSet<>();
        this.partners = new HashSet<>();
        this.partnersOf = new HashSet<>();
        this.employees = new HashSet<>();
        this.exEmployees = new HashSet<>();
        this.bankOperations = new HashSet<>();
        this.jucespDocuments = new HashSet<>();
        this.imports = new HashSet<>();
        this.exports = new HashSet<>();
        this.cnaes = new HashSet<>();
        this.companiesRelated = new HashSet<>();
        this.criCraDebentures = new HashSet<>();
    }

    @PrePersist
    private void preSave() {
        this.created = new Date();
        this.modified = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        this.modified = new Date();
    }

    public Company withMainInfo(CompanyMainInfo mainInfo) {
        setMainInfo(mainInfo);
        return this;
    }

}
