package com.homework.goi.np;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@SpringBootApplication
public class NationalParkIndiaApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(NationalParkIndiaApiApplication.class, args);
	}
	@Bean
	public CommonsMultipartResolver commonsMultipartResolver() {
		return new CommonsMultipartResolver();
	}
}
