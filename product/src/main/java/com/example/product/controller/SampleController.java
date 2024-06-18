package com.example.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class SampleController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/fetch-sample-api")
    public String fetchSampleApi() {
        String wireMockUrl = "http://localhost:8089/sample-api";
        return restTemplate.getForObject(wireMockUrl, String.class);
    }
}

