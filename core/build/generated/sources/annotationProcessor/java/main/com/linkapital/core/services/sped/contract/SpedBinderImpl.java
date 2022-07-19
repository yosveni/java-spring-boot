package com.linkapital.core.services.sped.contract;

import com.linkapital.core.services.sped.contract.to.HorizontalAnalysisTO;
import com.linkapital.core.services.sped.contract.to.SpedBalanceTO;
import com.linkapital.core.services.sped.contract.to.SpedDemonstrationTO;
import com.linkapital.core.services.sped.contract.to.SpedTO;
import com.linkapital.core.services.sped.contract.to.VerticalAnalysisTO;
import com.linkapital.core.services.sped.datasource.domain.Sped;
import com.linkapital.core.services.sped.datasource.domain.SpedBalance;
import com.linkapital.core.services.sped.datasource.domain.SpedDemonstration;
import com.linkapital.core.services.sped.datasource.domain.SpedPattern;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-02-17T22:29:28-0300",
    comments = "version: 1.4.1.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.1.1.jar, environment: Java 16.0.2 (Amazon.com Inc.)"
)
public class SpedBinderImpl implements SpedBinder {

    @Override
    public VerticalAnalysisTO bindToVerticalAnalysisTO(SpedPattern source) {
        if ( source == null ) {
            return null;
        }

        VerticalAnalysisTO verticalAnalysisTO = new VerticalAnalysisTO();

        verticalAnalysisTO.setCode( source.getCode() );
        verticalAnalysisTO.setCodeSynthetic( source.getCodeSynthetic() );
        verticalAnalysisTO.setCodeDescription( source.getCodeDescription() );
        verticalAnalysisTO.setEndValueSituation( source.getEndValueSituation() );
        verticalAnalysisTO.setCodeLevel( source.getCodeLevel() );
        verticalAnalysisTO.setEndValue( source.getEndValue() );

        return verticalAnalysisTO;
    }

    @Override
    public HorizontalAnalysisTO bindToHorizontalAnalysisTO(VerticalAnalysisTO source) {
        if ( source == null ) {
            return null;
        }

        HorizontalAnalysisTO horizontalAnalysisTO = new HorizontalAnalysisTO();

        horizontalAnalysisTO.setCode( source.getCode() );
        horizontalAnalysisTO.setCodeSynthetic( source.getCodeSynthetic() );
        horizontalAnalysisTO.setCodeDescription( source.getCodeDescription() );
        horizontalAnalysisTO.setEndValueSituation( source.getEndValueSituation() );
        horizontalAnalysisTO.setCodeLevel( source.getCodeLevel() );
        horizontalAnalysisTO.setEndValue( source.getEndValue() );

        return horizontalAnalysisTO;
    }

    @Override
    public Sped bind(SpedTO source) {
        if ( source == null ) {
            return null;
        }

        Sped sped = new Sped();

        sped.setDemonstrativeInitDate( source.getDemonstrativeInitDate() );
        sped.setDemonstrativeEndDate( source.getDemonstrativeEndDate() );
        sped.setSpedBalances( spedBalanceTOListToSpedBalanceList( source.getSpedBalances() ) );
        sped.setSpedDemonstrations( spedDemonstrationTOListToSpedDemonstrationList( source.getSpedDemonstrations() ) );

        return sped;
    }

    @Override
    public List<Sped> bind(List<SpedTO> source) {
        if ( source == null ) {
            return null;
        }

        List<Sped> list = new ArrayList<Sped>( source.size() );
        for ( SpedTO spedTO : source ) {
            list.add( bind( spedTO ) );
        }

        return list;
    }

    @Override
    public List<SpedBalanceTO> bindToSpedBalanceTO(List<SpedBalance> source) {
        if ( source == null ) {
            return null;
        }

        List<SpedBalanceTO> list = new ArrayList<SpedBalanceTO>( source.size() );
        for ( SpedBalance spedBalance : source ) {
            list.add( spedBalanceToSpedBalanceTO( spedBalance ) );
        }

        return list;
    }

    @Override
    public List<SpedDemonstrationTO> bindToSpedDemonstrationTO(List<SpedDemonstration> source) {
        if ( source == null ) {
            return null;
        }

        List<SpedDemonstrationTO> list = new ArrayList<SpedDemonstrationTO>( source.size() );
        for ( SpedDemonstration spedDemonstration : source ) {
            list.add( spedDemonstrationToSpedDemonstrationTO( spedDemonstration ) );
        }

        return list;
    }

