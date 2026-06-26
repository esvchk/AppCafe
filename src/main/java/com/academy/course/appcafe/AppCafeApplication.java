package com.academy.course.appcafe;

import com.academy.course.appcafe.repository.ProductRepository;
import com.academy.course.appcafe.service.ProductService;
import com.academy.course.appcafe.service.ProductServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppCafeApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppCafeApplication.class, args);

    }

}
