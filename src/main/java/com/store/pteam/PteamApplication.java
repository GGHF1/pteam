package com.store.pteam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PteamApplication {

	public static void main(String[] args) {
		SpringApplication.run(PteamApplication.class, args);
	}

}
