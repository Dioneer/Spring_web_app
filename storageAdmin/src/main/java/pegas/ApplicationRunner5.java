package pegas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestClient;

@SpringBootApplication
public class ApplicationRunner5 {
    @Bean
    public RestClient client(){
        return RestClient.builder().build();
    }

    @Bean
    public HttpHeaders headers()
    {
        return new HttpHeaders();
    }

    public static void main(String[] args) {
        var context = SpringApplication.run(ApplicationRunner5.class, args);
    }
}
