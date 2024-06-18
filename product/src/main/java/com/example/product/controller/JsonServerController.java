package com.example.product.controller;

import com.example.product.service.JsonServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class JsonServerController {

    @Autowired
    private JsonServerService jsonServerService;

    @GetMapping("/posts")
    public String getPosts() {
        return jsonServerService.getPosts();
    }

    @GetMapping("/posts/{id}")
    public String getPostById(@PathVariable int id) {
        return jsonServerService.getPostById(id);
    }

    @PostMapping("/posts")
    public String createPost(@RequestBody String post) {
        return jsonServerService.createPost(post);
    }

    @PutMapping("/posts/{id}")
    public void updatePost(@PathVariable int id, @RequestBody String post) {
        jsonServerService.updatePost(id, post);
    }

    @DeleteMapping("/posts/{id}")
    public void deletePost(@PathVariable int id) {
        jsonServerService.deletePost(id);
    }
}

