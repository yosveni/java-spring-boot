package com.linkapital.core.services.shared.contract;

import com.linkapital.core.services.company.datasource.domain.Company;
import com.linkapital.core.services.company.datasource.domain.EmployeeGrowth;
import com.linkapital.core.services.shared.datasource.domain.CompanyEmployees;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.ToIntBiFunction;

import static com.linkapital.core.util.json.JsonSerdes.convert;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toSet;

@Mapper
public interface CompanyGrowthBinder {

    CompanyGrowthBinder COMPANY_GROWTH_BINDER = Mappers.getMapper(CompanyGrowthBinder.class);

    //Level 1
    String growth = "crescimentoPorAnoRais";
    String employees = "totalFuncionarios";
    //Level 2
    String year = "ano";
    String percent = "percentual";
    String quantityEmployee = "quantidade";
    String employeeLawyerCount = "quantidadeAdvogados";
    String employeeAnalystCount = "quantidadeAnalistasTecnicos";
    String employeeBaseCount = "quantidadeBase";
    String employeeBuyerCount = "quantidadeCompradores";
    String employeeEngineerCount = "quantidadeEngenheirosArquitetos";
    String employeeManagerCount = "quantidadeGerentes";
    String employeeDoctorCount = "quantidadeMedicos";
    String employeeOtherCount = "quantidadeOutros";
    String employeePdvCount = "quantidadePdv";
    String employeeTeacherCount = "quantidadeProfessores";
    String employeeSupervisorCount = "quantidadeSupervisores";
    String employeeSellerCount = "quantidadeVendedores";

    ToIntBiFunction<Integer, Double> getGrowthRate = (companyEmployees, growthPercent) -> {
        var growthRate = (1 + growthPercent / 100);
        var growthDifference = companyEmployees / growthRate;

        return (int) Math.round(growthDifference);
    };

    Function<Map<String, Object>, CompanyEmployees> bindCompanyEmployees = source ->
            source.containsKey(employees)
                    ? convert(source.get(employees), CompanyEmployees.class)
                    : new CompanyEmployees()
                            .withQuantidade(0)
                            .withQuantidadeGrupo(0);

    ToIntBiFunction<CompanyEmployees, Double> calculateEmployeeGrowth = (companyEmployees, growthPercent) -> {
        var employeeGrowth = growthPercent == 0
                ? companyEmployees.getQuantidade()
                : getGrowthRate.applyAsInt(companyEmployees.getQuantidade(), growthPercent);
        companyEmployees.setQuantidade(employeeGrowth);

        return employeeGrowth;
    };

    BiFunction<CompanyEmployees, Map<String, Object>, EmployeeGrowth> buildEmployeeGrowth = (companyEmployees, source) -> {
        var growthPercent = nonNull(source.get(percent))
                ? parseDouble(source.get(percent).toString())
                : 0;

        return new EmployeeGrowth()
                .withYear(nonNull(source.get(year)) ? parseInt(source.get(year).toString()) : 0)
                .withEmployeeGrowth(calculateEmployeeGrowth.applyAsInt(companyEmployees, growthPercent))
                .withGrowth(growthPercent);
    };

    Function<Map, Set<EmployeeGrowth>> bindEmployeeGrowth = source -> {
        var companyEmployees = bindCompanyEmployees.apply(source);
        var growthLevel = (List<Map>) source.get(growth);

        return growthLevel
                .stream()
                .map(map -> buildEmployeeGrowth.apply(companyEmployees, map))
                .collect(toSet());
    };

    BiConsumer<Company, Map> bindCompany = (company, source) -> {
        var employeesLevel1 = (Map) source.get(employees);
        company.setQuantityEmployee(nonNull(employeesLevel1.get(quantityEmployee)) ? Integer.parseInt(employeesLevel1.get(quantityEmployee).toString()) : 0);
        company.setEmployeeLawyerCount(nonNull(employeesLevel1.get(employeeLawyerCount)) ? Integer.parseInt(employeesLevel1.get(employeeLawyerCount).toString()) : 0);
        company.setEmployeeAnalystCount(nonNull(employeesLevel1.get(employeeAnalystCount)) ? Integer.parseInt(employeesLevel1.get(employeeAnalystCount).toString()) : 0);
        company.setEmployeeBaseCount(nonNull(employeesLevel1.get(employeeBaseCount)) ? Integer.parseInt(employeesLevel1.get(employeeBaseCount).toString()) : 0);
        company.setEmployeeBuyerCount(nonNull(employeesLevel1.get(employeeBuyerCount)) ? Integer.parseInt(employeesLevel1.get(employeeBuyerCount).toString()) : 0);
        company.setEmployeeEngineerCount(nonNull(employeesLevel1.get(employeeEngineerCount)) ? Integer.parseInt(employeesLevel1.get(employeeEngineerCount).toString()) : 0);
        company.setEmployeeManagerCount(nonNull(employeesLevel1.get(employeeManagerCount)) ? Integer.parseInt(employeesLevel1.get(employeeManagerCount).toString()) : 0);
        company.setEmployeeDoctorCount(nonNull(employeesLevel1.get(employeeDoctorCount)) ? Integer.parseInt(employeesLevel1.get(employeeDoctorCount).toString()) : 0);
        company.setEmployeeOtherCount(nonNull(employeesLevel1.get(employeeOtherCount)) ? Integer.parseInt(employeesLevel1.get(employeeOtherCount).toString()) : 0);
        company.setEmployeePdvCount(nonNull(employeesLevel1.get(employeePdvCount)) ? Integer.parseInt(employeesLevel1.get(employeePdvCount).toString()) : 0);
        company.setEmployeeTeacherCount(nonNull(employeesLevel1.get(employeeTeacherCount)) ? Integer.parseInt(employeesLevel1.get(employeeTeacherCount).toString()) : 0);
        company.setEmployeeSupervisorCount(nonNull(employeesLevel1.get(employeeSupervisorCount)) ? Integer.parseInt(employeesLevel1.get(employeeSupervisorCount).toString()) : 0);
        company.setEmployeeSellerCount(nonNull(employeesLevel1.get(employeeSellerCount)) ? Integer.parseInt(employeesLevel1.get(employeeSellerCount).toString()) : 0);
    };

}
