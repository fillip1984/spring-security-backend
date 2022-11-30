package org.home.tutorial.springsecurity;

import org.home.tutorial.springsecurity.services.JpaUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JpaUserDetailsService userDetailsService;

    public SecurityConfig(JpaUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.ignoringAntMatchers("/h2-console/**"))
                .authorizeHttpRequests(authorize -> authorize
                        .antMatchers("/", "/unsecured/**").permitAll()
                        .antMatchers("/admin/**",
                                "/h2-console/**")
                        .hasRole("ADMIN")
                        .anyRequest().authenticated()) // this covers ALL other places not mentioned above, requiring
                                                       // for any other locations you must be authenticated
                .userDetailsService(userDetailsService)
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .headers(headers -> headers.frameOptions().sameOrigin())
                .exceptionHandling(ex -> ex
                        .accessDeniedPage("/error/accessDenied.html"));
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
