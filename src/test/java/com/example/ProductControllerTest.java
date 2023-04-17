package com.example;

import com.example.embedded.SingleInstancePostgresExtension;
import io.micronaut.context.ApplicationContext;
import io.micronaut.runtime.server.EmbeddedServer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import reactor.core.publisher.Flux;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductControllerTest {
    private static final int DB_PORT = 5430;
    private static final String DB_NAME = "serverdb";

    private static EmbeddedServer server;

    private static ProductClient productClient;

    @RegisterExtension
    public static SingleInstancePostgresExtension EPG = new SingleInstancePostgresExtension().customize(b -> b.setPort(DB_PORT));

    @BeforeAll
    public static void setupServer() throws SQLException {
        try (Connection c = EPG.getEmbeddedPostgres().getPostgresDatabase().getConnection(); Statement st = c.createStatement()) {
           // st.execute("DROP DATABASE " + DB_NAME);
            st.execute("CREATE DATABASE " + DB_NAME);
        }
        server = ApplicationContext.run(EmbeddedServer.class, Map.of("application.database.port", DB_PORT,
                "application.database.db-name", DB_NAME,
                "flyway.enabled", Boolean.FALSE), "test-no-datasource");
        productClient = server.getApplicationContext().getBean(ProductClient.class);
    }


    @Test
    public void computeDiffs() {
        List<Long> ids = Arrays.asList(1L, 2L, 3L, 4L);
        Flux<Product> flux = productClient.compute(1, Flux.fromIterable(ids), Optional.empty(), Optional.of(ModeArg.ALL));
        assertEquals(4L, flux.toStream().count());
    }
}
