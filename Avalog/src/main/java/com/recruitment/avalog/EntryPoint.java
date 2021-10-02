package com.recruitment.avalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * Class responsible for starting application
 *
 * @author  MrDanTan
 */
@SpringBootApplication
@ComponentScan({"com.recruitment.avalog.controller",
                "com.recruitment.avalog.service",
                "com.recruitment.avalog.dao"})
@EntityScan("com.recruitment.avalog.entity")
public class EntryPoint {

    public static void main( String[] args ) {
        SpringApplication.run(EntryPoint.class);
    }

}
