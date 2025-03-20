package com.canfly.weedingShare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WeedingShareApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeedingShareApplication.class, args);
	}

}
