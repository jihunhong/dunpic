package com.oz.dunpic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DemoApplication {

	public static final String APPLICATION_LOCATIONS = "spring.config.location="
					+ "classpath:application.yml,"
					+ "/app/config/dunpic/application-db.yml";

	public static void main(String[] args) {
			   new SpringApplicationBuilder(DemoApplication.class)
						.properties(APPLICATION_LOCATIONS)
						.run(args);
			   
	}

}

