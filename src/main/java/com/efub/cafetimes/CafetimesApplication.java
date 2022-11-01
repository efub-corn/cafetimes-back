package com.efub.cafetimes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CafetimesApplication {

	public static void main(String[] args) {
		SpringApplication.run(CafetimesApplication.class, args);
	}

}
