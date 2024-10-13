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

// La anotación @SpringBootApplication es una anotación de conveniencia que agrega todas las siguientes anotaciones:
// @Configuration: Marca la clase como fuente de definiciones de beans.
// @EnableAutoConfiguration: Le dice a Spring Boot que comience a agregar beans basados en la configuración de las clases de ruta.
// @ComponentScan: Le dice a Spring que busque otras componentes, configuraciones y servicios en el paquete com/example, permitiendo que encuentre los controladores.
@SpringBootApplication
public class DemoApplication implements ApplicationRunner {

	// La anotación @Autowired se utiliza para la inyección automática de dependencias en Spring.
	// En este caso, se inyectan instancias de DatosService y ExponencialService.
	@Autowired
	private DatosService datosService;
	@Autowired
	private ExponencialService exponencialService;

	// El método principal que se utiliza para iniciar la aplicación Spring Boot.
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	// El método run() se ejecutará después de que la aplicación Spring Boot se haya iniciado.
	@Override
	public void run(ApplicationArguments args) throws Exception {

		datosService.loadCSVToDatabase("src/main/resources/distribucion_normal.csv");
		exponencialService.loadCSVToDatabase("src/main/resources/distribucion_exponencial.csv");
		openBrowser("http://localhost:8080/MenuPrincipal.html");


		// Cambiamos a un pool de hilos más dinámico para manejar mejor la concurrencia.
		ExecutorService executorService = Executors.newCachedThreadPool();

		// Futuro para cargar los datos desde el CSV.
		// Creación de un ExecutorService con un solo hilo.
		ExecutorService executorService = Executors.newSingleThreadExecutor();

		// Carga de datos desde archivos CSV a la base de datos utilizando el ExecutorService.

		Future<?> loadFuture = executorService.submit(() -> {
			datosService.loadCSVToDatabase("distribucion_normal.csv");
			exponencialService.loadCSVToDatabase("distribucion_exponencial.csv");
		});


		// Creamos el menú interactivo.
		// Creación de un Scanner para leer la entrada del usuario.

		Scanner scanner = new Scanner(System.in);
		int opcion = 0;
		do {
			// Presentación del menú al usuario.
			System.out.println("Elige una opción:");
			System.out.println("1. Imprimir datos normales");
			System.out.println("2. Imprimir datos exponenciales");
			System.out.println("3. Salir");
			opcion = scanner.nextInt();
			ServiceFactory serviceFactory = null;
			switch (opcion) {
				case 1:

					// Imprimimos los datos normales y simulamos las bolas.
					future = datosService.printDatos();
					break;
				case 2:
					// Imprimimos los datos exponenciales.
					future = exponencialService.printExponencial();

					// Creación de una fábrica de servicios para DatosService.
					serviceFactory = new DatosServiceFactory(datosService);
					break;
				case 2:
					// Creación de una fábrica de servicios para ExponencialService.
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
				future.get(); // Esperamos a que el hilo termine antes de seguir.
			}
		} while (opcion != 3);


		// Apagamos los servicios al final.

			if (serviceFactory != null) {
				// Ejecución del servicio obtenido de la fábrica de servicios.
				Future<?> printFuture = executorService.submit(serviceFactory.getService());
				printFuture.get(); // Espera a que se complete la tarea de impresión
			}
		} while (opcion != 3);

		// Cierre de los ExecutorService.
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