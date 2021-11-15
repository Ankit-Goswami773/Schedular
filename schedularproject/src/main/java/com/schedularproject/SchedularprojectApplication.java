package com.schedularproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SchedularprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchedularprojectApplication.class, args);
	}

}
