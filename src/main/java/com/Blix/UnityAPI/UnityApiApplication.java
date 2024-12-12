package com.Blix.UnityAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
public class UnityApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(UnityApiApplication.class, args);
	}


}
