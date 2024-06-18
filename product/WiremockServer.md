Yes, you can run WireMock as a standalone server from within a Spring Boot application, with both running on different ports. To achieve this, you need to set up WireMock to start as an independent server when your Spring Boot application starts. Here’s a step-by-step guide on how to do this:

### Step 1: Add WireMock Dependencies

Add the WireMock dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>com.github.tomakehurst</groupId>
    <artifactId>wiremock-jre8</artifactId>
    <version>2.31.0</version>
</dependency>
```

### Step 2: Configure WireMock as a Standalone Server

Create a configuration class to start WireMock on a different port. Ensure that this server runs independently of the main application.

```java
import com.github.tomakehurst.wiremock.WireMockServer;
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
```

### Step 3: Make HTTP Requests to WireMock Server

In your Spring Boot application, use `RestTemplate` or another HTTP client to make requests to the WireMock server.

1. **Add RestTemplate Bean**:

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

2. **Create a Controller to Fetch Data from WireMock**:

   ```java
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
   ```

### Step 4: Run Your Application

When you run your Spring Boot application, the WireMock server will start on port 8089, while your Spring Boot application runs on its default port (e.g., 8080).

### Summary

1. **Configure WireMock**: Set up WireMock as a standalone server within your Spring Boot application configuration.
2. **Start WireMock**: Ensure WireMock starts when the application starts and stops when the application stops.
3. **Interact with WireMock**: Use `RestTemplate` or another HTTP client to interact with the WireMock server from your Spring Boot application.

By following these steps, you can run WireMock as a standalone server within a Spring Boot application, with both running on different ports, allowing you to store and retrieve sample API requests and responses.