package com.academy.course.appcafe.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth->auth
                        .requestMatchers("/getProductPage").permitAll()
                        .requestMatchers("/registerForm").permitAll()
                        .requestMatchers("/registerEmployee").permitAll()
                        .requestMatchers("/getEmployeePage").permitAll()
                        .requestMatchers("/").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form->form
                        .permitAll()
                        .defaultSuccessUrl("/getProductPage",true)
                )
                .logout(logout->logout
                        .permitAll()
                        .logoutSuccessUrl("/login")
                );
        return http.build();
    }
}
