package pegas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.web.client.RestClient;

import java.util.Objects;

@SpringBootApplication
public class ApplicationRunner5 {

    private OAuth2AuthorizedClientManager authorizedClientManager;

    @Bean
    public RestClient rest(ClientRegistrationRepository clientRegistrationRepository,
                           OAuth2AuthorizedClientRepository authorizedClientRepository){
        this.authorizedClientManager = new DefaultOAuth2AuthorizedClientManager(
                clientRegistrationRepository, authorizedClientRepository);
        return RestClient.builder()
                .requestInterceptor((request, body, execution) -> {
                    if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                        var token = Objects.requireNonNull(this.authorizedClientManager.authorize(
                                        OAuth2AuthorizeRequest
                                                .withClientRegistrationId("storage-app-client-credential")
                                                .principal(SecurityContextHolder.getContext().getAuthentication())
                                                .build()))
                                .getAccessToken().getTokenValue();
                        request.getHeaders().setBearerAuth(token);
                    }

                    return execution.execute(request, body);
                })
                .build();
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
