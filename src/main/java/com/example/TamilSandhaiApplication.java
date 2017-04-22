package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootApplication
public class TamilSandhaiApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(TamilSandhaiApplication.class);
	}

	public static void main(String[] args) {

		SpringApplication.run(TamilSandhaiApplication.class, args);
	}


}
