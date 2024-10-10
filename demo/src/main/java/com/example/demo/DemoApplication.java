package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class DemoApplication implements ApplicationRunner {

	@Autowired
	private DatosService datosService;
	@Autowired
	private ExponencialService exponencialService;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) {
		ExecutorService executorService = Executors.newSingleThreadExecutor();

		executorService.submit(() -> {
			datosService.loadCSVToDatabase("distribucion_normal.csv");
			exponencialService.loadCSVToDatabase("distribucion_exponencial.csv");
		});

		Scanner scanner = new Scanner(System.in);
		int opcion = 0;
		do {
			System.out.println("Elige una opción:");
			System.out.println("1. Imprimir datos normales");
			System.out.println("2. Imprimir datos exponenciales");
			System.out.println("3. Salir");
			opcion = scanner.nextInt();
			switch (opcion) {
				case 1:
					datosService.printDatos();
					break;
				case 2:
					exponencialService.printExponencial();
					break;
				case 3:
					System.out.println("Saliendo...");
					break;
				default:
					System.out.println("Opción no válida");
					break;
			}
		} while (opcion != 3);

		executorService.shutdown();
	}
}