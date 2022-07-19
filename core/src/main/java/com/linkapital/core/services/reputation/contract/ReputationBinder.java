package com.linkapital.core.services.reputation.contract;

import com.linkapital.core.services.reputation.contract.to.CreateRatingTO;
import com.linkapital.core.services.reputation.contract.to.ReputationTO;
import com.linkapital.core.services.reputation.datasource.domain.Rating;
import com.linkapital.core.services.reputation.datasource.domain.Reputation;
import com.linkapital.core.util.functions.TriConsumer;
import com.linkapital.core.util.functions.TriFunction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.ToDoubleBiFunction;
import java.util.function.ToDoubleFunction;

import static java.math.RoundingMode.HALF_UP;

@Mapper
public interface ReputationBinder {

    ReputationBinder REPUTATION_BINDER = Mappers.getMapper(ReputationBinder.class);

    BiFunction<String, List<Rating>, Optional<Rating>> getRateByEmail = (email, ratings) -> ratings
            .stream()
            .filter(rating -> rating.getEmail().equals(email))
            .findFirst();

    ToDoubleBiFunction<Integer, Integer> calculateAvgCount = (rating, total) ->
            Double.parseDouble(new DecimalFormat("#.0").format(
                            rating * 100L / total)
                    .replace(",", "."));

    ToDoubleBiFunction<Integer, Double> calculateAvg = (rating, avg) ->
            avg == 0 ? BigDecimal.valueOf(0).doubleValue() :
                    BigDecimal
                            .valueOf((avg + rating) / 2)
                            .setScale(2, HALF_UP)
                            .doubleValue();

    ToDoubleFunction<List<Rating>> updateAvg = ratings -> BigDecimal
            .valueOf(ratings
                    .stream()
                    .mapToDouble(Rating::getRatingValue)
                    .sum() / ratings.size())
            .setScale(2, HALF_UP)
            .doubleValue();

    TriConsumer<Integer, Reputation, Boolean> updateCount = (rating, reputation, increment) -> {
        switch (rating) {
            case 1:
                if (Boolean.TRUE.equals(increment))
                    reputation.setCount1(reputation.getCount1() + 1);
                else
                    reputation.setCount1(reputation.getCount1() - 1);
                break;
            case 2:
                if (Boolean.TRUE.equals(increment))
                    reputation.setCount2(reputation.getCount2() + 1);
                else
                    reputation.setCount2(reputation.getCount2() - 1);
                break;
            case 3:
                if (Boolean.TRUE.equals(increment))
                    reputation.setCount3(reputation.getCount3() + 1);
                else
                    reputation.setCount3(reputation.getCount3() - 1);
                break;
            case 4:
                if (Boolean.TRUE.equals(increment))
                    reputation.setCount4(reputation.getCount4() + 1);
                else
                    reputation.setCount4(reputation.getCount4() - 1);
                break;
            default:
                if (Boolean.TRUE.equals(increment))
                    reputation.setCount5(reputation.getCount5() + 1);
                else
                    reputation.setCount5(reputation.getCount5() - 1);
        }
    };

    Consumer<Reputation> updateAvgCount = reputation -> {
        reputation.setAvg1(calculateAvgCount.applyAsDouble(reputation.getCount1(), reputation.getTotalRating()));
        reputation.setAvg2(calculateAvgCount.applyAsDouble(reputation.getCount2(), reputation.getTotalRating()));
        reputation.setAvg3(calculateAvgCount.applyAsDouble(reputation.getCount3(), reputation.getTotalRating()));
        reputation.setAvg4(calculateAvgCount.applyAsDouble(reputation.getCount4(), reputation.getTotalRating()));
        reputation.setAvg5(calculateAvgCount.applyAsDouble(reputation.getCount5(), reputation.getTotalRating()));
    };

    BiFunction<Rating, Reputation, Reputation> buildReputation = (rating, reputation) -> {
        reputation.getRatings().add(rating);
        reputation.setTotalRating(reputation.getRatings().size());
        reputation.setAvg(calculateAvg.applyAsDouble(rating.getRatingValue(), reputation.getAvg()));

        updateCount.accept(rating.getRatingValue(), reputation, true);
        updateAvgCount.accept(reputation);

        return reputation;
    };

    TriFunction<Rating, Rating, Reputation, Reputation> updateRate = (newRating, rating, reputation) -> {
        updateCount.accept(rating.getRatingValue(), reputation, false);
        updateCount.accept(newRating.getRatingValue(), reputation, true);
        updateAvgCount.accept(reputation);

        rating.setComment(newRating.getComment());
        rating.setRatingValue(newRating.getRatingValue());
        reputation.setAvg(updateAvg.applyAsDouble(reputation.getRatings()));

        return reputation;
    };

    TriFunction<String, CreateRatingTO, Reputation, Reputation> setReputation = (email, to, reputation) -> {
        var newRating = REPUTATION_BINDER.bind(to);
        var saved = new AtomicReference<>(reputation);

        getRateByEmail.apply(email, reputation.getRatings())
                .ifPresentOrElse(rating -> saved.set(updateRate.apply(newRating, rating, reputation)),
                        () -> saved.set(buildReputation.apply(newRating, reputation)));

        return saved.get();
    };

    ReputationTO bind(Reputation source);

    @Mapping(target = "id", ignore = true)
    Rating bind(CreateRatingTO source);

}
