package com.js.purchaseservice.directive;

import com.js.purchaseservice.util.DateValidator;
import graphql.GraphQLError;
import graphql.Scalars;
import graphql.schema.GraphQLDirective;
import graphql.schema.GraphQLInputType;
import graphql.validation.constraints.AbstractDirectiveConstraint;
import graphql.validation.constraints.Documentation;
import graphql.validation.rules.ValidationEnvironment;

import java.util.Collections;
import java.util.List;

public class DateTimeFormatterDirective extends AbstractDirectiveConstraint {

    public DateTimeFormatterDirective() {
        super("DateTimeFormatter");
    }

    @Override
    protected List<GraphQLError> runConstraint(ValidationEnvironment validationEnvironment) {
        Object validatedValue = validationEnvironment.getValidatedValue();

        String date = String.valueOf(validatedValue);

        GraphQLDirective directive = validationEnvironment.getContextObject(GraphQLDirective.class, new Object[0]);

        String format = getStrArg(directive.toAppliedDirective(), "format");

        // Note for Date:
        //A RFC-3339 compliant date scalar that accepts string values like 1996-12-19 and produces java.time.LocalDate objects at runtime.
        // Note for DateTime:
        //A RFC-3339 compliant date time scalar that accepts string values like 1996-12-19T16:39:57-08:00 and produces java.time.OffsetDateTime objects at runtime.
        boolean isValid = DateValidator.getInstance().isValidDate(date, format, true);

        if (isValid) {
            return Collections.emptyList();
        }
        return mkError(validationEnvironment, new Object[0]);
    }

    public boolean appliesToType(GraphQLInputType inputType) {
        return isStringOrIDOrList(inputType);
    }

    @Override
    protected boolean appliesToListElements() {
        return true;
    }

    @Override
    public Documentation getDocumentation() {
        return Documentation.newDocumentation()
                .messageTemplate(getMessageTemplate())
                .description(
                        "The String must be formatted in a specified way.")
                .example("updateAccident( accidentDate : String @DateTimeFormatter) : DriverDetails")
                .applicableTypeNames(new String[] { Scalars.GraphQLString.getName(), Scalars.GraphQLID.getName(), "Lists"
                }).directiveSDL(
                        "directive @DateTimeFormatter(message : String = \"%s\") on ARGUMENT_DEFINITION | INPUT_FIELD_DEFINITION",
                        new Object[] {
                                getMessageTemplate()
                        }).build();
    }
}
