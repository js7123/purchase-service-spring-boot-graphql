package com.js.purchaseservice.config;

import graphql.GraphQLContext;
import graphql.execution.CoercedVariables;
import graphql.language.StringValue;
import graphql.language.Value;
import graphql.schema.*;
import jakarta.validation.constraints.NotNull;

import java.util.Locale;

public class ScalarConfig {

    public static final GraphQLScalarType NonEmptyString =
            GraphQLScalarType.newScalar().name("NonEmptyString").description("A custom scalar that handles non empty strings").coercing(new Coercing() {
        @Override
        public Object serialize(@NotNull Object dataFetcherResult, @NotNull GraphQLContext graphQLContext, @NotNull Locale locale) throws CoercingSerializeException {
            try {
                String value = (String) dataFetcherResult;
                if (value == null || value.trim().isEmpty()) {
                    throw new CoercingSerializeException("Invalid input: String is null or empty");
                }
                return value;
            } catch (CoercingSerializeException exception) {
                throw new CoercingSerializeException("Invalid input: " + exception.getMessage());
            }
        }

        @Override
        public Object parseValue(@NotNull Object input, @NotNull GraphQLContext graphQLContext, @NotNull Locale locale) throws CoercingParseValueException {
            try {
                String value = (String) input;
                if (value == null || value.trim().isEmpty()) {
                    throw new CoercingParseValueException("String is null or empty");
                }
                return value;
            } catch (RuntimeException exception) {
                throw new CoercingParseValueException("Invalid input: " + exception.getMessage());
            }
        }

        @Override
        public Object parseLiteral(@NotNull Value input, @NotNull CoercedVariables variables, @NotNull GraphQLContext graphQLContext, @NotNull Locale locale) throws CoercingParseLiteralException {
            try {
                StringValue stringValue = (StringValue) input;
                String value = stringValue.getValue();
                if (value == null || value.trim().isEmpty()) {
                    throw new CoercingParseLiteralException("String is null or empty");
                }
                return value;
            } catch (RuntimeException exception) {
                throw new CoercingParseLiteralException("Invalid input: " + exception.getMessage());
            }
        }

        @Override
        public @NotNull Value<?> valueToLiteral(@NotNull Object input, @NotNull GraphQLContext graphQLContext, @NotNull Locale locale) {
            return new StringValue(input.toString());
        }
    }).build();

}
