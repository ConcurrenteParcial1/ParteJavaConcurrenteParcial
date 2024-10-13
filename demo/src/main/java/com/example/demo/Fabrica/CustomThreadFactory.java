package com.example.demo.Fabrica;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

// Esta clase implementa la interfaz ThreadFactory, que es una interfaz funcional
// utilizada para crear nuevas instancias de Thread.
public class CustomThreadFactory implements ThreadFactory {
    // AtomicInteger es una clase en java.util.concurrent.atomic que proporciona operaciones atómicas en variables,
    // aquí se usa para generar un número único para cada hilo.
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    // Este es el prefijo que se utilizará para el nombre de cada hilo.
    private final String namePrefix;

    // Constructor de la clase CustomThreadFactory.
    // Acepta un argumento que es el prefijo del nombre del hilo.
    public CustomThreadFactory(String namePrefix) {
        this.namePrefix = namePrefix;
    }

    // Este método se utiliza para crear un nuevo hilo.
    // Acepta un objeto Runnable que contiene el código que se ejecutará en el nuevo hilo.
    @Override
    public Thread newThread(Runnable r) {
        // Crea un nuevo hilo con el objeto Runnable proporcionado y un nombre único
        // que se genera utilizando el prefijo proporcionado y el número de hilo.
        return new Thread(r, namePrefix + "-thread-" + threadNumber.getAndIncrement());
    }
}