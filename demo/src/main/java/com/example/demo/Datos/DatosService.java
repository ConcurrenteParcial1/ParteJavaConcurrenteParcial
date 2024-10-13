package com.example.demo.Datos;

import com.example.demo.Fabrica.CustomThreadFactory;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.*;

// La anotación @Service indica que esta clase es un componente de servicio en Spring.
@Service
public class DatosService {

    // La anotación @Autowired se utiliza para la inyección automática de dependencias en Spring.
    // En este caso, se inyecta una instancia de DatosRepository.
    @Autowired
    private DatosRepository datosRepository;

    // ExecutorService para manejar tareas en hilos separados.
    private final ExecutorService executor;
    // Semaphore para controlar el acceso a ciertos recursos.
    private final Semaphore semaphore;

    // Constructor de la clase.
    public DatosService() {
        this.executor = Executors.newFixedThreadPool(5, new CustomThreadFactory("pthreadpool-1"));
        this.semaphore = new Semaphore(1);
    }

    // Método para obtener todos los valores de Datos.
    public List<Datos> getAllValores() {
        return datosRepository.findAll();
    }

    // Método para guardar un valor de Datos.
    public Datos saveValor(Datos datos) {
        return datosRepository.save(datos);
    }

    // Método para cargar datos desde un archivo CSV a la base de datos.
    public void loadCSVToDatabase(String csvFile) {
        String line;
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new ClassPathResource(csvFile).getInputStream()))) {
            while ((line = br.readLine()) != null) {
                String[] conjunto = line.split(cvsSplitBy);
                CountDownLatch latch = new CountDownLatch(1);
                executor.submit(() -> {
                    try {
                        semaphore.acquire();
                        Datos datos = new Datos();
                        datos.setValue(String.valueOf(conjunto[0]));
                        saveValor(datos);
                        semaphore.release();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        latch.countDown();
                    }
                });
                latch.await();
            }
            System.out.println("finalizacion del llenadoD");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Método para imprimir los datos.
    public Future<?> printDatos() {
        List<Datos> allValores = getAllValores();
        CountDownLatch latch = new CountDownLatch(allValores.size());
        allValores.forEach(dato -> {
            executor.submit(() -> {
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + " - " + dato.getValue());
                    semaphore.release();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    latch.countDown();
                }
            });
        });
        return executor.submit(() -> {
            try {
                latch.await();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }

    // Método para cerrar el ExecutorService.
    public void shutdownExecutor() {
        executor.shutdown();
    }

    // Método que se ejecuta después de la inicialización del bean.
    // La anotación @PostConstruct indica que este método se debe ejecutar después de la inicialización del bean.
    @PostConstruct
    public void init() {
        System.out.println("Iniciando método init");
        System.out.println("fin llenado normal");
    }
}