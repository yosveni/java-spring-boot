package com.linkapital.core.services.cri_cra_debenture.contract;

import com.linkapital.core.services.cri_cra_debenture.contract.to.CreateCriCraDebentureTO;
import com.linkapital.core.services.cri_cra_debenture.contract.to.CriCraDebentureTO;
import com.linkapital.core.services.cri_cra_debenture.datasource.domain.CriCraDebenture;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-02-14T18:35:17-0300",
    comments = "version: 1.4.1.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.1.1.jar, environment: Java 16.0.2 (Amazon.com Inc.)"
)
public class CriCraDebentureBinderImpl implements CriCraDebentureBinder {

    @Override
    public List<CriCraDebenture> bind(List<CreateCriCraDebentureTO> source) {
        if ( source == null ) {
            return null;
        }

        List<CriCraDebenture> list = new ArrayList<CriCraDebenture>( source.size() );
        for ( CreateCriCraDebentureTO createCriCraDebentureTO : source ) {
            list.add( createCriCraDebentureTOToCriCraDebenture( createCriCraDebentureTO ) );
        }

        return list;
    }

    @Override
    public List<CriCraDebentureTO> bindToCriCraDebentureTO(Set<CriCraDebenture> source) {
        if ( source == null ) {
            return null;
        }

        List<CriCraDebentureTO> list = new ArrayList<CriCraDebentureTO>( source.size() );
        for ( CriCraDebenture criCraDebenture : source ) {
            list.add( criCraDebentureToCriCraDebentureTO( criCraDebenture ) );
        }

        return list;
    }

    protected CriCraDebenture createCriCraDebentureTOToCriCraDebenture(CreateCriCraDebentureTO createCriCraDebentureTO) {
        if ( createCriCraDebentureTO == null ) {
            return null;
        }

        CriCraDebenture criCraDebenture = new CriCraDebenture();

        criCraDebenture.setCode( createCriCraDebentureTO.getCode() );
        criCraDebenture.setDebtorIssuer( createCriCraDebentureTO.getDebtorIssuer() );
        criCraDebenture.setInsuranceSector( createCriCraDebentureTO.getInsuranceSector() );
        criCraDebenture.setSeriesIssue( createCriCraDebentureTO.getSeriesIssue() );
        criCraDebenture.setRemuneration( createCriCraDebentureTO.getRemuneration() );
        criCraDebenture.setIndicativeValue( createCriCraDebentureTO.getIndicativeValue() );
        criCraDebenture.setSeriesVolumeOnIssueDate( createCriCraDebentureTO.getSeriesVolumeOnIssueDate() );
        criCraDebenture.setPuParDebenture( createCriCraDebentureTO.getPuParDebenture() );
        criCraDebenture.setIssueDate( createCriCraDebentureTO.getIssueDate() );
        criCraDebenture.setDueDate( createCriCraDebentureTO.getDueDate() );
        criCraDebenture.setType( createCriCraDebentureTO.getType() );

        return criCraDebenture;
    }

    protected CriCraDebentureTO criCraDebentureToCriCraDebentureTO(CriCraDebenture criCraDebenture) {
        if ( criCraDebenture == null ) {
            return null;
        }

        CriCraDebentureTO criCraDebentureTO = new CriCraDebentureTO();

        criCraDebentureTO.setCode( criCraDebenture.getCode() );
        criCraDebentureTO.setDebtorIssuer( criCraDebenture.getDebtorIssuer() );
        criCraDebentureTO.setInsuranceSector( criCraDebenture.getInsuranceSector() );
        criCraDebentureTO.setSeriesIssue( criCraDebenture.getSeriesIssue() );
        criCraDebentureTO.setRemuneration( criCraDebenture.getRemuneration() );
        criCraDebentureTO.setIndicativeValue( criCraDebenture.getIndicativeValue() );
        criCraDebentureTO.setSeriesVolumeOnIssueDate( criCraDebenture.getSeriesVolumeOnIssueDate() );
        criCraDebentureTO.setPuParDebenture( criCraDebenture.getPuParDebenture() );
        criCraDebentureTO.setIssueDate( criCraDebenture.getIssueDate() );
        criCraDebentureTO.setDueDate( criCraDebenture.getDueDate() );
        criCraDebentureTO.setType( criCraDebenture.getType() );
        if ( criCraDebenture.getId() != null ) {
            criCraDebentureTO.setId( criCraDebenture.getId() );
        }

        return criCraDebentureTO;
    }
}
