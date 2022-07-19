package com.linkapital.core.services.person.contract.to;

import com.linkapital.core.services.directory.contract.to.DirectoryTO;
import com.linkapital.core.services.property_guarantee.contract.to.PropertyGuaranteeTO;
import com.linkapital.core.services.shared.contract.to.AddressTO;
import com.linkapital.core.services.shared.contract.to.DebitMteTO;
import com.linkapital.core.services.shared.contract.to.DebitPgfnDauTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ApiModel(description = "The person spouse data (light).")
@Getter
@Setter
public class LightPersonSpouseTO {

    @ApiModelProperty(value = "The id.")
    private long id;

    @ApiModelProperty(value = "The cpf.")
    @Size(max = 11)
    private String cpf;

    @ApiModelProperty(value = "The name.")
    private String name;

    @ApiModelProperty(value = "The sex.")
    private String sex;

    @ApiModelProperty(value = "The email.")
    private String email;

    @ApiModelProperty(value = "The National Individual Health Card Number.")
    private String cns;

    @ApiModelProperty(value = "The registration number assigned by Caixa Econômica Federal to people who will benefit" +
            " from a social project and do not yet have a PIS registration.")
    private String nis;

    @ApiModelProperty(value = "The mother's CPF.")
    private String motherCpf;

    @ApiModelProperty(value = "The mother's name.")
    private String motherName;

    @ApiModelProperty(value = "The father's name.")
    private String fatherName;

    @ApiModelProperty(value = "The origin country.")
    private String originCountry;

    @ApiModelProperty(value = "The number of the Integração Social da Pessoa Física Program.")
    private String socialInscription;

    @ApiModelProperty(value = "The debt Pfgn Dau.")
    private String debitPgfnDauValue;

    @ApiModelProperty(value = "The situation of the CPF.")
    private String situationCpf;

    @ApiModelProperty(value = "The education level.")
    private String educationLevel;

    @ApiModelProperty(value = "The deficiency type")
    private String deficiencyType;

    @ApiModelProperty(value = "The spouse name")
    private String spouse;

    @ApiModelProperty(value = "The profession exercised by the physical person.")
    private String professionNeoway;

    @ApiModelProperty(value = "The code of the worker's profession according to the Brazilian Classification of" +
            " Occupations (CBO).")
    private String professionCbo;

    @ApiModelProperty(value = "The description of the situation, considering INIDONEO / IDONEO.")
    private String situation;

    @ApiModelProperty(value = "The situation of the person's registration at SICAF (ACCREDITED / INACTIVE).")
    private String registerSituation;

    @ApiModelProperty(value = "The matrimonial regime of the person.")
    private String marriageRegime;

    @ApiModelProperty(value = "The CPF registration date.")
    private String inscriptionCpfDate;

    @ApiModelProperty(value = "The number of companies in which the person has a shareholding registration referring" +
            " to the registration of partners in the Federal Revenue and in the Commercial Registry.")
    private int quantityQsaUnique;

    @ApiModelProperty(value = "The age.")
    private int age;

    @ApiModelProperty(value = "The year of dead.")
    private int deadDate;

    @ApiModelProperty(value = "Indicates if the person has deficiencies.")
    private boolean deficiency;

    @ApiModelProperty(value = "Indicate if the person is deceased.")
    private boolean dead;

    @ApiModelProperty(value = "Indicates if the person has a death record.")
    private boolean deadConfirmation;

    @ApiModelProperty(value = "Indicate if the person is a public agent.")
    private boolean publicAgent;

    @ApiModelProperty(value = "The birth date.")
    private Date birthDate;

    @ApiModelProperty(value = "The main address.")
    private AddressTO mainAddress;

    @ApiModelProperty(value = "The PGFN DAU debits.")
    private DebitPgfnDauTO debitPgfnDau;

    @ApiModelProperty(value = "The MTE debits.")
    private DebitMteTO debitMte;

    @ApiModelProperty(value = "The judicial process.")
    private JudicialProcessTO judicialProcess;

    @ApiModelProperty(value = "The historical criminal.")
    private HistoricalCriminalTO historicalCriminal;

    @ApiModelProperty(value = "The phone list.")
    private List<String> phones;

    @ApiModelProperty(value = "The list of criminal process.")
    private List<String> criminalProcess;

    @ApiModelProperty(value = "The list of IRPF documents.")
    private List<DirectoryTO> irpfDocuments;

    @ApiModelProperty(value = "The list of IRPF receipt documents.")
    private List<DirectoryTO> irpfReceiptDocuments;

    @ApiModelProperty(value = "The data of the Income Tax and Physical Person.")
    private List<IrpfTO> irpf;

    @ApiModelProperty(value = "The list of the functional history of the person.")
    private List<HistoricalFunctionalTO> historicalFunctional;

    @ApiModelProperty(value = "The properties list.")
    private List<PropertyGuaranteeTO> properties;

    @ApiModelProperty(value = "The list of properties rural.")
    private List<PropertyGuaranteeTO> propertiesRural;

    @ApiModelProperty(value = "The relationship list.")
    private List<RelationshipTO> relationships;

    @ApiModelProperty(value = "The disabilities list into BACEN.")
    private List<DisabilitiesBacenTO> disabilitiesBacens;

    @ApiModelProperty(value = "The addresses list.")
    private List<AddressTO> otherAddresses;

    @ApiModelProperty(value = "The corporates participation list.")
    private List<CorporateParticipationTO> corporatesParticipation;

    public LightPersonSpouseTO() {
        this.phones = new ArrayList<>();
        this.criminalProcess = new ArrayList<>();
        this.irpfDocuments = new ArrayList<>();
        this.irpfReceiptDocuments = new ArrayList<>();
        this.irpf = new ArrayList<>();
        this.historicalFunctional = new ArrayList<>();
        this.properties = new ArrayList<>();
        this.propertiesRural = new ArrayList<>();
        this.disabilitiesBacens = new ArrayList<>();
        this.relationships = new ArrayList<>();
        this.otherAddresses = new ArrayList<>();
        this.corporatesParticipation = new ArrayList<>();
    }

    public LightPersonSpouseTO withProperties(List<PropertyGuaranteeTO> properties) {
        setProperties(properties);
        return this;
    }

    public LightPersonSpouseTO withPropertiesRural(List<PropertyGuaranteeTO> propertiesRural) {
        setPropertiesRural(propertiesRural);
        return this;
    }

}
