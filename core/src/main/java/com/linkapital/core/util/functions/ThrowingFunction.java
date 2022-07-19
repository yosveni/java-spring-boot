package com.linkapital.core.util.functions;

import java.util.function.Function;

@FunctionalInterface
public interface ThrowingFunction<T, R, E extends Throwable> {

    static <T, R, E extends Exception> Function<T, R> unchecked(ThrowingFunction<T, R, E> f) {
        return t -> {
            try {
                return f.apply(t);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        };
    }

    R apply(T t) throws E;

}