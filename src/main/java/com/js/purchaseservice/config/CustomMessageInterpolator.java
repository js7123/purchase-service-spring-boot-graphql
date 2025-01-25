package com.js.purchaseservice.config;

import com.js.purchaseservice.exception.BadRequestException;
import graphql.GraphQLError;
import graphql.language.SourceLocation;
import graphql.validation.interpolation.MessageInterpolator;
import graphql.validation.rules.ValidationEnvironment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class CustomMessageInterpolator implements MessageInterpolator {

    @Override
    public GraphQLError interpolate(String message, Map<String, Object> map,
                                    ValidationEnvironment validationEnvironment) {
        SourceLocation location = validationEnvironment.getLocation();
        String argumentName = validationEnvironment.getArgument().getName();
        String messageWithArgumentName = String.format(message, argumentName);

        return new BadRequestException(messageWithArgumentName, List.of(location));
    }
}