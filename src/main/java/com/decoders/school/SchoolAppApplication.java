package com.decoders.school;

import com.decoders.school.config.DatabaseSeeder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import java.time.LocalDateTime;
import java.util.TimeZone;

@SpringBootApplication
public class SchoolAppApplication {

    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(SchoolAppApplication.class, args);

        Environment env = context.getBean(Environment.class);

        if (env.getProperty("seed_database").trim().equals("true")) {
            DatabaseSeeder databaseSeeder = context.getBean(DatabaseSeeder.class);
            databaseSeeder.seed();
        }
    }

}
