package pegas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ApplicationRunner3 {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationRunner3.class, args);
    }
}
