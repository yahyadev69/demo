package com.example;

import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Patch;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.validation.Validated;
import reactor.core.publisher.Flux;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Validated
public interface ProductOperations {

    @Patch("/products/{id}/items{?mode:[PL|pl|SH|sh],args:[ALL|all|NEW|new]")
    Flux<Product> compute(long id, @NotNull @Body Flux<Long> itemIds, @QueryValue Optional<Mode> mode, @QueryValue Optional<ModeArg> args);
}
