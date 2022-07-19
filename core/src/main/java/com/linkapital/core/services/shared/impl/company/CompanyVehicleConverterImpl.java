package com.linkapital.core.services.shared.impl.company;

import com.linkapital.core.services.company.CompanyService;
import com.linkapital.core.services.company.datasource.domain.Aircraft;
import com.linkapital.core.services.company.datasource.domain.Company;
import com.linkapital.core.services.company.datasource.domain.HeavyVehicle;
import com.linkapital.core.services.company.datasource.domain.HeavyVehicleInfo;
import com.linkapital.core.services.shared.CompanyConverter;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toSet;

@Service
public class CompanyVehicleConverterImpl implements CompanyConverter {

    //Level 1
    private final String heavyVehicles = "veiculosPesados";
    private final String detran = "detran";
    private final String heavyVehicleInfo = "totalVeiculos";
    private final String aircraftOwner = "aeronavesProprietarios";
    private final String aircraftOperator = "aeronavesOperadores";
    //Level 2
    private final String productionYearVI = "anoFabricacao";
    private final String fuelVI = "combustivel";
    private final String anttVI = "constaAntt";
    private final String modelVI = "marcaModelo";
    private final String carPlateVI = "placa";
    private final String renavamVI = "renavam";
    private final String typeVI = "tipo";
    private final String ufVI = "uf";
    private final String heavyVehiclesDetran = "totalVeiculosPesados";
    private final String heavyVehiclesGroupDetran = "totalVeiculosPesadosGrupo";
    private final String over10TV = "acimaDezAnos";
    private final String upto1TV = "ateUmAno";
    private final String between2And5TV = "entreDoisCincoAnos";
    private final String between5And10TV = "entreCincoDezAnos";
    private final String groupOver10TV = "grupoAcimaDezAnos";
    private final String groupUpTo1TV = "grupoAteUmAno";
    private final String groupBetween2And5TV = "grupoEntreDoisCincoAnos";
    private final String groupBetween5And10TV = "grupoEntreCincoDezAnos";
    private final String aircraft = "aeronaves";
    //Level 3
    private final String productionYear = "anoFabricacao";
    private final String maker = "fabricante";
    private final String registration = "matricula";
    private final String model = "modelo";
    private final String ownerName = "proprietario";
    private final String operatorName = "operador";
    private final String situation = "situacaoAeronavegabilidade";
    private final String statusRAB = "situacaoRAB";

    private final CompanyService companyService;

