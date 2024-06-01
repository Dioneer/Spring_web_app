package pegas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(CsrfConfigurer::disable)
                .authorizeHttpRequests(i-> i
                        .requestMatchers("/v3/users/**","/v3/payment/**","/v3/storage/**").permitAll()
                        .anyRequest().authenticated())
                .formLogin(login-> login.loginPage("/v3/users/login")
                        .defaultSuccessUrl("/v3/users/income")
                        .permitAll())
                .logout(i->i.logoutUrl("/v3/users/logout")
                        .invalidateHttpSession(true)
                        .logoutSuccessUrl("/v3/users/login")
                        .deleteCookies("JSESSIONID")
                        .permitAll());
        return http.build();
    }
}