    protected SpedBalance spedBalanceTOToSpedBalance(SpedBalanceTO spedBalanceTO) {
        if ( spedBalanceTO == null ) {
            return null;
        }

        SpedBalance spedBalance = new SpedBalance();

        spedBalance.setCode( spedBalanceTO.getCode() );
        spedBalance.setCodeDescription( spedBalanceTO.getCodeDescription() );
        spedBalance.setCodeSynthetic( spedBalanceTO.getCodeSynthetic() );
        spedBalance.setEndValueSituation( spedBalanceTO.getEndValueSituation() );
        spedBalance.setCodeLevel( spedBalanceTO.getCodeLevel() );
        spedBalance.setEndValue( spedBalanceTO.getEndValue() );
        spedBalance.setInitDate( spedBalanceTO.getInitDate() );
        spedBalance.setEndDate( spedBalanceTO.getEndDate() );

        return spedBalance;
    }

    protected List<SpedBalance> spedBalanceTOListToSpedBalanceList(List<SpedBalanceTO> list) {
        if ( list == null ) {
            return null;
        }

        List<SpedBalance> list1 = new ArrayList<SpedBalance>( list.size() );
        for ( SpedBalanceTO spedBalanceTO : list ) {
            list1.add( spedBalanceTOToSpedBalance( spedBalanceTO ) );
        }

        return list1;
    }

    protected SpedDemonstration spedDemonstrationTOToSpedDemonstration(SpedDemonstrationTO spedDemonstrationTO) {
        if ( spedDemonstrationTO == null ) {
            return null;
        }

        SpedDemonstration spedDemonstration = new SpedDemonstration();

        spedDemonstration.setCode( spedDemonstrationTO.getCode() );
        spedDemonstration.setCodeDescription( spedDemonstrationTO.getCodeDescription() );
        spedDemonstration.setCodeSynthetic( spedDemonstrationTO.getCodeSynthetic() );
        spedDemonstration.setEndValueSituation( spedDemonstrationTO.getEndValueSituation() );
        spedDemonstration.setCodeLevel( spedDemonstrationTO.getCodeLevel() );
        spedDemonstration.setEndValue( spedDemonstrationTO.getEndValue() );

        return spedDemonstration;
    }

    protected List<SpedDemonstration> spedDemonstrationTOListToSpedDemonstrationList(List<SpedDemonstrationTO> list) {
        if ( list == null ) {
            return null;
        }

        List<SpedDemonstration> list1 = new ArrayList<SpedDemonstration>( list.size() );
        for ( SpedDemonstrationTO spedDemonstrationTO : list ) {
            list1.add( spedDemonstrationTOToSpedDemonstration( spedDemonstrationTO ) );
        }

        return list1;
    }

    protected SpedBalanceTO spedBalanceToSpedBalanceTO(SpedBalance spedBalance) {
        if ( spedBalance == null ) {
            return null;
        }

        SpedBalanceTO spedBalanceTO = new SpedBalanceTO();

        spedBalanceTO.setCode( spedBalance.getCode() );
        spedBalanceTO.setCodeSynthetic( spedBalance.getCodeSynthetic() );
        spedBalanceTO.setCodeDescription( spedBalance.getCodeDescription() );
        spedBalanceTO.setEndValueSituation( spedBalance.getEndValueSituation() );
        spedBalanceTO.setCodeLevel( spedBalance.getCodeLevel() );
        spedBalanceTO.setEndValue( spedBalance.getEndValue() );
        spedBalanceTO.setInitDate( spedBalance.getInitDate() );
        spedBalanceTO.setEndDate( spedBalance.getEndDate() );

        return spedBalanceTO;
    }

    protected SpedDemonstrationTO spedDemonstrationToSpedDemonstrationTO(SpedDemonstration spedDemonstration) {
        if ( spedDemonstration == null ) {
            return null;
        }

        SpedDemonstrationTO spedDemonstrationTO = new SpedDemonstrationTO();

        spedDemonstrationTO.setCode( spedDemonstration.getCode() );
        spedDemonstrationTO.setCodeSynthetic( spedDemonstration.getCodeSynthetic() );
        spedDemonstrationTO.setCodeDescription( spedDemonstration.getCodeDescription() );
        spedDemonstrationTO.setEndValueSituation( spedDemonstration.getEndValueSituation() );
        spedDemonstrationTO.setCodeLevel( spedDemonstration.getCodeLevel() );
        spedDemonstrationTO.setEndValue( spedDemonstration.getEndValue() );

        return spedDemonstrationTO;
    }
}
