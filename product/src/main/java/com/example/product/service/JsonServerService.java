package com.example.product.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class JsonServerService {

    @Autowired
    private RestTemplate restTemplate;

    private final String jsonServerUrl = "http://localhost:3001";

    public String getPosts() {
        return restTemplate.getForObject(jsonServerUrl + "/posts", String.class);
    }

    public String getPostById(int id) {
        return restTemplate.getForObject(jsonServerUrl + "/posts/" + id, String.class);
    }

    public String createPost(String post) {
        return restTemplate.postForObject(jsonServerUrl + "/posts", post, String.class);
    }

    public void updatePost(int id, String post) {
        restTemplate.put(jsonServerUrl + "/posts/" + id, post);
    }

    public void deletePost(int id) {
        restTemplate.delete(jsonServerUrl + "/posts/" + id);
    }
}

