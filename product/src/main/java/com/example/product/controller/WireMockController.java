package com.example.product.controller;

import com.example.product.model.StubRequest;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.http.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/wiremock")
public class WireMockController {

    @Autowired
    private WireMockServer wireMockServer;

    @PostMapping("/store")
    public String storeStub(@RequestBody StubRequest stubRequest) {
        wireMockServer.stubFor(WireMock.request(stubRequest.getMethod().getName(), WireMock.urlEqualTo(stubRequest.getUrl()))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(stubRequest.getResponseBody())));
        return "Stub created for " + stubRequest.getMethod() + " " + stubRequest.getUrl();
    }

    @GetMapping("/retrieve")

    public String retrieveStub(@RequestParam String url) {
        // Use RestTemplate to call the WireMock server
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject("http://localhost:8089" + url, String.class);
    }
}

