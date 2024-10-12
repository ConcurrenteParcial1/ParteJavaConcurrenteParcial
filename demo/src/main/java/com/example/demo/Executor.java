package com.example.demo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Executor {
    private final ExecutorService executorService;
    private final Semaphore semaphore;

    public Executor(int numThreads, int semaphoreSize) {
        // Creamos un ExecutorService con un pool de hilos de tamaño dinámico.
        this.executorService = Executors.newCachedThreadPool();
        this.semaphore = new Semaphore(semaphoreSize);
    }

    public void execute(Runnable task) {
        executorService.submit(() -> {
            try {
                semaphore.acquire(); // Controlamos la concurrencia con el semáforo.
                task.run(); // Ejecutamos la tarea que representa la simulación de la caída de una bola.
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restauramos el estado de la interrupción.
            } finally {
                semaphore.release(); // Liberamos el semáforo para permitir la ejecución de otro hilo.
            }
        });
    }

    public void shutdown() {
        executorService.shutdown(); // Cerramos correctamente el ExecutorService.
    }
}
