package com.example.demo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Executor {
    private final ExecutorService executorService;
    private final Semaphore semaphore;

    public Executor(int numThreads, int semaphoreSize) {
        this.executorService = Executors.newFixedThreadPool(numThreads);
        this.semaphore = new Semaphore(semaphoreSize);
    }

    public void execute(Runnable task) {
        executorService.submit(() -> {
            try {
                semaphore.acquire();
                task.run();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                semaphore.release();
            }
        });
    }

    public void shutdown() {
        executorService.shutdown();
    }
}