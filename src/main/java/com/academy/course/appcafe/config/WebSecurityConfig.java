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
                        .requestMatchers("/registerForm","/getEmployeePage"
                                ,"/getCategoryPage", "/manager",
                                "/registerEmployee","/removeProductFromCategory","/addCategory",
                                "/addProductInCategory","/updateEmployee","/deleteEmployee","/deleteCategory","/deleteProduct","/findEmployeeById","/findEmployeeByName"
                        )
                        .hasAnyAuthority("MANAGER")

                        .requestMatchers("/getProductPage","/addProduct",
                                "/setIsAvailable","/admin","/addProduct","/editLimit",
                                "/editProduct","/setIsAvailableInEditor","/updateProduct",
                                "/findProductByName","/findProductById")
                        .hasAnyAuthority("MANAGER","ADMIN")

                        .requestMatchers("/cashier","/getOrderPage","/removeProductFromOrder",
                                "/permitPurchase","/editLimit","/addProductInOrder/",
                                "/buyOrder","/addNewOrder","/newOrderPage/",
                                "/purchasingProcess/","/setProductLimit","/main","/getOrderPage")
                        .hasAnyAuthority("MANAGER","ADMIN","CASHIER")
                        .requestMatchers("/login").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form->form
                        .permitAll()
                        .defaultSuccessUrl("/main",true)
                )
                .logout(logout->logout
                        .permitAll()
                        .logoutSuccessUrl("/login")
                );
        return http.build();
    }

}
