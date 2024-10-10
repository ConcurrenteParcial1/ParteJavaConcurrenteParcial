package com.example.demo;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.*;

@Service
public class DatosService {

    @Autowired
    private DatosRepository datosRepository;

    private final ExecutorService executor;
    private final Semaphore semaphore;

    public DatosService() {
        this.executor = Executors.newFixedThreadPool(5, new CustomThreadFactory("pthreadpool-1"));
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

    public void shutdownExecutor() {
        executor.shutdown();
    }

    @PostConstruct
    public void init() {
        System.out.println("Iniciando método init");
        System.out.println("fin llenado normal");
    }
}