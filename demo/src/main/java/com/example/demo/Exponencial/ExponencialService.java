package com.example.demo.Exponencial;

import com.example.demo.Exponencial.Exponencial;
import com.example.demo.Exponencial.ExponencialRepository;
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
public class ExponencialService {
    // La anotación @Autowired se utiliza para la inyección automática de dependencias en Spring.
    // En este caso, se inyecta una instancia de ExponencialRepository.
    @Autowired
    private ExponencialRepository exponencialRepository;

    // ExecutorService para manejar tareas en hilos separados.
    private final ExecutorService loadExecutor;
    private final ExecutorService printExecutor;
    // Semaphore para controlar el acceso a ciertos recursos.
    private final Semaphore loadSemaphore;
    private final Semaphore printSemaphore;

    // Constructor de la clase.
    public ExponencialService() {
        this.loadExecutor = Executors.newFixedThreadPool(5, new CustomThreadFactory("loadthreadpool-1"));
        this.printExecutor = Executors.newFixedThreadPool(5, new CustomThreadFactory("printthreadpool-1"));
        this.loadSemaphore = new Semaphore(1);
        this.printSemaphore = new Semaphore(1);
    }

    // Método para obtener todos los valores de Exponencial.
    public List<Exponencial> getAllValores() {
        return exponencialRepository.findAll();
    }

    // Método para guardar un valor de Exponencial.
    public Exponencial saveValor(Exponencial exponencial) {
        return exponencialRepository.save(exponencial);
    }

    // Método para cargar datos desde un archivo CSV a la base de datos.
    public void loadCSVToDatabase(String csvFile) {
        String line;
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new ClassPathResource(csvFile).getInputStream()))) {
            while ((line = br.readLine()) != null) {
                String[] conjunto = line.split(cvsSplitBy);
                loadExecutor.submit(() -> {
                    try {
                        loadSemaphore.acquire();
                        Exponencial exponencial = new Exponencial();
                        exponencial.setValor(Double.valueOf(conjunto[0]));
                        saveValor(exponencial);
                        loadSemaphore.release();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });
            }
            System.out.println("finalizacion del llenado");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para imprimir los datos.
    public Future<?> printExponencial() {
        List<Exponencial> allValores = getAllValores();
        CountDownLatch latch = new CountDownLatch(allValores.size());
        allValores.forEach(exponencial -> {
            printExecutor.submit(() -> {
                try {
                    printSemaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + " - " + exponencial.getValor());
                    printSemaphore.release();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    latch.countDown();
                }
            });
        });
        return printExecutor.submit(() -> {
            try {
                latch.await();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }

    // Método para cerrar el ExecutorService.
    public void shutdownExecutor() {
        loadExecutor.shutdown();
        printExecutor.shutdown();
    }

    // Método que se ejecuta después de la inicialización del bean.
    // La anotación @PostConstruct indica que este método se debe ejecutar después de la inicialización del bean.
    @PostConstruct
    public void init() {
        System.out.println("Iniciando método init");
        System.out.println("fin llenado exponencial");
    }
}