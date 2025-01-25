package com.js.purchaseservice.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class DateValidator {

    private final int dateStyle;

    private final boolean strict;

    private static final DateValidator VALIDATOR = new DateValidator();

    public static DateValidator getInstance() {
        return VALIDATOR;
    }

    public DateValidator() {
        this(true, DateFormat.SHORT);
    }

    public DateValidator(final boolean strict, final int dateStyle) {
        this.strict = strict;
        this.dateStyle = dateStyle;
    }

    /**
     * Checks value is a valid date.
     * @param value Value on which validation is performed
     * @param datePattern Pattern passed
     * @param strict Whether an exact match of datePattern is wanted
     * @return true if date is valid
     */
    public boolean isValidDate(final String value, final String datePattern, final boolean strict) {

        if (value == null
                || datePattern == null
                || datePattern.isEmpty()) {

            return false;
        }

        final SimpleDateFormat formatter = new SimpleDateFormat(datePattern);
        formatter.setLenient(false);

        try {
            formatter.parse(value);
        } catch (final ParseException e) {
            return false;
        }

        if (strict && datePattern.length() != value.length()) {
            return false;
        }

        return true;
    }
}
