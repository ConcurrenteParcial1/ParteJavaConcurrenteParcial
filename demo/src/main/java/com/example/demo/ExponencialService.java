package com.example.demo;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@Service
public class ExponencialService {
    private final ExponencialRepository exponencialRepository;
    private final ExecutorService executorService = Executors.newFixedThreadPool(5, new CustomThreadFactory("pthreadpool-1"));
    private final Semaphore semaphore = new Semaphore(1);

    @Autowired
    public ExponencialService(ExponencialRepository exponencialRepository) {
        this.exponencialRepository = exponencialRepository;
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
                CountDownLatch latch = new CountDownLatch(1);
                executorService.submit(() -> {
                    try {
                        semaphore.acquire();
                        Exponencial exponencial = new Exponencial();
                        exponencial.setValor(Double.valueOf(conjunto[0]));
                        Exponencial savedExponencial = saveValor(exponencial);
                        System.out.println(Thread.currentThread().getName() + " - " + savedExponencial.getValor());
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
    }

    public void printExponencial() {
        List<Exponencial> allValores = exponencialRepository.findAll();
        allValores.forEach(exponencial -> {
            executorService.submit(() -> {
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + " - " + exponencial.getValor());
                    semaphore.release();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        });
    }

    public void shutdownExecutor() {
        executorService.shutdown();
    }

    @PostConstruct
    public void init() {
        System.out.println("Iniciando método init");

        System.out.println("fin llenado exponencial");
    }
}