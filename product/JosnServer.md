To use JSON Server as a standalone server along with a Spring Boot application, you'll need to set up JSON Server independently of the Spring Boot application. JSON Server can be used to mock a REST API using a simple JSON file, and you can interact with it from your Spring Boot application.

### Step 1: Install JSON Server

First, install JSON Server globally using npm:

```sh
npm install -g json-server
```

### Step 2: Create a JSON File

Create a `db.json` file that contains your mock data. For example:

```json
{
  "posts": [
    { "id": 1, "title": "Hello World" },
    { "id": 2, "title": "JSON Server" }
  ],
  "comments": [
    { "id": 1, "body": "some comment", "postId": 1 },
    { "id": 2, "body": "another comment", "postId": 1 }
  ]
}
```

### Step 3: Run JSON Server

Start JSON Server to serve the data from the `db.json` file. By default, JSON Server runs on port 3000.

```sh
json-server --watch db.json --port 3001
```

This starts JSON Server on port 3001. You can change the port as needed.

### Step 4: Interact with JSON Server from Spring Boot

In your Spring Boot application, you can use `RestTemplate` to interact with the JSON Server.

1. **Add RestTemplate Bean**

   ```java
   import org.springframework.context.annotation.Bean;
   import org.springframework.context.annotation.Configuration;
   import org.springframework.web.client.RestTemplate;

   @Configuration
   public class AppConfig {

       @Bean
       public RestTemplate restTemplate() {
           return new RestTemplate();
       }
   }
   ```

2. **Create a Service to Interact with JSON Server**

   ```java
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
   ```

3. **Create a Controller to Expose Endpoints**

   ```java
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
   ```

### Step 5: Run Your Spring Boot Application

When you run your Spring Boot application, it will interact with the JSON Server running on port 3001. You can now test your endpoints and verify that they correctly interact with the JSON Server.

### Summary

1. **Install JSON Server**: Install JSON Server globally using npm.
2. **Create JSON Data**: Define your mock data in a `db.json` file.
3. **Run JSON Server**: Start JSON Server to serve your mock data.
4. **Configure Spring Boot**: Use `RestTemplate` in your Spring Boot application to interact with the JSON Server.
5. **Create Service and Controller**: Implement services and controllers to expose endpoints that interact with the JSON Server.

By following these steps, you can run JSON Server as a standalone server alongside your Spring Boot application, enabling you to mock and test API interactions easily.