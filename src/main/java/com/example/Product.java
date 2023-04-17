package com.example;

import io.micronaut.core.annotation.Introspected;

import java.io.Serializable;

@Introspected
public class Product implements Serializable {
    private Long id;

    public Product(Long id) {
        this.id = id;
    }
}
