package org.home.tutorial.springsecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .antMatchers("/", "/unsecured*/**").permitAll()
                        .antMatchers("/admin*/**").hasRole("ADMIN")
                        // .antMatchers("/").permitAll()
                        .anyRequest().authenticated()) // this covers ALL other places not mentioned above, requiring
                                                       // for any other locations you must be authenticated
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .exceptionHandling(ex -> ex
                        .accessDeniedPage("/error/accessDenied.html"));
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(
                User.builder()
                        .username("user")
                        .password(passwordEncoder.encode("user"))
                        .roles("USER")
                        .build());

        manager.createUser(
                User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .roles("ADMIN")
                        .build());

        return manager;
    }
}
