package com.square.www.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/*
 * Controllers, clients and a UI layer has not been covered as described in the original task:
 * This means; the services are not exposed via restful APIs nor consumed by UI components or another client
 * to satisfy original requirements only.
 * The services are well tested though and most of the edge cases are covered under test sources root.
 */
@SpringBootApplication(scanBasePackages = {"com.square.www.platform"})
public class Starter extends SpringBootServletInitializer {

    public static void main(String... args) {
        SpringApplication.run(Starter.class, args);
    }
}
