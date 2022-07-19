package com.linkapital.core.util.generic;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Function;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class GenericUtil {

    public static Function<String, Boolean> isStringNullOrEmpty = s -> isNull(s) || s.trim().equals("");

    public static Function<Map<?, ?>, Boolean> isMapNullOrEmpty = m -> isNull(m) || m.isEmpty();

    public static BiPredicate<Map<?, ?>, String[]> isMapContainsKeys = (map, candidates) -> {
        if (map.isEmpty()) return false;

        return Arrays
                .stream(candidates)
                .filter(s -> map.containsKey(s) || nonNull(map.get(s)))
                .count() == Arrays.asList(candidates).size();
    };

    public static Function<Collection<?>, Boolean> isCollectionNullOrEmpty = c -> isNull(c) || c.isEmpty();

}

