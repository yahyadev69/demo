package com.example;

import io.micronaut.context.annotation.Parameter;
import io.micronaut.http.annotation.Controller;
import reactor.core.publisher.Flux;

import java.util.Optional;

@Controller("/app")
public class ProductController implements ProductOperations {

    @Override
    public Flux<Product> compute(long id, Flux<Long> itemIds, @Parameter() Optional<Mode> mode, @Parameter() Optional<ModeArg> args) {
        return itemIds.map(Product::new);
    }
}
