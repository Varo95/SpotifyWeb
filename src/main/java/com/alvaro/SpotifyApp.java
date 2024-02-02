package com.alvaro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpotifyApp {

    private static ConfigurableApplicationContext context;

    public static void main(final String[] args) {
        context = SpringApplication.run(SpotifyApp.class, args);
    }

}
