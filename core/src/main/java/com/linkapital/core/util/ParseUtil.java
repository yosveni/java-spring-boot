package com.linkapital.core.util;

import java.nio.charset.Charset;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import static org.flywaydb.core.internal.util.StringUtils.hasText;

/**
 * Has the responsibility to perform operations on String objects.
 */
public class ParseUtil {

    /**
     * Converts a list of string to a string.
     */
    public static final Function<List<String>, String> parseList = stringList -> {
        var value = new StringBuilder();

        for (String s : stringList) {
            if (stringList.indexOf(s) == 0)
                value = new StringBuilder(s);
            else
                value.append(", ").append(s);
        }

        return value.toString();
    };

    /**
     * Encodes a String into provided charset
     */
    public static final BiFunction<String, Charset, String> encodeString = (s, charset) -> hasText(s)
            ? charset.decode(charset.encode(s)).toString()
            : s;

    private ParseUtil() {
    }

}
