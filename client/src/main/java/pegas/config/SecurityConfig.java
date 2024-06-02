package pegas.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final SuccessHandler successHandler;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(i -> i
                        .requestMatchers( "v3/storage","v3/image/**").permitAll()
                        .anyRequest().authenticated())
                .formLogin(login -> login.loginPage("/v3/users/login")
                        .successHandler(successHandler)
                        .permitAll())
                .logout(i->i.logoutUrl("/v3/users/logout")
                        .invalidateHttpSession(true)
                        .logoutSuccessUrl("/v3/users/login")
                        .deleteCookies("JSESSIONID")
                        .permitAll());
        return http.build();
    }
}
