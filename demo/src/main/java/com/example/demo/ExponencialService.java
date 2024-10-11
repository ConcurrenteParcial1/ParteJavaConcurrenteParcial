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
public class ExponencialService {
    @Autowired
    private ExponencialRepository exponencialRepository;

    private final ExecutorService loadExecutor;
    private final ExecutorService printExecutor;
    private final Semaphore loadSemaphore;
    private final Semaphore printSemaphore;

    public ExponencialService() {
        this.loadExecutor = Executors.newFixedThreadPool(5, new CustomThreadFactory("loadthreadpool-1"));
        this.printExecutor = Executors.newFixedThreadPool(5, new CustomThreadFactory("printthreadpool-1"));
        this.loadSemaphore = new Semaphore(1);
        this.printSemaphore = new Semaphore(1);
    }

    public List<Exponencial> getAllValores() {
        return exponencialRepository.findAll();
    }

    public Exponencial saveValor(Exponencial exponencial) {
        return exponencialRepository.save(exponencial);
    }

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

    public void shutdownExecutor() {
        loadExecutor.shutdown();
        printExecutor.shutdown();
    }

    @PostConstruct
    public void init() {
        System.out.println("Iniciando método init");
        System.out.println("fin llenado exponencial");
    }
}