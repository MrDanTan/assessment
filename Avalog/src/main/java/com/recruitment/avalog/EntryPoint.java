package com.recruitment.avalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.recruitment.avalog.controller")
public class EntryPoint {
    public static void main( String[] args ) {
        SpringApplication.run(EntryPoint.class);
    }
}
