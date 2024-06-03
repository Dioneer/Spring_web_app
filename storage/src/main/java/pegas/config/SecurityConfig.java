package pegas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/api/v1","/api/v1/filter").permitAll()
                        .requestMatchers( antMatcher("/api/v1/{\\id}"), antMatcher("/api/v1/{\\id}/sale")
                        ,antMatcher("/api/v1/{\\id}/reservation")
                                ,antMatcher("/api/v1/{\\id}/unreservation")
                                ,antMatcher("/api/v1/{\\id}/image")
                                ,antMatcher("/api/v1/{\\id}/findImage"))
                        .permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(config -> config.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(CsrfConfigurer::disable)
                .oauth2ResourceServer(i -> i.jwt(jwt -> {
                    var jwtAuthenticationConverter = new JwtAuthenticationConverter();
                    jwtAuthenticationConverter.setPrincipalClaimName("preferred_username");
                    jwt.jwtAuthenticationConverter(jwtAuthenticationConverter);
                }));

        return http.build();
    }

}
