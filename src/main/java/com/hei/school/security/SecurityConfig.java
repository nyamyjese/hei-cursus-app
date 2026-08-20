package com.hei.school.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(
            auth ->
                auth.requestMatchers(
                        "/ping", "/health/**", "/swagger-ui/**", "/v3/api-docs/**", "/test/**")
                    .permitAll()
                    .requestMatchers("/admin/**", "/promotions/**")
                    .hasRole("ADMIN")
                    .requestMatchers("/courses/*/grades/**", "/grades/**")
                    .hasAnyRole("TEACHER", "ADMIN")
                    .requestMatchers("/students/**")
                    .hasAnyRole("STUDENT", "ADMIN")
                    .anyRequest()
                    .authenticated())
        .httpBasic(basic -> {});

    return http.build();
  }
}
