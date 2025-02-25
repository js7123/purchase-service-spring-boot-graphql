package com.js.purchaseservice.exception;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CustomExceptionResolver extends DataFetcherExceptionResolverAdapter {
    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        if (ex instanceof CustomerNotFoundException) {
            return GraphqlErrorBuilder.newError().errorType(ErrorType.NOT_FOUND)
                    .extensions(((CustomerNotFoundException) ex).getExtensions())
                    .message(ex.getMessage()).path(env.getExecutionStepInfo().getPath())
                    .location(env.getField().getSourceLocation()).build();
        } else if (ex instanceof LineItemNotFoundException) {
            return GraphqlErrorBuilder.newError().errorType(ErrorType.NOT_FOUND)
                    .extensions(((LineItemNotFoundException) ex).getExtensions())
                    .message(ex.getMessage()).path(env.getExecutionStepInfo().getPath())
                    .location(env.getField().getSourceLocation()).build();
        } else if (ex instanceof PurchaseOrderNotFoundException) {
            return GraphqlErrorBuilder.newError().errorType(ErrorType.NOT_FOUND)
                    .extensions(((PurchaseOrderNotFoundException) ex).getExtensions())
                    .message(ex.getMessage()).path(env.getExecutionStepInfo().getPath())
                    .location(env.getField().getSourceLocation()).build();
        } else if (ex instanceof StockItemNotFoundException) {
            return GraphqlErrorBuilder.newError().errorType(ErrorType.NOT_FOUND)
                    .extensions(((StockItemNotFoundException) ex).getExtensions())
                    .message(ex.getMessage()).path(env.getExecutionStepInfo().getPath())
                    .location(env.getField().getSourceLocation()).build();
        } else if (ex instanceof EntityNotFoundException) {
            return GraphqlErrorBuilder.newError().errorType(ErrorType.NOT_FOUND)
                    .message(ex.getMessage()).path(env.getExecutionStepInfo().getPath())
                    .location(env.getField().getSourceLocation()).build();
        } else if (ex instanceof BadRequestException) {
            return GraphqlErrorBuilder.newError().errorType(ErrorType.NOT_FOUND)
                    .message(ex.getMessage()).path(env.getExecutionStepInfo().getPath())
                    .location(env.getField().getSourceLocation()).build();
        } else if (ex instanceof AbstractGraphQLException) {
            return GraphqlErrorBuilder.newError().errorType(ErrorType.INTERNAL_ERROR).message(ex.getMessage()).path(env.getExecutionStepInfo().getPath()).location(env.getField().getSourceLocation()).extensions(((AbstractGraphQLException) ex).getExtensions()).build();
        } else {
            return null;
        }
    }
}
