package dev.uedercardoso.snack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class SnackWebApplication extends SpringBootServletInitializer {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(SnackWebApplication.class, args);
	}
	
}
