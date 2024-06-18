package com.example.product.model;

import com.github.tomakehurst.wiremock.http.RequestMethod;

public class StubRequest {
    private String url;
    private String responseBody;
    private RequestMethod method;

    // Getters and Setters
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public RequestMethod getMethod() {
        return method;
    }

    public void setMethod(RequestMethod method) {
        this.method = method;
    }
}