    public CompanyVehicleConverterImpl(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostConstruct
    public void init() {
        companyService.addConverter(this);
    }

    @Override
    public Company convert(Company company, @NonNull Map data) throws ParseException {
        var heavyVehicleInfo = new HeavyVehicleInfo();
        if (nonNull(data.get(this.heavyVehicles))) {
            var heavyVehiclesLevel1 = (List<Map>) data.get(this.heavyVehicles);

            heavyVehicleInfo.getVehicles().addAll(heavyVehiclesLevel1
                    .stream()
                    .map(map -> {
                        var heavyVehicle = new HeavyVehicle();

                        heavyVehicle.setProductionYear(nonNull(map.get(productionYearVI))
                                ? parseInt(map.get(productionYearVI).toString())
                                : 0);
                        heavyVehicle.setFuel(nonNull(map.get(fuelVI))
                                ? map.get(fuelVI).toString()
                                : null);
                        heavyVehicle.setAntt(nonNull(map.get(anttVI)) && (boolean) map.get(anttVI));
                        heavyVehicle.setModel(nonNull(map.get(modelVI))
                                ? map.get(modelVI).toString()
                                : null);
                        heavyVehicle.setCarPlate(nonNull(map.get(carPlateVI))
                                ? map.get(carPlateVI).toString()
                                : null);
                        heavyVehicle.setRenavam(nonNull(map.get(renavamVI))
                                ? map.get(renavamVI).toString()
                                : null);
                        heavyVehicle.setType(nonNull(map.get(typeVI))
                                ? map.get(typeVI).toString()
                                : null);
                        heavyVehicle.setUf(nonNull(map.get(ufVI))
                                ? map.get(ufVI).toString()
                                : null);

                        return heavyVehicle;
                    })
                    .toList());
        }

        if (nonNull(data.get(detran))) {
            var detranLevel1 = (Map) data.get(detran);
            heavyVehicleInfo.setHeavyVehicles(nonNull(detranLevel1.get(heavyVehiclesDetran))
                    ? parseInt(detranLevel1.get(heavyVehiclesDetran).toString())
                    : 0);
            heavyVehicleInfo.setHeavyVehiclesGroup(nonNull(detranLevel1.get(heavyVehiclesGroupDetran))
                    ? parseInt(detranLevel1.get(heavyVehiclesGroupDetran).toString())
                    : 0);
        }

        if (nonNull(data.get(this.heavyVehicleInfo))) {
            var totalVehiclesLevel1 = (Map) data.get(this.heavyVehicleInfo);
            heavyVehicleInfo.setOver10(nonNull(totalVehiclesLevel1.get(over10TV))
                    ? parseInt(totalVehiclesLevel1.get(over10TV).toString())
                    : 0);
            heavyVehicleInfo.setUpto1(nonNull(totalVehiclesLevel1.get(upto1TV))
                    ? parseInt(totalVehiclesLevel1.get(upto1TV).toString())
                    : 0);
            heavyVehicleInfo.setBetween2And5(nonNull(totalVehiclesLevel1.get(between2And5TV))
                    ? parseInt(totalVehiclesLevel1.get(between2And5TV).toString())
                    : 0);
            heavyVehicleInfo.setBetween5And10(nonNull(totalVehiclesLevel1.get(between5And10TV))
                    ? parseInt(totalVehiclesLevel1.get(between5And10TV).toString())
                    : 0);
            heavyVehicleInfo.setGroupOver10(nonNull(totalVehiclesLevel1.get(groupOver10TV))
                    ? parseInt(totalVehiclesLevel1.get(groupOver10TV).toString())
                    : 0);
            heavyVehicleInfo.setGroupUpTo1(nonNull(totalVehiclesLevel1.get(groupUpTo1TV))
                    ? parseInt(totalVehiclesLevel1.get(groupUpTo1TV).toString())
                    : 0);
            heavyVehicleInfo.setGroupBetween2And5(nonNull(totalVehiclesLevel1.get(groupBetween2And5TV))
                    ? parseInt(totalVehiclesLevel1.get(groupBetween2And5TV).toString())
                    : 0);
            heavyVehicleInfo.setGroupBetween5And10(nonNull(totalVehiclesLevel1.get(groupBetween5And10TV))
                    ? parseInt(totalVehiclesLevel1.get(groupBetween5And10TV).toString())
                    : 0);
        }
        company.setHeavyVehicleInfo(heavyVehicleInfo);

        var aircraftOwnerLevel2 = nonNull(data.get(aircraftOwner))
                ? (List<Map>) ((Map) data.get(aircraftOwner)).get(aircraft)
                : null;
        if (nonNull(aircraftOwnerLevel2)) {
            var aircraftOperatorLevel2 = nonNull(data.get(aircraftOperator))
                    ? (List<Map>) ((Map) data.get(aircraftOperator)).get(aircraft)
                    : null;
            var map1 = nonNull(aircraftOperatorLevel2)
                    ? aircraftOperatorLevel2
                    .stream()
                    .findFirst()
                    .orElse(null)
                    : null;
            var name = nonNull(map1.get(ownerName))
                    ? map1.get(ownerName).toString()
                    : null;
            var aircraftList = company.getAircraft();

            if (!aircraftList.isEmpty())
                aircraftList.clear();

            aircraftList.addAll(aircraftOwnerLevel2
                    .stream()
                    .map(map -> {
                        var aircraft = new Aircraft();
                        aircraft.setProductionYear(nonNull(map.get(productionYear))
                                ? parseInt(map.get(productionYear).toString())
                                : 0);
                        aircraft.setMaker(nonNull(map.get(maker))
                                ? map.get(maker).toString()
                                : null);
                        aircraft.setRegistration(nonNull(map.get(registration))
                                ? map.get(registration).toString()
                                : null);
                        aircraft.setModel(nonNull(map.get(model))
                                ? map.get(model).toString()
                                : null);
                        aircraft.setOperatorName(nonNull(map.get(operatorName))
                                ? map.get(operatorName).toString()
                                : null);
                        aircraft.setSituation(nonNull(map.get(situation))
                                ? map.get(situation).toString()
                                : null);
                        aircraft.setStatusRAB(nonNull(map.get(statusRAB))
                                ? map.get(statusRAB).toString()
                                : null);
                        aircraft.setOwnerName(name);

                        return aircraft;
                    })
                    .collect(toSet()));
        }

        return company;
    }

}
