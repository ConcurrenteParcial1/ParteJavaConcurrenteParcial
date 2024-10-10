package com.example.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication{

	private static DatosService valorDataService;

	@Autowired
	public DemoApplication(DatosService valorDataService) {
		DemoApplication.valorDataService = valorDataService;
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		valorDataService.loadCSVToDatabase("demo/src/main/resources/distribucion_normal.csv");
	}

}