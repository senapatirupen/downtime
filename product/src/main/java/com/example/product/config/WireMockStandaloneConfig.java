package com.example.product.config;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WireMockStandaloneConfig {

    @Bean
    public WireMockServer wireMockServer() {
        WireMockServer wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().port(8089));
        wireMockServer.start();

        // Add your stub configurations here if needed
        wireMockServer.stubFor(WireMock.get(WireMock.urlEqualTo("/sample-api"))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"message\": \"Hello, WireMock!\"}")));

        return wireMockServer;
    }

    @Bean
    public CommandLineRunner commandLineRunner(WireMockServer wireMockServer) {
        return args -> {
            Runtime.getRuntime().addShutdownHook(new Thread(wireMockServer::stop));
        };
    }
}

