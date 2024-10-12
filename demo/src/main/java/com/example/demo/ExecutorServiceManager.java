package com.example.demo;

import java.util.concurrent.ExecutorService;
import java.util.List;

public class ExecutorServiceManager {
    private final ExecutorService executorService;
    private final DatosService datosService;

    public ExecutorServiceManager(ExecutorService executorService, DatosService datosService) {
        this.executorService = executorService;
        this.datosService = datosService;
    }

    public void executeTasks() {
        // Cargamos los datos desde el CSV.
        datosService.loadCSVToDatabase("demo/src/main/resources/distribucion_normal.csv");

        // Obtenemos todos los datos para procesarlos como "bolas".
        List<Datos> datosList = datosService.getAllValores();
        for (Datos dato : datosList) {
            // Ejecutamos la simulación para cada valor de la base de datos.
            executorService.submit(() -> {
                System.out.println("Bola cayendo con valor: " + dato.getValue());
                // Aquí se puede agregar la lógica que envíe el dato a un sistema visual o a una cola.
            });
        }

        // Apagamos el servicio una vez que se completan las tareas.
        executorService.shutdown();
    }
}
