package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	@Autowired
	private DatosService datosService;
	@Autowired
	private ExponencialService exponencialService;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) {
		datosService.loadCSVToDatabase("distribucion_normal.csv");
		exponencialService.loadCSVToDatabase("distribucion_exponencial.csv");

		Scanner scanner = new Scanner(System.in);
		System.out.println("Elige una opción:");
		System.out.println("1. Imprimir datos normales");
		System.out.println("2. Imprimir datos exponenciales");

		int opcion = scanner.nextInt();

		switch (opcion) {
			case 1:
				datosService.printDatos();
				break;
			case 2:
				exponencialService.printExponencial();
				break;
			default:
				System.out.println("Opción no válida");
				break;
		}
	}
}