package com.example.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	private static DatosService DataService;

	@Autowired
	public DemoApplication(DatosService DataService) {
		DemoApplication.DataService = DataService;
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		DataService.loadCSVToDatabase("demo/src/main/resources/distribucion_normal.csv");

		ExecutorServiceManager executorServiceManager = new ExecutorServiceManager(5, 1, DataService);
		executorServiceManager.executeTasks();
	}
}