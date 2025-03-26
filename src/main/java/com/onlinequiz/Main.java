package com.onlinequiz;

import com.onlinequiz.ui.ConsoleUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public CommandLineRunner run(ConsoleUI consoleUI) {
        return args -> {
            logger.info("Starting Online Quiz Application");
            if (args.length > 0 && args[0].equalsIgnoreCase("console")) {
                logger.info("Running in console mode");
                try {
                    consoleUI.start();
                } catch (Exception e) {
                    logger.error("Fatal error in console mode: {}", e.getMessage(), e);
                }
            } else {
                logger.info("Running in API mode");
            }
        };
    }
}