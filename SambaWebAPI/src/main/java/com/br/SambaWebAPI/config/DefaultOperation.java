package com.br.SambaWebAPI.config;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * DefaultOperation
 * Auto Doc Swagger
 * Github-RodolfoBrandaoOficial
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Operation
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "404", description = "Resource not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
})
public @interface DefaultOperation {
    String summary() default "";
    String description() default "";
    String[] tags() default {};
    Parameter[] parameters() default {};
}

