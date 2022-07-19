package com.linkapital.core.services.company.datasource;

import com.linkapital.core.services.company.datasource.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long>, CompanyRepositoryCustom {

    Optional<Company> findByMainInfo_Cnpj(String cnpj);

    @Query(value = "select * from tab_company tc join tab_company_main_info tcmi on tc.main_info_id = tcmi.id \n" +
            "where tc.main_info_id = ?1 or tc.matrix_info_id = ?1", nativeQuery = true)
    List<Company> findByMainInfoOrMatrixInfo(long mainInfoId);

    List<Company> findAllByMainInfo_CnpjIn(List<String> cnpjList);

    List<Company> findAllByClient(boolean isClient);

    @Query(value = "select *\n" +
            "from tab_company\n" +
            "         join tab_company_main_info tcmi on tcmi.id = tab_company.main_info_id\n" +
            "         join tab_address ta on ta.id = tcmi.address_id\n" +
            "where\n" +
            "    /* First condition allows to search for points at an approximate distance:\n" +
            "       a distance computed using a 'box', instead of a 'circumference'.\n" +
            "       This first condition will use the index.\n" +
            "       (45.1013021, 46.3021011) = (lat, lng) of search center.\n" +
            "       25000 = search radius (in m)\n" +
            "    */\n" +
            "        earth_box(ll_to_earth(?1, ?2), ?3) @> ll_to_earth(ta.latitude, ta.longitude)\n" +
            "\n" +
            "    /* This second condition (which is slower) will \"refine\"\n" +
            "       the previous search, to include only the points within the\n" +
            "       circumference.\n" +
            "    */\n" +
            "  and earth_distance(ll_to_earth(?1, ?2),\n" +
            "                     ll_to_earth(?1, ?2)) < ?3", nativeQuery = true)
    List<Company> findAllInRadioLocation(double latitude, double longitude, double radius);

}
