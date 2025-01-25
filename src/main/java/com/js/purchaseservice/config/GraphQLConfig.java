package com.js.purchaseservice.config;

import graphql.scalars.ExtendedScalars;
import graphql.schema.GraphQLScalarType;
import graphql.validation.rules.OnValidationErrorStrategy;
import graphql.validation.rules.ValidationRules;
import graphql.validation.schemawiring.ValidationSchemaWiring;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;


@Configuration
@RequiredArgsConstructor
public class GraphQLConfig {

    private final CustomMessageInterpolator customMessageInterpolator;

    /**
     * ExtendedScalars needed to register 'Long' and 'Date' extended GraphQL types.
     * Adds GraphQL validation with validation rules and message interpolator.
     */
    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {

        ValidationRules validationRules =
                ValidationRules.newValidationRules().onValidationErrorStrategy(OnValidationErrorStrategy.RETURN_NULL)
                        .messageInterpolator(customMessageInterpolator).build();

        ValidationSchemaWiring schemaWiring = new ValidationSchemaWiring(validationRules);

        GraphQLScalarType nonEmptyStringScalar = ScalarConfig.NonEmptyString;

        return wiringBuilder -> wiringBuilder
                .scalar(nonEmptyStringScalar)
                .scalar(ExtendedScalars.GraphQLLong)
                .scalar(ExtendedScalars.DateTime)
                .directiveWiring(schemaWiring);
    }
}
