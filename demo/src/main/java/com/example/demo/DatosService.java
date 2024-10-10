package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;

@Service
public class DatosService {

    @Autowired
    private DatosRepository datosRepository;

    private final Semaphore semaphore = new Semaphore(1);

    public List<Datos> getAllValores() {
        return datosRepository.findAll();
    }

    public Datos saveValor(Datos datos) {
        return datosRepository.save(datos);
    }

    public void loadCSVToDatabase(String csvFile, ExecutorService executorService) {
        String line;
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] conjunto = line.split(cvsSplitBy);
                Datos datos = new Datos();
                datos.setValue(String.valueOf(conjunto[0]));

                executorService.execute(() -> {
                    try {
                        semaphore.acquire();
                        Datos savedDatos = datosRepository.save(datos);
                        System.out.println(Thread.currentThread().getName() + " - " + savedDatos.getValue());
                        semaphore.release();
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
}