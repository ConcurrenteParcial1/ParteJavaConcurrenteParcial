package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class DatosService {

    @Autowired
    private DatosRepository datosRepository;

    private final ExecutorService executorService = Executors.newFixedThreadPool(10); // Pool de hilos

    // Método para guardar un valor en la base de datos
    public Datos saveValor(Datos datos) {
        return datosRepository.save(datos); // Guardamos el objeto Datos en la base de datos.
    }

    public void loadCSVToDatabase(String filePath) {
        // Lógica para cargar datos desde un archivo CSV a la base de datos.
        System.out.println("Iniciando la carga de datos desde el CSV...");
        // Implementar la lógica de carga aquí.
    }

    public List<Datos> getAllValores() {
        return datosRepository.findAll(); // Obtenemos todos los valores.
    }

    public Future<?> printDatos() {
        // Creamos una tarea para imprimir los datos en paralelo.
        return executorService.submit(() -> {
            List<Datos> datos = datosRepository.findAll();
            datos.forEach(d -> {
                System.out.println("Hilo " + Thread.currentThread().getName() + " - Valor: " + d.getValue());
            });
        });
    }

    public void shutdownExecutor() {
        executorService.shutdown(); // Apagamos el pool de hilos.
    }
}
