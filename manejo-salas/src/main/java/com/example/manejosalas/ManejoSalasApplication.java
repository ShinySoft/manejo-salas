package com.example.manejosalas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan( basePackages = {"com.example.manejosalas"})   
public class ManejoSalasApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManejoSalasApplication.class, args);
		
	}

}
