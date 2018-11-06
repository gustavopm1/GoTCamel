package com.github.gustavopm1.gotcamel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class GotcamelApplication {

	public static void main(String[] args) {
		SpringApplication.run(GotcamelApplication.class, args);
	}
}
