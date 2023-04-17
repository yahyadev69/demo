package com.example;

import io.micronaut.http.client.annotation.Client;

@Client("/app")
public interface ProductClient extends ProductOperations {
}
