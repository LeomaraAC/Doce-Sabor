package com.leomara.delivery.doce_sabor.resources.config;


import com.leomara.delivery.doce_sabor.config.ConfigurationTests;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class ConfigurationResourceTests extends ConfigurationTests {
    @LocalServerPort
    protected int porta;

    @BeforeEach
    void configuracaoDaPorta() {
        RestAssured.port = porta;
    }
}
