package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class DemoApplication {

	private static DatosService DataService;
	private static ExecutorService executorService;

	@Autowired
	public DemoApplication(DatosService DataService) {
		DemoApplication.DataService = DataService;
		DemoApplication.executorService = Executors.newFixedThreadPool(5);
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

		ExecutorServiceManager executorServiceManager = new ExecutorServiceManager(executorService, DataService);
		executorServiceManager.executeTasks();
	}
}