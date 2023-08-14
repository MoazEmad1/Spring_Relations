package com.example.relations;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories({"com.example.relations.repository"})
public class RelationsApplication {

	public static void main(String[] args) {
		SpringApplication.run(RelationsApplication.class, args);
	}

}
