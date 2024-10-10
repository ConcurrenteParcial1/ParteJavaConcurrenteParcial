package com.example.demo;

import java.util.concurrent.ExecutorService;

public class ExecutorServiceManager {
    private final ExecutorService executorService;
    private final DatosService dataService;

    public ExecutorServiceManager(ExecutorService executorService, DatosService dataService) {
        this.executorService = executorService;
        this.dataService = dataService;
    }

    public void executeTasks() {
        dataService.loadCSVToDatabase("demo/src/main/resources/distribucion_normal.csv", executorService);
        executorService.shutdown();
    }
}