package de.schmiereck.eventDemo1;

import java.util.Objects;

public class ParseValueUtils {

    public static Integer parseIntegerValue(final String valueStr) {
        final Integer retValue;
        if (Objects.nonNull(valueStr) && !valueStr.isBlank()) {
            retValue = Integer.valueOf(valueStr);
        } else {
            retValue = null;
        }
        return retValue;
    }

    public static String parseIntegerProperty(final Integer value) {
        final String ret;
        if (Objects.nonNull(value)) {
            ret = String.valueOf(value);
        } else {
            ret = "";
        }
        return ret;
    }
}
