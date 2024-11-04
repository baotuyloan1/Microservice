package com.example.microserviceaccounts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MicroserviceAccountsApplication {

    /**
     * This is the main entry point for the microservice.  It starts
     * the spring application.
     *
     * @param args Command line arguments.  These are ignored.
     */
    public static void main(String[] args) {
        SpringApplication.run(MicroserviceAccountsApplication.class, args);
    }

}
