package com.skkuwhere.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SkkuwhereApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkkuwhereApplication.class, args);
	}
}
