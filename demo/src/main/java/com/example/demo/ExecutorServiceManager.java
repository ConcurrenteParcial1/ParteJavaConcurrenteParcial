package com.example.demo;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.stream.Stream;

public class ExecutorServiceManager {
    private final ExecutorService executorService;
    private final Semaphore semaphore;
    private final DatosService dataService;

    public ExecutorServiceManager(int numThreads, int semaphoreSize, DatosService dataService) {
        this.executorService = Executors.newFixedThreadPool(numThreads);
        this.semaphore = new Semaphore(semaphoreSize);
        this.dataService = dataService;
    }

    public void executeTasks() {
        Stream<Datos> datosStream = dataService.getAllValores().stream();
        datosStream.forEach(dato -> {
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + " - " + dato.getValue());
                    semaphore.release();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        });

        executorService.shutdown();
    }
}