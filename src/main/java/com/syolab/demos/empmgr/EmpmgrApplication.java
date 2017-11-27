package com.syolab.demos.empmgr;

import com.syolab.demos.empmgr.spring.DatabaseInitializer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
@EnableCaching
public class EmpmgrApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmpmgrApplication.class, args);
	}

	@Bean
	@Profile("local")
	CommandLineRunner commandLineRunner(DatabaseInitializer databaseInitializer) {
		return args -> {
			// Initialize the database for end to end integration testing
			databaseInitializer.populate();
		};
	}
}
