package com.example.renderfarm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RenderFarmApplication {

    public static void main(String[] args) {
        SpringApplication.run(RenderFarmApplication.class, args);
    }

}
