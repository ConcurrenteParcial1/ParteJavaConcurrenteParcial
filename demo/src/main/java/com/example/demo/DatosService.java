package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@Service
public class DatosService {

    @Autowired
    private DatosRepository datosRepository;

    private final ExecutorService executor;
    private final Semaphore semaphore;

    public DatosService() {
        this.executor = Executors.newFixedThreadPool(5);
        this.semaphore = new Semaphore(1);
    }

    public List<Datos> getAllValores() {
        return datosRepository.findAll();
    }

    public Datos saveValor(Datos datos) {
        return datosRepository.save(datos);
    }

    public void loadCSVToDatabase(String csvFile) {
        String line;
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] conjunto = line.split(cvsSplitBy);
                CountDownLatch latch = new CountDownLatch(1);
                executor.submit(() -> {
                    try {
                        semaphore.acquire();
                        Datos datos = new Datos();
                        datos.setValue(String.valueOf(conjunto[0]));
                        datosRepository.save(datos);
                        System.out.println(Thread.currentThread().getName() + " - " + datos.getValue());
                        semaphore.release();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        latch.countDown();
                    }
                });
                latch.await();
            }
            System.out.println("finalizacion del llenado");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        executor.shutdown();
    }
}
