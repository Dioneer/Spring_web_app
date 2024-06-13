package pegas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApplicationRunner4 {
    public static void main(String[] args) {
        var context = SpringApplication.run(ApplicationRunner4.class, args);
    }
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder){
        return builder.routes()
                .route("app1", r->r.path("/tasks/**").uri("http://localhost:8080/"))
                .route("app1", r->r.path("/upload/**").uri("http://localhost:8080/"))
                .build();
    }
}
