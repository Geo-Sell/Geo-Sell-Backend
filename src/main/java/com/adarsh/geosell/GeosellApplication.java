package com.adarsh.geosell;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class GeosellApplication {

	public static void main(String[] args) {
		SpringApplication.run(GeosellApplication.class, args);
	}

}
