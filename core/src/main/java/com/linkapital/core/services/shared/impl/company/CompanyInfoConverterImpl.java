package com.linkapital.core.services.shared.impl.company;

import com.linkapital.core.services.company.CompanyService;
import com.linkapital.core.services.company.datasource.domain.Company;
import com.linkapital.core.services.company.datasource.domain.CompanyMainInfo;
import com.linkapital.core.services.shared.CompanyConverter;
import com.linkapital.core.services.shared.datasource.domain.Address;
import lombok.NonNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Map;

import static com.linkapital.core.services.company.contract.enums.RegistrationSituation.getRegistrationSituation;
import static java.time.format.DateTimeFormatter.ISO_ZONED_DATE_TIME;

@Service
public class CompanyInfoConverterImpl implements CompanyConverter {

    //Level 1
    private final String enterprise = "empresa";
    //Level 2
    private final String cnpj = "cnpj";
    private final String openingDate = "abertura";
    private final String socialReason = "razaoSocial";
    private final String registrationSituation = "situacaoCadastral";
    //Level 3
    private final String status = "status";
    //Address
    //Level 1
    private final String address = "endereco";
    private final String neighborhood = "barrio";
    private final String originalNeighborhood = "bairroOriginal";
    private final String zip = "cep";
    private final String address1 = "logradouro";
    private final String address2 = "complemento";
    private final String deliveryRestriction = "cepRestritoFlag";
    private final String residentialAddress = "enderecoResidencial";
    private final String latitude = "latitude";
    private final String longitude = "longitude";
    private final String mRegion = "mesoRegiao";
    private final String microRegion = "microRegiao";
    private final String region = "regiao";
    private final String municipality = "municipio";
    private final String borderMunicipality = "municipioFronteirico";
    private final String number = "numero";
    private final String precision = "precisao";
    private final String uf = "uf";
    //Level 2
    private final String building = "mapeamentoEdificios";
    private final String collectiveBuilding = "edificioColetivo";
    private final String buildingType = "tipoEdificio";

    private final CompanyService companyService;

    public CompanyInfoConverterImpl(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostConstruct
    public void init() {
        this.companyService.addConverter(this);
    }

    @Override
    public Company convert(@NonNull Company company, @NonNull Map data) {
        if (data.get(enterprise) != null) {
            var enterpriseLevel1 = (Map) data.get(enterprise);
            var companyMainInfo = company.getMainInfo() == null
                    ? new CompanyMainInfo()
                    : company.getMainInfo();

            populateMainInfo(enterpriseLevel1, companyMainInfo);
            companyMainInfo.setAddress(getAddress(data));
            company.setMainInfo(companyMainInfo);
        }

        return company;
    }

    private void populateMainInfo(@NonNull Map map, @NonNull CompanyMainInfo companyMainInfo) {
        companyMainInfo.setCnpj(map.get(cnpj) == null
                ? null
                : map.get(cnpj).toString());
        companyMainInfo.setOpeningDate(map.get(openingDate) == null
                ? null
                : LocalDateTime.parse(map.get(openingDate).toString(), ISO_ZONED_DATE_TIME));
        companyMainInfo.setSocialReason(map.get(socialReason) == null
                ? null
                : map.get(socialReason).toString());

        if (map.get(registrationSituation) != null) {
            var registrationSituationLevel2 = (Map) map.get(registrationSituation);
            companyMainInfo.setRegistrationSituation(registrationSituationLevel2.get(status) == null
                    ? null
                    : getRegistrationSituation(registrationSituationLevel2.get(status).toString()));
        }
    }

    private @Nullable Address getAddress(@NonNull Map data) {
        if (data.get(address) == null)
            return null;

        var addressLevel1 = (Map) data.get(address);
        var addressNew = new Address();

        addressNew.setNeighborhood(addressLevel1.get(neighborhood) == null
                ? null
                : addressLevel1.get(neighborhood).toString());
        addressNew.setOriginalNeighborhood(addressLevel1.get(originalNeighborhood) == null
                ? null
                : addressLevel1.get(originalNeighborhood).toString());
        addressNew.setZip(addressLevel1.get(zip) == null
                ? null
                : addressLevel1.get(zip).toString());
        addressNew.setAddress1(addressLevel1.get(address1) == null
                ? null
                : addressLevel1.get(address1).toString());
        addressNew.setAddress2(addressLevel1.get(address2) == null
                ? null
                : addressLevel1.get(address2).toString());
        addressNew.setDeliveryRestriction(addressLevel1.get(deliveryRestriction) != null &&
                (boolean) addressLevel1.get(deliveryRestriction));
        addressNew.setResidentialAddress(addressLevel1.get(residentialAddress) != null &&
                (boolean) addressLevel1.get(residentialAddress));
        addressNew.setLatitude(addressLevel1.get(latitude) == null
                ? null
                : (Double) addressLevel1.get(latitude));
        addressNew.setLatitude(addressLevel1.get(longitude) == null
                ? null
                : (Double) addressLevel1.get(longitude));
        addressNew.setMRegion(addressLevel1.get(mRegion) == null
                ? null
                : addressLevel1.get(mRegion).toString());
        addressNew.setMicroRegion(addressLevel1.get(microRegion) == null
                ? null
                : addressLevel1.get(microRegion).toString());
        addressNew.setRegion(addressLevel1.get(region) == null
                ? null
                : addressLevel1.get(region).toString());
        addressNew.setMunicipality(addressLevel1.get(municipality) == null
                ? null
                : addressLevel1.get(municipality).toString());
        addressNew.setBorderMunicipality(addressLevel1.get(borderMunicipality) == null
                ? null
                : addressLevel1.get(borderMunicipality).toString());
        addressNew.setNumber(addressLevel1.get(number) == null
                ? null
                : addressLevel1.get(number).toString());
        addressNew.setPrecision(addressLevel1.get(precision) == null
                ? null
                : addressLevel1.get(precision).toString());
        addressNew.setUf(addressLevel1.get(uf) == null
                ? null
                : addressLevel1.get(uf).toString());

        if (addressLevel1.get(building) != null) {
            var dataLevel2 = (Map) addressLevel1.get(building);

            addressNew.setCollectiveBuilding(dataLevel2.get(collectiveBuilding) != null
                    && (boolean) dataLevel2.get(collectiveBuilding));
            addressNew.setBuildingType(dataLevel2.get(buildingType) == null
                    ? null
                    : dataLevel2.get(buildingType).toString());
        }

        return addressNew;
    }

}
