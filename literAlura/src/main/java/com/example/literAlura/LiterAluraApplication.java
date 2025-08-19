package com.example.literAlura;

import model.EscolhaMenu;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.Scanner;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.literAlura", "model", "services"})
public class LiterAluraApplication {

	public static void main(String[] args) {
		SpringApplication.run(LiterAluraApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(EscolhaMenu executarMenu) {
		return args -> {
			executarMenu.executarMenu();
		};
	}

}
