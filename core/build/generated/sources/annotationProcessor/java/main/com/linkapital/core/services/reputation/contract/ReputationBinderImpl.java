package com.linkapital.core.services.reputation.contract;

import com.linkapital.core.services.reputation.contract.to.CreateRatingTO;
import com.linkapital.core.services.reputation.contract.to.RatingTO;
import com.linkapital.core.services.reputation.contract.to.ReputationTO;
import com.linkapital.core.services.reputation.datasource.domain.Rating;
import com.linkapital.core.services.reputation.datasource.domain.Reputation;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-02-14T18:35:17-0300",
    comments = "version: 1.4.1.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.1.1.jar, environment: Java 16.0.2 (Amazon.com Inc.)"
)
public class ReputationBinderImpl implements ReputationBinder {

    @Override
    public ReputationTO bind(Reputation source) {
        if ( source == null ) {
            return null;
        }

        ReputationTO reputationTO = new ReputationTO();

        reputationTO.setId( source.getId() );
        reputationTO.setTotalRating( source.getTotalRating() );
        reputationTO.setCount1( source.getCount1() );
        reputationTO.setCount2( source.getCount2() );
        reputationTO.setCount3( source.getCount3() );
        reputationTO.setCount4( source.getCount4() );
        reputationTO.setCount5( source.getCount5() );
        reputationTO.setAvg( source.getAvg() );
        reputationTO.setAvg1( source.getAvg1() );
        reputationTO.setAvg2( source.getAvg2() );
        reputationTO.setAvg3( source.getAvg3() );
        reputationTO.setAvg4( source.getAvg4() );
        reputationTO.setAvg5( source.getAvg5() );
        reputationTO.setRatings( ratingListToRatingTOList( source.getRatings() ) );

        return reputationTO;
    }

    @Override
    public Rating bind(CreateRatingTO source) {
        if ( source == null ) {
            return null;
        }

        Rating rating = new Rating();

        rating.setEmail( source.getEmail() );
        rating.setComment( source.getComment() );
        rating.setRatingValue( source.getRatingValue() );

        return rating;
    }

    protected RatingTO ratingToRatingTO(Rating rating) {
        if ( rating == null ) {
            return null;
        }

        RatingTO ratingTO = new RatingTO();

        ratingTO.setId( rating.getId() );
        ratingTO.setEmail( rating.getEmail() );
        ratingTO.setComment( rating.getComment() );
        ratingTO.setRatingValue( rating.getRatingValue() );

        return ratingTO;
    }

    protected List<RatingTO> ratingListToRatingTOList(List<Rating> list) {
        if ( list == null ) {
            return null;
        }

        List<RatingTO> list1 = new ArrayList<RatingTO>( list.size() );
        for ( Rating rating : list ) {
            list1.add( ratingToRatingTO( rating ) );
        }

        return list1;
    }
}
