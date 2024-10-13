package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
	public void run(ApplicationArguments args) throws Exception {
		datosService.loadCSVToDatabase("src/main/resources/distribucion_normal.csv");
		exponencialService.loadCSVToDatabase("src/main/resources/distribucion_exponencial.csv");
		openBrowser("http://localhost:8080/MenuPrincipal.html");


		// Cambiamos a un pool de hilos más dinámico para manejar mejor la concurrencia.
		ExecutorService executorService = Executors.newCachedThreadPool();

		// Futuro para cargar los datos desde el CSV.
		Future<?> loadFuture = executorService.submit(() -> {
			datosService.loadCSVToDatabase("distribucion_normal.csv");
			exponencialService.loadCSVToDatabase("distribucion_exponencial.csv");
		});

		// Creamos el menú interactivo.
		Scanner scanner = new Scanner(System.in);
		int opcion = 0;
		do {
			System.out.println("Elige una opción:");
			System.out.println("1. Imprimir datos normales");
			System.out.println("2. Imprimir datos exponenciales");
			System.out.println("3. Salir");
			opcion = scanner.nextInt();
			Future<?> future = null;
			switch (opcion) {
				case 1:
					// Imprimimos los datos normales y simulamos las bolas.
					future = datosService.printDatos();
					break;
				case 2:
					// Imprimimos los datos exponenciales.
					future = exponencialService.printExponencial();
					break;
				case 3:
					System.out.println("Saliendo...");
					break;
				default:
					System.out.println("Opción no válida");
					break;
			}
			if (future != null) {
				future.get(); // Esperamos a que el hilo termine antes de seguir.
			}
		} while (opcion != 3);

		// Apagamos los servicios al final.
		datosService.shutdownExecutor();
		exponencialService.shutdownExecutor();
		executorService.shutdown();
	}
		private void openBrowser(String url) {
		String os = System.getProperty("os.name").toLowerCase();
		Runtime runtime = Runtime.getRuntime();

		try {
			if (os.contains("win")) {
			runtime.exec("rundll32 url.dll,FileProtocolHandler " + url);
			} else if (os.contains("mac")) {
			runtime.exec("open " + url);
			} else if (os.contains("nix") || os.contains("nux")) {
			runtime.exec("xdg-open " + url);
			} else {
			System.err.println("Unsupported OS. Please open the following URL manually: " + url);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}