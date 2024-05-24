package pegas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ApplicationRunner {

    @Bean
    public RestTemplate template(){
        return new RestTemplate();
    }
    @Bean
    public RestClient client(){
        return RestClient.builder().build();
    }

    public static void main(String[] args) {
        var context = SpringApplication.run(ApplicationRunner.class, args);
    }
}


