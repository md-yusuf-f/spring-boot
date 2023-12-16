package com.saf.springscheduling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // ensures that a background task executor is created. Without it, nothing gets scheduled.
public class SpringSchedulingApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSchedulingApplication.class, args);
	}

}
