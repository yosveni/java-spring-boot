package com.linkapital.core.services.method_k.contract;

import com.linkapital.core.services.method_k.contract.to.ScoreAnalysisTO;
import com.linkapital.core.services.method_k.contract.to.ScoreOperationTO;
import com.linkapital.core.services.method_k.datasource.domain.ScoreAnalysis;
import com.linkapital.core.services.method_k.datasource.domain.ScoreOperation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-02-17T22:29:28-0300",
    comments = "version: 1.4.1.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.1.1.jar, environment: Java 16.0.2 (Amazon.com Inc.)"
)
public class ScoreSummaryBinderImpl implements ScoreSummaryBinder {

    @Override
    public List<ScoreAnalysisTO> bind(Set<ScoreAnalysis> source) {
        if ( source == null ) {
            return null;
        }

        List<ScoreAnalysisTO> list = new ArrayList<ScoreAnalysisTO>( source.size() );
        for ( ScoreAnalysis scoreAnalysis : source ) {
            list.add( scoreAnalysisToScoreAnalysisTO( scoreAnalysis ) );
        }

        return list;
    }

    protected ScoreOperationTO scoreOperationToScoreOperationTO(ScoreOperation scoreOperation) {
        if ( scoreOperation == null ) {
            return null;
        }

        ScoreOperationTO scoreOperationTO = new ScoreOperationTO();

        if ( scoreOperation.getId() != null ) {
            scoreOperationTO.setId( scoreOperation.getId() );
        }
        scoreOperationTO.setDescription( scoreOperation.getDescription() );
        scoreOperationTO.setValue( scoreOperation.getValue() );

        return scoreOperationTO;
    }

    protected List<ScoreOperationTO> scoreOperationSetToScoreOperationTOList(Set<ScoreOperation> set) {
        if ( set == null ) {
            return null;
        }

        List<ScoreOperationTO> list = new ArrayList<ScoreOperationTO>( set.size() );
        for ( ScoreOperation scoreOperation : set ) {
            list.add( scoreOperationToScoreOperationTO( scoreOperation ) );
        }

        return list;
    }

    protected ScoreAnalysisTO scoreAnalysisToScoreAnalysisTO(ScoreAnalysis scoreAnalysis) {
        if ( scoreAnalysis == null ) {
            return null;
        }

        ScoreAnalysisTO scoreAnalysisTO = new ScoreAnalysisTO();

        if ( scoreAnalysis.getId() != null ) {
            scoreAnalysisTO.setId( scoreAnalysis.getId() );
        }
        scoreAnalysisTO.setYear( scoreAnalysis.getYear() );
        scoreAnalysisTO.setTotal( scoreAnalysis.getTotal() );
        scoreAnalysisTO.setType( scoreAnalysis.getType() );
        scoreAnalysisTO.setOperations( scoreOperationSetToScoreOperationTOList( scoreAnalysis.getOperations() ) );

        return scoreAnalysisTO;
    }
}
