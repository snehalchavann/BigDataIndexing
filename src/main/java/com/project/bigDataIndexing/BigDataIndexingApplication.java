package com.project.bigDataIndexing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.ShallowEtagHeaderFilter;


@RestController
@SpringBootApplication
public class BigDataIndexingApplication {

	public static void main(String[] args) {
		SpringApplication.run(BigDataIndexingApplication.class, args);
	}

	@Bean
	ShallowEtagHeaderFilter shallowEtagHeaderFilter(){
		return new ShallowEtagHeaderFilter();
	}
}
