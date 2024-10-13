package com.example.demo;

import com.example.demo.Datos.DatosService;
import com.example.demo.Datos.DatosServiceFactory;
import com.example.demo.Exponencial.ExponencialService;
import com.example.demo.Exponencial.ExponencialServiceFactory;
import com.example.demo.Fabrica.ServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
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

		datosService.loadCSVToDatabase("distribucion_normal.csv");
		exponencialService.loadCSVToDatabase("distribucion_exponencial.csv");
		openBrowser("http://localhost:8080/MenuPrincipal.html");

		ExecutorService executorService = Executors.newCachedThreadPool();

		Future<?> loadFuture = executorService.submit(() -> {
			datosService.loadCSVToDatabase("distribucion_normal.csv");
			exponencialService.loadCSVToDatabase("distribucion_exponencial.csv");
		});

		Scanner scanner = new Scanner(System.in);
		int opcion = 0;
		Future<?> future = null;
		ServiceFactory serviceFactory = null;
		do {
			System.out.println("Elige una opción:");
			System.out.println("1. Imprimir datos normales");
			System.out.println("2. Imprimir datos exponenciales");
			System.out.println("3. Salir");
			opcion = scanner.nextInt();
			switch (opcion) {
				case 1:
					future = datosService.printDatos();
					serviceFactory = new DatosServiceFactory(datosService);
					break;
				case 2:
					future = exponencialService.printExponencial();
					serviceFactory = new ExponencialServiceFactory(exponencialService);
					break;
				case 3:
					System.out.println("Saliendo...");
					break;
				default:
					System.out.println("Opción no válida");
					break;
			}

			if (future != null) {
				future.get();
			}
		} while (opcion != 3);

		if (serviceFactory != null) {
			Future<?> printFuture = executorService.submit(serviceFactory.getService());
			printFuture.get();
		}

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